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


public class Calculator extends JPanel {

	static JTextField display;	
	static JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
	static JButton bAdd, bSub, bMult, bDiv, bDot, bC;
	static JFrame jf;
	static Calculator stp;
	GridBagConstraints gbc = new GridBagConstraints();
	static boolean flag = false;
	static double n1 = 0, n2 = 0;
	String strD = "";
	
	
	
	public Calculator() { // Constructor
		setLayout(new GridBagLayout());
		
		display = new JTextField();
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setEditable(false);
		display.setFont(new Font("Calibri", Font.BOLD, 14));		
		
		b0 = new JButton("0");
		b1 = new JButton("1");
		b2 = new JButton("2");
		b3 = new JButton("3");
		b4 = new JButton("4");
		b5 = new JButton("5");
		b6 = new JButton("6");
		b7 = new JButton("7");
		b8 = new JButton("8");
		b9 = new JButton("9");
		bAdd = new JButton("+");
		bSub = new JButton("-");
		bMult = new JButton("*");
		bDiv = new JButton("/");
		bDot = new JButton(".");
		bC = new JButton("C");
		

		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.anchor = GridBagConstraints.NORTHWEST;
		
		//gbc.insets = new Insets(6,6,6,6);
		gbc.insets = new Insets(3,3,3,3);
		gbc.gridwidth =4;
		gbc.gridx = 1;
		
		gbc.gridx = 1;add(display,gbc);
		
		
		gbc.gridwidth =1;
		gbc.gridy = 1;
		gbc.gridx = 1;add(b0,gbc);
		gbc.gridx = 2;add(bDot,gbc);
		gbc.gridx = 3;add(bC,gbc);
		gbc.gridx = 4;add(bAdd,gbc);
		
		gbc.gridy = 2;
		gbc.gridx = 1;add(b1,gbc);
		gbc.gridx = 2;add(b2,gbc);
		gbc.gridx = 3;add(b3,gbc);
		gbc.gridx = 4;add(bSub,gbc);
		
		gbc.gridy = 3;
		gbc.gridx = 1;add(b4,gbc);
		gbc.gridx = 2;add(b5,gbc);
		gbc.gridx = 3;add(b6,gbc);
		gbc.gridx = 4;add(bMult,gbc);
		
		gbc.gridy = 4;
		gbc.gridx = 1;add(b7,gbc);
		gbc.gridx = 2;add(b8,gbc);
		gbc.gridx = 3;add(b9,gbc);
		gbc.gridx = 4;add(bDiv,gbc);
		  
	}

	public void startM() {
		jf = new JFrame("Basic Calculator");
		stp = new Calculator();
		jf.setSize(240,220);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		//jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(stp);
		jf.setVisible(true);
		
		b0.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("0");}});
		b1.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("1");}});
		b2.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("2");}});
		b3.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("3");}});
		b4.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("4");}});
		b5.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("5");}});
		b6.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("6");}});
		b7.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("7");}});
		b8.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("8");}});
		b9.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("9");}});
		bAdd.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("+");}});
		bSub.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("-");}});
		bMult.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("*");}});
		bDiv.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("/");}});
		bDot.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate(".");}});
		bC.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{Calculate("C");}});
		
		
	}
	
	
	private void Calculate(String txt) {
		
		if (txt=="+") {Operation("+"); return;}
		if (txt=="-") {Operation("-"); return;}
		if (txt=="*") {Operation("*"); return;}
		if (txt=="/") {Operation("/"); return;}
		if (txt=="C") {
			display.setText("");
			n1 = 0; n2 = 0; flag = false;
			return;
		}
		if (flag == false) {
			display.setText(txt);
			flag = true;
		} else {
			strD = display.getText() + txt; 
			display.setText(strD);
		}
		
		
	}
	
	private void Operation(String txt) {
		n2 = Double.parseDouble(display.getText());
		if (txt=="+") {n2 = n1 + n2;}
		if (txt=="-") {n2 = n1 - n2;}
		if (txt=="*") {n2 = n1 * n2;}
		if (txt=="/") {n2 = n1 / n2;}
		strD = Double.toString(n2);
		display.setText(strD);
		flag = false;
		n1 = n2;
		
	}	
	
}
