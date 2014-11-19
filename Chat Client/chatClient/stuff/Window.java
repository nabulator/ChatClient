package stuff;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Window implements Runnable 
{
	private NetworkConnection network;
	private JFrame frame;
	private JTextArea userText, chatText;
	private JButton textButton;
	
	public final int FRAME_LENGTH = 300;
	public final int FRAME_WIDTH = 400;
	
	public Window(NetworkConnection network)
	{
		this.network = network;
		frame = new JFrame();
		frame.setSize(FRAME_LENGTH, FRAME_WIDTH);
		
		createUserText();
		createChatText();
		createButton();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void createUserText()
	{
		final int TEXT_ROWS = 3;
		final int TEXT_COLUMNS = 17;
		userText = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(userText);
		
		textButton = new JButton("Send");

		JPanel panel = new JPanel();
		panel.add(scrollPane);
		panel.add(textButton);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Enter Chat"));
		
		frame.add(panel, BorderLayout.SOUTH);
	}
	
	private void createChatText()
	{
		chatText = new JTextArea();
		chatText.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chatText);
		frame.add(scrollPane);
	}
	
	public void createButton()
	{
		class ChatListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String text = userText.getText();
				if(text.length() > 0)
				{
					if(text.charAt(0) == '/')
					{
						String command;
						if(text.contains(" "))
							command = text.substring(1, text.indexOf(" "));
						else
							command = text;
						
						/*
						 * FIX THIS METHOD NAME
						 */
						network.runCommnad(command);
					}
					else
						network.sendMsg(text);
				}
				
//				if(userText.getText().length() > 0)
//					chatText.append(">" + userText.getText() + "\n");
				
				userText.setText("");
			}
		}
		
		textButton.addActionListener(new ChatListener());
	}

	/**
	 * Updates the screen as long as login every few seconds
	 */
	public void run() 
	{
		System.out.println("update");
	}
}
