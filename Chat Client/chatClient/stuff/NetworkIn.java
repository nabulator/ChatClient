package stuff;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * A thread to get input
 * @author brian
 *
 */
public class NetworkIn implements Runnable{
	
	private Scanner in;
	private String output;
	
	public NetworkIn(Scanner in,  String output)
	{
		this.in = in;
		this.output = output;
	}
	
	public void run()
	{
		getResults();
	}
	
	public void getResults()
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
		output = output + "\n" + results;
	}
}
