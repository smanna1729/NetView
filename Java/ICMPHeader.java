/*
 *  ICMPHeader class.
 *  Defines different elements of the ICMP Header
 *  Internet Control Messaging Protocol is used to carry error, routing 
 *  and control messages and data, and is often considered as a protocol 
 *  of the network layer.
 *  total icmp header length: 8 bytes (=64 bits)
 */ 
public class ICMPHeader {

        /*
	 *  Data Members
	 */
	private int type,code,id, seq, chksum ;

        /*
	 *  Default Constructor
	 */
	public ICMPHeader () {
		chksum = 0 ;
	}

	/*  
         *  Set the message type: ( 1 byte ) 
     	 *  0 - echo reply
     	 *  8 - echo request
	 *  3 - destination unreachable.
	 *
	 * @param ty		Type of the message
  	 */
	public void setType (int ty){
		type = ty ;
	}

	/*  
         *  Significant when sending an error message 
         *  (unreach), and specifies the kind of error. 
         *  again, consult the include file for more. 
         * 
         *  @param num		Set the code the ICMP Packet
         */
	public void setCode (int num){ 
		code = num ;
	}

	/*
	 *  The checksum for the icmp header + data - 2 bytes
	 *
	 * @param checkSum	CheckSum of the datagram
	 */
	public void setCheckSum (int checkSum){ 
		chksum = checkSum ;
	}

	/* 
	 * The next 32 bits in an icmp packet can be used in many different ways. 
	 * This depends on the icmp type and code. the most commonly seen structure, 
	 * an ID and sequence number, is used in echo requests and replies 
	 * The following data structures are ICMP type specific 
	 */

	/*
	 * Used in echo request/reply messages, to identify 
	 * the request - 2 Bytes
	 *
	 * @param ID		ID of the datagram
	 */

	public void setID (int ID){ 
		id = ID ;
	}

	/*
	 * Sequence number associated with ICMP Packet
	 * identifies the sequence of echo messages, if more 
         * than one is sent. - 2 Bytes
	 *
	 * @param num		sequence number of the packet
	 */
	public void setSequenceNumber (int num){
		seq = num ;
	}

	/*
	 * @return type		the type of the ICMP Packet
	 */
	public int getType (){
		return type ;
	}

	/*
	 * @return code		the code of the ICMP Packet
	 */
	public int getCode (){ 
		return code ;
	}

	/*
	 * @return id 		the id of the ICMP Packet
	 */
	public int getID (){ 
		return id ;
	}

	/*
	 * @return chksum	the checksum associated with the packet
	 */
	public int getCheckSum (){ 
		return chksum ;
	}

	/*
	 * @return seq		the sequence number of the packet
	 */
	public int getSequenceNumber (){
		return seq ;
	}

	/*
	 * Show the ICMP Packet components
	 */
	public void showICMPHeader () {
		System.out.println ( "\nICMP Header :\n" ) ;
		System.out.println ( "\nType : " + type ) ;
		System.out.println ( "\nCode : " + code ) ;
		System.out.println ( "\nCheck Sum : " + chksum ) ;
		System.out.println ( "\nIdentification : " + id ) ;
		System.out.println ( "\nSequence Number : " + seq ) ;
	}
}
