import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;

public class SendIPPacket extends JFrame implements ActionListener {
	private JTextField device ;
	private JButton send ;
	private EthernetHeader ethHeader; 
	private IPHeader iphdr;

	public SendIPPacket (EthernetHeader ethhdr, IPHeader ipheader) {
		super ( "Device Name" );
		ethHeader = ethhdr; 
		iphdr = ipheader ; 
		
		JPanel jpanel = new JPanel () ;
		jpanel.setLayout( new BoxLayout ( jpanel, BoxLayout.X_AXIS ) ) ;
		jpanel.add (Box.createHorizontalStrut (20)) ;
		jpanel.add ( (device = new JTextField() ) ) ;
		jpanel.add (Box.createHorizontalStrut (20)) ;
		jpanel.add ( (send = new JButton ("Send")) ) ;
		send.addActionListener (this) ;
		jpanel.add (Box.createHorizontalStrut (20)) ;

		JPanel jp = new JPanel () ;
		jp.setLayout( new BoxLayout ( jp, BoxLayout.Y_AXIS ) ) ;
		jp.add (Box.createVerticalStrut (20)) ;
		jp.add (jpanel) ;
		jp.add (Box.createVerticalStrut (20)) ;

		Container c = getContentPane() ;  
		c.setLayout( new BorderLayout ( ) ) ;
		c.add (jp) ;
    
		setSize ( 550 , 270 ) ;
		setLocation( 100 , 100 ) ;
		setResizable( false ) ;
		setVisible( true ) ;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		WindowListener wl = new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				((Window) we.getSource()).dispose();
				System.exit(0);
			}
		};
		this.addWindowListener(wl);
	}

	public native void sendMessageToNetwork_IP (String src, String dst, String type, int tos, int id, int flag, int offset, int ttl, String proto, String src_ip, String dst_ip, String device) ;


	static {
		System.loadLibrary ( "SendPacket_IP" ) ;
	}

	public void actionPerformed (ActionEvent ae) {
		if (ae.getSource() == send) {
			sendMessageToNetwork_IP (ethHeader.getSourceMACAddress(), ethHeader.getDestinationMACAddress(), ethHeader.getProtocol(), iphdr.getTOS(), iphdr.getID(), iphdr.getFlag(), iphdr.getOffset(), iphdr.getTimetoLive(), iphdr.getProtocol(), iphdr.getSourceIPAddress(), iphdr.getDestinationIPAddress(), device.getText().trim()) ;			
		}
	}

	public static void main (String[] args) {
		SendIPPacket pkt = new SendIPPacket (null,null);
	}
}
