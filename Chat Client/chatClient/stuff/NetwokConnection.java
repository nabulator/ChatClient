package stuff;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class NetwokConnection {
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private String message;
	
	public NetwokConnection(String host, int port) throws IOException
	{
		socket = new Socket(host, port);
		InputStream inStream = socket.getInputStream();
		OutputStream outStream = socket.getOutputStream();
		
		in = new Scanner(inStream);
		out = new PrintWriter(outStream);
	}
	
	public void send(String msg)
	{
		String command = "SEND " + msg;
		out.print(command);
		out.flush();
	}
	
	public void fetch()
	{
		String command = "FETCH";
		out.print(command);
		out.flush();
	}
	
	public void join(String user)
	{
		String command = "JOIN " + user;
		out.print(command);
		out.flush();
	}
	
	public void list()
	{
		String command = "LIST";
		out.print(command);
		out.flush();
	}
	
	public void whisper(String id, String msg)
	{
		String command = "WHISPER " + id + " " + msg;
		out.print(command);
		out.flush();
	}
	
	public void getMessage(String msg){
		message=msg;
	}
	
	public void destructor() throws IOException
	{
		socket.close();
	}
}
