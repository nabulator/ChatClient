package stuff;

import java.net.Socket;

public class ClientConnection {
	
	private Socket s;
	private String username;
	private int startMsgIndex, currentMsgIndex; 
	
	public ClientConnection(Socket sock, String usr, int start )
	{
		this.s = s;
		username = usr;
		startMsgIndex = start;
		currentMsgIndex = start;
	}
	
	public void incrementIndex()
	{
		currentMsgIndex++;
	}
	
	public String getUser()
	{
		return username;
	}

}
