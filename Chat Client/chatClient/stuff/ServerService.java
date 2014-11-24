package stuff;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerService implements Runnable {

	private Socket s;
	private Scanner in;
	private PrintWriter out;
	
	public ServerService(Socket s) 
	{
		this.s = s;
	}
	
	public void run() 
	{
		try
		{
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
			if(!in.hasNext())
				return;
			String command = in.next();
			
			
		}
	}
	
	public void executeCommnand()
	{
		
	}

}
