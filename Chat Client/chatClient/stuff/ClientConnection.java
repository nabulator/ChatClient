package stuff;

import java.net.Socket;

public class ClientConnection {
	
	private Socket s;
	private String username;
	private int startMsgIndex, currentMsgIndex; 
	private boolean hasJoined;
	
	public ClientConnection(Socket sock, int start)
	{
		this.s = s;
		username = s.getLocalAddress().toString();
		startMsgIndex = start;
		currentMsgIndex = start;
		hasJoined = false;
	}
	
	public void updateUserName(String newName)
	{	
		hasJoined = true;
		username = newName;
	}
	
	public void incrementIndex()
	{
		currentMsgIndex++;
	}
	
	public String getUser()
	{
		return username;
	}
	
	public boolean hasJoined()
	{
		return hasJoined;
	}
	
	public Socket getSocket()
	{
		return s;
	}

}
