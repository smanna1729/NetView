/*
 *  UDPHeader Class
 *  Defines the structure of UDP Header
 *  The User Datagram Protocol is a transport protocol for sessions that need 
 *  to exchange data. UDP provide 65535 different source and destination ports. 
 *  The destination port is used to connect to a specific service on that port. 
 *  UDP is not reliable, since it doesn't use sequence numbers and stateful 
 *  connections. This means UDP datagrams can be spoofed, since they are not 
 *  acknowledged using replies and sequence numbers. 
 *  total udp header length: 8 bytes (=64 bits)
 */
public class UDPHeader {

        /*
	 *  Data Members
	 */
	private int src_port, dst_port, len, chksum ;

        /*
	 *  Default Constructor
	 */
	public UDPHeader () {
		chksum = 0 ;
		len = 8 ;
	}

        /*
	 *  Set the Source Port number of the UDP
	 *  The source port that a client bind()s to, and the 
	 *  contacted server will reply back to in order to direct 
	 *  his responses to the client. - 2 byes 
	 *
	 *  @param port		Port Number
	 */
	public void setSourcePort (int port){
		src_port = port ;
	}

        /*
	 *  Set the Destination Port number of the UDP
	 *  The destination port that a specific server can be 
	 *  contacted on. - 2 byes
	 *
	 *  @param port		Port Number
	 */
	public void setDestinationPort (int port){
		dst_port = port ;
	}

        /*
	 *  Set the length of the UDP
	 *  The length of udp header and payload data in bytes.
	 *  2 bytes
	 *
	 *  @param length	length of the Datagram
	 */
	public void setLength (int length){
		len = length ;
	}

        /*
	 *  Set the CheckSum of the UDP
	 *
	 *  @param csum		CheckSum
	 */
	public void setCheckSum (int csum){
		chksum = csum ;
	}

        /*
	 *  Get the Source Port number of the UDP
	 *
	 *  @return src_port	Port Number
	 */
	public int getSourcePort (){
		return src_port ;
	}

        /*
	 *  Get the Destination Port number of the UDP
	 *
	 *  @return dst_port	Port Number
	 */
	public int getDestinationPort (){
		return dst_port;
	}

        /*
	 *  Get the length of the UDP
	 *
	 *  @return len		length of the Datagram
	 */
	public int getLength (){
		return len;
	}

        /*
	 *  Get the CheckSum of the UDP header and data
	 *
	 *  @return chksum	CheckSum
	 */
	public int getCheckSum (){
		return chksum ;
	}

        /*
	 *  Show the details of Each field of the UDP
	 */
	public void showUDPHeader () {
		System.out.println ( "\n\nUDP Header :\n" ) ;
		System.out.println ( "\nSource Port : " + src_port ) ;
		System.out.println ( "\nDestination Port : " + dst_port ) ;
		System.out.println ( "\nLength : " + len ) ;
		System.out.println ( "\nCheckSum : " + chksum ) ;
	}
}
