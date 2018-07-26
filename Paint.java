
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;



public class Paint {
	// GUI Variables
	static JLabel l1, l2;
	static JButton b1, b2, b3, b4, b5, b6, b7, b8, b9;
	static JButton bNewGame, bExit;
	static JTextField t1, t2, t3;
	static JFrame jf;
	static VisiCalc stp;
	static GridBagConstraints gbc, gbc2, gbc3, gbc4;
	static JPanel panel1, panel2, panel3, panel4;
	// Where the GUI is created:
	static JMenuBar menuBar;
	static JMenu menu, submenu;
	static JMenuItem menuItem;
	static JCheckBox ck1;
	static JComboBox cbox1;

	static JTable jt;
	static String[] columns;
	static String[][] data;
	static String header;

	static JColorChooser cd;
	
	// Project Variables
    //Project Variables
    Graphics2D g1, g2;
    Color dColor;
    //Pen dPen;
    //Brush dBrush;
    Font dFont;
    int size_h, size_w, dsize = 50, penSize = 2;
    String selShape = "", selItem = "";
    iPoint lp1, lp2, p1, p2;
    
    String currFile = "";
    BufferedImage currBmp;
    Image currImg;
    boolean flagImg = false;
    boolean flagPaint = false, flagFill = false, flagP1= false;
    Rectangle eR;
    
