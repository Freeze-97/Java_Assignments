package se.miun.toya1800.dt062g.jpaint;

import javax.swing.SwingUtilities;

/**
 * <h1>Assignment 7</h1> This class is the starting point for the drawing
 * application. It creates our JFrame and makes it visible.
 * 
 *
 * @author Tommy Yasi (toya1800)
 * @version 1.0
 * @since 2020-02-04
 */
public class Assignment7 {

	public static void main(String[] args) {
		// Make sure GUI is created on the event dispatching thread
		// This will be explained in the lesson about threads
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JavaFrame().setVisible(true);
			}
		});
	}
}