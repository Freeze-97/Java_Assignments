package se.miun.toya1800.dt062g.jpaint;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
* <h1>Client</h1>
* This class handles communications 
* with the server
* 
*
* @author  Tommy Yasi (toya1800)
* @version 1.0
* @since   2020-02-22
*/

public class Client {
	public static final String DEFAULT_ADDRESS = "localhost";
	public static final int DEFAULT_PORT = 10000;
	
	public static final String path = "C:" + File.separator + "tmp";
	
	private String address;
	private int port;
	private Socket s = null;
	
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public Client() {
		this.address = DEFAULT_ADDRESS;
		this.port = DEFAULT_PORT;
	}
	
	protected boolean connect() throws UnknownHostException, IOException{
		try {
			// Check if socket is already being used
			if(s == null) {
				s = new Socket(address, port);
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				return true;
			} else {
				System.err.println("Connection already exists");
				return false;
			}
		}
		catch(UnknownHostException e) {
			System.err.println(e.getMessage());
			return false;
		}
		catch(IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	protected void disconnect() throws IOException {
		try {
			// CLose all resources
			dis.close();
			dis = null;
			dos.close();
			dos = null;
			s.close();
			s = null;
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	protected String[] getFilenamesFromServer() throws UnknownHostException, IOException {
		// ArrayList instead of built-in array because it can resize
		ArrayList<String> files = new ArrayList<>();
		
		try {
			if(connect()) {
				dos.writeUTF("list"); // Sends command to the server side
				
				// Check if there are no files
				int amount = dis.readInt();
				if(amount == 0) {
					String[] zero = new String[0];
					return zero;
				}
				
				// Store all filenames
				for(int i = 0; i < amount; i++) {
					files.add(dis.readUTF());
				}
				disconnect();
			} else {
				return null;
			}
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		String[] names = files.toArray(new String[files.size()]);
		return names;
	}
	
	protected String getFileFromServer(String filename) throws UnknownHostException, IOException {
		if(connect()) {
			try {
				// If the catalog does not exist, create it
				if (!new File(path).exists()) {
					try {
						File file = new File(path);
						file.mkdirs();
					}
					catch (Exception e) {
						System.err.println(e.getMessage());
						System.err.println("Could not create dictionary");
					}
				}
				
				dos.writeUTF("load"); // Sends command to the server side
				dos.writeUTF(filename); // Search for the file
				String fullPath = path + File.separator + filename;
				
				// Check if it exists and start writing
				if(dis.readBoolean()) {
					FileOutputStream fs = new FileOutputStream(fullPath);
					
					// Stream all data
					byte [] data = new byte[dis.readInt()]; // New array and set size
					dis.read(data);
					fs.write(data);
					
					fs.close();
					fs = null;
				} else {
					return "";
				}
				disconnect(); // Clear streams, socket
				return fullPath;
			}
			catch (IOException e) {
				System.err.println(e.getMessage());
				return null;
			}
		} 
		else {
			System.err.println("Could not connect");
			return null;
		}
	}
	
	public boolean saveAsFileToServer(String localFilename, String serverFilename) {
		try {
			String fullPath = path + File.separator + localFilename;
			if(connect()) {
				File f = new File(fullPath);
				
				// If the file does not exist, return false
				if(!f.exists()) {
					return false;
				}
				
				// Else continue here
				dos.writeUTF("save"); // Sends command to the server side
				dos.writeUTF(serverFilename);
				dos.writeInt((int) Files.size(f.toPath()));
				
				// FileData to the server
				FileInputStream fs = new FileInputStream(f);
				
				byte[] dataByte = new byte[(int) Files.size(f.toPath())];
				int read;
				while ((read = fs.read(dataByte)) != -1) {
					dos.write(dataByte, 0, read);
				}
				fs.close();
				fs = null;
				
				// Disconnect and check if it managed to get saved or not
				boolean saved = dis.readBoolean();
				disconnect();
				return saved;
			}
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
		
		return false;
	}
	
	public boolean saveFileToServer(String localFilename) {
		// Same as saveFileToServer but the 2 arguments are the same string
		return saveAsFileToServer(localFilename, localFilename);
	}
}
