#ifndef  __UTILITY__
#define  __UTILITY__

#include<linux/if_ether.h>
int rawSocket (int );
int bindSocket_Interface (char *, int, int) ;
int injectPacket (int, unsigned char *, int );
struct ethhdr* createEthernetHeader (char *, char *, int );

#endif
