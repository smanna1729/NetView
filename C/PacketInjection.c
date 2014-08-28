#include<stdio.h>
#include<stdlib.h>
#include<linux/if_ether.h>
#include<errno.h>
#include<string.h>

#define PROTOCOL_TO_FOLLOW ETH_P_IP

/*
 *  Function Name : sentArbitratyPacketToNetwork                                 
 *  Argument : char*, int, int, unsigned char*
 *  Return Type : void
 *  Send arbitrary packet (arbitrary size) to network containing 
 *  random or specified message. The number of packets to be sent 
 *  (All of equal size) is denoted by totalPacket.
 */

void sentArbitratyPacketToNetwork (char* interface, int totalPacket, int bufferSize, unsigned char *message) {
  /* Socket descriptor */
  int raw;

  /* Buffer to hold the packet */
  unsigned char *packet = message ;

  /* Create the raw socket */
  raw = rawSocket (PROTOCOL_TO_FOLLOW) ;

  /* Bind raw socket to interface */
  bindSocket_Interface (interface, raw, PROTOCOL_TO_FOLLOW) ;

  /* Inject all the packet to the network */
  while ( totalPacket -- ) {

    /* Send the packet - Checks whether sending is successful or not S*/
    if ( ! injectPacket (raw, packet, bufferSize) ) 
      perror("Error sending packet");
    else  printf("Packet sent successfully\n");
  }

  /* Close the socket */
  close(raw);

  /* free the allocated buffer */
//  free (packet) ;
}

