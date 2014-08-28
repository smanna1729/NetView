/*
 *  IPHeader Class
 *
 *  The Internet Protocol is the network layer protocol, used for routing 
 *  the data from the source to its destination. Every datagram contains 
 *  an IP header.
 *  total ip header length: 20 bytes (=160 bits) 
 */
public class IPHeader {

	/*
	 *  Data Members
	 */
	private String src_ip, dst_ip, protocol ;
	private int version, ihl, tos, tot_len, id, flags, offset, ttl, chksum ;

        /*
	 *  Default Constructor
	 */
	public IPHeader () {
		version = 4 ;
		ihl = 5 ;
		chksum = 0 ;
		offset = 0 ;
		tot_len = 20 ;
	}

	/*
	 *  
	 *  @param ver
	 */
	public void setVersion (int ver){
		version = ver ;
	}

	/*
	 *  For header part it should be considered as 4 bytes counter
	 *
	 *  @param len		Length of the ip header of the packet
	 */
	public void setInternetHeaderLength (int len){ 
		ihl = len ;
	}

	/*
	 *  @param TOS		Terms of service (6 bits used, 2 bits unused)
	 */
	public void setTOS (int TOS){ 
		tos = TOS ;
	}

	/*
	 *  @param len 		Length of this frame (2 bytes)
	 */
	public void setTotalLength (int len){
		tot_len = len ;
	}

	/*  
	 *  @param ID		Sequence Number (2 bytes)
	 */
	public void setID (int ID){ 
		id = ID ;
	}

	/*
	 *  @param flag		Flags associated with ip header (3 bits)
	 */
	public void setFlag (int flag){ 
		flags = flag ;
	}

	/*
	 *  @param  off		Fragment offset of the whole file sending (2 bytes)
	 */
	public void setOffset (int off){ 
		offset = off ;
	}

	/*  
	 *  @param TTL		Time to live (1 byte)
	 */
	public void setTimetoLive (int TTL){ 
		ttl = TTL ;
	}

	/*
	 *  @param proto	Protocol Used (1 byte)
	 */
	public void setProtocol (String proto){
		protocol = proto ;
	}

	/*
	 *  @param checkSum	Check Sum (2 bytes)
	 */
	public void setCheckSum (int checkSum){ 
		chksum = checkSum ;
	}

	/*
	 *  @param src		Source Address (4 bytes)
	 */
	public void setSourceIPAddress (String src){
		src_ip = src ;
	}

	/*
	 *  @param dst		Destination Address (4 bytes)
	 */
	public void setDestinationIPAddress (String dst){
		dst_ip = dst ;
	}


	public int getVersion (){
		return version ;
	}

	public int getInternetHeaderLength (){ 
		return ihl ;
	}

	public int getTOS (){ 
		return tos ;
	}

	public int setTotalLength (){
		return tot_len ;
	}

	public int getID (){ 
		return id ;
	}

	public int getFlag (){ 
		return flags ;
	}

	public int getOffset (){ 
		return offset ;
	}

	public int getTimetoLive (){ 
		return ttl ;
	}

	public String getProtocol (){
		return protocol ;
	}

	public int getCheckSum (){ 
		return chksum ;
	}

	public String getSourceIPAddress (){
		return src_ip ;
	}

	public String getDestinationIPAddress (){
		return dst_ip ;
	}

        /*
	 *  Show the details of Each field of the UDP
	 */
	public void showIPHeader () {
		System.out.println ( "\nIP Header :\n" ) ;
		System.out.println ( "\nVersion : " + version ) ;
		System.out.println ( "\nIP Header Length : " + ihl ) ;
		System.out.println ( "\nType of Services : " + tos ) ;
		System.out.println ( "\nTotal Packet Length : " + tot_len ) ;
		System.out.println ( "\nIdentification : " + id ) ;
		System.out.println ( "\nFlags : " + flags ) ;
		System.out.println ( "\nFragment Offset : " + offset ) ;
		System.out.println ( "\nTime to live : " + ttl ) ;
		System.out.println ( "\nProtocol Used : " + protocol ) ;
		System.out.println ( "\nCheck Sum : " + chksum ) ;
		System.out.println ( "\nSource IP Address : " + src_ip ) ;
		System.out.println ( "\nDestination IP Address : " + dst_ip ) ;
	}
}
