/*
 *  EthernetHeader Class
 */
public class EthernetHeader {

	/*
	 *  Data Members
	 */
	private String src_ip, dst_ip, protocol ;

        /*
	 *  Default Constructor
	 */
	public EthernetHeader () {}

	/*
	 *  @param src		Source Address (6 bytes)
	 */	
	public void setSourceMACAddress (String source){
		src_ip = source ;
	}

	/*
	 *  @param dst		Destination Address (6 bytes)
	 */
	public void setDestinationMACAddress (String dst){
		dst_ip = dst ;
	}

	/*
	 *  @param proto	Protocol used to send the packet (2 bytes)
	 */
	public void setProtocol (String proto){
		protocol = proto ;
	}

	public String getSourceMACAddress (){
		return src_ip ;
	}

	public String getDestinationMACAddress (){
		return dst_ip ;
	}

	public String getProtocol (){
		return protocol ;
	}

        /*
	 *  Show the details of Each field of the UDP
	 */
	public void showEthernetHeader () {
		System.out.println ( "\n\nEthernet Header :\n" ) ;
		System.out.println ( "\nSource MAC Address : " + src_ip ) ;
		System.out.println ( "\nDestination MAC Address : " + dst_ip ) ;
		System.out.println ( "\nProtocol Used : " + protocol ) ;
	}
}