	public Paint() { // Constructor
		// setLayout(new GridBagLayout());

		// Panels (Group Box)
		panel1 = new JPanel();
		// panel1.setSize(320,30);
		panel1.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "[Select Shape]-----[Flag]---[Size]-[Pen]------[   Text   ]"));
		// panel1.setLayout(new FlowLayout());
		GridBagLayout layout = new GridBagLayout();
		panel1.setLayout(layout);

		panel2 = new JPanel();
		// panel2.setSize(320,30);
		panel2.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Shortcuts:"));
		// panel2.setLayout(new FlowLayout());
		GridBagLayout layout2 = new GridBagLayout();
		panel2.setLayout(layout2);

		panel3 = new JPanel();
		//panel3.setSize(640, 480);
		panel3.setPreferredSize(new Dimension(640, 480));
		panel3.setMinimumSize(new Dimension(640, 480));
		panel3.setBorder(new TitledBorder(new LineBorder(Color.black, 1), ""));
		//GridBagLayout layout3 = new GridBagLayout();
		//panel4.setLayout(layout4);
		
		
		panel4 = new JPanel();
		panel4.setSize(680, 560);
		// panel4.setBorder(new TitledBorder (new LineBorder (Color.black,
		// 1),""));
		// panel4.setLayout(new FlowLayout());
		GridBagLayout layout4 = new GridBagLayout();
		panel4.setLayout(layout4);

		// ***********************************************************************************
		// -------- Table ----------
		JScrollPane JCP = new JScrollPane(panel3);
		JCP.setPreferredSize(new Dimension(640, 480));
		JCP.setMinimumSize(new Dimension(640, 480));
		
		// ***********************************************************************************

		// GridBad (Layout setup)
		gbc = new GridBagConstraints();
		gbc2 = new GridBagConstraints();
		gbc3 = new GridBagConstraints();
		gbc4 = new GridBagConstraints();
		
		//ComboBox and CheckBox
		cbox1 = new JComboBox();
		cbox1.setFont(new Font("Calibri", Font.BOLD, 12));
		cbox1.setPreferredSize(new Dimension(100, 20));
		cbox1.setMinimumSize(new Dimension(100, 20));
		
		ck1 = new JCheckBox("Fill");
		ck1.setFont(new Font("Calibri", Font.BOLD, 12));		

		l1 = new JLabel("Selected Shape: Drawing");
		l1.setFont(new Font("Calibri", Font.BOLD, 14));
		// TextFields
		
		t1 = new JTextField("");
		t1.setPreferredSize(new Dimension(30, 20));
		t1.setMinimumSize(new Dimension(30, 20));
		t2 = new JTextField("");
		t2.setPreferredSize(new Dimension(30, 20));
		t2.setMinimumSize(new Dimension(30, 20));
		t3 = new JTextField("");
		t3.setPreferredSize(new Dimension(120, 20));
		t3.setMinimumSize(new Dimension(120, 20));
		// Buttons
		int size = 12;
		int b_w = 60, b_h = 20;

		b1 = new JButton("Cut");
		b1.setFont(new Font("Calibri", Font.BOLD, size));
		b1.setPreferredSize(new Dimension(b_w, b_h));
		b1.setMinimumSize(new Dimension(b_w, b_h));
		
		b2 = new JButton("Copy");
		b2.setFont(new Font("Calibri", Font.BOLD, size));
		b3 = new JButton("Paste");
		b3.setFont(new Font("Calibri", Font.BOLD, size));
		b4 = new JButton("Font");
		b4.setFont(new Font("Calibri", Font.BOLD, size));
		b5 = new JButton("Color");
		b5.setFont(new Font("Calibri", Font.BOLD, size));
		b6 = new JButton("Exit");
		b6.setFont(new Font("Calibri", Font.BOLD, size));
		b7 = new JButton("Clear");
		b7.setFont(new Font("Calibri", Font.BOLD, size));
		
		// ************ Panel #1 ************************
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(2, 2, 2, 2);

		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.gridx = 1;
		panel1.add(cbox1, gbc);
		gbc.gridx = 2;
		panel1.add(ck1, gbc);
		gbc.gridx = 3;
		
		panel1.add(t1, gbc);
		gbc.gridx = 4;
		panel1.add(t2, gbc);
		gbc.gridx = 5;
		panel1.add(t3, gbc);

		// ************ Panel #2 ************************
		gbc2.fill = GridBagConstraints.BOTH;
		gbc2.anchor = GridBagConstraints.NORTHWEST;
		gbc2.insets = new Insets(3, 3, 3, 3);

		gbc2.gridwidth = 1;
		gbc2.gridy = 1;

		gbc2.gridx = 0;
		panel2.add(b7, gbc2);
		gbc2.gridx = 1;
		panel2.add(b1, gbc2);
		gbc2.gridx = 2;
		panel2.add(b2, gbc2);
		gbc2.gridx = 3;
		panel2.add(b3, gbc2);
		gbc2.gridx = 4;
		panel2.add(b4, gbc2);
		gbc2.gridx = 5;
		panel2.add(b5, gbc2);
		gbc2.gridx = 6;
		panel2.add(b6, gbc2);

		// ************ Menu ************************
		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);

		menuItem = new JMenuItem("New File", KeyEvent.VK_N);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFile("New File");
			}
		});

		menuItem = new JMenuItem("Open File", KeyEvent.VK_O);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFile("Open File");
			}
		});

		menuItem = new JMenuItem("Save File", KeyEvent.VK_S);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFile("Save File");
			}
		});

		menuItem = new JMenuItem("Save File As", KeyEvent.VK_A);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFile("Save File As");
			}
		});

		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFile("Exit");
			}
		});

		// Build the second menu.
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);
		menuBar.add(menu);

		menuItem = new JMenuItem("Cut", KeyEvent.VK_T);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEdit("Cut");
			}
		});
		menuItem = new JMenuItem("Copy", KeyEvent.VK_C);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEdit("Copy");
			}
		});

		menuItem = new JMenuItem("Paste", KeyEvent.VK_P);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEdit("Paste");
			}
		});
		menu.addSeparator();
		menuItem = new JMenuItem("Change Font", KeyEvent.VK_F);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Font");
			}
		});

		menuItem = new JMenuItem("Change Color", KeyEvent.VK_L);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Color");
			}
		});

		// Build third menu in the menu bar.
		menu = new JMenu("Help");
		menuItem = new JMenuItem("About", KeyEvent.VK_A);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("About");
			}
		});

		menu.setMnemonic(KeyEvent.VK_H);
		// menu.getAccessibleContext().setAccessibleDescription("This menu does
		// nothing");
		menuBar.add(menu);

		// frame.setJMenuBar(theJMenuBar);

		// ************ Panel #4 ************************
		gbc4.fill = GridBagConstraints.BOTH;
		gbc4.anchor = GridBagConstraints.NORTHWEST;
		gbc4.insets = new Insets(3, 3, 3, 3);

		gbc4.gridwidth = 1;
		gbc4.gridy = 1;
		gbc4.gridx = 1;
		panel4.add(panel1, gbc4);
		gbc4.gridx = 2;
		panel4.add(panel2, gbc4);

		gbc4.gridy = 2;
		gbc4.gridx = 1;
		panel4.add(l1, gbc4);
		
		gbc4.gridwidth = 2;
		gbc4.gridheight = 2;
		gbc4.gridx = 1;
		gbc4.gridy = 3;
		gbc4.gridwidth = 640;
		gbc4.gridheight = 480;
		panel4.add(JCP, gbc4);
		
		
		// ************************************************
	}

	//***************** Format Components *****************
	public JButton f_button(String txt, int w, int h) {
		JButton btn = new JButton (txt);
		btn = new JButton("Clear");
		btn.setFont(new Font("Calibri", Font.BOLD, 12));
		btn.setPreferredSize(new Dimension(w, h));
		btn.setMinimumSize(new Dimension(w, h));
		return btn;
	}
	//*****************************************************
	
	public void startM() {
		jf = new JFrame("Basic Paint Program");
		//stp = new Paint();

		jf.setSize(820, 640);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		// jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// jf.add(stp);
		jf.add(panel4);
		jf.setJMenuBar(menuBar);
		jf.setVisible(true);


		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEdit("Cut");
			}
		});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEdit("Copy");
			}
		});
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEdit("Paste");
			}
		});
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFontChooser fc = new JFontChooser();
				if(fc.showDialog(null) == JFontChooser.OK_OPTION) {
					dFont = fc.getSelectedFont();
					Font tFont = dFont.deriveFont(12f);
					t3.setFont(tFont);
				}
				
			}
		});
		
		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//dColor = Color.WHITE;
					dColor =  JColorChooser.showDialog(b5,
		                     "Choose Background Color",
		                     Color.BLACK); 
					//b5.setForeground(dColor);
				//********************************************************
				
			}
		});
		b6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jf.dispose();
			}
		});
		b7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearScreen();
			}
		});

		//************ Change the flagFill, penSize, and Shape Size *************
		ck1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setShape();
			}
		});
		t1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setShape();
			}
		});
		t2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setShape();
			}
		});
		//***********************************************************************
		
		start();//Setup
		mouseEvents();//Add Mouse Events to panel3
	}

	private void menuSel(String txt) {
		switch (txt) {

		case "New File":
			break;
		case "Exit":
			jf.dispose();
			break;
		case "About":
			msgbox("Basic Paint Program \n Made by P. Ramos in Jul/2016\n As Part of a Training Program",
					"Paint Program");
			break;
		default:
			System.out.println(txt);
		}

	}

	private int msgbox(String msg, String title) {
		int n = JOptionPane.showOptionDialog(null, msg, title, JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, null, null);
		return n;
	}

	static String inputBox(String title, String prompt) {
		String str = "";
		str = JOptionPane.showInputDialog(null, prompt, title, JOptionPane.PLAIN_MESSAGE);
		return str;
	}

	//************************** PAINT CODE *************************
    private void start()
    {

        int pw = panel3.getWidth();
        int ph = panel3.getHeight();

        panel3.setBackground(Color.WHITE);
		g1 = (Graphics2D) panel3.getGraphics();
	
		g1.setColor(Color.BLACK);
		
		//------------[ Empty BMP ]-------------------
        currBmp = new BufferedImage (pw, ph, BufferedImage.TYPE_INT_ARGB );
		g2 = currBmp.createGraphics();
        g2.setColor(panel3.getBackground());
        g2.fillRect(0, 0, pw, ph);
		//--------------------------------------------
    	
        dsize = 50; size_h = 50; size_w = 50;

        //-----------------------------
        t1.setText (String.valueOf(dsize));
        t2.setText(String.valueOf(2));
        //------------------------------
        
        dColor = Color.BLACK;
        //dBrush = new SolidBrush(dColor);
        //dPen = new Pen(dBrush,2);
        flagPaint = false;
        flagFill = false;
        flagP1 = true;
        selShape = "Rectangle";
        //dFont = txtInput.Font;
        t1.setText(String.valueOf(dsize));
        t2.setText(String.valueOf(penSize));
        clearScreen();
        createDropDown();

    }
	
	private void createDropDown() {
        cbox1.removeAllItems();
        cbox1.addItem("Drawing");
        cbox1.addItem("Rectangle (p1,p2)");
        cbox1.addItem("Ellipse (p1,p2)");
        cbox1.addItem("Square");
        cbox1.addItem("Rectangle (H)");
        cbox1.addItem("Rectangle (V)");
        cbox1.addItem("Circle");
        cbox1.addItem("Line");
        cbox1.addItem("Text");
        cbox1.addItem("Erase");
        cbox1.addItem("Erase Range");
        
		cbox1.setSelectedIndex(0);
        setShape();
        cbox1.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                setShape();
            }
        });        
	}
    private void setShape()
    {
        selItem = cbox1.getSelectedItem().toString();
        //MessageBox.Show(selItem);
        if (ck1.isSelected()) {flagFill=true;} else {flagFill = false;}
        
        dsize = Integer.valueOf(t1.getText().toString());
        penSize = Integer.valueOf(t2.getText().toString());
        size_w = dsize; size_h = dsize;
        selShape = selItem;
        switch (selItem)
        {
            case "Drawing": selShape = "Drawing"; break;
            case "Square": selShape = "Rectangle"; break;
            case "Rectangle (V)": selShape = "Rectangle"; size_w = dsize; size_h = dsize * 2; break;
            case "Rectangle (H)": selShape = "Rectangle"; size_w = dsize * 2; size_h = dsize; break;
            case "Circle": selShape = "Ellipse"; break;
            case "Line": selShape = "Line"; break;
            default: break;
        }
        flagP1 = true;
        l1.setText("Selected Shape: " + selShape);
        MoveGraphics();
    }
    private void drawAnimation(Graphics2D g, String shape, int x, int y, int sz_w, int sz_h)
    {

		g.setColor(dColor);//Pen and SolidBrush Color
		g.setStroke(new BasicStroke(penSize));
        switch (shape)
        {
            case "Rectangle (p1,p2)":
                if (flagP1 == false){
                    lp2 = new iPoint(x, y);
                    if (flagFill == false) g.drawRect( lp1.getX(), lp1.getY(), lp2.getX() - lp1.getX(), lp2.getY() - lp1.getY());
                    if (flagFill == true) g.fillRect( lp1.getX(), lp1.getY(), lp2.getX() - lp1.getX(), lp2.getY() - lp1.getY());
                }
                break;
            case "Ellipse (p1,p2)":
                if (flagP1 == false){
                    lp2 = new iPoint(x, y);
                    if (flagFill == false) g.drawOval( lp1.getX(), lp1.getY(), lp2.getX() - lp1.getX(), lp2.getY() - lp1.getY());
                    if (flagFill == true) g.fillOval( lp1.getX(), lp1.getY(), lp2.getX() - lp1.getX(), lp2.getY() - lp1.getY());
                }
                break;

            case "Rectangle":
                if (flagFill == false) g.drawRect( x, y, sz_w, sz_h);
                if (flagFill == true) g.fillRect( x, y, size_w, size_h);
                break;
            case "Ellipse":
                if (flagFill == false) g.drawOval( x, y, sz_w, sz_h);
                if (flagFill == true) g.fillOval( x, y, size_w, size_h);
                break;
            case "Line":
                if (flagP1 == false) {lp2 = new iPoint(x, y); g.drawLine(lp1.getX(), lp1.getY(), lp2.getX(), lp2.getY());}
                break;
            case "Text":
                if (t3.getText().length() > 0)
                {
                	g.setFont(dFont);
                    g.drawString(t3.getText(), x, y);
                }
                break;
            //********** Edit Events [Copy, Cut, Paste] ***********
            case "Erase":
            	g.setColor(Color.BLUE);
                g.drawRect(x , y, sz_w, sz_h);
                break;
            case "Erase Range":
                if (flagP1 == false) 
                {
                    lp2 = new iPoint(x, y);
                    g.setColor(Color.BLUE);
                    g.drawRect(lp1.getX(), lp1.getY(), lp2.getX() - lp1.getX(), lp2.getY() - lp1.getY());
                }
                break;
            case "Copy Image":
                if (flagP1 == false) 
                {
                    lp2 = new iPoint(x,y);
                    g.setColor(Color.BLUE);
                    g.drawRect(lp1.getX(), lp1.getY(), lp2.getX() - lp1.getX(), lp2.getY() - lp1.getY());
                }

                break;
            case "Cut Image":
                if (flagP1 == false) 
                {
                    lp2 = new iPoint(x,y);
                    g.setColor(Color.BLUE);
                    g.drawRect(lp1.getX(), lp1.getY(), lp2.getX() - lp1.getX(), lp2.getY() - lp1.getY());
                }

                break;
            case "Paste Image":
                if (flagImg == true)
                {
                    g.drawImage(currImg, x, y, null);
                }
                break;

            //******************************************************
            default: break;
        }
        l1.setText ("Selected Shape: " + selShape);
    }

    private void drawShape(Graphics2D g, String shape, int x, int y, int sz_w, int sz_h)
    //private void drawShape(Graphics g, String shape, Pen pen, Brush  int x, int y, int sz_w, int sz_h)
    {
    		g.setColor(dColor);//Pen and SolidBrush Color
    		g.setStroke(new BasicStroke(penSize));
            switch (shape) {
                case "Rectangle (p1,p2)":
                    if (flagP1 == true){
                        lp1 = new iPoint(x, y); flagP1 = false;
                    } else{
                    	
                        lp2 = new iPoint(x, y); flagP1 = true;
                        
                        if (flagFill == false) g.drawRect(lp1.getX(), lp1.getY(), lp2.getX()-lp1.getX(),lp2.getY()-lp1.getY());
                        if (flagFill == true) g.fillRect(lp1.getX(), lp1.getY(), lp2.getX() - lp1.getX(), lp2.getY() - lp1.getY());
                    }
                    break;
                case "Ellipse (p1,p2)":
                    if (flagP1 == true)
                    {
                        lp1 = new iPoint(x, y); flagP1 = false;
                    }
                    else
                    {
                        lp2 = new iPoint(x, y); flagP1 = true;
                        if (flagFill == false) g.drawOval(lp1.getX(), lp1.getY(), lp2.getX() - lp1.getX(), lp2.getY() - lp1.getY());
                        if (flagFill == true) g.fillOval(lp1.getX(), lp1.getY(), lp2.getX() - lp1.getX(), lp2.getY() - lp1.getY());
                    }
                    break;
                case "Rectangle":
                    if (flagFill == false) g.drawRect( x, y, sz_w, sz_h);
                    if (flagFill == true ) g.fillRect( x, y, size_w, size_h);
                    break;
                case "Ellipse": 
                    if (flagFill == false) g.drawOval( x, y, sz_w, sz_h);
                    if (flagFill == true) g.fillOval( x, y, size_w, size_h);
                    break;
                case "Line":
                    if (flagP1==true){
                        lp1 = new iPoint(x,y); flagP1 = false;
                    } else {
                        lp2 = new iPoint(x, y);g.drawLine(lp1.getX(), lp1.getY(), lp2.getX(), lp2.getY()); flagP1 = true;
                        
                    }
                    break;
                case "Text":
                    if (t3.getText().length() > 0){
                    	g.setFont(dFont);
                        g.drawString(t3.getText(), x, y);
                    }
                    break;
                case "Erase":
                	g.setColor(panel3.getBackground());
                    g.fillRect(x, y, size_w, size_h);
                    break;
                case "Erase Range":
                    if (flagP1 == true){lp1 = new iPoint(x, y); flagP1 = false;
                    }else{
                        lp2 = new iPoint(x, y); flagP1 = true;
                        g.setColor(panel3.getBackground());
                        g.fillRect(lp1.getX(), lp1.getY(), lp2.getX()-lp1.getX(), lp2.getY()-lp1.getY());
                    }
                    break;
                //********** Edit Events [Copy, Cut, Paste] ***********
                case "Copy Image":
                    if (flagP1==true){
                        lp1 = new iPoint(x,y); flagP1 = false;
                    } else {
                        lp2 = new iPoint(x, y);
                        flagP1 = true; int w1 = lp2.getX() - lp1.getX(); int h1 = lp2.getY() - lp1.getY();
                        try
                        {
                            //currImg = currBmp.getSubimage(lp1.getX(), lp1.getY(), w1, h1);
                            currImg = copyImage(currBmp, lp1.getX(), lp1.getY(), w1, h1);
                            setClipboardImage(currImg);
                            setEdit("Paste"); // Paste After Copy
                        }
                        catch (Exception ex) { }
                    }
                    break;
                case "Cut Image":
                    if (flagP1==true) {
                        lp1 = new iPoint(x, y); flagP1 = false;
                    } else {
                        lp2 = new iPoint(x, y); int w1 = lp2.getX() - lp1.getX(); int h1 = lp2.getY() - lp1.getY();
                        flagP1 = true;
                        try
                        {
                            //currImg = currBmp.getSubimage(lp1.getX(), lp1.getY(), w1, h1);
                            currImg = copyImage(currBmp, lp1.getX(), lp1.getY(), w1, h1);
                            
                            eR = new Rectangle(lp1.getX(), lp1.getY(), lp2.getX()-lp1.getX(), lp2.getY()-lp1.getY());
                            setClipboardImage(currImg);
                            
                            //Erasing Image
                            g.setColor(panel3.getBackground());
                            g.fillRect(eR.x, eR.y, eR.width, eR.height);
                            setEdit("Paste"); // Paste After Copy
                            
                        }
                        catch (Exception ex) { }

                    }
                	break;

                case "Paste Image":
                	g.drawImage(currImg, x, y, null);
                	setShape();
                    break;
                //******************************************************
                default:break;
            }
            l1.setText ("Selected Shape: " + selShape);
    }
    private void clearScreen()
    {
        //g1.Clear(panel1.BackColor);
        int w = panel3.getWidth();
        int h = panel3.getHeight();
        //g2.clearRect(0, 0, w, h);
        g2.setColor(panel3.getBackground());
        g2.fillRect(0, 0, w, h);
        MoveGraphics();//Moves g2(BitMap) to g1(Panel)
    }
	
