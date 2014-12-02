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
	private List<String> msgs;
	private List<ClientConnection> clients;

	private Scanner in;
	private PrintWriter out;
	
	public ServerService(ClientConnection cc, List<String> m, List<ClientConnection> ccs) throws IOException
	{
		this.cc = cc;
		this.msgs = m;
		this.clients = ccs;
		
		//in = new Scanner( cc.getSocket().getInputStream() );
		//out = new PrintWriter( cc.getSocket().getOutputStream() );
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
		System.out.println("SS: Start Do service" );
		while(true)
		{
			if( in.hasNext() )
			{
				System.out.println("Got next!");
				String command = in.nextLine();
				executeCommnand(command);
			}
		}
	}
	
	public void executeCommnand(String command)
	{
		String copyOfCommand = new String(command);
		StringTokenizer st = new StringTokenizer(command);
		String keyword = st.nextToken();
		
		switch(keyword)
		{
			case "JOIN":
				String usr = st.nextToken();
				cc.updateUserName(usr);
				
				break;
			case "SEND":
				String txt = copyOfCommand.substring(5); //removes "SEND "
				msgs.add(txt);
				
				break;
			case "FETCH":
				if( cc.getCurrentIndex() <= msgs.size()-1 )
					out.print("200 OK");
				else
					out.print( msgs.get( cc.getCurrentIndex() ));
				out.flush();
				break;
			case "LIST":
				out.println( clients.toString() );
				out.flush();
				break;
			case "WHISPER":
				String id = st.nextToken();
				String msg = st.nextToken();
				out.println("Whisper is not implemented by the server :( Sorry");
				out.flush();
				break;
			default:
			{
				System.out.println("Bad command by user " + cc.getUser() + " socket ID : " + cc.getSocket().getInetAddress() );
				out.println("402 BAD COMMAND");
				out.flush();
			}
		}
		System.out.println("Command: " + command + " SOCKET ID: " + cc.getSocket().getLocalAddress() + " |Usr " + cc.getUser());
	}

}
