package se.miun.toya1800.dt062g.jpaint;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

/**
* <h1>ClientHandler</h1>
* This class creates a thread for every connection
* to the server
*
* @author  Tommy Yasi (toya1800)
* @version 1.0
* @since   2020-02-21
*/

public class ClientHandler extends Thread {
	private final Socket socket;
	private final DataInputStream dis;
	private final DataOutputStream dos;
	
	private final String catalogPath = "."+ File.separator + "xml" + File.separator;
	
	public ClientHandler(Socket s) throws IOException {
		this.socket = s;
		this.dis = new DataInputStream(s.getInputStream());
		this.dos = new DataOutputStream(s.getOutputStream());
	}
	
	@SuppressWarnings("finally")
	@Override 
	public void run() {
		String delivered;
		
		while (true) {
			try {
				delivered = dis.readUTF(); 
				
				switch(delivered) {
				case "save": saveFile();
					break;
				case "load": loadFile();
					break;
				case "list": listFiles();
					break;
				default: System.err.println("Invalid input!");
				    break;
				}
			}
			catch(IOException e) {
				System.err.println(e.getMessage());
			}
			
			finally {
				System.out.println("Client from " +
				    socket.getInetAddress().getHostAddress() + ":" + socket.getPort() +
							" has disconnected");
				try {
					// Close all resources
					dos.close();
					dis.close();
					socket.close();
				} 
				catch (IOException e) {
					System.err.println(e.getMessage());
				}
				break; // stop while loop
			}
		}
	}
	
	private void saveFile() throws IOException {
		// Output to console
		System.out.println("Command 'save' received from " +
		socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
		String filename = dis.readUTF();
		
		System.out.println("Recieving " + filename + " from " + 
		socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
		
		String fullPath = catalogPath + filename;
		FileOutputStream fs = new FileOutputStream(fullPath);
		int fileSize = dis.readInt();
		byte[] fileByte = new byte[fileSize];
		try {
			dis.read(fileByte);
			fs.write(fileByte);
			fs.close();
		}
		catch(IOException e) {
			System.err.println(e.getMessage());
		}
		dos.writeBoolean(new File(fullPath).exists()); // Checking if it worked
		
		// Clear OutputStream
		dos.flush();
		fs = null;
	}
	
	private void loadFile() throws IOException {
		// Output to console
		System.out.println("Command 'load' received from " +
		socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
		String filename;
		File file = null;
		
		try {
			filename = dis.readUTF();
			file = new File(catalogPath + filename);
		}
		catch(IOException e) {
			System.err.println("Could not load file");
		}
		
		// Check if the file exists
		if(!file.exists()) {
			System.err.println("File was not found");
			dos.writeBoolean(false);
			return;
		}
		dos.writeBoolean(true); // File exists
		
		long fileSize = Files.size(file.toPath());
		dos.writeInt((int) fileSize);
		System.out.println("Size of " + file.getName() + " is " 
		+ fileSize + " bytes");
		
		System.out.println("Sending file to " + 
		socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
		
		FileInputStream fs = new FileInputStream(file);
		try {
			byte[] dataByte = new byte[(int) fileSize];
			int read;
			while ((read = fs.read(dataByte))  != -1) {
				dos.write(dataByte, 0, read);
			}
		}
		catch(IOException e) {
			System.err.println(e.getMessage());
		}
		
		// Clear OutputStream
		dos.flush();
		fs.close();
		fs = null;
	}
	
	private void listFiles() throws IOException {
		// Output to console
		System.out.println("Command 'list' received from " +
		socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
		
		File catalog = new File(catalogPath); // XML files are in this catalog only
		
		// Create filter so only files which ends with ".xml" is chosen
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File f, String name) {
				return name.toLowerCase().endsWith(".xml");
				}
		};
		
		// Add all files but with filter
		File[] files = catalog.listFiles(filter);
		
		try {
			dos.writeInt(files.length); // number of files
			
			// Output to console
			System.out.println("Sending list of files to " +
			socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
		
			// Sending name of every file
			for (File i : files) {
				dos.writeUTF(i.getName());
			}
		}	
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
