package stuff;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server 
{
	private boolean isRunning = true;
	private List<ClientConnection> clients;
	private List<String> messages;
	
	public Server() throws IOException
	{
		final int PORT = 9999;
		ServerSocket server = new ServerSocket(PORT);
		clients = new ArrayList<ClientConnection>();
		messages = new ArrayList<String>();
		
		while( isRunning )
		{
			Socket s = server.accept();
			ClientConnection cc = new ClientConnection(s, messages.size());
			clients.add( cc );
			System.out.println("Get client! " + s.getLocalSocketAddress() );
			
			ServerService ss = new ServerService( cc, clients );
			Thread t = new Thread(ss);
		}
	}
	
	public void turnOn()
	{
		isRunning = true;
	}
	
	public void turnOff()
	{
		isRunning = false;
		
		for(int i=0 ; i < clients.size())
		{
			//TODO cleanup server connections
		}
	}
}
