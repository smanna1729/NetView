#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<sys/ioctl.h>
#include<net/if.h>
#include<net/ethernet.h>
#include<linux/ip.h>
#include<linux/tcp.h>
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
	unsigned short int tcp_length;

}PseudoHeader;

unsigned char* CreatePseudoHeaderAndComputeTcpChecksum (struct tcphdr *tcp_header, struct iphdr *ip_header, unsigned char *data,int size) {
  /*The TCP Checksum is calculated over the PseudoHeader + TCP header +Data*/
  /* Find the size of the TCP Header + Data */
  int segment_len = ntohs(ip_header->tot_len) - ip_header->ihl*4; 

  /* Total length over which TCP checksum will be computed */
  int header_len = sizeof(PseudoHeader) + segment_len;

  /* Allocate the memory */
  unsigned char *hdr = (unsigned char *)malloc(header_len);

  /* Fill in the pseudo header first */
  PseudoHeader *pseudo_header = (PseudoHeader *)hdr;

  pseudo_header->source_ip = ip_header->saddr;
  pseudo_header->dest_ip = ip_header->daddr;
  pseudo_header->reserved = 0;
  pseudo_header->protocol = ip_header->protocol;
  pseudo_header->tcp_length = htons(segment_len);	
	
  /* Now copy TCP */
  memcpy((hdr + sizeof(PseudoHeader)), (void *)tcp_header, tcp_header->doff*4);

  /* Now copy the Data */
  memcpy((hdr + sizeof(PseudoHeader) + tcp_header->doff*4), data, size);

  /* Calculate the Checksum */
  tcp_header->check = checksum(hdr, header_len);

  /* Free the PseudoHeader */
//  free(hdr);
  return hdr ;
}

void sentArbitratyPacketToNetwork (struct ethhdr* ethernetHeader,struct iphdr* ipheader,struct tcphdr *tcpheader,char* interface, int totalPacket, int bufferSize, unsigned char *message) {
  /* Socket descriptor */
  int raw, pkt_len ;
  unsigned char *computeHeader;
  unsigned char *data = message;
  /* Buffer to hold the packet */
  unsigned char *packet ;

  /* Create the raw socket */
  raw = rawSocket(ETH_P_ALL);

  /* Bind raw socket to interface */
  bindSocket_Interface (interface, raw, PROTOCOL_TO_FOLLOW) ;

  /* Create PseudoHeader and compute TCP Checksum  */
  computeHeader = CreatePseudoHeaderAndComputeTcpChecksum(tcpheader, ipheader, data, bufferSize);

  /* Packet length = ETH + IP header + TCP header + Data*/
  pkt_len = sizeof(struct ethhdr) + ntohs(ipheader->tot_len);
  printf ( "\n\n%d \n\n",pkt_len ) ;

  /* Allocate memory */
  packet = (unsigned char *)malloc(pkt_len);


  if ( ethernetHeader == NULL) {
		printf ( "\nEthernet emopty\n" ) ;
  }
  if ( ipheader == NULL) {
		printf ( "\nIP emopty\n" ) ;
  }
  if ( tcpheader == NULL) {
		printf ( "\nTCP emopty\n" ) ;
  }
  /* Copy the Ethernet header first */
  memcpy(packet, ethernetHeader, sizeof(struct ethhdr));

  /* Copy the IP header -- but after the ethernet header */
  memcpy((packet + sizeof(struct ethhdr)), ipheader, ipheader->ihl*4);

  /* Copy the TCP header after the IP header */
  memcpy((packet + sizeof(struct ethhdr) + ipheader->ihl*4),tcpheader, tcpheader->doff*4);
	
  /* Copy the Data after the TCP header */
  memcpy((packet + sizeof(struct ethhdr) + ipheader->ihl*4 + tcpheader->doff*4), data, bufferSize);

  /* Inject all the packet to the network */
  while ( totalPacket -- ) {

    /* Send the packet - Checks whether sending is successful or not S*/
    if ( ! injectPacket (raw, packet, pkt_len) ) 
      perror("Error sending packet");
    else  printf("Packet sent successfully\n");
  }

  free(ethernetHeader);
  free(ipheader);
  free(tcpheader);
  free(data);
  free(packet);
  free (computeHeader);

  /* Close the socket */
  close(raw);
  return ;
}
