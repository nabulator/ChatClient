package stuff;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StartServer {
	
	public static void main(String[] args) throws IOException {
		
		List<ClientConnection> clients = new ArrayList<ClientConnection>();
		List<String> messages = new ArrayList<String>();
		
		Server s = new Server(messages, clients);
		
	}

}
