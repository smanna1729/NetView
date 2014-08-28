#include <jni.h>
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

#include "SendPacket.h"

struct udphdr *createUDPHeader(int src_port, int dst_port) {
  struct udphdr *udp_header;

  udp_header = (struct udphdr *)malloc(sizeof(struct udphdr));

  udp_header->source = htons (src_port);
  udp_header->dest = htons (dst_port);
  udp_header->len = htons (sizeof(struct udphdr)) ;
//  printf ( "\nHeader len = %d", ntohs(udp_header->len) ) ;
  udp_header->check = 0 ;
  return udp_header;
}

struct iphdr *createIPHeader (int tos, int id, int flag, int offset, int ttl, char* src_ip, char* dst_ip, int datasize) {
	struct iphdr *ip_header;

	ip_header = (struct iphdr *)malloc(sizeof(struct iphdr));

	ip_header->version = 4;
	ip_header->ihl = (sizeof(struct iphdr))/4 ;
	ip_header->tos = tos;
	ip_header->tot_len = htons( sizeof(struct iphdr) + sizeof(struct udphdr) + datasize);
	ip_header->id = htons(id);
	ip_header->frag_off = offset;
	ip_header->ttl = ttl;
	ip_header->protocol = IPPROTO_UDP;
	ip_header->check = 0; /* We will calculate the checksum later */
	ip_header->saddr = inet_addr(src_ip);
	ip_header->daddr = inet_addr(dst_ip);

	/* Calculate the IP checksum now : 
	   The IP Checksum is only over the IP header */

	ip_header->check = checksum((unsigned char *)ip_header, ip_header->ihl*4);

	return (ip_header);
}

JNIEXPORT void JNICALL Java_SendPacket_sendMessageToNetwork_1UDP
  (JNIEnv *env, jobject object, jstring src, jstring dst, jstring type, jint tos, jint id, jint flag, jint offset, jint ttl, jstring proto, jstring src_ip, jstring dst_ip, jint src_port, jint dst_port, jstring device, jint totalPacket, jint bufferSize, jstring message) {

	char *src_mac, *dst_mac, *src_ip_addr, *dst_ip_addr ;
	unsigned char* msg ;
 	char *interface ;
	int src_len = (*env)->GetStringLength(env, src);
	int dst_len = (*env)->GetStringLength(env, dst);
	int src_ip_len = (*env)->GetStringLength(env, src_ip);
	int dst_ip_len = (*env)->GetStringLength(env, dst_ip);	
	int itr_len =  (*env)->GetStringLength(env, device);
	int msg_len =  (*env)->GetStringLength(env, message);

	struct ethhdr* ethernetHeader;
	struct iphdr* ipheader ;
	struct udphdr *udpheader ;

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

	ipheader = createIPHeader (tos, id, flag, offset, ttl, src_ip_addr, dst_ip_addr, bufferSize) ;

	udpheader = createUDPHeader(src_port, dst_port) ;

	interface = (unsigned char *) malloc (sizeof (char) * (itr_len+1)) ;
	msg = (unsigned char *) malloc (sizeof (char) * (msg_len+1)) ;
	(*env)->GetStringUTFRegion(env, device, 0, itr_len, interface);
	(*env)->GetStringUTFRegion(env, message, 0, msg_len, msg);
	*(interface+itr_len) = '\0';
  	*(msg+msg_len) = '\0' ;
	sentArbitratyPacketToNetwork_UDP (ethernetHeader, ipheader, udpheader, interface, totalPacket, bufferSize, msg);
}
