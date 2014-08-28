#include<stdio.h>
#include<stdlib.h>
#include<sys/socket.h>
#include<features.h>
#include<linux/if_packet.h>
#include<linux/if_ether.h>
#include<errno.h>
#include<sys/ioctl.h>
#include<net/if.h>
#include<string.h>

#include "SocketUtility.h"

/*
 *  Function Name : rawSocket                                 
 *  Argument : int
 *  Return Type : int
 *  Get the file name from the user.                            
 */

int rawSocket (int req_protocol) {
  int rawsocket;

  if( ( rawsocket = socket (PF_PACKET, SOCK_RAW, htons(req_protocol)) ) == -1 ) {
    perror("Raw Socket Cannot be created !!\nReason : ");
    exit(-1);
  }
  return rawsocket;
}

 
/*
 *  Function Name : bindSocket_Interface                                 
 *  Argument : char*, int, int
 *  Return Type : int
 *  Get the file name from the user.                            
 */

int bindSocket_Interface (char *interface, int rawsocket, int protocol) {
  struct sockaddr_ll saddr;
  struct ifreq ifr;

  /* Writes zero-valued bytes within the sizes of the packets */
  bzero (&saddr, sizeof(saddr)) ;
  bzero (&ifr, sizeof(ifr)) ;
	
  /* First Get the Interface Index  */
  strncpy ((char *)ifr.ifr_name, interface, IFNAMSIZ) ;
  if ( (ioctl(rawsocket, SIOCGIFINDEX, &ifr)) == -1 ) {
    printf("Error getting Interface index !\n");
    exit(-1);
  }

  /* Bind our raw socket to this interface */
  saddr.sll_family = AF_PACKET;
  saddr.sll_ifindex = ifr.ifr_ifindex;
  saddr.sll_protocol = htons(protocol); 

  /* Build raw socket to interface */
  if ( ( bind(rawsocket, (struct sockaddr *)&saddr, sizeof(saddr))) == -1 ) {
    perror("Error binding raw socket to interface\n");
    exit(-1);
  }
  return 1;
}


/*
 *  Function Name : injectPacket                                 
 *  Argument : int, unsigned char*, int
 *  Return Type : int
 *
 *  Inject the packet of specified length into the network.
 */

int injectPacket (int rawsocket, unsigned char *packet, int pkt_len) {
  int sent_pkt_len;
  printf ("Length of packet to be sent: %d\n", pkt_len);

  /* A simple write on the socket ..thats all it takes ! */
  if ( (sent_pkt_len = write(rawsocket, packet, pkt_len)) != pkt_len ) {

    /* Error : The whole packet was not sent to the network */
    printf("Could only send %d bytes of packet of length %d\n", sent_pkt_len, pkt_len);
    return 0;
  }
  /* Packet Sending Successful */
  return 1;
}

/* Ripped from Richard Stevans Book */
unsigned short checksum(unsigned char *header, int len) {
  long sum = 0;  /* assume 32 bit long, 16 bit short */
  unsigned short *ip_header = (unsigned short *)header;

  while (len > 1) {
    sum += *((unsigned short*) ip_header)++;
    if(sum & 0x80000000)   /* if high order bit set, fold */
       sum = (sum & 0xFFFF) + (sum >> 16);
    len -= 2;
  }

  if(len)       /* take care of left over byte */
    sum += (unsigned short) *(unsigned char *)ip_header;
          
  while ( sum >> 16 )
    sum = (sum & 0xFFFF) + (sum >> 16) ;
  return ~sum;
}

/*
 *  Function Name : createEthernetHeader                                 
 *  Argument : char*, char*, int
 *  Return Type : struct ethhdr*
 *
 *  Create the Ethernet header using the provided source Mac Address,
 *  destination Mac Address, Protocol. Returns the structure containing
 *  source & destination MAC Address and protocol used arranged in 
 *  network byte order.
 */

struct ethhdr* createEthernetHeader (char *src_mac, char *dst_mac, int protocol) {
  struct ethhdr* ethernetHeader;
  ethernetHeader = (struct ethhdr *) malloc (sizeof(struct ethhdr)) ;

  /* copy the destination MAC address */
  memcpy (ethernetHeader->h_dest, (void *) ether_aton (dst_mac), 6 ) ;

  /* copy the source MAC address */
  memcpy (ethernetHeader->h_source, (void *) ether_aton (src_mac), 6 ) ;

  /* copy the protocol */
  ethernetHeader->h_proto = htons(protocol) ;

  return ethernetHeader ;
}
