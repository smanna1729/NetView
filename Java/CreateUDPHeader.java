import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;

public class CreateUDPHeader extends JFrame implements ActionListener {
	private JTextField src_port, dst_port, length, chksum;
	private JButton create, reset;
	EthernetHeader ethHeader;
	IPHeader ipHeader ;	
	UDPHeader udphdr ; 

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

	public CreateUDPHeader (EthernetHeader ethhdr, IPHeader iphdr) {
		super ( "Create UDP Header.." ) ;
		ethHeader = ethhdr ;
		ipHeader = iphdr ;

		JPanel first2bytes = new JPanel ( ) ;
		first2bytes.setLayout( new BoxLayout ( first2bytes , BoxLayout.X_AXIS ) );
		first2bytes.add ( Box.createHorizontalStrut(20) ) ;
		first2bytes.add ( createLabelledField ( (src_port = new JTextField ()),"Source Port") ) ;
		first2bytes.add ( Box.createHorizontalStrut(10) ) ;
		first2bytes.add ( createLabelledField( (dst_port = new JTextField ()), "Destination Port" ) ) ;
		first2bytes.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel last2bytes = new JPanel ( ) ;
		last2bytes.setLayout( new BoxLayout ( last2bytes , BoxLayout.X_AXIS ) );
		last2bytes.add ( Box.createHorizontalStrut(20) ) ;
		last2bytes.add ( createLabelledField( (length=new JTextField ("8")), "UDP Length") ) ;
		length.setEditable (false);
		last2bytes.add ( Box.createHorizontalStrut(10) ) ;
		last2bytes.add ( createLabelledField ((chksum=new JTextField ()),"UDP CheckSum") ) ;
		chksum.setEditable (false);
		last2bytes.add ( Box.createHorizontalStrut(20) ) ;

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
		intermediatePanel.add( first2bytes ) ;
		intermediatePanel.add( Box.createVerticalStrut( 20 ) ) ;
		intermediatePanel.add( last2bytes ) ;
		intermediatePanel.add( Box.createVerticalStrut( 20 ) ) ;	
		intermediatePanel.add( buttonPanel ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		
		Container c = getContentPane() ; 
		c.setLayout( new BorderLayout ( ) ) ;
		c.add ( intermediatePanel ) ;
 	
		setResizable ( false ) ;
		setLocation ( 180 , 100 ) ;
		setSize ( 450 , 230 ) ; 
		setVisible ( true ) ;	
  
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		WindowListener wl = new WindowAdapter() {
	  	  public void windowClosing(WindowEvent we) {
	      	((Window) we.getSource()).dispose();
	  	  }
		};
		this.addWindowListener(wl);			
	}

	public boolean isValidNumber (String num) {
		try {
			int number = Integer.parseInt (num) ;
			if ( number < 0 ) {
				JOptionPane.showMessageDialog ( null, "Wrong input given : Input number : " + num ) ;
				return false ;
			}
		}catch ( Exception exp ) {
			JOptionPane.showMessageDialog ( null, "Number format Exception : Input number : " + num ) ;
			return false ;
		}
		return true ;
	}

	public void actionPerformed (ActionEvent ae) {
		if ( ae.getSource() == reset ) {
			src_port.setText ("") ;
			dst_port.setText ("") ;
		} else if (ae.getSource() == create ) {
			String str ;
			if ( (str=src_port.getText().trim()).equals("") ) {
				JOptionPane.showMessageDialog ( null, "Source Port cannot be empty!" ) ;
				return ;
			} else if ( !isValidNumber(str) ) {
				JOptionPane.showMessageDialog ( null, "Source Port number is not valid!" ) ;
				return ;
			} else if ( (str=dst_port.getText().trim()).equals("") ) {
				JOptionPane.showMessageDialog ( null, "Destination Port cannot be empty!" ) ;
				return ;
			} else if ( !isValidNumber(str) ) {
				JOptionPane.showMessageDialog ( null, "Destination Port number is not valid!" ) ;
				return ;
			}
			udphdr = new UDPHeader () ;
			udphdr.setSourcePort ( Integer.parseInt( src_port.getText().trim() ) ) ;
			udphdr.setDestinationPort ( Integer.parseInt( dst_port.getText().trim() ) ) ;
			setVisible (false);
			ethHeader.showEthernetHeader () ;
			ipHeader.showIPHeader () ;
			udphdr.showUDPHeader () ;
			SendPacket sendpkt = new SendPacket (ethHeader, ipHeader, null, udphdr) ;
		}
	}
	
	public static void main (String[] args) {
		CreateUDPHeader hdr = new CreateUDPHeader (null,null) ;
	}
}
