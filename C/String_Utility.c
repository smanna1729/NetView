#include<stdio.h>
#include<string.h>
#include<stdlib.h>

#include "String_Utility.h"

/*
 *  Function Name : ltrim                                 
 *  Argument : char*
 *  Return Type : char*
 *  Trim all the whitespaces present at the left
 *  from the character stream. 
 */

char* ltrim (char *str) {
  while ( str ) {
    /* remove all the whitespaces until find another character */
    if ( *str != ' ' )
      break ;
    str ++ ;
  }
  return str ;
}

/*
 *  Function Name : rtrim
 *  Argument : char*
 *  Return Type : char*
 *  Trim all the whitespaces present at the right 
 *  from the character stream.  
 */

char* rtrim (char *str) {

  /* calculate the length */
  int length = strlen (str) ;
  while ( length > 0 ) {

    /* trim all the right white spacesuntil find another character */
    if ( *(str+length-1) != ' ' )
      break ;
    length -- ;
  }

  /* write the null character at the end */
  *(str+length) = '\0' ;
  return str ;
}

/*
 *  Function Name : trim                                 
 *  Argument : char*
 *  Return Type : char*
 *  Trim the white spaces from the character stream.
 */

char* trim (char *str){

  /* trim the left whitespaces */
  str = ltrim (str) ;

  /* trim the right whitespaces */
  str = rtrim (str) ;
  return str ;
}
