#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include "ArbitSocketGUI.h"

JNIEXPORT void JNICALL Java_ArbitSocketGUI_sendMessageToNetwork
  (JNIEnv *env, jobject obj, jstring device, jint tot_pkt, jint pkt_len, jstring message){
	unsigned char* msg ;
 	char *interface ;
	int itr_len =  (*env)->GetStringLength(env, device);
	int msg_len =  (*env)->GetStringLength(env, message);
	interface = (unsigned char *) malloc (sizeof (char) * (itr_len+1)) ;
	msg = (unsigned char *) malloc (sizeof (char) * (msg_len+1)) ;
	(*env)->GetStringUTFRegion(env, device, 0, itr_len, interface);
	(*env)->GetStringUTFRegion(env, message, 0, msg_len, msg);
	*(interface+itr_len) = '\0';
  	*(msg+msg_len) = '\0' ;

 	sentArbitratyPacketToNetwork (interface, tot_pkt, pkt_len, message);
}
