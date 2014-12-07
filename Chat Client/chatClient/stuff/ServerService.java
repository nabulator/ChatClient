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
		while(true)
		{
			if( in.hasNext() )
			{
				String command = in.nextLine();
				executeCommnand(command);
			}
		}
	}
	
	public void executeCommnand(String command)
	{
		String copyOfCommand = new String(command);
		StringTokenizer st = new StringTokenizer(command);
		String keyword = st.hasMoreTokens() ? st.nextToken().toUpperCase() : "NO_TOKEN";
		
		switch(keyword)
		{
			case "JOIN":
				String usr = st.nextToken();

				//check if string already used
				boolean nameTaken = false;
				for( int i=0 ; i<clients.size() && ! nameTaken ; i++ )
					if( clients.get(i).getUser().equals( usr ) )
						nameTaken = true;
				
				if( ! nameTaken )
					cc.updateUserName(usr);
				else
				{
					out.println("421 Bad UserName");
					out.flush();
				}				
				break;
			case "SEND":
				String txt = copyOfCommand.substring(5); //removes "SEND "
				msgs.add(this.cc.getUser() + ": " + txt);
				
				break;
			case "FETCH":
				
				String fetchedText;
				if( cc.getCurrentIndex() < msgs.size() ) //client is behind
				{
					fetchedText = "200 " + msgs.get( cc.getCurrentIndex() );
					cc.incrementIndex();
				}
				else if ( cc.getCurrentIndex() == msgs.size() )	//client already up to date
					fetchedText = "201";
				else
					fetchedText = "401 BADID " + cc.getCurrentIndex();
				
				//System.out.println("OUT " + fetchedText );
				out.println( fetchedText );
				out.flush();
				break;
				
			case "LIST":
				out.println( "200 Users Online: " + clients.size() + " " + clients.toString() );
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
		System.out.println("Usr " + cc.getUser() +"\t Socket " + cc.getSocket().getLocalAddress() +"\t Cmd " + command);
	}

}
