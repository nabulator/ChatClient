package stuff;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Window implements Runnable
{

	private JFrame frame;
	private JTextArea userTextBox, chatTextBox;
	private JButton textButton;
	private String chatText;
	private PrintWriter pw;
	
	public final int FRAME_LENGTH = 300;
	public final int FRAME_WIDTH = 400;
	
	public Window(PrintWriter pw)
	{
		chatText = "";
		this.pw = pw;
		frame = new JFrame();
		frame.setSize(FRAME_LENGTH, FRAME_WIDTH);
		
		createUserText();
		createChatText();
		createButton();
		
	}
	
	public void run ()
	{
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		System.out.println("Initialized JQBark Client");
	}
	
	private void createUserText()
	{
		final int TEXT_ROWS = 3;
		final int TEXT_COLUMNS = 17;
		userTextBox = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(userTextBox);
		
		textButton = new JButton("Send");

		JPanel panel = new JPanel();
		panel.add(scrollPane);
		panel.add(textButton);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Enter Chat"));
		
		frame.add(panel, BorderLayout.SOUTH);
	}
	
	private void createChatText()
	{
		chatTextBox = new JTextArea();
		chatTextBox.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chatTextBox);
		frame.add(scrollPane);
	}
	
	public void createButton()
	{
		class ChatListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				{
					enterText();
				}
			}
		}
		
		class KeyboardListener implements KeyListener
		{
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.equals(KeyEvent.VK_ENTER))
				{
					enterText();
				}
			}
			public void keyReleased(KeyEvent arg0) 
			{
				
			}

			public void keyTyped(KeyEvent arg0) 
			{
				if(arg0.equals(KeyEvent.VK_ENTER))
				{
					enterText();
				}
			}
			
		}
		textButton.addActionListener(new ChatListener());
		userTextBox.addKeyListener(new KeyboardListener());
	}
	
	public void addDisplayText( String s )
	{
		chatText += s + "\n";
		chatTextBox.setText( chatText );
	}
	
	public void callFetch()
	{
		NetworkOut out = new NetworkOut(pw, "FETCH");
		Thread t = new Thread(out);
		t.run();
	}
	
	public void enterText()
	{
		String text = userTextBox.getText();
		if(text.length() > 0)
		{
			NetworkOut out;
			
			if(text.charAt(0) == '/')
				out = new NetworkOut(pw, text.substring(1, text.length()) );
			else
				out = new NetworkOut(pw, "SEND " + text);
			
			Thread t = new Thread(out);
			t.run();
		}
		//clears text box
		userTextBox.setText("");
	}
	
}
