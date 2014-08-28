#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<sys/ioctl.h>
#include<net/if.h>
#include<net/ethernet.h>
#include<linux/ip.h>
#include<linux/udp.h>
#include<arpa/inet.h>
#include<string.h>
#include<sys/time.h>

#include "Utility.h"
#include "SocketUtility.h"

#define PROTOCOL_TO_FOLLOW ETH_P_IP

typedef struct PseudoHeader{
	unsigned long int source_ip;
	unsigned long int dest_ip;
	unsigned char reserved;
	unsigned char protocol;
	unsigned short int udp_length;
}PseudoHeader;

unsigned char* CreatePseudoHeaderAndComputeUDPChecksum (struct udphdr *udp_header, struct iphdr *ip_header, unsigned char *data, int size) {
  unsigned short sum ;
  /*The udp Checksum is calculated over the PseudoHeader + UDP header +Data*/
  /* Find the size of the udp Header + Data */
  int segment_len = ntohs(ip_header->tot_len) - ip_header->ihl*4; 

  /* Total length over which UDP checksum will be computed */
  int header_len = sizeof(PseudoHeader) + segment_len;

  /* Allocate the memory */
  unsigned char *hdr = (unsigned char *) malloc (header_len);

  /* Fill in the pseudo header first */
  PseudoHeader *pseudo_header = (PseudoHeader *)hdr;

  pseudo_header->source_ip = ip_header->saddr;
  pseudo_header->dest_ip = ip_header->daddr;
//  pseudo_header->reserved = 0;
  pseudo_header->protocol = ip_header->protocol;
  pseudo_header->udp_length = htons(segment_len);	
	
  /* Now copy UDP */
  memcpy((hdr + sizeof(PseudoHeader)), (void *)udp_header, ntohs(udp_header->len) );

  /* Now copy the Data */
  memcpy((hdr + sizeof(PseudoHeader) + udp_header->len), data, size);

  /* Calculate the Checksum */
  if ( (sum=checksum(hdr, header_len)) == 0)
	  udp_header->check = ~sum ;
  else   udp_header->check = 0;

  /* Free the PseudoHeader */
//  free(hdr);
  return hdr ;
}

void sentArbitratyPacketToNetwork_UDP (struct ethhdr* ethernetHeader, struct iphdr* ipheader, struct udphdr *udpheader,char* interface, int totalPacket, int bufferSize, unsigned char *message) {
  /* Socket descriptor */
  int raw, pkt_len ;
  unsigned char *computeHeader;
//  unsigned char *data = message;
  /* Buffer to hold the packet */
  unsigned char *packet ;

  /* Create the raw socket */
  raw = rawSocket(ETH_P_ALL);

  /* Bind raw socket to interface */
  bindSocket_Interface (interface, raw, PROTOCOL_TO_FOLLOW) ;

  /* Create PseudoHeader and compute UDP Checksum  */
  computeHeader = CreatePseudoHeaderAndComputeUDPChecksum(udpheader, ipheader, message, bufferSize);

  /* Packet length = ETH + IP header + UDP header + Data*/
  pkt_len = sizeof(struct ethhdr) + ntohs(ipheader->tot_len);
  printf ( "\n\n%d \n\n",pkt_len ) ;

  printf ( "msg =%s" , message ) ;
  /* Allocate memory */
  packet = (unsigned char *)malloc(pkt_len);

  /* Copy the Ethernet header first */
  memcpy(packet, (unsigned char *)ethernetHeader, sizeof(struct ethhdr));

  /* Copy the IP header -- but after the ethernet header */
  memcpy((packet + sizeof(struct ethhdr)), (unsigned char *)ipheader, ipheader->ihl*4);

  /* Copy the TCP header after the IP header */
  memcpy((packet + sizeof(struct ethhdr) + ipheader->ihl*4),(unsigned char *)udpheader, ntohs(udpheader->len));
	
  /* Copy the Data after the UDP header */
  memcpy((packet + sizeof(struct ethhdr) + ipheader->ihl*4 + ntohs(udpheader->len)), message, bufferSize);

  /* Inject all the packet to the network */
  while ( totalPacket -- ) {

    /* Send the packet - Checks whether sending is successful or not */
    if ( ! injectPacket (raw, packet, pkt_len) ) 
      perror("Error sending packet");
    else  printf("Packet sent successfully\n");
  }

  free(ethernetHeader);
  free(ipheader);
  free(udpheader);
  free(message);
  free(packet);
  free (computeHeader);

  /* Close the socket */
  close(raw);
  return ;
}
