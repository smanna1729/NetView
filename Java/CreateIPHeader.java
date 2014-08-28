import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import javax.swing.SwingConstants;
import java.io.IOException ;

public class CreateIPHeader extends JFrame implements ActionListener {
	private JTextField version, ihl, tos, tot_len, id, flags, offset, ttl, proto, chksum ;
	private JTextField src_ip[], dst_ip[] ;
	private JButton create, reset ;
	private IPHeader iphdr = null ;
	private EthernetHeader ethHeader ;

	protected JPanel createLabelledField (JComponent component, String str) {
		JPanel innerPanel = new JPanel () ;
		innerPanel.setLayout( new BoxLayout ( innerPanel , BoxLayout.Y_AXIS ) );

		JPanel innerPanel_label = new JPanel () ;
		innerPanel_label.setLayout( new BoxLayout ( innerPanel_label , BoxLayout.X_AXIS ) );

		JLabel label = new JLabel (str, SwingConstants.CENTER) ;
                innerPanel_label.add (label) ;

		innerPanel.add (innerPanel_label) ;
                innerPanel.add (Box.createVerticalStrut(10) );
		innerPanel.add (component) ;
                return innerPanel ;
	}

	protected JPanel createLabelledField (JComponent component, JComponent component_up) {
		JPanel innerPanel = new JPanel () ;
		innerPanel.setLayout( new BoxLayout ( innerPanel , BoxLayout.Y_AXIS ) );
		innerPanel.add (component_up) ;
                innerPanel.add (Box.createVerticalStrut(10) );
		innerPanel.add (component) ;
                return innerPanel ;
	}

