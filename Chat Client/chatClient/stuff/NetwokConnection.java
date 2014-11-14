package stuff;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class NetwokConnection {
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String command;
	
	public NetwokConnection(String host, int port) throws IOException
	{
		socket = new Socket(host, port);
		InputStream inStream = socket.getInputStream();
		OutputStream outStream = socket.getOutputStream();
		
		in = new Scanner(inStream);
		out = new PrintWriter(outStream);
		
		command = "JOIN fjoe";
		out.print(command);
		out.flush();
	}
	
	public void getCommnad(String msg)
	{
		//d
	}
	
	public void destructor() throws IOException
	{
		socket.close();
	}
}