public void mouseEvents() {
    panel3.addMouseListener(new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
        	
            if (selShape == "Drawing")
            {
                flagPaint = true;
            } else {
            	drawShape(g2, selShape, e.getX(), e.getY(), size_w, size_h);
            }
            p1 = new iPoint(e.getX(), e.getY());
            //g1.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        	if (selShape == "Drawing") flagP1 = true;
            flagPaint = false;        	
        }
    });
    
    panel3.addMouseMotionListener(new MouseAdapter() {
    	@Override
        public void mouseMoved(MouseEvent e) {
            //System.out.println("Mouse moved (" + e.getX() + ',' + e.getY() + ')');
            //drawShape(g1, selShape, e.getX(), e.getY(), size_w, size_h);
            if (selShape != "Drawing") {//Animation
                MoveGraphics();// moves g2 (BitMap) to g1 - Panel            	//clearScreen();
                drawAnimation(g1, selShape, e.getX(), e.getY(), size_w, size_h);
            }
    		
        }
    	 @Override
    	    public void mouseDragged(MouseEvent e) {
 	        //System.out.println("Mouse dragged (" + e.getX() + ',' + e.getY() + ')');
                if (flagPaint == true && selShape == "Drawing")
                {
                    p2 = new iPoint(e.getX(), e.getY());
            		g2.setColor(dColor);//Pen and SolidBrush Color
            		g2.setStroke(new BasicStroke(penSize));//pen size
                    g2.drawLine(p1.getX(), p1.getY(),p2.getX(), p2.getY());
                    p1 = p2;
                    MoveGraphics();// moves g2 (BitMap) to g1 - Panel
                }
            	
    	        
    	    }
    	
    });
    
}

