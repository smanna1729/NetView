#include <jni.h>
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

#include "SendIPPacket.h"

#define PROTOCOL_TO_FOLLOW ETH_P_IP

struct iphdr *createIPHeader (int tos, int id, int flag, int offset, int ttl, char* src_ip, char* dst_ip) {
	struct iphdr *ip_header;

	ip_header = (struct iphdr *)malloc(sizeof(struct iphdr));

	ip_header->version = 4;
	ip_header->ihl = (sizeof(struct iphdr))/4 ;
	ip_header->tos = tos;
	ip_header->tot_len = htons(sizeof(struct iphdr));
	ip_header->id = htons(id);
	ip_header->frag_off = offset;
	ip_header->ttl = ttl;
	ip_header->protocol = IPPROTO_TCP;
	ip_header->check = 0; /* We will calculate the checksum later */
	ip_header->saddr = inet_addr(src_ip);
	ip_header->daddr = inet_addr(dst_ip);

	/* Calculate the IP checksum now : 
	   The IP Checksum is only over the IP header */

	ip_header->check = checksum((unsigned char *)ip_header, ip_header->ihl*4);

	return (ip_header);
}

void sentPacketToNetwork ( struct ethhdr* ethernetHeader, struct iphdr* iphdr, char* device) {
	int raw;
	unsigned char *packet;
	unsigned char *ethernet_header;
	unsigned char *ip_header;
	int pkt_len;
	
	/* Create the raw socket */
	raw = rawSocket (PROTOCOL_TO_FOLLOW) ;

	/* Bind raw socket to interface */
	bindSocket_Interface (device, raw, PROTOCOL_TO_FOLLOW) ;

	ethernet_header = (unsigned char *) ethernetHeader ;

	/* Create IP Header */
	ip_header = (unsigned char *) iphdr;
	
	/* Packet length = ETH + IP header */
	pkt_len = sizeof(struct ethhdr) + sizeof(struct iphdr);

	/* Allocate memory */
	packet = (unsigned char *)malloc(pkt_len);

	/* Copy the Ethernet header first */
	memcpy(packet, ethernet_header, sizeof(struct ethhdr));

	/* Copy the IP header -- but after the ethernet header */
	memcpy((packet + sizeof(struct ethhdr)), ip_header, sizeof(struct iphdr));

  	// send the packet on the wire 
  	if ( !injectPacket(raw, packet, pkt_len) ) 
    		perror("Error sending packet");
  	else  printf("Packet sent successfully\n");

	/* Free the headers back to the heavenly heap */
	free(ethernet_header);
	free(ip_header);
	free(packet);

	close(raw);
}

JNIEXPORT void JNICALL Java_SendIPPacket_sendMessageToNetwork_1IP
  (JNIEnv *env, jobject object, jstring src, jstring dst, jstring type, jint tos, jint id, jint flag, jint offset, jint ttl, jstring proto, jstring src_ip, jstring dst_ip, jstring device) {

	char *src_mac, *dst_mac, *src_ip_addr, *dst_ip_addr ;
 	char *interface ;
	struct ethhdr* ethernetHeader;
	struct iphdr* ipheader ;

	int src_len = (*env)->GetStringLength(env, src);
	int dst_len = (*env)->GetStringLength(env, dst);
	int src_ip_len = (*env)->GetStringLength(env, src_ip);
	int dst_ip_len = (*env)->GetStringLength(env, dst_ip);	
	int itr_len =  (*env)->GetStringLength(env, device);

	src_mac = (char *) malloc (sizeof (char) * (src_len+1)) ;
	dst_mac = (char *) malloc (sizeof (char) * (dst_len+1)) ;
	(*env)->GetStringUTFRegion(env, src, 0, src_len, src_mac);
	(*env)->GetStringUTFRegion(env, dst, 0, dst_len, dst_mac);
	*(src_mac+src_len) = '\0';
  	*(dst_mac+dst_len) = '\0' ;

	ethernetHeader = (struct ethhdr*)createEthernetHeader (src_mac, dst_mac, ETHERTYPE_IP);

	src_ip_addr = (char *) malloc (sizeof (char) * (src_ip_len+1)) ;
	dst_ip_addr = (char *) malloc (sizeof (char) * (dst_ip_len+1)) ;
	(*env)->GetStringUTFRegion(env, src_ip, 0, src_ip_len, src_ip_addr);
	(*env)->GetStringUTFRegion(env, dst_ip, 0, dst_ip_len, dst_ip_addr);
	*(src_ip_addr+src_ip_len) = '\0';
  	*(dst_ip_addr+dst_ip_len) = '\0' ;

	ipheader = createIPHeader (tos, id, flag, offset, ttl, src_ip_addr, dst_ip_addr) ;

	interface = (unsigned char *) malloc (sizeof (char) * (itr_len+1)) ;
	(*env)->GetStringUTFRegion(env, device, 0, itr_len, interface);
	*(interface+itr_len) = '\0';
	sentPacketToNetwork (ethernetHeader, ipheader, interface);
}
