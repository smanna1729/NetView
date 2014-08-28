#include <jni.h>
#include<stdio.h>
#include<stdlib.h>
#include<errno.h>
#include<sys/ioctl.h>
#include<net/if.h>
#include<net/ethernet.h>
#include<linux/ip.h>
#include<arpa/inet.h>
#include<string.h>

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <linux/icmp.h>
#include <unistd.h>

#include "SendPacket.h"

unsigned short in_cksum(unsigned short *addr, int len)
{
    register int sum = 0;
    u_short answer = 0;
    register u_short *w = addr;
    register int nleft = len;
    /*
     * Our algorithm is simple, using a 32 bit accumulator (sum), we add
     * sequential 16 bit words to it, and at the end, fold back all the
     * carry bits from the top 16 bits into the lower 16 bits.
     */
    while (nleft > 1) {
	  sum += *w++;
	  nleft -= 2;
    }
    /* mop up an odd byte, if necessary */
    if (nleft == 1) {
	  *(u_char *) (&answer) = *(u_char *) w;
	  sum += answer;
    }
    /* add back carry outs from top 16 bits to low 16 bits */
    sum = (sum >> 16) + (sum & 0xffff);		/* add hi 16 to low 16 */
    sum += (sum >> 16);				/* add carry */
    answer = ~sum;				/* truncate to 16 bits */
    return (answer);
}

JNIEXPORT void JNICALL Java_CreateICMPHeader_sendMessageToNetwork_1ICMP
  (JNIEnv *env, jobject object, jstring src, jstring dst, jstring type, jint tos, jint id, jint flag, jint offset, jint ttl, jstring proto, jstring src_ip, jstring dst_ip, jint type_icmp, jint code, jint seq, jint id_icmp) {

	char *src_mac, *dst_mac, *src_ip_addr, *dst_ip_addr ;
	
	int src_len = (*env)->GetStringLength(env, src);
	int dst_len = (*env)->GetStringLength(env, dst);
	int src_ip_len = (*env)->GetStringLength(env, src_ip);
	int dst_ip_len = (*env)->GetStringLength(env, dst_ip);	

	struct ethhdr* ethernetHeader;
	struct iphdr* ipheader ;
	struct iphdr* ip;
  	struct iphdr* ip_reply;
 	struct icmphdr* icmp;

  	struct sockaddr_in connection;
  	unsigned char* packet;
  	unsigned char* buffer;
  	int sockfd;
  	int optval;
  	int addrlen;

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
    
  	/* allocate all necessary memory */
  	ip = (struct iphdr *) malloc (sizeof(struct iphdr));
  	ip_reply = (struct iphdr *) malloc (sizeof(struct iphdr));
  	icmp = (struct icmphdr *) malloc (sizeof(struct icmphdr));
  	packet = (unsigned char *) malloc (sizeof(struct iphdr) + sizeof(struct icmphdr));
  	buffer = (unsigned char *) malloc (sizeof(struct iphdr) + sizeof(struct icmphdr));
  
  	/****************************************************************/
    	ip = (struct iphdr*) packet;
    	icmp = (struct icmphdr*) (packet + sizeof(struct iphdr));
    
    	/*  here the ip packet is set up except checksum */
    	ip->ihl	= 5;
    	ip->version = 4;
    	ip->tos = tos;
    	ip->tot_len = sizeof(struct iphdr) + sizeof(struct icmphdr);
    	ip->id = htons(id);
    	ip->ttl	= ttl ;
    	ip->protocol = IPPROTO_ICMP;
    	ip->saddr = inet_addr(src_ip_addr);
    	ip->daddr = inet_addr(dst_ip_addr);

    
    	if ((sockfd = socket(AF_INET, SOCK_RAW, IPPROTO_ICMP)) == -1) {
		perror("socket");
		exit(EXIT_FAILURE);
    	}
    
    	/* 
     	 * IP_HDRINCL must be set on the socket so that
     	 * the kernel does not attempt to automatically add
     	 * a default ip header to the packet
     	 */
    
    	setsockopt(sockfd, IPPROTO_IP, IP_HDRINCL, &optval, sizeof(int));
    
    	/* here the icmp packet is created also the ip checksum is generated */
     	icmp->type = ICMP_ECHO;
    	icmp->code = code;
    	icmp->un.echo.id = id_icmp;
    	icmp->un.echo.sequence = seq;
    	icmp->checksum = 0;
    	icmp-> checksum = in_cksum((unsigned short *)icmp, sizeof(struct icmphdr));
    
    	ip->check = in_cksum((unsigned short *)ip, sizeof(struct iphdr));
    
    	connection.sin_family = AF_INET;
    	connection.sin_addr.s_addr = inet_addr(dst_ip_addr);
    
    	/* now the packet is sent */
    	sendto(sockfd, packet, ip->tot_len, 0, (struct sockaddr *)&connection, sizeof(struct sockaddr));
    	printf("Sent %d byte packet to %s\n", sizeof(packet), dst_ip_addr);
    
    	/* now we listen for responses */
    	addrlen = sizeof(connection);
    	if (recvfrom(sockfd, buffer, sizeof(struct iphdr) + sizeof(struct icmphdr), 0, (struct sockaddr *)&connection, &addrlen) == -1) {
		perror("recv Error : ");
    	} else {
		printf("Received %d byte reply from %s:\n", sizeof(buffer), dst_ip_addr);
	        ip_reply = (struct iphdr*) buffer;
		printf("ID: %d\n", ntohs(ip_reply->id));
		printf("TTL: %d\n", ip_reply->ttl);
	}
    	close(sockfd);
}
