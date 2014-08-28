import java.awt.* ;
import java.awt.event.* ;
import java.util.* ;
import java.net.URL;
import java.io.IOException ;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.swing.* ;

public class PacketInjectionMenu extends JFrame {
  private JButton ip, tcp, arp, udp, arbit_packet, icmp, help, exit, sniff ;
  private static ResourceBundle resources ;
  private Hashtable command ;

  public static int PROTOCOL_USED ;
  /**
    * Suffix applied to the key used in resource file
    * lookups for tip text.
    */
  public static final String tipSuffix = "tip";

  /**
    * Suffix applied to the key used in resource file
    * lookups for command text.
    */
  public static final String commandSuffix = "command";

  /**
    * Suffix applied to the key used in resource file
    * lookups for an image.
    */
  public static final String imageSuffix = "Image";

  static {
    try  {
      	resources = ResourceBundle.getBundle("resources/UtilInfo", Locale.getDefault() );
    } catch (MissingResourceException mre) {
      	System.err.println("UtilInfo.properties not found");
        System.exit(1);
    }
  }
   
  /*
   * PacketInjection Menu - Opens the Menu Window
   */
    
  public PacketInjectionMenu ( ) {
    super ( "Packet Injection Menu - Sent Packets to Network" ) ;	
    command = new Hashtable();
	
	// Puts all the Default action in the hashtable
    for (int i = 0; i < defaultActions.length; i++) {
      Action a = defaultActions[i] ;
      command.put ( a.getValue(Action.NAME), a );
    }

	JPanel toolPanel = createToolPanel() ;
	
    JButton[] buttonList = getPanelButtonList ( "arbit_packet - ip - icmp" ) ;
    arbit_packet = buttonList[0] ;
    ip = buttonList[1] ;
    icmp = buttonList[2] ;
    JPanel upperPanel = getPanel ( buttonList ) ;
    
    buttonList = getPanelButtonList ( "tcp - udp - arp" ) ;
    tcp = buttonList[0] ;
    udp = buttonList[1] ;
    arp = buttonList[2] ;
    JPanel lowerPanel = getPanel ( buttonList ) ;
    
    JPanel finalPanel = new JPanel () ;
    finalPanel.setLayout ( new BoxLayout (finalPanel, BoxLayout.Y_AXIS ) ) ;
    finalPanel.add ( Box.createVerticalStrut (20) ) ;
    finalPanel.add ( toolPanel ) ;
    finalPanel.add ( Box.createVerticalStrut (20) ) ;
    finalPanel.add ( upperPanel ) ;
    finalPanel.add ( Box.createVerticalStrut (20) ) ;
    finalPanel.add ( lowerPanel ) ;
    finalPanel.add ( Box.createVerticalStrut (20) ) ;

    Container c = getContentPane() ;  
    c.setLayout( new BorderLayout ( ) ) ;
    c.add ( finalPanel ) ;
		
    setSize ( 550 , 370 ) ;
    setLocation( 100 , 100 ) ;
    setResizable( false ) ;
    setVisible( true ) ;
		    
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    WindowListener wl = new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        ((Window) we.getSource()).dispose();
//        System.exit(0);
      }
    };
    this.addWindowListener(wl);
  }  	

  /*
   * Retrive the Resource String from the Resource File
   */
  private String getResourceString (String nm) {
    String str;
    try {
      str = resources.getString (nm);
    } catch (MissingResourceException mre) {
      str = null;
    }
    return str;
  }
  
  /*
   * Retrive the URL of the specified key
   */
  protected URL getResource (String key) {
    String name = getResourceString (key);

      if (name != null) {
        URL url = this.getClass().getResource(name);
        return url;
      }
      return null;
  }
	
  /*
   * Tokenize the specified 'instruction' with given 'tokenizer'
   * Returns all the tokens
   */
  private String[] tokenize ( String instruction, String tokenizer ) {
    StringTokenizer str = new StringTokenizer ( instruction , tokenizer ) ;
    String []cmd = new String [str.countTokens()] ;
    for ( int i = 0 , len = str.countTokens() ; i < len ; i ++ )
      cmd[i] = str.nextToken().trim() ;
	
    return cmd ;
  }

  /*
   * Retrive Actions from the hash table specified by the cmd
   */
  protected Action getAction (String cmd) {
    return (Action) command.get(cmd) ;
  }
  
  /*
   * Returns all the buttons as a button-list, the name of
   * the buttons are specified by the @panelString. All the 
   * tips and actions associated with the buttons are attached
   * with the buttons. 
   */
  private JButton[] getPanelButtonList ( String panelString ) {
  	String[] elements = tokenize ( panelString, "-" ) ;
  	JButton[] buttonList = new JButton [elements.length] ;
  	
  	for ( int i = 0 ; i < buttonList.length ; i ++ ) 
      buttonList[i] = createButton ( elements[i] ) ;
    
  	return buttonList ;
  }
  
  /*
   * Embed the buttons in a panel
   */
  private JPanel getPanel ( JButton[] buttonList ) {
    JPanel innerPanel = new JPanel () ;
    innerPanel.setLayout ( new BoxLayout (innerPanel, BoxLayout.X_AXIS ) ) ;
    innerPanel.add ( Box.createHorizontalStrut (20) ) ;
    
    for ( int i = 0 ; i < buttonList.length ; i ++ ) {
      innerPanel.add ( buttonList[i] ) ;
      innerPanel.add ( Box.createHorizontalStrut (20) ) ;
    }
    return innerPanel ;
  }

  /**
    * Create a button to go inside of the toolbar.  By default this
    * will load an image resource.  The image filename is relative to
    * the classpath (including the '.' directory if its a part of the
    * classpath), and may either be in a JAR file or a separate file.
    * 
    * @param key The key in the resource file to serve as the basis
    *  of lookups.
    */
  protected JButton createButton (String key) {
    URL url = getResource (key + imageSuffix) ;
    JButton b = new JButton (new ImageIcon(url)) {
      public float getAlignmentY() { return 0.5f; }
    };
      
    b.setRequestFocusEnabled (false);

    Action a = getAction ( key ) ;
    if (a != null) {
      b.setActionCommand (key);
      b.addActionListener (a);
    } 
    else {
      b.setEnabled (false);
    }
    String tip = getResourceString (key + tipSuffix) ;
    if (tip != null) {
      b.setToolTipText (tip);
    }
    return b;
  }

  /*
   * Create a panel - which behaves like a toolbar
   */
  private JPanel createToolPanel () {
  	JPanel innerPanel = new JPanel () ;
    innerPanel.setLayout ( new BoxLayout (innerPanel, BoxLayout.X_AXIS ) ) ;
    
    innerPanel.add ( Box.createHorizontalStrut(30) );
    JButton[] buttonList = getPanelButtonList ( "sniffer" ) ;
    sniff = buttonList[0] ;
    innerPanel.add ( sniff ) ;
    innerPanel.add ( Box.createHorizontalGlue() ) ;
    
    buttonList = getPanelButtonList ( "help - exit" ) ;
    help = buttonList[0] ;
    exit = buttonList[1] ;
    innerPanel.add ( getPanel ( buttonList ) ) ;
    innerPanel.add ( Box.createHorizontalStrut(10) );
    
    return innerPanel ;
  }
  
  /**
   * Actions defined by the PacketInjectionMenu class
   */  
  private Action[] defaultActions = {
    new PacketInjection_IP_Packet (),
    new PacketInjection_TCP_Packet (),
    new PacketInjection_UDP_Packet (),
    new PacketInjection_ICMP_Packet (),
    new PacketInjection_ARP_Packet (),
    new PacketInjection_ARBIT_Packet (),
    new PacketSniffer (),
    new HelpAction () ,
    new ExitAction (),
  };  

  class HelpAction extends AbstractAction {
  	HelpAction () {
  	  super ( "help" ) ;
  	}
  	public void actionPerformed (ActionEvent ae) {
		HtmlDemo demo = new HtmlDemo();  		
//		Test t = new Test ( "help" ) ;
  	}
  }
  
  class PacketSniffer extends AbstractAction {
  	PacketSniffer () {
  	  super ( "sniffer" ) ;
  	}
  	public void actionPerformed (ActionEvent ae) {
//  		Test t = new Test ( "sniffer" ) ;
		executeCommand ("java -jar jars/JpcapDumper-0.3.jar") ;
  	}
  }
  
  class ExitAction extends AbstractAction {
  	ExitAction () {
  	  super ( "exit" ) ;	
  	}
  	public void actionPerformed (ActionEvent ae) {
  	  System.exit(0);	
  	}
  }
  
  class PacketInjection_IP_Packet extends AbstractAction	{
    PacketInjection_IP_Packet ( ) {
      super ( "ip" ) ;
    }
  
    public void actionPerformed(ActionEvent e) {
//	PROTOCOL_USED = 4 ;
	executeCommand ( getResourceString("ip"+commandSuffix) );
//	Test t = new Test ( "ip" ) ;
    }		
  }
	
  class PacketInjection_TCP_Packet extends AbstractAction	{
    PacketInjection_TCP_Packet ( ) {
      super ( "tcp" ) ;
    }
  
    public void actionPerformed(ActionEvent e) {
//	PROTOCOL_USED = 1 ;
//	Test t = new Test ( "tcp" ) ;
	executeCommand ( getResourceString("tcp"+commandSuffix) );
    }
  }

  class PacketInjection_UDP_Packet extends AbstractAction	{
    PacketInjection_UDP_Packet ( ) {
      super ( "udp" ) ;
    }
  
    public void actionPerformed(ActionEvent e) {
//	PROTOCOL_USED = 2 ;
	executeCommand ( getResourceString("udp"+commandSuffix) );
//	Test t = new Test ( "udp" ) ;
    }
  }

  class PacketInjection_ICMP_Packet extends AbstractAction	{
    PacketInjection_ICMP_Packet ( ) {
      super ( "icmp" ) ;
    }
  
    public void actionPerformed(ActionEvent e) {
//	PROTOCOL_USED = 3 ;
//	Test t = new Test ( "icmp" ) ;
//	executeCommandPrintStream ( getResourceString("icmp"+commandSuffix) );
	executeCommand ( getResourceString("icmp"+commandSuffix) );
    }
  }

  class PacketInjection_ARP_Packet extends AbstractAction {
    PacketInjection_ARP_Packet ( ) {
      super ( "arp" ) ;
    }
  
    public void actionPerformed(ActionEvent e) {
	executeCommand ( getResourceString("arp"+commandSuffix) );
//	Test t = new Test ( "arp" ) ;
    }
  }

  class PacketInjection_ARBIT_Packet extends AbstractAction {
    PacketInjection_ARBIT_Packet ( ) {
      super ( "arbit_packet" ) ;
    }
    public void actionPerformed(ActionEvent ae) {
      executeCommand ( getResourceString("arbit_packet"+commandSuffix) );
    }
  }
  
  protected void executeCommand (String cmd) {
    if (cmd == null) return ;
    try {
	System.out.println (cmd) ;
      Process process = Runtime.getRuntime().exec (cmd);
      try {
        process.waitFor();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
    } catch (IOException ioe) { 
          ioe.printStackTrace();
    }
  }

  protected void executeCommandPrintStream (String cmd) {
    if (cmd == null) return ;
    OutputStream stdin = null;
    BufferedReader br = null;
    String line = null;
    String command = null;

    try {
	System.out.println (cmd) ;
      Process process = Runtime.getRuntime().exec (cmd);
      try {
        process.waitFor();
      } catch (InterruptedException e) {
          e.printStackTrace();
      }
      try {
	stdin = process.getOutputStream(); //use this to push commands

	//processing stdout
	br = new BufferedReader(new InputStreamReader(process.getInputStream()));
	while((line=br.readLine())!=null) System.out.println(line);
      } catch ( Exception exec) {
          exec.printStackTrace();
      }
    } catch (IOException ioe) { 
          ioe.printStackTrace();
    }
  }


  public static void main (String[] args) {
  	PacketInjectionMenu pim = new PacketInjectionMenu ( ) ;
  }
}
