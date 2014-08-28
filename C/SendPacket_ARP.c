#include <jni.h>
#include<stdio.h>
#include<stdlib.h>
#include<sys/socket.h>
#include<features.h>
#include<net/if.h>
#include<linux/if_packet.h>
#include<linux/if_ether.h>
#include<errno.h>
#include<sys/ioctl.h>
#include<net/ethernet.h>
#include<net/if_arp.h>
#include<arpa/inet.h>
#include<netinet/in.h>
#include<string.h>

#include "SendARPPacket.h"

#define PROTOCOL_TO_FOLLOW ETH_P_IP

typedef struct ARPHeader{
  unsigned short hardware_type;
  unsigned short protocol_type;
  unsigned char hard_addr_len;
  unsigned char prot_addr_len;
  unsigned short opcode;
  unsigned char source_hardware[6];
  unsigned char source_ip[4];
  unsigned char dest_hardware[6];
  unsigned char dest_ip[4];
}ARPHeader;

ARPHeader* createARPHeader (char *src_mac, char *dst_mac, char *src_ip_addr, char *dst_ip_addr) {
  ARPHeader *arp_header;
  in_addr_t temp;

  arp_header = (ARPHeader *) malloc (sizeof(struct ARPHeader)) ;

  /* Fill the ARP header */
  arp_header->hardware_type = htons(ARPHRD_ETHER) ;
  arp_header->protocol_type = htons(ETHERTYPE_IP) ;
  arp_header->hard_addr_len = 6 ;
  arp_header->prot_addr_len = 4 ;
  arp_header->opcode = htons(ARPOP_REPLY) ;
  memcpy (arp_header->source_hardware, (void *)ether_aton(src_mac) , 6) ;
  temp = inet_addr(src_ip_addr) ;
  memcpy (&(arp_header->source_ip), &temp, 4) ;
  memcpy (arp_header->dest_hardware, (void *) ether_aton(dst_mac) , 6) ;
  temp = inet_addr(dst_ip_addr);
  memcpy (&(arp_header->dest_ip), &temp, 4) ;

  return arp_header;
}

void sentArbitratyPacketToNetwork (char* src_mac, char* dst_mac, char* src_ip_addr, char* dst_tp_addr, char* interface) {
  int raw;
  unsigned char *packet;
  struct ethhdr* ethernetHeader;
  ARPHeader *arp_header;
  int pkt_len;

  /* Create the raw socket */
  raw = rawSocket (PROTOCOL_TO_FOLLOW);

  /* Bind raw socket to interface */
  bindSocket_Interface (interface, raw, PROTOCOL_TO_FOLLOW) ;

  /* create Ethernet header */
  ethernetHeader = (struct ethhdr*)createEthernetHeader (src_mac, dst_mac, ETHERTYPE_ARP);

  /* Create ARP header */
  arp_header = createARPHeader (src_mac, dst_mac, src_ip_addr, dst_tp_addr);

  /* Find packet length  */
  pkt_len = sizeof(struct ethhdr) + sizeof(ARPHeader);

  /* Allocate memory to packet */
  packet = (unsigned char *) malloc (pkt_len) ;

  /* Copy the Ethernet header first */
  memcpy (packet, ethernetHeader, sizeof(struct ethhdr)) ;

  /* Copy the ARP header - but after the ethernet header */
  memcpy ((packet + sizeof(struct ethhdr)), arp_header, sizeof(ARPHeader)) ;
	
  /* Send the packet out ! */
  if ( !injectPacket(raw, packet, pkt_len) )
    perror("Error sending packet");
  else  printf("Packet sent successfully\n");

  /* Free the allocated memory used for ethernet_header, arp_header, packet buffer */
  free (ethernetHeader);
  free (arp_header);
  free (packet);

  close(raw);
}

JNIEXPORT void JNICALL Java_SendARPPacket_sendMessageToNetwork_1ARP
  (JNIEnv *env, jobject object, jstring src_mac, jstring dst_mac, jstring src_ip, jstring dst_ip, jstring device){
	char *src_mac_addr, *dst_mac_addr, *src_ip_addr, *dst_ip_addr, *interface ;

	int src_len = (*env)->GetStringLength(env, src_mac);
	int dst_len = (*env)->GetStringLength(env, dst_mac);
	int src_ip_len = (*env)->GetStringLength(env, src_ip);
	int dst_ip_len = (*env)->GetStringLength(env, dst_ip);	
	int itr_len =  (*env)->GetStringLength(env, device);

	src_mac_addr = (char *) malloc (sizeof (char) * (src_len+1)) ;
	dst_mac_addr = (char *) malloc (sizeof (char) * (dst_len+1)) ;
	(*env)->GetStringUTFRegion(env, src_mac, 0, src_len, src_mac_addr);
	(*env)->GetStringUTFRegion(env, dst_mac, 0, dst_len, dst_mac_addr);
	*(src_mac_addr+src_len) = '\0';
  	*(dst_mac_addr+dst_len) = '\0' ;

	src_ip_addr = (char *) malloc (sizeof (char) * (src_ip_len+1)) ;
	dst_ip_addr = (char *) malloc (sizeof (char) * (dst_ip_len+1)) ;
	(*env)->GetStringUTFRegion(env, src_ip, 0, src_ip_len, src_ip_addr);
	(*env)->GetStringUTFRegion(env, dst_ip, 0, dst_ip_len, dst_ip_addr);
	*(src_ip_addr+src_ip_len) = '\0';
  	*(dst_ip_addr+dst_ip_len) = '\0' ;

	interface = (unsigned char *) malloc (sizeof (char) * (itr_len+1)) ;
	(*env)->GetStringUTFRegion(env, device, 0, itr_len, interface);
	*(interface+itr_len) = '\0';

	sentArbitratyPacketToNetwork (src_mac_addr, dst_mac_addr, src_ip_addr, dst_ip_addr, interface);
}