	public CreateIPHeader (EthernetHeader ethHdr, String chooseProto) {
		super ( "Create IP Header.." ) ;
		ethHeader = ethHdr ;

		JPanel first2bytes_label = new JPanel ( ) ;
		first2bytes_label.setLayout( new BoxLayout ( first2bytes_label , BoxLayout.X_AXIS ) );
		first2bytes_label.add ( Box.createHorizontalStrut(20) ) ;		
		first2bytes_label.add ( createLabelledField( (version = new JTextField ( 5 )) , "Ver") ) ;
		version.setText ( "IPV4" );
		version.setEditable (false) ;
		first2bytes_label.add ( Box.createHorizontalStrut(2) ) ;
		first2bytes_label.add ( createLabelledField( (ihl = new JTextField ( 5 )) , "H Len") ) ;
		ihl.setText ( "5" ) ;
		ihl.setEditable (false);
		first2bytes_label.add ( Box.createHorizontalStrut(2) ) ;
		first2bytes_label.add ( createLabelledField( (tos = new JTextField ( 9 )) , "TOS") ) ;
		first2bytes_label.add ( Box.createHorizontalStrut(20) ) ;
		first2bytes_label.add ( createLabelledField( (tot_len = new JTextField ( 23 )), "Total Length") ) ;
		tot_len.setText ( "Auto Fill (Now 20)" ) ;
		tot_len.setEditable (false);
		first2bytes_label.add ( Box.createHorizontalStrut(20) ) ;

		JPanel second2bytes_label = new JPanel ( ) ;
		second2bytes_label.setLayout( new BoxLayout ( second2bytes_label , BoxLayout.X_AXIS ) );
		second2bytes_label.add ( Box.createHorizontalStrut(20) ) ;
		second2bytes_label.add ( createLabelledField ( (id = new JTextField ( 26 )), "Identification" ) ) ;
		second2bytes_label.add ( Box.createHorizontalStrut(20) ) ;
		second2bytes_label.add ( createLabelledField ( (flags = new JTextField ( 7 )), "Flags" ) ) ;
		second2bytes_label.add ( Box.createHorizontalStrut(5) ) ;
		second2bytes_label.add ( createLabelledField ( (offset = new JTextField (18)), "Offset Value") ) ;
		flags.setText ( "0-7" );
		second2bytes_label.add ( Box.createHorizontalStrut(20) ) ;

		JPanel third2bytes_label = new JPanel ( ) ;
		third2bytes_label.setLayout( new BoxLayout ( third2bytes_label , BoxLayout.X_AXIS ) );
		third2bytes_label.add ( Box.createHorizontalStrut(20) ) ;
		third2bytes_label.add ( createLabelledField ( (ttl= new JTextField ( 16 )),"Time to live") ) ;
		third2bytes_label.add ( Box.createHorizontalStrut(20) ) ;
		third2bytes_label.add ( createLabelledField ((proto=new JTextField ( chooseProto,16 )), "Protocol" ) ) ;
		proto.setEditable (false) ;
		third2bytes_label.add ( Box.createHorizontalStrut(21) ) ;
		third2bytes_label.add ( createLabelledField ( (chksum=new JTextField ( 37 )), "Header Checksum") ) ;
		chksum.setText ( "Auto Fill - (Computer Generated)" );
		chksum.setEditable (false);			
		third2bytes_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel src_ip_label = new JPanel ( ) ;
		src_ip_label.setLayout( new BoxLayout ( src_ip_label , BoxLayout.X_AXIS ) );
		JLabel src = new JLabel ( "Source IP Address" ) ; 
		src_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		src_ip_label.add ( src ) ;
		src_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel src_label = new JPanel ( ) ;
		src_label.setLayout( new BoxLayout ( src_label , BoxLayout.X_AXIS ) );
		src_ip = new JTextField [4] ;
		src_label.add ( Box.createHorizontalStrut(20) ) ;
		for ( int i = 0 ; i < 3 ; i ++ ) {
		  src_ip[i] = new JTextField () ;
		  src_label.add ( src_ip[i] ) ;
		  src_label.add ( Box.createHorizontalStrut(10) ) ;
		  src_label.add ( new JLabel ( ":" ) ) ;
		  src_label.add ( Box.createHorizontalStrut(10) ) ;
		}
		src_label.add ( (src_ip[3] = new JTextField ()) ) ;
		src_label.add ( Box.createHorizontalStrut(20) ) ;
		  
		JPanel dst_ip_label = new JPanel ( ) ;
		dst_ip_label.setLayout( new BoxLayout ( dst_ip_label , BoxLayout.X_AXIS ) );
		JLabel dst = new JLabel ( "Destination IP Address" ) ; 
		dst_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		dst_ip_label.add ( dst ) ;
		dst_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel dst_label = new JPanel ( ) ;
		dst_label.setLayout( new BoxLayout ( dst_label , BoxLayout.X_AXIS ) );
		dst_ip = new JTextField [4] ;
		dst_label.add ( Box.createHorizontalStrut(20) ) ;
		for ( int i = 0 ; i < 3 ; i ++ ) {
		  dst_ip[i] = new JTextField () ;
		  dst_label.add ( dst_ip[i] ) ;
		  dst_label.add ( Box.createHorizontalStrut(10) ) ;
		  dst_label.add ( new JLabel ( ":" ) ) ;
		  dst_label.add ( Box.createHorizontalStrut(10) ) ;
		}
		dst_label.add ( (dst_ip[3]=new JTextField()) ) ;
		dst_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel buttonPanel = new JPanel ( ) ;
    		buttonPanel.setLayout( new BoxLayout ( buttonPanel , BoxLayout.X_AXIS ) ) ;
    		create = new JButton ( "Create Header") ;
    		create.addActionListener( this ) ;
    		buttonPanel.add( Box.createHorizontalGlue() ) ;
    		buttonPanel.add( create ) ;
  		buttonPanel.add( Box.createHorizontalStrut( 15 ) ) ;
    		reset = new JButton ( "Reset" ) ;
    		reset.addActionListener( this ) ;
	    	buttonPanel.add( reset ) ;
  		buttonPanel.add( Box.createHorizontalStrut( 20 ) ) ;
  		
  		JPanel intermediatePanel = new JPanel ( ) ;
		intermediatePanel.setLayout( new BoxLayout ( intermediatePanel, BoxLayout.Y_AXIS ) ) ;
		intermediatePanel.add( Box.createVerticalStrut( 20 ) ) ;	 	 
		intermediatePanel.add( first2bytes_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;		 
		intermediatePanel.add( second2bytes_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;		 
		intermediatePanel.add( third2bytes_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		intermediatePanel.add ( src_ip_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;		 	
		intermediatePanel.add( src_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;		 	
		intermediatePanel.add( dst_ip_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;		 	
		intermediatePanel.add( dst_label ) ;		
		intermediatePanel.add( Box.createVerticalStrut( 25 ) ) ;		 	
		intermediatePanel.add( buttonPanel ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
	
	    	Container c = getContentPane() ; 
		c.setLayout( new BorderLayout ( ) ) ;
		c.add ( intermediatePanel ) ;
 	
		setResizable ( false ) ;
		setLocation ( 180 , 100 ) ;
		setSize ( 600 , 415 ) ; 
		setVisible ( true ) ;	

	    	setDefaultCloseOperation(EXIT_ON_CLOSE);
		WindowListener wl = new WindowAdapter() {
	  	  public void windowClosing(WindowEvent we) {
	      	((Window) we.getSource()).dispose();
	  	  }
		};
		this.addWindowListener(wl);	   		
	}
	
  	private int mapCharacter (char ch) {
		if (ch >= '0' && ch <= '9')
 			return (ch-'0') ;
		else if (ch >= 'a' && ch <= 'f')
 			return (ch-'a'+10) ;
		else if (ch >= 'A' && ch <= 'F' )
 			return (ch-'A'+10) ;
		return -1;
	}

	private boolean isValidated (String token) {
		if ( token.length() > 3 ) {
			JOptionPane.showMessageDialog (null, "Token length > 3") ;
			return false ;
		}
 		int accm = 0 ;
		for ( int i = 0 ; i < token.length() ; i ++ ) {
			char ch = token.charAt(i) ;
			if ( ( ch <= 'f' && ch >= 'a') || ( ch >= 'A' && ch <= 'F' ) || ( ch >= '0' && ch <= '9' )) 
				accm = accm*10 + mapCharacter(ch);
 			else {
				JOptionPane.showMessageDialog (null, "Invalid character : " + ch) ;
				return false ;
			}
		}
		if ( accm > 256 )
			return false ;
		return true ;
	}

	private boolean isValidNumber (String number, int uprange, int lowrange) {
		try {
			int num = Integer.parseInt (number) ;
			if ( num < lowrange || num >= uprange ) {
				JOptionPane.showMessageDialog (null, "Out of range : Range ( " + lowrange + " to " + uprange + " )" ) ;
				return false ;
			} 
			
		} catch (Exception exp) {
			JOptionPane.showMessageDialog (null, "Wrong Number given : Input String : " + number) ;
			return false ;
		}
		return true ;
	}

	public void actionPerformed (ActionEvent ae) {
		if (ae.getSource() == reset) {
			tos.setText ("") ;
			id.setText ( "" ) ;
			flags.setText ( "0-7" ) ;
			offset.setText ( "" ) ;
			ttl.setText ( "" ) ;
			for ( int i = 0 ; i < 4 ; i ++ ) {
				src_ip[i].setText ( "" ) ;
				dst_ip[i].setText ( "" ) ;
			}
		} /*else if ( ae.getSource() == proto_label ) {
			if (proto_label.getSelectedIndex() == 0)
				proto.setText ( "" ) ;
			else if (proto_label.getSelectedIndex() == 1)
				proto.setText ( "IPPROTO_TCP" ) ;
			else if (proto_label.getSelectedIndex() == 2)
				proto.setText ( "IPPROTO_UDP" ) ;
			else if (proto_label.getSelectedIndex() == 2)
				proto.setText ( "IPPROTO_UDP" ) ;
			else if (proto_label.getSelectedIndex() == 3)
				proto.setText ( "IPPROTO_ICMP" ) ;
		}*/ else if (ae.getSource() == create) {
			String str ;
			if ( (str=tos.getText().trim()).equals("")) {
				JOptionPane.showMessageDialog (null, "Type of Service field has to be filled!");
				return ;	
			} else if ( !isValidNumber(str, (1<<8), 0))  {
				return ;
			} else if (id.getText().trim().equals("")) {
				JOptionPane.showMessageDialog (null, "Identification field has to be filled!");
				return ;	
			} else if (flags.getText().trim().equals("")) {
				JOptionPane.showMessageDialog (null, "Flags field has to be filled!");
				return ;	
			} else if (offset.getText().trim().equals("")) {
				JOptionPane.showMessageDialog (null, "Offset field has to be filled!");
				return ;	
			} else if (ttl.getText().trim().equals("")) {
				JOptionPane.showMessageDialog (null, "Time to live field has to be filled!");
				return ;	
			} else if (proto.getText().trim().equals("")) {
				JOptionPane.showMessageDialog (null, "Protocol field has to be filled!");
				return ;	
			}
			String token_s, token_d ;
			String src_ip_addr = "" , dst_ip_addr = "" ;
			for ( int i = 0 ; i < 4 ; i ++ ) {
				if ( (token_s = src_ip[i].getText().trim()).equals("") ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Source MAC Address is missing!");
					return ;
				} else if ( !isValidated (token_s) ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Source MAC Address is not valid!\nInput token : " + token_s );
					return ;
				} else if ( (token_d = dst_ip[i].getText().trim()).equals("") ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Destination MAC Address is missing!");
					return ;
				} else if ( !isValidated (token_d) ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Destination MAC Address is not valid!\nInput token : " + token_d );
					return ;
				}
				if ( token_s.length() == 1 )
					token_s = "0" + token_s ;
				src_ip_addr = src_ip_addr + token_s + ":" ;
				if ( token_d.length() == 1 )
					token_d = "0" + token_d ;
				dst_ip_addr = dst_ip_addr + token_d + ":" ;
			}
			src_ip_addr = src_ip_addr.substring (0, src_ip_addr.length()-1) ;
			dst_ip_addr = dst_ip_addr.substring (0, dst_ip_addr.length()-1) ;
			iphdr = new IPHeader () ;

			iphdr.setTOS (Integer.parseInt(tos.getText().trim())) ;
			iphdr.setID (Integer.parseInt(id.getText().trim()));
			iphdr.setFlag (Integer.parseInt(flags.getText().trim())) ;
			iphdr.setOffset (Integer.parseInt(offset.getText().trim())) ;
			iphdr.setTimetoLive (Integer.parseInt(ttl.getText().trim())) ;
			iphdr.setProtocol (proto.getText().trim());
			iphdr.setSourceIPAddress (src_ip_addr);
			iphdr.setDestinationIPAddress (dst_ip_addr);
//			iphdr.showIPHeader () ;
			setVisible(false); 
			if ( proto.getText().equals("IPPROTO_TCP") ) {
				CreateTCPHeader tcpHdr = new CreateTCPHeader (ethHeader, iphdr) ; }
			else if ( proto.getText().equals("IPPROTO_UDP") ){
				CreateUDPHeader udpHdr = new CreateUDPHeader (ethHeader, iphdr) ;
			} else if ( proto.getText().equals("IPPROTO_ICMP") ) {
				CreateICMPHeader icmphdr = new CreateICMPHeader (ethHeader, iphdr) ;
			} else if ( proto.getText().equals("IPPROTO_IP") ) {
				SendIPPacket sendpkt = new SendIPPacket (ethHeader, iphdr) ;
			}
		}
	}

	public IPHeader getIPHeader () {
		return iphdr ;
	}

	public static void main (String[] args) {
		CreateIPHeader hdr = new CreateIPHeader (null,"") ;
	}
}
