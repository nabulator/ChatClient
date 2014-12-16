package stuff;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import jdk.nashorn.internal.runtime.regexp.joni.constants.CCSTATE;

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
				msgs.add( "S" + this.cc.getUser() + ": " + txt);
				
				break;
			case "FETCH":
				
				String fetchedText = "";
				if( cc.getCurrentIndex() < msgs.size() ) //client is behind
				{
					String everything = msgs.get( cc.getCurrentIndex() );
					char flag = everything.charAt(0);
					
					if( flag == 'W' ) //checks if whisper
					{
						String usrAndMsg = everything.substring(1);
						StringTokenizer st2 = new StringTokenizer(usrAndMsg);
						String potentialUser = st2.nextToken();
						
						if(cc.getUser().equalsIgnoreCase( potentialUser ) )//checks if the the client is intended recipient
							fetchedText = "200 " + usrAndMsg.substring( potentialUser.length() + 1 );
					}
					else
					{
						fetchedText = "200 " + msgs.get( cc.getCurrentIndex() ).substring(1);
					}
					
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
				int substringCutoff = new String("whisper").length() + 2 + id.length();
				String msg = command.substring( substringCutoff );
				
				//TODO check if user exists, warns sender if recipient is bad
				//ClientConnection found = 
				
				msgs.add( "W" + id + " " + this.cc.getUser() + ": " + msg);
				
				//out.println("400 Whisper is not implemented by the server :( Sorry");
				//out.flush();
				break;
			case "DISCONNECT":
			case "EXIT":
				try {
					cc.getSocket().close();
					clients.remove( cc );
					executeCommnand("SEND " + cc.getUser() + " has quit");
					out.println("Exited the server!");
					out.flush();
				} catch (IOException e) {
					out.println("Failed to exit!");
					out.flush();
					e.printStackTrace();
				}
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
