package stuff;

import java.net.Socket;
import java.net.UnknownHostException;

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
		try {
			userName = s.getInetAddress().getLocalHost().toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			userName = new Double(Math.random() * Double.MAX_VALUE).toString();
			e.printStackTrace();
		}
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
