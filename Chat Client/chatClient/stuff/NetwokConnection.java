package stuff;

import java.io.*;
import java.net.Socket;
import java.util.*;
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
	
	/**
	 * Runs the user command
	 * @param command
	 */
	public void runCommnad(String command)
	{
		String[] commands = { "JOIN", "SEND", "FETCH", "LIST", "WHISPER" };
		
		StringTokenizer st = new StringTokenizer(command);
		String keyword = st.nextToken();
		
		boolean found = false;
		for( int i=0 ; i<commands.length ; i++ )
			if( keyword.equals(commands[i]));
		
		if( !found )
			throw new IllegalArgumentException("Bad command");
		
		out.print(command);
		out.flush();
	}
	
	/**
	 * Sends a message to server 
	 */
	public void sendMsg(String msg)
	{
		out.print("SEND " + msg);
		out.flush();
	}
	
	/**
	 * Sends the fetch command to the server
	 */
	public void fetch()
	{
		out.print("FETCH");
		out.flush();
	}
	
	/**
	 * Gets the results. Removes success code, else prints error code
	 * @return The results ommiting success codes
	 */
	public String getResults()
	{
		String results = null;
		if( in.hasNext() )
		{
			StringTokenizer st = new StringTokenizer(results);
			String protocolCode = st.nextToken();
			if(protocolCode.charAt(0) == 2)
				results = results.substring(4, results.length());
			
			results = in.nextLine();
			
		}
		return results;
	}
	
	/**
	 * Closes the network connection
	 * @throws IOException
	 */
	public void destructor() throws IOException
	{
		socket.close();
	}
}
