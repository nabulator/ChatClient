package stuff;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.StringTokenizer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A thread to get input
 * @author brian
 *
 */
public class NetworkIn {
	
	private Scanner in;
	private Window w;
	private Timer refreshScreen;
	
	public NetworkIn(Scanner in, Window w)
	{
		this.in = in;
		this.w = w;
		
		class PoopClass extends TimerTask
		{
			public void run() {
				getResults();				
			}
			
		}
		
		PoopClass poop = new PoopClass();
		
		refreshScreen = new Timer();
		refreshScreen.scheduleAtFixedRate(poop, 1000, 1000);
	}
	
	
	/**
	 * Removes success messages "2xx "
	 */
	public void getResults()
	{
		w.callFetch();
		
		String results = null;
		if( in.hasNext() )
		{
			results = in.next();
			StringTokenizer st = new StringTokenizer(results);
			String protocolCode = st.nextToken();
			if(protocolCode.charAt(0) == '2')
				results = results.substring(3, results.length());
			
			results = in.nextLine();
			
		}
		//System.out.println("\"" + results + "\"");
		if( results.length() > 0 )
			w.addDisplayText( results );
	}
	
	
}