//************************* Interface with the Buttons and Menus **********************
private void setEdit(String opt) {
	if (opt=="Cut") {
        selShape = "Cut Image";
	}
	if (opt=="Copy") {
        selShape = "Copy Image";
	}
	if (opt=="Paste") {
        selShape = "Paste Image";
        flagP1 = true;
        try
        {
            currImg = getImageFromClipboard();
            if (currImg != null) flagImg = true; 
        }
        catch (Exception ex)
        {
            flagImg = false;
        };
		
	}
    flagP1 = true;
	l1.setText("Selected Shape: " + selShape);	
}
private void setFile(String opt) {
	if (opt =="New File") {
		currFile = "";
		clearScreen();
	}
	if (opt =="Open File") {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currFile = fc.getSelectedFile().toString();
			if (currFile != "" && currFile != null) {
				File file1 = new File(currFile);
				try {
					clearScreen();
					Image tmpImg = ImageIO.read(file1);
					int w, h, w1, h1, w2, h2;
					w2 = tmpImg.getWidth(null);
					h2 = tmpImg.getHeight(null);
					
					w1 = currBmp.getWidth();
					h1 = currBmp.getHeight();
					
					
					h = h1;w = w1;
					if (h2>h1) h = h2;
					if (w2>w1) w = w2;
					
					resizePanel(tmpImg, w, h);
				} catch (IOException e) {
					msgbox("Error Opening the '"+currFile+"'file!\n Error: "+e.getMessage(), "Open File Error");
				}
			}

		}
		
		
	}
	if (opt =="Save File") {
		if (currFile == "" || currFile == null) {
			saveFileAs();
		} else {
			saveFile();
		}
	}
	if (opt =="Save File As") {
		saveFileAs();
		
	}
	if (opt== "Exit") {
		jf.dispose();
	}
}

