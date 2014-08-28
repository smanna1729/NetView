import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import java.util.Random;

public class ArbitSocketGUI extends JFrame implements ActionListener {
	private JTextField device, tot_pkt, pkt_len;
	private JTextArea msg ;
	private JButton send, reset ;
	private JRadioButton random, selective, trim_y, trim_n ;
	private String message ;

	public ArbitSocketGUI () {
		super ( "Send Arbitrary packet to network!" ) ;
		
		JPanel device_panel = new JPanel ( ) ;
		device_panel.setLayout( new BoxLayout ( device_panel , BoxLayout.X_AXIS ) ) ;
		device_panel.add ( Box.createHorizontalStrut(20) ) ;
		JLabel device_label = new JLabel ( "Device Name : " ) ;
		device_panel.add ( device_label ) ;
		device_panel.add ( Box.createHorizontalStrut(106) ) ;
		device = new JTextField ( ) ;
		device_panel.add ( device );	
		device_panel.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel tot_pkt_panel = new JPanel ( ) ;
		tot_pkt_panel.setLayout( new BoxLayout ( tot_pkt_panel , BoxLayout.X_AXIS ) ) ;
		tot_pkt_panel.add ( Box.createHorizontalStrut(20) ) ;
		JLabel tot_pkt_label = new JLabel ( "Total packets to be sent : " ) ;
		tot_pkt_panel.add ( tot_pkt_label ) ;
		tot_pkt_panel.add ( Box.createHorizontalStrut(23) ) ;
		tot_pkt = new JTextField ( ) ;
		tot_pkt_panel.add ( tot_pkt );	
		tot_pkt_panel.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel pkt_len_panel = new JPanel ( ) ;
		pkt_len_panel.setLayout( new BoxLayout ( pkt_len_panel , BoxLayout.X_AXIS ) ) ;
		pkt_len_panel.add ( Box.createHorizontalStrut(20) ) ;
		JLabel pkt_len_label = new JLabel ( "Single Packet Length : " ) ;
		pkt_len_panel.add ( pkt_len_label ) ;
		pkt_len_panel.add ( Box.createHorizontalStrut(46) ) ;
		pkt_len = new JTextField ( ) ;
		pkt_len_panel.add ( pkt_len );	
		pkt_len_panel.add ( Box.createHorizontalStrut(20) ) ;
		
		random = new JRadioButton ( "Random" ) ;
		random.addActionListener (this);
		selective = new JRadioButton ( "Selective" ) ;
		selective.addActionListener (this) ;
		ButtonGroup bg = new ButtonGroup () ;
		bg.add (random) ;
		bg.add (selective) ;

		JPanel msg_mode_panel = new JPanel ( ) ;
		msg_mode_panel.setLayout( new BoxLayout ( msg_mode_panel , BoxLayout.X_AXIS ) ) ;
		msg_mode_panel.add ( Box.createHorizontalStrut(20) ) ;
		JLabel msg_mode_label = new JLabel ( "Message Mode : " ) ;
		msg_mode_panel.add ( msg_mode_label ) ;
		msg_mode_panel.add ( Box.createHorizontalGlue() ) ;
		msg_mode_panel.add ( random );	
		msg_mode_panel.add ( Box.createHorizontalStrut(20) ) ;
		msg_mode_panel.add ( selective );	
		msg_mode_panel.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel msg_panel = new JPanel ( ) ;
		msg_panel.setLayout( new BoxLayout ( msg_panel , BoxLayout.X_AXIS ) ) ;
		msg_panel.add ( Box.createHorizontalStrut(20) ) ;
		JLabel msg_label = new JLabel ( "Message : " ) ;
		msg_panel.add ( msg_label ) ;
		msg_panel.add ( Box.createHorizontalStrut(132) ) ;
		msg = new JTextArea ( 5, 30) ;
		JScrollPane jsp = new JScrollPane (msg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		msg_panel.add ( jsp );	
		msg_panel.add ( Box.createHorizontalStrut(20) ) ;

		trim_y = new JRadioButton ( "Yes" ) ;
		trim_y.addActionListener (this) ;
		trim_n = new JRadioButton ( "No" ) ;
		trim_n.addActionListener (this);
		ButtonGroup bg1 = new ButtonGroup () ;
		bg1.add (trim_y) ;
		bg1.add (trim_n) ;

		JPanel trim_panel = new JPanel ( ) ;
		trim_panel.setLayout( new BoxLayout ( trim_panel , BoxLayout.X_AXIS ) ) ;
		trim_panel.add ( Box.createHorizontalStrut(20) ) ;
		JLabel trim_label = new JLabel ( "Send packet of specified size : " ) ;
		trim_panel.add ( trim_label ) ;
		trim_panel.add ( Box.createHorizontalGlue() ) ;
		trim_panel.add ( trim_y );	
		trim_panel.add ( Box.createHorizontalStrut(40) ) ;
		trim_panel.add ( trim_n );	
		trim_panel.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel buttonPanel = new JPanel ( ) ;
	    	buttonPanel.setLayout( new BoxLayout ( buttonPanel , BoxLayout.X_AXIS ) ) ;
    		send = new JButton ( "Send Packet") ;
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
		intermediatePanel.add( device_panel ) ;
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;
		intermediatePanel.add( tot_pkt_panel ) ;
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;		
		intermediatePanel.add( pkt_len_panel ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		intermediatePanel.add( msg_mode_panel ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;		
		intermediatePanel.add( msg_panel ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 5 ) ) ;
		intermediatePanel.add( trim_panel ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;	
		intermediatePanel.add( buttonPanel ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		
		Container c = getContentPane() ; 
		c.setLayout( new BorderLayout ( ) ) ;
		c.add ( intermediatePanel ) ;
 	
		setResizable ( false ) ;
		setLocation ( 180 , 100 ) ;
		setSize ( 550 , 340 ) ; 
		setVisible ( true ) ;	
  	
  		defaultAction() ;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		WindowListener wl = new WindowAdapter() {
	  	  public void windowClosing(WindowEvent we) {
	      	((Window) we.getSource()).dispose();
	  	  }
		};
		this.addWindowListener(wl);			
	}
	
	private void defaultAction () {
		msg.setText( "" ) ;
		random.setSelected(true) ;
		msg.setEnabled (false) ;
		trim_y.setEnabled (false) ;
		trim_n.setEnabled (false) ;
	}

	private int isValidated (String number) {
		int num = -1 ;
		try {
			num = Integer.parseInt (number) ;
			if ( num  <= 0 ) {
				JOptionPane.showMessageDialog ( null, "Given Argument cannot be -ve : Input String " + number);
				return -1 ;
			}
		} catch (Exception exp) {
			JOptionPane.showMessageDialog (null, "Exception Occured : Input String " + number) ;
		}
		return num ;
	}

	private char[] createMessageBuffer (boolean rand) {				
		char[] messageBuffer ;
		int pktLen = Integer.parseInt(pkt_len.getText().trim()) ;
		if ( rand ) {
			Random ran = new Random () ;
			messageBuffer = new char [pktLen] ;
			for ( int i = 0 ; i < pktLen ; i ++ )
				messageBuffer[i] = (char)( 128 * ran.nextDouble() );
		} else {
			String message = msg.getText() ;
			int size = message.length() ;
			if ( trim_y.isSelected() ) {
				Random ran = new Random () ;
				messageBuffer = new char [pktLen] ;
				System.arraycopy (message.toCharArray(),0,messageBuffer,0,size) ;
				for ( int i = size ; i < pktLen ; i ++ )
					messageBuffer[i] = (char)( 128 * ran.nextDouble() );
			} else {
				messageBuffer = message.toCharArray () ;
			}
		}
		return messageBuffer ;
	}

	public void actionPerformed (ActionEvent ae) {
		if ( ae.getSource() == random ) {
			if ( random.isSelected() )
				defaultAction() ;
 		} else if ( ae.getSource() == selective ) {
 			msg.setEnabled(true) ;
 			trim_y.setEnabled(true) ;
 			trim_n.setEnabled(true) ;
 			trim_y.setSelected(true) ;
 		} else if ( ae.getSource() == send ) {
			String total_pkts, pktLen ;
			int singlePktLen ;
			if ( device.getText().trim().equals ("") ) {
				JOptionPane.showMessageDialog (null, "Device name is not given!") ;
				return ;
			} else if ( (total_pkts = tot_pkt.getText().trim()).equals ("") ) {
				JOptionPane.showMessageDialog (null, "Total Number of packets is not given!") ;
				return ;
			} else if ( (pktLen = pkt_len.getText().trim()).equals ("") ) {
				JOptionPane.showMessageDialog (null, "Packet Length is not given!") ;
				return ;
			} else if ( isValidated (total_pkts) == -1 || (singlePktLen=isValidated (pktLen)) == -1 ) {
				JOptionPane.showMessageDialog (null, "Total Packet | packet length is not validated!") ;
				return ;
			} 
			if ( singlePktLen > 800 ) {
				pkt_len.setText ("" ) ;
				JOptionPane.showMessageDialog (null, "Message (Length) Upper Limit (800) is crossed!!") ;
				return ;
			}
			char[] messageBuffer = createMessageBuffer (random.isSelected());
			String str = new String(messageBuffer) ;

			sendMessageToNetwork (device.getText().trim(), Integer.parseInt(total_pkts), str.length(), str ) ;
			tot_pkt.setText ( "" ) ;
			pkt_len.setText ( "" ) ;
			defaultAction() ;
		} else if ( ae.getSource() == reset ) {
			device.setText ( "" ) ;
			tot_pkt.setText ( "" ) ;
			pkt_len.setText ( "" ) ;
			defaultAction() ;
		} 
	}

	public native void sendMessageToNetwork (String device, int totalPacket, int bufferSize, String message) ;

	static {
		System.loadLibrary ("ArbitSocketGUI") ;
	}

	public static void main (String[] args) {
		ArbitSocketGUI socket = new ArbitSocketGUI () ;
	}	

}
