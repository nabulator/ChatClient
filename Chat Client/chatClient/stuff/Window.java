package stuff;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Window 
{
	private NetwokConnection network;
	private JFrame frame;
	private JTextArea userText;
	private JLabel chat;
	private JRadioButton textButton;
	private JPanel panel;
	
	public Window()
	{
		this.network = network;
		frame = new JFrame();
		userText = new JTextArea();
		userText.
		userText.setSize(200, 200);
		chat = new JLabel();
		textButton = new JRadioButton();
				
		frame.add(textButton, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(300, 400);
	}
	
	public void createUserText()
	{
		class ChatListener implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
//				network.setText(userText.getText());
			}
		}
		panel = new JPanel();
		textButton.addActionListener(new ChatListener());
		panel.add(userText);
		panel.add(textButton);
		
		frame.add(panel, BorderLayout.SOUTH);
	}
}
