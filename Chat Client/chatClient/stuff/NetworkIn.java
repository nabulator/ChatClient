package stuff;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.Timer;

/**
 * A thread to get input
 * @author brian
 *
 */
public class NetworkIn implements Runnable{
	
	private Scanner in;
	private Window w;
	private Timer refreshScreen;
	
	public NetworkIn(Scanner in, Window w)
	{
		this.in = in;
		this.w = w;
		
		ActionListener ac = new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				getResults();
			}
		};
		refreshScreen = new Timer(1000, ac); //1 second
	}
	
	public void run()
	{
		refreshScreen.start();
	}
	
	public void getResults()
	{
		String results = null;
		if( in.hasNext() )
		{
			results = in.next();
			StringTokenizer st = new StringTokenizer(results);
			String protocolCode = st.nextToken();
			if(protocolCode.charAt(0) == 2)
				results = results.substring(4, results.length());
			
			results = in.nextLine();
			
		}
		w.addDisplayText( results );
	}
	
	
}
