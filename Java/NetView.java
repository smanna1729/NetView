/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package netview;

/**
 *
 * @author MIMO
 */
import java.awt.Dimension;
import javax.swing.JFrame;
public class NetView {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Start start=new Start();
        start.setPreferredSize(new Dimension(580,480));
	start.setLocation (200, 100);
        start.setVisible(true);
        start.setResizable(false);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
