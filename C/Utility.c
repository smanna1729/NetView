#include<stdio.h>
#include<stdlib.h>

#include "Utility.h"

/*
 *  Function Name : rawSocket                                 
 *  Argument : int
 *  Return Type : int
 *  Get the file name from the user.                            
 */

char* charInputCommand (char *command, int size) {
  char *device ; 
  device = (char *) malloc (sizeof(char) * size) ;
  printf ( "%s", command ) ;
  scanf ( "%[^\n]s", device ) ;
  return device ;
}

/*
 *  Function Name : rawSocket                                 
 *  Argument : int
 *  Return Type : int
 *  Get the file name from the user.                            
 */

int checkRange (int data, int max, int min) {
  if ( data < min ) {
    printf ( "\nBad Parameters !! Lower range value violation..\nLower Range value : %d", min ) ;
    return -1 ;
  }
  else if ( data > max ) {
    printf ( "\nBad Parameters !! Higher range value violation..\nHigher Range value : %d", max ) ;
    return -1 ;
  }
  return data ;
}

/*
 *  Function Name : rawSocket                                 
 *  Argument : int
 *  Return Type : int
 *  Get the file name from the user.                            
 */

int intInputCommand (char *command) {
  int input ;
  printf ( "\n%s\t", command ) ;
  scanf ( "%d", &input ) ;
  return input ;
}

/*
 *  Function Name : rawSocket                                 
 *  Argument : int
 *  Return Type : int
 *  Get the file name from the user.                            
 */

int getTotalPacketNumber () {
  int count = intInputCommand ( "\nEnter the total number of packets to be sent :" ) ;
  if ( checkRange (count, 65536, 0) == -1 )
    return getTotalPacketNumber () ;
  return count ;
}

/*
 *  Function Name : rawSocket                                 
 *  Argument : int
 *  Return Type : int
 *  Get the file name from the user.                            
 */

int getPacketBufferSize () {
  int buff_size = intInputCommand ( "\nEnter the packet buffer size :" ) ;
  if ( checkRange (buff_size, 65536, 1) == -1 )
    return getPacketBufferSize () ;
  return buff_size ;
}

/*
 *  Function Name : rawSocket                                 
 *  Argument : int
 *  Return Type : int
 *  Get the file name from the user.                            
 */

int validMACAddress (char *macAddr) {
  char* macAddress = macAddr ;
  int colonCount = 0 ;
  while ( *macAddress != '\0' ) {
    if ( *macAddress == ':' && colonCount < 5 )
      ++ colonCount ;
    else if ( (*macAddress <= 'f' && *macAddress >= 'a') || ( *macAddress >= 'A' && *macAddress <= 'F' ) || (*macAddress >= '0' && *macAddress <= '9' )) ;
    else {
      printf ( "\n\nWrong Address : character :\t %c" , *macAddress ) ;
      return 0 ;
    }
    macAddress ++ ;
  }
  if ( colonCount != 5 )
    return 0 ;
  return 1 ;
}
