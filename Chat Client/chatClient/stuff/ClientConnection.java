package stuff;

import java.net.Socket;

/**
 * A bunch of dumb variables. Like a strucutre in c
 * @author 167504
 *
 */
public class ClientConnection {
	
	private Socket s;
	private String userName;
	private int currentMsgIndex; 
	private boolean hasJoined;
	
	public ClientConnection(Socket sock, int currentMsgIndex)
	{
		this.s = sock;
		userName = s.getLocalAddress().toString();
		this.currentMsgIndex = currentMsgIndex;
		hasJoined = false;
	}
	
	public void updateUserName(String newName)
	{	
		hasJoined = true;
		userName = newName;
	}
	
	public void incrementIndex()
	{
		currentMsgIndex++;
	}
	
	public int getCurrentIndex()
	{
		return currentMsgIndex;
	}
	
	public String getUser()
	{
		return userName;
	}
	
	public boolean hasJoined()
	{
		return hasJoined;
	}
	
	public Socket getSocket()
	{
		return s;
	}
	
	public String toString()
	{
		return userName;
	}

}
