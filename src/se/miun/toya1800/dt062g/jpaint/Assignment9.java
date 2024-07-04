package se.miun.toya1800.dt062g.jpaint;

import javax.swing.SwingUtilities;


/**
* <h1>Assignment 9</h1>
* This class is the starting point for the drawing application.
* It creates our JFrame and makes it visible. A Client is created
* that the JFrame uses to communicate with the server.
* 
*
* @author  Tommy Yasi (toya1800)
* @version 1.0
* @since   2020-03-25
*/
public class Assignment9 {

	public static void main(String[] args) {
		// Default address and port to server.
		String address = Client.DEFAULT_ADDRESS;
		int port = Client.DEFAULT_PORT;
				
		// Check arguments if different address and port should be used.		
		// YOUR CODE HERE
		
		/* Declare this variables as final so that it can be used
		   in our anonymous class below (new Runnable). A variable referenced
		   from inside an anonymous class (that is not declared in the anonymous
		   class itself) must be either an instance variable or a local variable
		   that is not seen to change in the calling scope. An anonymous
		   class cannot access local variables in its enclosing scope that are not
		   declared as final.
		*/
		@SuppressWarnings("unused")
		final Client client = new Client(address, port);
		
		// Make sure GUI is created on the event dispatching thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JavaFrame().setVisible(true);
			}
		});
	}
}