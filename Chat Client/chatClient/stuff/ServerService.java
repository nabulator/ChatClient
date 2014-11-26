package stuff;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ServerService implements Runnable {

	private ClientConnection cc;
	private List<ClientConnection> c;
	private Scanner in;
	private PrintWriter out;
	
	public ServerService(ClientConnection cc, List<ClientConnection> c) 
	{
		this.cc = cc;
		this.c = c;
	}
	
	public void run() 
	{
		try
		{
			Socket s = cc.getSocket();
			try
			{
				in = new Scanner( s.getInputStream() ); 
				out = new PrintWriter( s.getOutputStream() );
				doService();
			}
			finally
			{
				s.close();
			}
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
		
	}
	
	public void doService ()
	{
		while(true)
		{
			if( in.hasNext() )
			{
				String command = in.next();
				executeCommnand(command);
			}			
		}
	}
	
	public void executeCommnand(String command)
	{
		StringTokenizer st = new StringTokenizer(command);
		String keyword = st.nextToken();
		
		switch(keyword)
		{
			case "JOIN":
				String usr = st.nextToken();
				
				break;
			case "SEND":
				String txt = st.nextToken();
				
				break;
			case "FETCH":
				
				break;
			case "LIST":
				out.println( c.toString() );
				out.flush();
				break;
			case "WHISPER":
				String id = st.nextToken();
				String msg = st.nextToken();
				
				break;
			default:
			{
				System.out.println("Bad command: " + command);
				out.println("402 BAD COMMAND");
				out.flush();
			}
			
		}
	}

}
