package stuff;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException
	{
		String ip = "127.0.0.1";
		int port = 16001;
		
		Socket s = new Socket(ip, port);
		Scanner scan = new Scanner( s.getInputStream() );
		PrintWriter pw = new PrintWriter( s.getOutputStream() );
		Window w = new Window(pw);
		NetworkIn in = new NetworkIn( scan, w );
		
		Thread t = new Thread(in);
		t.run();
	}

}
