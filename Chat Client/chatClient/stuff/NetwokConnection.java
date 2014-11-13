package stuff;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class NetwokConnection {
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	public NetwokConnection(String host, int port) throws IOException
	{
		socket = new Socket(host, port);
		InputStream inStream = socket.getInputStream();
		OutputStream outStream = socket.getOutputStream();
		
		in = new Scanner(inStream);
		out = new PrintWriter(outStream);
	}
	
	
	
	public void destructor () throws IOException
	{
		socket.close();
	}
}
