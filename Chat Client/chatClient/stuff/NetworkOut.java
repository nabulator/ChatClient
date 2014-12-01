package stuff;

import java.io.PrintWriter;
import java.util.StringTokenizer;

public class NetworkOut implements Runnable{

	private PrintWriter out;
	private String command;
	
	public NetworkOut(PrintWriter out, String c)
	{
		this.out = out;
		this.command = c;
	}	
	
	public void run() {
		runCommand( command );
	}
	
	/**
	 * Runs the user command & checks user input
	 * @param command the command sent
	 */
	private void runCommand(String command)
	{
		String[] commands = { "JOIN", "SEND", "FETCH", "LIST", "WHISPER" };
		
		StringTokenizer st = new StringTokenizer(command);
		String keyword = st.nextToken();
		
		boolean found = false;
		for( int i=0 ; i<commands.length ; i++ )
			if( keyword.equals(commands[i]));
				found = true;
		
		if( !found )
			throw new IllegalArgumentException("Bad command: " + command);
		
		out.print(command);
		out.flush();
	}

}
