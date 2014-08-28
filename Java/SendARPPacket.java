import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;

public class SendARPPacket extends JFrame implements ActionListener {
	private JTextField protocol, device ;
	private JTextField src_mac[], dst_mac[], src_ip[], dst_ip[] ;
	private JButton send, reset ;

	public SendARPPacket ( ) {
		super ( "Send ARP Packet to network" );
		JPanel src_mac_label = new JPanel ( ) ;
		src_mac_label.setLayout( new BoxLayout ( src_mac_label , BoxLayout.X_AXIS ) );
		JLabel src = new JLabel ( "Source MAC Address" ) ; 
		src_mac_label.add ( Box.createHorizontalStrut(20) ) ;
		src_mac_label.add ( src ) ;
		src_mac_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel src_label = new JPanel ( ) ;
		src_label.setLayout( new BoxLayout ( src_label , BoxLayout.X_AXIS ) );
		src_mac = new JTextField [6] ;
		src_label.add ( Box.createHorizontalStrut(20) ) ;
		for ( int i = 0 ; i < src_mac.length - 1 ; i ++ ) {
		  src_mac[i] = new JTextField () ;
		  src_label.add ( src_mac[i] ) ;
		  src_label.add ( Box.createHorizontalStrut(10) ) ;
		  src_label.add ( new JLabel ( ":" ) ) ;
		  src_label.add ( Box.createHorizontalStrut(10) ) ;
		}
		src_label.add ( (src_mac[src_mac.length-1] = new JTextField ()) ) ;
		src_label.add ( Box.createHorizontalStrut(20) ) ;
		  
		JPanel dst_mac_label = new JPanel ( ) ;
		dst_mac_label.setLayout( new BoxLayout ( dst_mac_label , BoxLayout.X_AXIS ) );
		JLabel dst = new JLabel ( "Destination MAC Address" ) ; 
		dst_mac_label.add ( Box.createHorizontalStrut(20) ) ;
		dst_mac_label.add ( dst ) ;
		dst_mac_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel dst_label = new JPanel ( ) ;
		dst_label.setLayout( new BoxLayout ( dst_label , BoxLayout.X_AXIS ) );
		dst_mac = new JTextField [6] ;
		dst_label.add ( Box.createHorizontalStrut(20) ) ;
		for ( int i = 0 ; i < dst_mac.length-1 ; i ++ ) {
		  dst_mac[i] = new JTextField () ;
		  dst_label.add ( dst_mac[i] ) ;
		  dst_label.add ( Box.createHorizontalStrut(10) ) ;
		  dst_label.add ( new JLabel ( ":" ) ) ;
		  dst_label.add ( Box.createHorizontalStrut(10) ) ;
		}
		dst_label.add ( (dst_mac[dst_mac.length-1] = new JTextField()) ) ;
		dst_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel proto_panel = new JPanel ( ) ;
		proto_panel.setLayout( new BoxLayout ( proto_panel , BoxLayout.X_AXIS ) ) ;
		proto_panel.add ( Box.createHorizontalStrut(20) ) ;
		JLabel proto_label = new JLabel ( "Protocol : " ) ;
		proto_panel.add ( proto_label ) ;
		proto_panel.add ( Box.createHorizontalStrut(85) ) ;
		protocol = new JTextField ( "ETHERTYPE_ARP" ) ;
		protocol.setEditable (false);
		proto_panel.add ( protocol );	
		proto_panel.add ( Box.createHorizontalStrut(20) ) ;

		JPanel src_ip_label = new JPanel ( ) ;
		src_ip_label.setLayout( new BoxLayout ( src_ip_label , BoxLayout.X_AXIS ) );
		JLabel src_i = new JLabel ( "Source IP Address" ) ; 
		src_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		src_ip_label.add ( src_i ) ;
		src_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel s_label = new JPanel ( ) ;
		s_label.setLayout( new BoxLayout ( s_label , BoxLayout.X_AXIS ) );
		src_ip = new JTextField [4] ;
		s_label.add ( Box.createHorizontalStrut(20) ) ;
		for ( int i = 0 ; i < src_ip.length-1 ; i ++ ) {
		  src_ip[i] = new JTextField () ;
		  s_label.add ( src_ip[i] ) ;
		  s_label.add ( Box.createHorizontalStrut(10) ) ;
		  s_label.add ( new JLabel ( ":" ) ) ;
		  s_label.add ( Box.createHorizontalStrut(10) ) ;
		}
		s_label.add ( (src_ip[src_ip.length-1] = new JTextField ()) ) ;
		s_label.add ( Box.createHorizontalStrut(20) ) ;

		JPanel dst_ip_label = new JPanel ( ) ;
		dst_ip_label.setLayout( new BoxLayout ( dst_ip_label , BoxLayout.X_AXIS ) );
		JLabel dst_i = new JLabel ( "Destination IP Address" ) ; 
		dst_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		dst_ip_label.add ( dst_i ) ;
		dst_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel d_label = new JPanel ( ) ;
		d_label.setLayout( new BoxLayout ( d_label , BoxLayout.X_AXIS ) );
		dst_ip = new JTextField [4] ;
		d_label.add ( Box.createHorizontalStrut(20) ) ;
		for ( int i = 0 ; i < dst_ip.length-1 ; i ++ ) {
		  dst_ip[i] = new JTextField () ;
		  d_label.add ( dst_ip[i] ) ;
		  d_label.add ( Box.createHorizontalStrut(10) ) ;
		  d_label.add ( new JLabel ( ":" ) ) ;
		  d_label.add ( Box.createHorizontalStrut(10) ) ;
		}
		d_label.add ( (dst_ip[dst_ip.length-1] = new JTextField()) ) ;
		d_label.add ( Box.createHorizontalStrut(20) ) ;

		JPanel device_panel = new JPanel ( ) ;
		device_panel.setLayout( new BoxLayout ( device_panel , BoxLayout.X_AXIS ) ) ;
		device_panel.add ( Box.createHorizontalStrut(20) ) ;
		JLabel device_label = new JLabel ( "Device : " ) ;
		device_panel.add ( device_label ) ;
		device_panel.add ( Box.createHorizontalStrut(72) ) ;
		device = new JTextField ( ) ;
		device_panel.add ( device );
		device_panel.add ( Box.createHorizontalStrut(20) ) ;

		JPanel buttonPanel = new JPanel ( ) ;
    		buttonPanel.setLayout( new BoxLayout ( buttonPanel , BoxLayout.X_AXIS ) ) ;
    		send = new JButton ( "Send") ;
    		send.addActionListener( this ) ;
    		buttonPanel.add( Box.createHorizontalGlue() ) ;
    		buttonPanel.add( send ) ;
  		buttonPanel.add( Box.createHorizontalStrut( 15 ) ) ;
    		reset = new JButton ( "Reset" ) ;
    		reset.addActionListener( this ) ;
    		buttonPanel.add( reset ) ;    
  		buttonPanel.add( Box.createHorizontalStrut( 20 ) ) ;
  		
  		JPanel intermediatePanel = new JPanel ( ) ;
		intermediatePanel.setLayout( new BoxLayout ( intermediatePanel, BoxLayout.Y_AXIS ) ) ;
		intermediatePanel.add( Box.createVerticalStrut( 20 ) ) ;
		intermediatePanel.add( dst_mac_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;		 	
		intermediatePanel.add( dst_label ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;	 	 
		intermediatePanel.add ( src_mac_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;		 	
		intermediatePanel.add( src_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatePanel.add( proto_panel ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatePanel.add ( src_ip_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;		 	
		intermediatePanel.add( s_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 20 ) ) ;
		intermediatePanel.add( dst_ip_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;
		intermediatePanel.add( d_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatePanel.add( device_panel ) ;
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatePanel.add( buttonPanel ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
	
	    	Container c = getContentPane() ; 
		c.setLayout( new BorderLayout ( ) ) ;
		c.add ( intermediatePanel ) ;
 	
		setResizable ( false ) ;
		setLocation ( 180 , 100 ) ;
		setSize ( 550 , 420 ) ;
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

	private char reverseMapCharacter ( int ch ) {
		if (ch>=0 && ch<= 9)
			return (char)(ch+'0') ;
		if (ch>=10 && ch<=15)
			return (char)(ch-10+'A') ;
		return 'z';
	}

	private String isValidated (String token) {
		if ( token.length() > 3 ) {
			JOptionPane.showMessageDialog (null, "Token length > 3") ;
			return null ;
		}
 		int accm = 0 ;
		for ( int i = 0 ; i < token.length() ; i ++ ) {
			char ch = token.charAt(i) ;
			if ( ( ch <= 'f' && ch >= 'a') || ( ch >= 'A' && ch <= 'F' ) || ( ch >= '0' && ch <= '9' )) 
				accm = accm*16 + mapCharacter(ch);
 			else {
				JOptionPane.showMessageDialog (null, "Invalid character : " + ch) ;
				return null ;
			}
		}
		if ( accm > 256 )
			return null ;
		String str = String.valueOf (reverseMapCharacter(accm/16)) + String.valueOf (reverseMapCharacter(accm%16)) ;
		return str ;
	}

	public void actionPerformed (ActionEvent ae) {
		if (ae.getSource() == reset) {
			for (int i = 0; i < src_mac.length ; i ++) {
				src_mac[i].setText ("") ;
				dst_mac[i].setText ("") ;
			}
			protocol.setText ("") ;
			for (int i = 0; i < src_ip.length ; i ++) {
				src_ip[i].setText ("") ;
				dst_ip[i].setText ("") ;
			}
		} else if (ae.getSource() == send) {
			String token_s, token_d ;
			String src_mac_addr = "" , dst_mac_addr = "" ;
			String src_ip_addr = "", dst_ip_addr = "" ;

			for ( int i = 0 ; i < dst_mac.length ; i ++ ) {
				if ( (token_d = dst_mac[i].getText().trim()).equals("") ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Destination MAC Address is missing!");
					return ;
				} else if ( (token_d=isValidated (token_d)) == null ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Destination MAC Address is not valid!\nInput token : " + token_d );
					return ;
				} else if ( (token_s = src_mac[i].getText().trim()).equals("") ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Source MAC Address is missing!");
					return ;
				} else if ( (token_s=isValidated (token_s)) == null ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Source MAC Address is not valid!\nInput token : " + token_s );
					return ;
				}
				src_mac_addr = src_mac_addr + token_s + ":" ;
				dst_mac_addr = dst_mac_addr + token_d + ":" ;
			}
			src_mac_addr = src_mac_addr.substring (0, src_mac_addr.length()-1) ;
			dst_mac_addr = dst_mac_addr.substring (0, dst_mac_addr.length()-1) ;

			for ( int i = 0 ; i < dst_ip.length ; i ++ ) {
				if ( (token_s = src_ip[i].getText().trim()).equals("") ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Source IP Address is missing!");
					return ;
				} else if ( (token_s=isValidated (token_s)) == null ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Source IP Address is not valid!\nInput token : " + token_s );
					return ;
				} else if ( (token_d = dst_ip[i].getText().trim()).equals("") ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Destination IP Address is missing!");
					return ;
				} else if ( (token_d=isValidated (token_d)) == null ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Destination IP Address is not valid!\nInput token : " + token_d );
					return ;
				}
				src_ip_addr = src_ip_addr + token_s + ":" ;
				dst_ip_addr = dst_ip_addr + token_d + ":" ;
			}
			src_ip_addr = src_ip_addr.substring (0, src_ip_addr.length()-1) ;
			dst_ip_addr = dst_ip_addr.substring (0, dst_ip_addr.length()-1) ;
			String iface ;			
			if ( (iface = device.getText().trim()) == "" ) {
				JOptionPane.showMessageDialog (null, "Interface device is missing!!");
				return ;
			}
			sendMessageToNetwork_ARP ( src_mac_addr, dst_mac_addr, src_ip_addr, dst_ip_addr, iface) ;
		}
	}

	public native void sendMessageToNetwork_ARP (String src_mac, String dst_mac, String src_ip, String dst_ip, String device);
	
	static {
		System.loadLibrary ("SendPacket_ARP") ;
	}

	public static void main (String[] args) {
		SendARPPacket ceh = new SendARPPacket () ;
	}
}

