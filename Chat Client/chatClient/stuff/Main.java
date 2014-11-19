package stuff;

public class Main {

	public static void main(String[] args) {
	
		Window w = new Window(null);
		Thread t = new Thread(w);
		t.start(); //adf
	}

}
