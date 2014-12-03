package stuff;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException
	{
		String ip = "10.5.100.93";
		int port = 16002;
		
		Socket s = new Socket(ip, port);
		Scanner scan = new Scanner( s.getInputStream() );
		PrintWriter pw = new PrintWriter( s.getOutputStream() );
		Window w = new Window(pw);
		NetworkIn in = new NetworkIn( scan, w );
		
		System.out.println(" FQBark Client Sucessful init");
		
		Thread t1 = new Thread(w);
		t1.run();
	}

}