private void resizePanel(Image img, int pw, int ph) {

	/*
	panel3.setBackground(Color.WHITE);
	panel3.setSize(new Dimension (pw, ph));
	panel3.setMinimumSize(new Dimension (pw, ph));
	panel3.setPreferredSize(new Dimension (pw, ph));
	g1 = (Graphics2D) panel3.getGraphics();
	g1.setColor(Color.BLACK);
	*/
	
	//------------[ Empty BMP ]-------------------
    currBmp = new BufferedImage (pw, ph, BufferedImage.TYPE_INT_ARGB );
	g2 = currBmp.createGraphics();
    g2.setColor(panel3.getBackground());
    g2.fillRect(0, 0, pw, ph);
	//--------------------------------------------
	g2.drawImage(img, 0, 0, null);
	MoveGraphics();
}
private void saveFileAs() {
	final JFileChooser fc = new JFileChooser();
	int returnVal = fc.showOpenDialog(null);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
		currFile = fc.getSelectedFile().toString();
		saveFile();
	}
}

private void saveFile() {
	if (currFile == "" || currFile == null) {
		msgbox("Select a valid file!", "Save File Error");
		return;
	} else {
	try {
	    File outputfile = new File(currFile);
	    ImageIO.write(currBmp, "png", outputfile);
	    msgbox("File '"+currFile+"' Saved!", "Save File");
	} catch (IOException e) {
	    msgbox("Error Saving the '"+currFile+"'file!\n Error: "+e.getMessage(), "Error Saving the File");
	}
	}
	
}

