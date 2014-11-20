package stuff;

public class Main {

	public static void main(String[] args) {
	
		Window w = new Window(null, null);
		NetworkIn in = new NetworkIn(null, w);
		
		Thread t = new Thread(in);
	}

}
