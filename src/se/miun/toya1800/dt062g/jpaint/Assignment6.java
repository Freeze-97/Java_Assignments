package se.miun.toya1800.dt062g.jpaint;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * <h1>Assignment 6</h1> This class is the starting point for the drawing
 * application. It creates our JFrame and makes it visible.
 * 
 *
 * @author Tommy Yasi (toya1800)
 * @version 1.0
 * @since 2019-12-15
 */

public class Assignment6 {

	public static void main(String[] args) {
		// Make sure GUI is created on the event dispatching thread
		// This will be explained in the lesson about threads
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				new JavaFrame().setVisible(true);
			}
		});
	}
}