//************************* [Usefull Routines ]****************************************
//******************* Copy to Clipboard and Paste from ClipBoard ************
//Code from: http://stackoverflow.com/questions/20174462/how-to-do-cut-copy-paste-in-java
public static Image getImageFromClipboard() throws Exception {
    Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable cc = sysc.getContents(null);
    if (cc == null)
        return null;
    else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
        return (Image) cc.getTransferData(DataFlavor.imageFlavor);
    return null;
}

public static void setClipboardImage(final Image image) {
    Transferable trans = new Transferable() {
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { DataFlavor.imageFlavor };
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {
            if (isDataFlavorSupported(flavor))
                return image;
            throw new UnsupportedFlavorException(flavor);
        }
    };
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans,
            null);
}
//***************************************************************************
private Image copyImage(BufferedImage bi, int x, int y, int w, int h) {
	
	BufferedImage tmpBmp = new BufferedImage (w, h, BufferedImage.TYPE_INT_ARGB );
    Graphics2D g3 = tmpBmp.createGraphics();
	Image tmpImg = bi.getSubimage(x, y, w, h);
	g3.drawImage(tmpImg, 0, 0, w, h, null);
	g3.dispose();
	return (Image) tmpBmp;
}


private void MoveGraphics()
{
	//Moves g2 (Bitmap) to the g1 (panel)
    g1.drawImage(currBmp, 0,0, null);
    
}
//*************************************************************************************



}// End of Class

class iPoint {//Compactability
	public int X;
	public int Y;
	iPoint(int x, int y) {
		this.X = x;
		this.Y = y;
	}
	public int getX() {return X;}
	public int getY() {return Y;}
}
