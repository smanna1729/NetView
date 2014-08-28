/*
 * TCPHeader Class
 * The Transmission Control Protocol is the mostly used transport protocol that provides 
 * mechanisms to establish a reliable connection with some basic authentication, using 
 * connection states and sequence numbers. 
 * total tcp header length: 20 bytes (=160 bits)
 */

public class TCPHeader {

        /*
	 *  Data Members
	 */
	private int src_port, dst_port, seq_num, ack, chksum, urg_ptr, d_offset, reserved, window, urg, ackk, psh, rst, syn, fin ;

        /*
	 *  Default Constructor
	 */
	public TCPHeader () {
		chksum = 0;
		reserved = 0 ;
	}

        /*
	 *  Set the Source Port number of the TCP
	 *
	 *  @param port		Port Number
	 */
	public void setSourcePort (int port){
		src_port = port ;
	}

        /*
	 *  Set the Destination Port number of the TCP
	 *
	 *  @param port		Port Number
	 */
	public void setDestinationPort (int port){ 
		dst_port = port ;
	}

        /*
	 *  Set the sequence number of the TCP
	 *
	 *  @param seq		sequence number of the Datagram
	 */
	public void setSequenceNumber (int seq){ 
		seq_num = seq ;
	}

        /*
	 *  Every packet that is sent and a valid part of a 
         *  connection is acknowledged with an empty TCP 
         *  segment with the ACK flag set and the ack field 
         *  containing the previous the_seq number - 4 bytes
	 *
	 *  @param ackn		Acknowledgement of the Datagram
	 */
	public void setAcknowledgement (int ackn){
		ack = ackn ;
	}

        /*
	 *  Urgent pointer. Only used if the urgent flag is set, 
         *  else zero. It points to the end of the payload data 
         *  that should be sent with priority.
	 *
	 *  @param urgPtr	UrgentPointer to the data
	 */
	public void setUrgentPointer (int urgPtr){ 
		urg_ptr = urgPtr ;
	}

        /*
	 *  Set the Offset inside the packet from where data begins
	 *
	 *  @param dOffset	Start of Data
	 */
	public void setDataOffset (int dOffset){ 
		d_offset = dOffset ;
	}

        /*
	 *  Set the Reserve Bits in TCP
	 *
	 *  @param rev		Reserve bits of the Datagram
	 */
	public void setReservedBits (int rev){ 
		reserved = rev ;
	}

        /*
	 *  Window. The amount of bytes that can be sent 
         *  before the data should be acknowledged with 
         *  an ACK before sending more segments
	 *
	 *  @param ackn		Acknowledgement of the Datagram
	 */
	public void setWindow (int windw){ 
		window = windw ;
	}

	/*  flags: This field consists of six binary flags.
	 *  URG: Urgent.
	 *  ACK: Acknowledgement.
	 *  PSH: Push. The systems IP stack will not buffer
	 *       the segment and forward it to the application immediately 
	 *  RST: Reset. Tells the peer that the connection has been terminated.
	 *  SYN: Synchronization.
	 *  FIN: Final. The connection should be closed. 
	 */
	public void setURG (int on) { urg = on; } 
	public void setACK (int on) { ackk = on; } 
	public void setPSH (int on) { psh = on; } 
	public void setRST (int on) { rst = on; } 
	public void setSYN (int on) { syn = on; } 
	public void setFIN (int on) { fin = on; }

	public int getURG () {return urg;} 
	public int getACK () {return ackk;} 
	public int getPSH () {return psh;} 
	public int getRST () {return rst;} 
	public int getSYN () {return syn;} 
	public int getFIN () {return fin;}

	public int getSourcePort (){
		return src_port ;
	}

	public int getDestinationPort (){ 
		return dst_port ;
	}

	public int getSequenceNumber (){ 
		return seq_num ;
	}

	public int getAcknowledgement (){
		return ack ;
	}

	public int getUrgentPointer (){ 
		return urg_ptr ;
	}

	public int getDataOffset (){ 
		return d_offset ;
	}

	public int getReservedBits (){ 
		return reserved ;
	}

	public int getWindow (){ 
		return window ;
	}

        /*
	 *  Show the details of Each field of the UDP
	 */
	public void showTCPHeader () {
		System.out.println ( "\nTCP Header :\n" ) ;
		System.out.println ( "\nSource Port : " + src_port ) ;
		System.out.println ( "\nDstination Port : " + dst_port ) ;
		System.out.println ( "\nSequence Number : " + seq_num ) ;
		System.out.println ( "\nAcknowledgement Number : " + ack ) ;
		System.out.println ( "\nCheckSum : " + chksum ) ;
		System.out.println ( "\nUrgent Pointer : " + urg_ptr ) ;
		System.out.println ( "\nData Offset : " + d_offset ) ;
		System.out.println ( "\nReserved : " + reserved ) ;
		System.out.println ( "\nWindow : " + window ) ;
		System.out.println ( "\nUrgent Flag : " ) ;
		System.out.print ( (urg==1?"on":"off") ) ;
		System.out.println ( "\nAckowledgement Flag : " ) ;
		System.out.print ( (ackk==1?"on":"off") ) ;
		System.out.println ( "\nPush Flag : " ) ;
		System.out.print ( (psh==1?"on":"off") ) ;
		System.out.println ( "\nReset Flag " ) ;
		System.out.print ( (rst==1?"on":"off") ) ;
		System.out.println ( "\nSynchrinisation Flag" ) ;
		System.out.print ( (syn==1?"on":"off") ) ;
		System.out.println ( "\nFinal Flag : " ) ;
		System.out.print ( (fin==1?"on":"off") ) ;
	}

}
