package termex.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TermEx {

	JFrame jframe = new JFrame();
	
	JMenuBar jmb = new JMenuBar();
	JMenu jm_file, jm_help;
	JMenuItem jm_open, jm_exit;
	
	/**
	 * Constructor, set name and size
	 * 
	 * @param title
	 */
	public TermEx (String title) {
		jframe.setTitle(title);
		jframe.setSize(new Dimension(400, 300));
	}
	
	/**
	 * Create Menu Bar
	 */
	void setMenuBar () {
		jmb.add(jm_file = new JMenu("File"));
		jmb.add(jm_help = new JMenu("Help"));
		
		jm_file.add(jm_open = new JMenuItem("Open"));
		jm_file.add(jm_exit = new JMenuItem("Exit"));
		
		// Add Menu Bar to Frame
		jframe.add(jmb);
		jframe.pack();
		jframe.setVisible(true);
	}
	
	public static void main (String args[]) {
		TermEx window = new TermEx("TermEx");
		window.setMenuBar();
	}
}
