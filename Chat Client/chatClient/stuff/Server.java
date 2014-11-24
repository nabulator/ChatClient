package stuff;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server 
{
	private static boolean isRunning = true;
	private static List<Socket> sockets;
	
	public static void main(String[] args) throws IOException
	{
		final int PORT = 9999;
		ServerSocket server = new ServerSocket(PORT);
		sockets = new ArrayList<Socket>();
		
		while( isRunning )
		{
			Socket s = server.accept();
			sockets.add(s);
			System.out.println("Get client! " + s.getLocalSocketAddress() );
			
			ServerService ss = new ServerService(s);
			Thread t = new Thread(ss);
			t.start();
		}
	}
	
	private void turnOn()
	{
		isRunning = true;
	}
	
	private void turnOff()
	{
		isRunning = false;
		
		for(int i=0; i<sockets.size(); i++)
		{
			//TODO cleanup server connections
		}
	}
}
