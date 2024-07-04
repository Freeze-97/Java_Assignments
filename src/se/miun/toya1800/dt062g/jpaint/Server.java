package se.miun.toya1800.dt062g.jpaint;

import java.io.IOException;
import java.net.*;

/**
* <h1>Server</h1>
* Server where the connection between the clients
* will be created.
*
* @author  Tommy Yasi (toya1800)
* @version 1.0
* @since   2020-02-20
*/

public class Server {
	public static void main(String[] args) throws IOException {
		// If no argument or can't convert to int, use port 10000
		int port = 10000;
		if(args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			}
			catch (Exception e) {
				port = 10000;
				System.err.println(e.getMessage());
			}
		}
		
		ServerSocket ss = null;
		try {
			// ServerSocket to listen for clients at port
			ss = new ServerSocket(port);
			System.out.println("Server started on port " + ss.getLocalPort());
		
			while(true) {
			Socket s = ss.accept();
			System.out.println("New client connected from " + 
			s.getInetAddress().getHostAddress() + ":" + s.getPort());
			 new ClientHandler(s).start();
			}
		}
		catch(IOException e) {
			System.err.print(e.getMessage());
		}
	}
}
