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

#include "SendPacket.h"

struct tcphdr *createTcpHeader(int src_port, int dst_port, int seq, int ack, int d_offset, int urg, int ackk, int psh, int rst, int syn, int fin, int window, int urg_ptr) {
  struct tcphdr *tcp_header;

  /* Check /usr/include/linux/tcp.h for header definiation */
  tcp_header = (struct tcphdr *)malloc(sizeof(struct tcphdr));

  tcp_header->source = htons(src_port);
  tcp_header->dest = htons(dst_port);
  tcp_header->seq = htonl(seq);
  tcp_header->ack_seq = htonl(ack);
  tcp_header->res1 = 0;
  tcp_header->doff = (sizeof(struct tcphdr))/4;
  tcp_header->syn = syn;
  tcp_header->window = htons(window);
  tcp_header->check = 0; /* Will calculate the checksum with pseudo-header later */
  tcp_header->urg_ptr = urg_ptr;
  return tcp_header;
}

struct iphdr *createIPHeader (int tos, int id, int flag, int offset, int ttl, char* src_ip, char* dst_ip, int datasize) {
	struct iphdr *ip_header;

	ip_header = (struct iphdr *)malloc(sizeof(struct iphdr));

	ip_header->version = 4;
	ip_header->ihl = (sizeof(struct iphdr))/4 ;
	ip_header->tos = tos;
	ip_header->tot_len = htons( sizeof(struct iphdr) + sizeof(struct tcphdr) + datasize);
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

JNIEXPORT void JNICALL Java_SendPacket_sendMessageToNetwork_1TCP
  (JNIEnv *env, jobject object, jstring src, jstring dst, jstring type, jint tos, jint id, jint flag, jint offset, jint ttl, jstring proto, jstring src_ip, jstring dst_ip, jint src_port, jint dst_port, jint seq, jint ack, jint d_offset, jint urg, jint ackk, jint psh, jint rst, jint syn, jint fin, jint window, jint urg_ptr, jstring device, jint totalPacket, jint bufferSize, jstring message) {

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
	struct tcphdr *tcpheader ;

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

	tcpheader = createTcpHeader (src_port, dst_port, seq, ack, d_offset, urg, ackk, psh, rst, syn, fin, window, urg_ptr);

	interface = (unsigned char *) malloc (sizeof (char) * (itr_len+1)) ;
	msg = (unsigned char *) malloc (sizeof (char) * (msg_len+1)) ;
	(*env)->GetStringUTFRegion(env, device, 0, itr_len, interface);
	(*env)->GetStringUTFRegion(env, message, 0, msg_len, msg);
	*(interface+itr_len) = '\0';
  	*(msg+msg_len) = '\0' ;
	sentArbitratyPacketToNetwork (ethernetHeader, ipheader,tcpheader, interface, totalPacket, bufferSize, msg);
}
