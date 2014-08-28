import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;

/*
 *  CreateTCPHeader
 *  Opens through CreateEthernetHeader GUI accpets Ethernet, IPHeader
 *  Sends TCP Packet to the network
 */

public class CreateTCPHeader extends JFrame implements ActionListener {
	
	private JTextField src_port, dst_port, seq_num, ack, chksum, urg_ptr,d_offset, reserved, window;
	private JToggleButton urg, ackn, psh, rst, syn, fin ;
	private JButton create, reset ;
	EthernetHeader ethHeader;
	IPHeader ipHeader ;
	private TCPHeader tcphdr = null ;

	/*
	 * createLabelledField creates a Labelled Component - Label should be
	 * put above the Component.
	 *
	 * @param component		Component which should be labelled
	 * @param str			Labelled String
	 *
	 * @return JPanel		Returns a panel containing Labelled Component
	 */

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

	/*
	 * createLabelledField creates a Labelled Component - Label should be
	 * put above the Component.
	 *
	 * @param component		Component which should be labelled
	 * @param component_up		Component used for labelling 
	 *
	 * @return JPanel		Returns a panel containing Labelled Component
	 */

	protected JPanel createLabelledField (JComponent component, JComponent component_up) {
		JPanel innerPanel = new JPanel () ;
		innerPanel.setLayout( new BoxLayout ( innerPanel , BoxLayout.Y_AXIS ) );
		innerPanel.add (component_up) ;
                innerPanel.add (Box.createVerticalStrut(10) );
		innerPanel.add (component) ;
                return innerPanel ;
	}

