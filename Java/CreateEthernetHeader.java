import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;

public class CreateEthernetHeader extends JFrame implements ActionListener {
	private JTextField src_ip[], dst_ip[] ;
	private JButton create, reset ;
	private EthernetHeader ethHeader = null ;
	private JComboBox protocol ;

	public CreateEthernetHeader ( int selectProto ) {
		super ( "Create Ethernet Header" );
		JPanel src_ip_label = new JPanel ( ) ;
		src_ip_label.setLayout( new BoxLayout ( src_ip_label , BoxLayout.X_AXIS ) );
		JLabel src = new JLabel ( "Source MAC Address" ) ; 
		src_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		src_ip_label.add ( src ) ;
		src_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel src_label = new JPanel ( ) ;
		src_label.setLayout( new BoxLayout ( src_label , BoxLayout.X_AXIS ) );
		src_ip = new JTextField [6] ;
		src_label.add ( Box.createHorizontalStrut(20) ) ;
		for ( int i = 0 ; i < 5 ; i ++ ) {
		  src_ip[i] = new JTextField () ;
		  src_label.add ( src_ip[i] ) ;
		  src_label.add ( Box.createHorizontalStrut(10) ) ;
		  src_label.add ( new JLabel ( ":" ) ) ;
		  src_label.add ( Box.createHorizontalStrut(10) ) ;
		}
		src_label.add ( (src_ip[5] = new JTextField ()) ) ;
		src_label.add ( Box.createHorizontalStrut(20) ) ;
		  
		JPanel dst_ip_label = new JPanel ( ) ;
		dst_ip_label.setLayout( new BoxLayout ( dst_ip_label , BoxLayout.X_AXIS ) );
		JLabel dst = new JLabel ( "Destination MAC Address" ) ; 
		dst_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		dst_ip_label.add ( dst ) ;
		dst_ip_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel dst_label = new JPanel ( ) ;
		dst_label.setLayout( new BoxLayout ( dst_label , BoxLayout.X_AXIS ) );
		dst_ip = new JTextField [6] ;
		dst_label.add ( Box.createHorizontalStrut(20) ) ;
		for ( int i = 0 ; i < 5 ; i ++ ) {
		  dst_ip[i] = new JTextField () ;
		  dst_label.add ( dst_ip[i] ) ;
		  dst_label.add ( Box.createHorizontalStrut(10) ) ;
		  dst_label.add ( new JLabel ( ":" ) ) ;
		  dst_label.add ( Box.createHorizontalStrut(10) ) ;
		}
		dst_label.add ( (dst_ip[5] = new JTextField()) ) ;
		dst_label.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel proto_panel = new JPanel ( ) ;
		proto_panel.setLayout( new BoxLayout ( proto_panel , BoxLayout.X_AXIS ) ) ;
		proto_panel.add ( Box.createHorizontalStrut(20) ) ;
		proto_panel.add ( new JLabel ("Protocol : ") ) ;
		proto_panel.add ( Box.createHorizontalStrut(90) ) ;
		protocol = new JComboBox () ;
		protocol.addItem ( "Protocol" ) ;
		protocol.addItem ( "TCP" ) ;
		protocol.addItem ( "UDP" ) ;
		protocol.addItem ( "ICMP" ) ;
		protocol.addItem ( "IP" ) ;
		proto_panel.add ( protocol );	
		proto_panel.add ( Box.createHorizontalStrut(20) ) ;
		protocol.setSelectedIndex (selectProto) ;
		protocol.setEnabled (false) ;

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
		intermediatePanel.add( dst_ip_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;		 	
		intermediatePanel.add( dst_label ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;	 	 
		intermediatePanel.add ( src_ip_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;		 	
		intermediatePanel.add( src_label ) ;
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatePanel.add( proto_panel ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;	 	
		intermediatePanel.add( buttonPanel ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
	
	    	Container c = getContentPane() ; 
		c.setLayout( new BorderLayout ( ) ) ;
		c.add ( intermediatePanel ) ;
 	
		setResizable ( false ) ;
		setLocation ( 180 , 100 ) ;
		setSize ( 500 , 250 ) ; 
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

	private String mapProtocolName (String name) {
		return "IPPROTO_"+name ;
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


	private boolean isValidProtocol (int pro) {
		if (pro >0 && pro <5)
			return true ;
		return false ;
	}

	public void actionPerformed (ActionEvent ae) {
		if (ae.getSource() == reset) {
			for (int i = 0; i < 6 ; i ++) {
				src_ip[i].setText ("") ;
				dst_ip[i].setText ("") ;
			}
			protocol.setSelectedIndex (0) ;
		} else if (ae.getSource() == create) {
			String token_s, token_d ;
			String src_mac = "" , dst_mac = "" ;
			for ( int i = 0 ; i < 6 ; i ++ ) {
				if ( (token_d = dst_ip[i].getText().trim()).equals("") ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Destination MAC Address is missing!");
					return ;
				} else if ( !isValidated (token_d) ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Destination MAC Address is not valid!\nInput token : " + token_d );
					return ;
				} else if ( (token_s = src_ip[i].getText().trim()).equals("") ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Source MAC Address is missing!");
					return ;
				} else if ( !isValidated (token_s) ) {
					JOptionPane.showMessageDialog (null, (i+1) + " th token of Source MAC Address is not valid!\nInput token : " + token_s );
					return ;
				} 
				if ( token_s.length() == 1 )
					token_s = "0" + token_s ;
				src_mac = src_mac + token_s + ":" ;
				if ( token_d.length() == 1 )
					token_d = "0" + token_d ;
				dst_mac = dst_mac + token_d + ":" ;
			}
			if ( !isValidProtocol (protocol.getSelectedIndex()) ) {
				JOptionPane.showMessageDialog (null, "Select Protocol Name!" );
				return ;
			}
			src_mac = src_mac.substring (0, src_mac.length()-1) ;
			dst_mac = dst_mac.substring (0, dst_mac.length()-1) ;
			
			ethHeader = new EthernetHeader ();
			ethHeader.setSourceMACAddress (src_mac) ;
			ethHeader.setDestinationMACAddress (dst_mac) ;
			ethHeader.setProtocol ( "ETHERTYPE_IP" );
//			ethHeader.showEthernetHeader () ;
			setVisible (false) ;
			CreateIPHeader ipHdr = new CreateIPHeader (ethHeader, mapProtocolName((String)protocol.getSelectedItem()) ) ;
		}
	}
	
	public EthernetHeader getEthernetHeader () {
		return ethHeader ;
	}

	public static void main (String[] args) {
		CreateEthernetHeader ceh = new CreateEthernetHeader (Integer.parseInt(args[0]) );
	}
}
