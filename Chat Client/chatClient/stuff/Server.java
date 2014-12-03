package stuff;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server 
{
	private List<String> msgs;
	private List<ClientConnection> clients;
	
	public Server(List<String> m, List<ClientConnection> ccs) throws IOException
	{
		final int PORT = 16002;
		ServerSocket server = new ServerSocket(PORT);
		msgs = m;
		clients = ccs;
		
		System.out.println("Start Server Franciso !");
		
		while( true )
		{
				Socket s = server.accept();
				ClientConnection cc = new ClientConnection(s, msgs.size() );
				System.out.println("Get client! " + s.getLocalSocketAddress()  );
				
				ServerService ss = new ServerService( cc, msgs, clients );
				Thread t = new Thread(ss);
				t.run();
				System.out.println("Actived ServerService Thread");
		}
	}
		
	//TODO cleanup connections? probably not
}