	public CreateTCPHeader (EthernetHeader ethhdr, IPHeader iphdr) {
		super ( "Create TCP Header" );
		ethHeader = ethhdr ;
		ipHeader = iphdr ;

		// First Panel Row - Source Port & Destination Port
		JPanel first2bytes = new JPanel ( ) ;
		first2bytes.setLayout( new BoxLayout ( first2bytes , BoxLayout.X_AXIS ) );
		first2bytes.add ( Box.createHorizontalStrut(20) ) ;
		first2bytes.add ( createLabelledField ( (src_port = new JTextField (20)), "Source Port" ) ) ;
		first2bytes.add ( Box.createHorizontalStrut(10) ) ;
		first2bytes.add ( createLabelledField ( (dst_port = new JTextField (20)), "Destination Port" ) ) ;
		first2bytes.add ( Box.createHorizontalStrut(20) ) ;

		// First Panel Row - Source Port & Destination Port
		JPanel seq_panel = new JPanel ( ) ;
		seq_panel.setLayout( new BoxLayout ( seq_panel , BoxLayout.X_AXIS ) ) ;
		seq_panel.add ( Box.createHorizontalStrut(20) ) ;
		JLabel seq_label = new JLabel ( "Sequence Number : " ) ;
		seq_panel.add ( seq_label ) ;
		seq_panel.add ( Box.createHorizontalStrut(167) ) ;
		seq_num = new JTextField ( ) ;
		seq_panel.add ( seq_num );	
		seq_panel.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel ack_panel = new JPanel ( ) ;
		ack_panel.setLayout( new BoxLayout ( ack_panel , BoxLayout.X_AXIS ) ) ;
		ack_panel.add ( Box.createHorizontalStrut(20) ) ;
		JLabel ack_label = new JLabel ( "Acknowledgement Number : " ) ;
		ack_panel.add ( ack_label ) ;
		ack_panel.add ( Box.createHorizontalStrut(106) ) ;
		ack = new JTextField ( ) ;
		ack_panel.add ( ack );	
		ack_panel.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel fourth2bytes_label = new JPanel ( ) ;
		fourth2bytes_label.setLayout( new BoxLayout ( fourth2bytes_label , BoxLayout.X_AXIS ) );
		fourth2bytes_label.add ( Box.createHorizontalStrut(30) ) ;

		JPanel offset_panel = new JPanel ();
		offset_panel.setLayout( new BoxLayout ( offset_panel, BoxLayout.Y_AXIS ) );
		offset_panel.add ( new JLabel ( "       Data" ));
		offset_panel.add ( new JLabel ( "      Offset" ));
	
		JPanel urg_panel = new JPanel ();
		urg_panel.setLayout( new BoxLayout ( urg_panel, BoxLayout.Y_AXIS ) );
		urg_panel.add ( new JLabel ( "    U",SwingConstants.CENTER ));
		urg_panel.add ( new JLabel ( "    R",SwingConstants.CENTER  ));
		urg_panel.add ( new JLabel ( "    G",SwingConstants.CENTER  ));

		JPanel ackl_panel = new JPanel ();
		ackl_panel.setLayout( new BoxLayout ( ackl_panel, BoxLayout.Y_AXIS ) );
		ackl_panel.add ( new JLabel ( "    A" ));
		ackl_panel.add ( new JLabel ( "    C" ));
		ackl_panel.add ( new JLabel ( "    K" ));

		JPanel psh_panel = new JPanel ();
		psh_panel.setLayout( new BoxLayout ( psh_panel, BoxLayout.Y_AXIS ) );
		psh_panel.add ( new JLabel ( "    P" ));
		psh_panel.add ( new JLabel ( "    S" ));
		psh_panel.add ( new JLabel ( "    H" ));

		JPanel rst_panel = new JPanel ();
		rst_panel.setLayout( new BoxLayout ( rst_panel, BoxLayout.Y_AXIS ) );
		rst_panel.add ( new JLabel ( "    R" ));
		rst_panel.add ( new JLabel ( "    S" ));
		rst_panel.add ( new JLabel ( "    T" ));

		JPanel syn_panel = new JPanel ();
		syn_panel.setLayout( new BoxLayout ( syn_panel, BoxLayout.Y_AXIS ) );
		syn_panel.add ( new JLabel ( "    S" ));
		syn_panel.add ( new JLabel ( "    Y" ));
		syn_panel.add ( new JLabel ( "    N" ));

		JPanel fin_panel = new JPanel ();
		fin_panel.setLayout( new BoxLayout ( fin_panel, BoxLayout.Y_AXIS ) );
		fin_panel.add ( new JLabel ( "    F" ));
		fin_panel.add ( new JLabel ( "    I" ));
		fin_panel.add ( new JLabel ( "    N" ));
		
		JPanel fourth2bytes = new JPanel ( ) ;
		fourth2bytes.setLayout( new BoxLayout ( fourth2bytes , BoxLayout.X_AXIS ) );
		 
		fourth2bytes.add ( Box.createHorizontalStrut(20) ) ;
		fourth2bytes.add ( createLabelledField ((d_offset = new JTextField (10)), offset_panel) ) ;
		fourth2bytes.add ( Box.createHorizontalStrut(10) ) ;
		fourth2bytes.add ( createLabelledField ( (reserved = new JTextField (5) ) , "Reserve") ) ;
		reserved.setEditable (false) ;

		urg = new JToggleButton ( "0" ) ;
		urg.addActionListener (this) ;
		fourth2bytes.add (Box.createHorizontalStrut(10) ) ;
		fourth2bytes.add ( createLabelledField(urg,urg_panel) ) ;

		ackn = new JToggleButton ( "0" ) ;
		ackn.addActionListener (this) ;
		fourth2bytes.add (Box.createHorizontalStrut(2) ) ;
		fourth2bytes.add ( createLabelledField(ackn,ackl_panel) ) ;

		psh = new JToggleButton ( "0" ) ;
		psh.addActionListener (this) ;
		fourth2bytes.add (Box.createHorizontalStrut(2) ) ;
		fourth2bytes.add ( createLabelledField(psh,psh_panel) ) ;

		rst = new JToggleButton ( "0" ) ;
		rst.addActionListener (this) ;
		fourth2bytes.add (Box.createHorizontalStrut(2) ) ;
		fourth2bytes.add ( createLabelledField(rst,rst_panel) ) ;

		syn = new JToggleButton ( "0" ) ;
		syn.addActionListener (this) ;
		fourth2bytes.add (Box.createHorizontalStrut(2) ) ;
		fourth2bytes.add ( createLabelledField(syn,syn_panel) ) ;

		fin = new JToggleButton ( "0" ) ;
		fin.addActionListener (this) ;
		fourth2bytes.add (Box.createHorizontalStrut(2) ) ;
		fourth2bytes.add ( createLabelledField(fin,fin_panel) ) ;
		fourth2bytes.add ( Box.createHorizontalStrut(10) ) ;
		fourth2bytes.add ( createLabelledField( (window=new JTextField (20)), "Window" ) ) ;
		fourth2bytes.add ( Box.createHorizontalStrut(20) ) ;
		
		JPanel fifth2bytes = new JPanel ( ) ;
		fifth2bytes.setLayout( new BoxLayout ( fifth2bytes , BoxLayout.X_AXIS ) );
		fifth2bytes.add ( Box.createHorizontalStrut(20) ) ;
		fifth2bytes.add ( createLabelledField((chksum = new JTextField () ),"CheckSum") ) ;
		chksum.setEditable (false) ;
		fifth2bytes.add ( Box.createHorizontalStrut(10) ) ;
		fifth2bytes.add ( createLabelledField((urg_ptr = new JTextField ()),"Urgent Pointer")) ;
		fifth2bytes.add ( Box.createHorizontalStrut(20) ) ;
			
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
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;	
		intermediatePanel.add( seq_panel ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;	 	 
		intermediatePanel.add ( ack_panel ) ;
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;		 	
		intermediatePanel.add( fourth2bytes ) ;		
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;
		intermediatePanel.add( fifth2bytes ) ;
		intermediatePanel.add( Box.createVerticalStrut( 15 ) ) ;	
		intermediatePanel.add( buttonPanel ) ;	
		intermediatePanel.add( Box.createVerticalStrut( 10 ) ) ;
		
		Container c = getContentPane() ; 
		c.setLayout( new BorderLayout ( ) ) ;
		c.add ( intermediatePanel ) ;
 	
		setResizable ( false ) ;
		setLocation ( 180 , 100 ) ;
		setSize ( 650 , 380 ) ; 
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
		if ( ae.getSource() == urg ) {
			if ( urg.isSelected() )
				urg.setText( "1" ) ;
			else urg.setText( "0" ) ;
		} else if ( ae.getSource() == ackn ) {
			if ( ackn.isSelected() )
				ackn.setText( "1" ) ;
			else ackn.setText( "0" ) ;
		} else if ( ae.getSource() == psh ) {
			if ( psh.isSelected() )
				psh.setText( "1" ) ;
			else psh.setText( "0" ) ;
		} else if ( ae.getSource() == rst ) {
			if ( rst.isSelected() )
				rst.setText( "1" ) ;
			else rst.setText( "0" ) ;
		} else if ( ae.getSource() == syn ) {
			if ( syn.isSelected() )
				syn.setText( "1" ) ;
			else syn.setText( "0" ) ;			
		} else if ( ae.getSource() == fin ) {
			if ( fin.isSelected() )
				fin.setText( "1" ) ;
			else fin.setText( "0" ) ;			
		} else if ( ae.getSource() == reset ) {
			src_port.setText ("");
			dst_port.setText ("");
			seq_num.setText ("");
			ack.setText ("");
			urg_ptr.setText ("");
			d_offset.setText ("");
			window.setText ("");
			urg.setSelected (false);
			ackn.setSelected (false);
			psh.setSelected (false);
			rst.setSelected (false);
			syn.setSelected (false);
			fin.setSelected (false);
		} else if ( ae.getSource() == create ) {
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
			} else if ( (str=seq_num.getText().trim()).equals("") ) {
				JOptionPane.showMessageDialog ( null, "Sequence Number cannot be empty!" ) ;
				return ;
			} else if ( !isValidNumber(str) ) {
				JOptionPane.showMessageDialog ( null, "Sequence number is not valid!" ) ;
				return ;
			} else if ( (str=ack.getText().trim()).equals("") ) {
				JOptionPane.showMessageDialog ( null, "Acknowledgement field cannot be empty!" ) ;
				return ;
			} else if ( !isValidNumber(str) ) {
				JOptionPane.showMessageDialog ( null, "Acknowledgement field number is not valid!" ) ;
				return ;
			} else if ( (str=urg_ptr.getText().trim()).equals("") ) {
				JOptionPane.showMessageDialog ( null, "Urgent Pointer field cannot be empty!" ) ;
				return ;
			} else if ( !isValidNumber(str) ) {
				JOptionPane.showMessageDialog ( null, "Urgent Pointer is not valid!" ) ;
				return ;
			} else if ( (str=d_offset.getText().trim()).equals("") ) {
				JOptionPane.showMessageDialog ( null, "Data Offset field cannot be empty!" ) ;
				return ;
			} else if ( !isValidNumber(str) ) {
				JOptionPane.showMessageDialog ( null, "Data Offset is not valid!" ) ;
				return ;
			} else if ( (str = window.getText().trim()).equals("") ) {
				JOptionPane.showMessageDialog ( null, "Window field cannot be empty!" ) ;
				return ;
			} else if ( !isValidNumber(str) ) {
				JOptionPane.showMessageDialog ( null, "Window number is not valid!" ) ;
				return ;
			} 
			tcphdr = new TCPHeader () ;
			tcphdr.setSourcePort ( Integer.parseInt( src_port.getText().trim() ) ) ;
			tcphdr.setDestinationPort ( Integer.parseInt( dst_port.getText().trim() ) ) ;
			tcphdr.setSequenceNumber ( Integer.parseInt( seq_num.getText().trim() ) ) ;
			tcphdr.setAcknowledgement ( Integer.parseInt( ack.getText().trim() ) ) ;
			tcphdr.setUrgentPointer ( Integer.parseInt( urg_ptr.getText().trim() ) ) ;
			tcphdr.setDataOffset ( Integer.parseInt( d_offset.getText().trim() ) ) ;
			tcphdr.setWindow ( Integer.parseInt( window.getText().trim() ) );

			tcphdr.setURG ( (urg.isSelected() ? 1 : 0) ) ; 
			tcphdr.setACK ( (ackn.isSelected() ? 1 : 0) ) ;
			tcphdr.setPSH ( (psh.isSelected() ? 1 : 0) ) ;
			tcphdr.setRST ( (rst.isSelected() ? 1 : 0) ) ;
			tcphdr.setSYN ( (syn.isSelected() ? 1 : 0) ) ;
			tcphdr.setFIN ( (fin.isSelected() ? 1 : 0) ) ;

			setVisible(false); 
			ethHeader.showEthernetHeader () ;
			ipHeader.showIPHeader () ;
			tcphdr.showTCPHeader() ;
			SendPacket sendpkt = new SendPacket (ethHeader, ipHeader, tcphdr, null) ;
		}
	}

	public TCPHeader getTCPHeader () {
		return tcphdr ;
	}
	
	public static void main (String[] args) {
		CreateTCPHeader hdr = new CreateTCPHeader (null,null) ;
 	}
}
