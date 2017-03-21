package clueGame;

import java.awt.*;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class ControlGUI extends JPanel{
	private JTextField name;

	public ControlGUI()
	{
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,1));
		JPanel panel = createNamePanel();
		add(panel);
		panel = createButtonPanel();
		add(panel);
		
	}

	 private JPanel createNamePanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			//panel.setLayout(new GridLayout(1,2));
		 	JLabel nameLabel = new JLabel("Whose Turn?");
			name = new JTextField(20);
			name.setSize(50, 200);
			name.setText("Shredda");
			name.setEditable(false);
			
			panel.add(nameLabel);
			panel.add(name);
			JPanel bigPanel = new JPanel();
			bigPanel.setLayout(new GridLayout(1,3));
			bigPanel.add(panel);
			//panel.setBorder(new TitledBorder (new EtchedBorder(), "Who are you?"));
			JButton agree = new JButton("Next Player");
			JButton disagree = new JButton("Make an accusation");
			bigPanel.add(agree);
			bigPanel.add(disagree);
			return bigPanel;
	}
	 
	private JPanel createButtonPanel() {
		// no layout specified, so this is flow
		JPanel panel1 = new JPanel();
	 	// Use a grid layout, 1 row, 2 elements (label, text)
		//panel1.setLayout(new GridLayout(1,2));
		panel1.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		JLabel nameLabel = new JLabel("Roll");
		JTextField name = new JTextField(3);
		name.setSize(50, 200);
		
		name.setEditable(false);
		panel1.add(nameLabel);
		panel1.add(name);
		panel1.setSize(20, 20);
		JPanel panel2 = new JPanel();
	 	// Use a grid layout, 1 row, 2 elements (label, text)
		//panel2.setLayout(new GridLayout(1,2));
		panel2.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		nameLabel = new JLabel("Guess");
		name = new JTextField(30);
		name.setSize(50, 200);
		
		name.setEditable(false);
		panel2.add(nameLabel);
		panel2.add(name);
		
		JPanel panel3 = new JPanel();
	 	// Use a grid layout, 1 row, 2 elements (label, text)
		//panel3.setLayout(new GridLayout(1,2));
		panel3.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		nameLabel = new JLabel("Guess Result");
		name = new JTextField(10);
		name.setSize(50, 200);
		
		name.setEditable(false);
		panel3.add(nameLabel);
		panel3.add(name);
		
		JPanel bigPanel = new JPanel();
	 	// Use a grid layout, 1 row, 2 elements (label, text)
		bigPanel.setLayout(new GridLayout(1,3));
		bigPanel.add(panel1);
		bigPanel.add(panel2);
		bigPanel.add(panel3);
		
		
		
		
		return bigPanel;
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI Example");
		frame.setSize(1200, 250);	
		// Create the JPanel and add it to the JFrame
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}
}
