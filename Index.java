import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;


public class Index extends JPanel {

	static JLabel l1;	
	static JButton b1, b2, b3, b4, b5, b6, b7;
	static JFrame jf;
	static Index stp;
	GridBagConstraints gbc = new GridBagConstraints();
	
	
	public Index() { // Constructor
		setLayout(new GridBagLayout());
		
		l1 = new JLabel("List of Training Programs:");
		
		b1 = new JButton("Basic Calculator");
		b2 = new JButton("Basic Tic-Tac-Toe Game");
		b3 = new JButton("Basic Visicalc Spreadsheet");
		b4 = new JButton("Basic Paint Program");

		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		
		gbc.insets = new Insets(6,6,6,6);
		//gbc.insets = new Insets(3,3,3,3);
		gbc.gridwidth =1; gbc.gridx = 1;
		
		gbc.gridy = 1;  add(l1,gbc);
		gbc.gridy = 2;  add(b1,gbc);
		gbc.gridy = 3;  add(b2,gbc);
		gbc.gridy = 4;	add(b3,gbc);
		gbc.gridy = 5;	add(b4,gbc);
	}

	public void startM() {
		jf = new JFrame("Training Programs - Java - Jun/2016");
		stp = new Index();

		jf.setSize(400,350);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(stp);
		jf.setVisible(true);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calculator c = new Calculator();
				c.startM();
				
			}
		});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TicTacToe c = new TicTacToe();
				c.startM();
				
			}
		});
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VisiCalc c = new VisiCalc();
				c.startM();
				
			}
		});
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Paint c = new Paint();
				c.startM();
				
			}
		});
		
	}
	

	
	

}
