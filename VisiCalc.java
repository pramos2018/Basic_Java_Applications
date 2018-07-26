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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

class cell {
	public int format;
	public String formula;
	public float value;
	public int flag; // 0 - Empty, 1 - Value (Float), 2 - Text

	public cell() {// Constructor
		format = 0;
		formula = "";
		value = 0;
		flag = 0;
	}
}

class RC {
	public int r;
	public int c;

	public RC(int r1, int c1) {
		r = r1;
		c = c1;
	}
}

public class VisiCalc {
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

	static JTable jt;
	static String[] columns;
	static String[][] data;
	static String header;

	// Project Variables

	String currPath = "c:\\temp\\vcalc\\";
	String currFile = "vcalc1.txt";
	static int NROW = 100;
	static int NCOL = 50;

	static cell tb[][] = new cell[NROW][NCOL];// Table

	static int[] col_size = new int[NCOL];// default size
	static int[] col_hide = new int[NCOL];// default unhide
	static int[] row_hide = new int[NROW + 1];// default unhide
	static int lastRow = 0;
	static int lastCol = 0;
	static int curCols = 0;
	static int pr = 0, pc = 0;// For Scrolling
	static char k;// keyboard input
	static int prtX = 0;
	static int prtY = 0;

	public VisiCalc() { // Constructor
		// setLayout(new GridBagLayout());

		// Panels (Group Box)
		panel1 = new JPanel();
		// panel1.setSize(320,30);
		panel1.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "[Cell]------[Value]-------[Formula/Text]"));
		// panel1.setLayout(new FlowLayout());
		GridBagLayout layout = new GridBagLayout();
		panel1.setLayout(layout);

		panel2 = new JPanel();
		// panel2.setSize(320,30);
		panel2.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Shortcuts:"));
		// panel2.setLayout(new FlowLayout());
		GridBagLayout layout2 = new GridBagLayout();
		panel2.setLayout(layout2);

		panel4 = new JPanel();
		panel4.setSize(680, 560);
		// panel4.setBorder(new TitledBorder (new LineBorder (Color.black,
		// 1),""));
		// panel4.setLayout(new FlowLayout());
		GridBagLayout layout4 = new GridBagLayout();
		panel4.setLayout(layout4);

		// ***********************************************************************************
		// -------- Table ----------
		newSpreadsheet();
		createTable();
		JScrollPane JCP = new JScrollPane(jt);
		// ***********************************************************************************

		// GridBad (Layout setup)
		gbc = new GridBagConstraints();
		gbc2 = new GridBagConstraints();
		gbc3 = new GridBagConstraints();
		gbc4 = new GridBagConstraints();
		// TextFields
		t1 = new JTextField("");
		t1.setPreferredSize(new Dimension(40, 20));
		t1.setMinimumSize(new Dimension(40, 20));
		t2 = new JTextField("");
		t2.setPreferredSize(new Dimension(60, 20));
		t2.setMinimumSize(new Dimension(60, 20));
		t3 = new JTextField("");
		t3.setPreferredSize(new Dimension(140, 20));
		t3.setMinimumSize(new Dimension(140, 20));
		// Buttons
		int size = 12;
		b1 = new JButton("Save");
		b1.setFont(new Font("Calibri", Font.BOLD, size));
		b2 = new JButton("New");
		b2.setFont(new Font("Calibri", Font.BOLD, size));
		b3 = new JButton("Help");
		b3.setFont(new Font("Calibri", Font.BOLD, size));
		b4 = new JButton("Edit");
		b4.setFont(new Font("Calibri", Font.BOLD, size));
		b5 = new JButton("Exit");
		b5.setFont(new Font("Calibri", Font.BOLD, size));
		b6 = new JButton("Update");
		b5.setFont(new Font("Calibri", Font.BOLD, size));

		// ************ Panel #1 ************************
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(2, 2, 2, 2);

		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.gridx = 1;
		panel1.add(t1, gbc);
		gbc.gridx = 2;
		panel1.add(t2, gbc);
		gbc.gridx = 3;
		panel1.add(t3, gbc);
		gbc.gridx = 4;
		panel1.add(b6, gbc);

		// ************ Panel #2 ************************
		gbc2.fill = GridBagConstraints.BOTH;
		gbc2.anchor = GridBagConstraints.NORTHWEST;
		gbc2.insets = new Insets(3, 3, 3, 3);

		gbc2.gridwidth = 1;
		gbc2.gridy = 1;

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
				menuSel("New File");
			}
		});

		menuItem = new JMenuItem("Open File", KeyEvent.VK_O);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Open File");
			}
		});

		menuItem = new JMenuItem("Save File", KeyEvent.VK_S);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Save File");
			}
		});

		menuItem = new JMenuItem("Save File As", KeyEvent.VK_A);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Save File As");
			}
		});

		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Exit");
			}
		});

		// Build the second menu.
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);
		menuBar.add(menu);

		menuItem = new JMenuItem("Edit Cell", KeyEvent.VK_C);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Edit Cell");
			}
		});
		menuItem = new JMenuItem("View Cell", KeyEvent.VK_V);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("View Cell");
			}
		});

		menu.addSeparator();
		menuItem = new JMenuItem("Format Range", KeyEvent.VK_F);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Format Range");
			}
		});

		menuItem = new JMenuItem("Delete Range", KeyEvent.VK_D);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Delete Range");
			}
		});

		menuItem = new JMenuItem("Copy Range", KeyEvent.VK_P);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Copy Range");
			}
		});

		menuItem = new JMenuItem("Resize Columns", KeyEvent.VK_R);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Resize Columns");
			}
		});

		menuItem = new JMenuItem("Hide/Unhide Rows/Cols", KeyEvent.VK_H);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Hide/Unhide Rows/Cols");
			}
		});

		menu.addSeparator();
		menuItem = new JMenuItem("Insert Row", KeyEvent.VK_I);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Insert Row");
			}
		});

		menuItem = new JMenuItem("Insert Column", KeyEvent.VK_N);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Insert Column");
			}
		});

		menuItem = new JMenuItem("Delete Row(s)", KeyEvent.VK_R);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Delete Row(s)");
			}
		});

		menuItem = new JMenuItem("Delete Column(s)", KeyEvent.VK_C);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Delete Column(s)");
			}
		});

		menuItem = new JMenuItem("Exclude Row(s)", KeyEvent.VK_X);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Exclude Row(s)");
			}
		});

		menuItem = new JMenuItem("Exclude Column(s)", KeyEvent.VK_L);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Exclude Column(s)");
			}
		});

		// Build third menu in the menu bar.
		menu = new JMenu("Help");
		menuItem = new JMenuItem("Show Help", KeyEvent.VK_H);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Show Help");
			}
		});
		menuItem = new JMenuItem("Functions", KeyEvent.VK_F);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuSel("Functions");
			}
		});
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

		gbc4.gridwidth = 2;
		gbc4.gridheight = 2;
		gbc4.gridx = 1;
		gbc4.gridy = 2;
		panel4.add(JCP, gbc4);

		// ************************************************
	}

	public void startM() {
		jf = new JFrame("Basic VisiCalc Spreadsheet");
		stp = new VisiCalc();

		jf.setSize(680, 580);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		// jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// jf.add(stp);
		jf.add(panel4);
		jf.setJMenuBar(menuBar);
		jf.setVisible(true);

		// ******* Testing ********
		newSpreadsheet();
		preSet();
		showMatrix(); // Sets jt - JavaTable
		// ************************

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newSpreadsheet();
			}
		});
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				help();
				;
			}
		});
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editOptions(0);
				;
			}
		});

		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExit();
			}
		});
		b6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateCell();
			}
		});

	}

	private static void createTable() {
		// ****************************** Setting the Table
		// *********************************************
		
		jt = new JTable(data, columns) {
			public boolean isCellEditable(int data, int columns) {
				return false;
			};
			
			int lv = 240;
			Color cx = new Color(lv,lv,lv, 255);
			public Component prepareRenderer(TableCellRenderer r, int data, int columns) {
				Component c = super.prepareRenderer(r, data, columns);
				if (data % 2 == 0)
					c.setBackground(Color.WHITE);
				else
					c.setBackground(Color.WHITE);
				// c.setBackground(Color.LIGHT_GRAY);

				 if(columns == 0) c.setBackground(cx);
				
				if (isCellSelected(data, columns))
					c.setBackground(Color.LIGHT_GRAY);
				return c;
			}
		};
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// jt.setPreferredScrollableViewportSize(new Dimension(640,480));
		jt.setFillsViewportHeight(false);
		// jt.setAutoCreateRowSorter(true);

		TableColumnModel tcm = jt.getColumnModel();
		int sz;
		for (int c = 0; c < curCols; c++) {
			sz = col_size[c];
			tcm.getColumn(c).setPreferredWidth(sz * 6);
		}
		jt.setAutoCreateColumnsFromModel(true);

		// End of Table

	}

	private void menuSel(String txt) {
		switch (txt) {

		case "New File":
			newSpreadsheet();
			break;
		case "Open File":
			files(2);
			break;
		case "Save File":
			files(3);
			;
			break;
		case "Save File As":
			files(4);
			;
			break;
		case "Exit":
			btnExit();
			break;

		case "Edit Cell":
			editOptions(11);
			break;
		case "View Cell":
			editOptions(13);
			break;
		case "Format Range":
			editOptions(9);
			break;
		case "Copy Range":
			editOptions(8);
			break;

		case "Resize Columns":
			editOptions(10);
			break;
		case "Delete Range":
			editOptions(7);
			break;
		case "Insert Row":
			editOptions(1);
			break;
		case "Insert Column":
			editOptions(2);
			break;
		case "Delete Row(s)":
			editOptions(3);
			break;
		case "Delete Column(s)":
			editOptions(4);
			break;
		case "Exclude Row(s)":
			editOptions(5);
			break;
		case "Exclude Column(s)":
			editOptions(6);
			break;

		case "Hide/Unhide Rows/Cols":
			editOptions(12);
			break;

		case "Show Help":
			help();
			break;
		case "Functions":
			listFunctions();
			break;
		case "About":
			msgbox("Basic VisiCalc SpreadSheet \n Made by P. Ramos in May/2016\n As Part of a Training Program",
					"Basic VisiCalc");
			break;

		default:
			System.out.println(txt);
		}

		/*
		 * msg = msg + "\n" + (" ************************************** "); msg
		 * = msg + "\n" + (" ** ========= EDIT OPTIONS=========  ** "); msg =
		 * msg + "\n" + (" ** 1 - Insert Row   2 - Insert Col  ** "); msg = msg
		 * + "\n" + (" ** 3 - Delete Rows  4 - Delete Cols ** "); msg = msg +
		 * "\n" + (" ** 5 - Excl.  Rows  6 - Excl. Cols  ** "); msg = msg + "\n"
		 * + (" ** 7 - Delete Range 8 - Copy Range  ** "); msg = msg + "\n" + (
		 * " ** 9 - Format Range 10- Resize Cols ** "); msg = msg + "\n" + (
		 * " ** 11- Edit Cells   12- (Un)Hide RCs** "); msg = msg + "\n" + (
		 * " ** 13- View Cells                   ** "); msg = msg + "\n" + (
		 * " ************************************** ");
		 */

	}

	private void btnExit() {
		jf.dispose();
	}

	void preSet() {
		int r = 1, c = 1, i = 0;
		for (i = 1; i < 50; i++)
			col_size[i] = 8;// Default size;

		col_size[1] = 20;
		for (i = 2; i <= 8; i++)
			col_size[i] = 10;

		tb[1][1] = setCell(2, 320, 0, "Income Statement");
		tb[2][1] = setCell(2, 120, 0, "Net Sales");
		tb[3][1] = setCell(2, 120, 0, "Costs");
		tb[4][1] = setCell(2, 120, 0, "Margin");
		tb[5][1] = setCell(2, 120, 0, "----------------");
		tb[6][1] = setCell(2, 120, 0, "SG&A");
		tb[7][1] = setCell(2, 120, 0, "----------------");
		tb[8][1] = setCell(2, 120, 0, "Operating Profit");

		setTest("Jan", 2);
		setTest("Feb", 3);
		setTest("Mar", 4);
		setTest("Apr", 5);
		setTest("May", 6);
		setTest("Jun", 7);
		setTest("Jul", 8);
		setTest("Aug", 9);
		setTest("Sep", 10);
		setTest("Oct", 11);
		setTest("Nov", 12);
		setTest("Dec", 13);

		showMatrix();
	}

	void setTest(String title, int c) {

		tb[1][c] = setCell(2, 320, 0, title);
		tb[2][c] = setCell(1, 220, 100, "");
		tb[3][c] = setCell(1, 220, 80, "");
		tb[4][c] = setCell(1, 220, 20, "");
		tb[5][c] = setCell(2, 220, 0, "-------");
		tb[6][c] = setCell(1, 220, 10, "");
		tb[7][c] = setCell(2, 220, 0, "-------");
		tb[8][c] = setCell(1, 220, 10, "");
		int r = 8;
		if (c > lastCol) {
			lastCol = c;
		}
		if (r > lastRow) {
			lastRow = r;
		}
	}

	public static void showMatrix() {

		int r, c, r1, c1, sz;
		int rows = NROW - 1;
		int cols = NCOL - 1;
		int lmt = 4;
		int ctc, ctr;

		// *** Define the Number of Columns ***
		ctc = 0;
		for (c = 0; c < cols; c++) {// Header
			if (col_hide[c + pc] == 0) {
				lmt = lmt + col_size[c + pc];
				ctc++;
			}
			// if (lmt>=75) break;
		}

		curCols = ctc + 1;
		cols = curCols;
		// *********** Resize Columns ****************
		try {
			TableColumnModel tcm = jt.getColumnModel();
			c1 = 0;
			for (c = 0; c < cols; c++) {
				if (c1 >= curCols)
					break;
				if (col_hide[c + pc] == 0) {
					lmt = lmt + col_size[c + pc];
					sz = col_size[c + pc];
					tcm.getColumn(c1).setPreferredWidth(sz * 6);
					c1++;
				}
			}

			jt.setAutoCreateColumnsFromModel(true);
		} catch (Exception ex) {
		}

		// *** Create the Variables for the Table ***
		columns = new String[cols];
		data = new String[rows][cols];

		// *** Columns Header ***
		columns[0] = " ";
		lmt = 4;
		c1 = 1;
		for (c = 1; c < cols; c++) {// Header
			if (col_hide[c + pc] == 0) {
				columns[c1] = getCol(c + pc);
				try {
					TableColumnModel tcm = jt.getColumnModel();
					TableColumn tc = tcm.getColumn(c1);
					tc.setHeaderValue(columns[c1]);
				} catch (Exception ex) {}
				
				lmt = lmt + col_size[c + pc];
				c1++;
			}
			// if (lmt>=75) break;
		}

		// *** Update the Table ***
		calculateCells();

		r1 = 0;
		for (r = 1; r < rows; r++) {
			lmt = 4;
			if (row_hide[r + pr] == 0) {
				c1 = 0;

				for (c = 0; c < cols; c++) {
					try {
						if (col_hide[c + pc] == 0) {
							if (c==0) {
								data[r1][c1] = String.valueOf(r + pr);
							} else {
								data[r1][c1] = printCell(tb[r + pr][c + pc], c + pc);
							}
							jt.setValueAt(data[r1][c1], r1, c1);
							sz = col_size[c + pc] * 8;
							jt.getColumnModel().getColumn(c1).setWidth(sz);
							lmt = lmt + col_size[c + pc];
							c1++;
						}

					} catch (Exception ex) {
						// System.out.println("("+r+","+c+") "+
						// ex.getMessage());
					}

					// if (lmt>=75) break;
				}
				r1++;
			}
		}
		// *************************
	}

	static String getCol(int c) {
		String str = "";
		int c1, c2;
		c2 = c;
		if (c >= 27) {
			c1 = (int) Math.round(c / 26);
			str = str + String.valueOf(Character.toChars(64 + c1));
			c2 = c - c1 * 26;
		}
		str = str + String.valueOf(Character.toChars(64 + c2));
		return str;
	}

	void newSpreadsheet() {
		int r, c;
		pc = 0;
		pr = 0;

		// tb[][] = new cell[NROW][NCOL];

		for (c = 0; c < NCOL; c++) {
			col_hide[c] = 0;// Default
			col_size[c] = 8;// Default
		}
		for (r = 0; r < NROW; r++) {// reseting columns
			row_hide[r] = 0;
			for (c = 0; c < NCOL; c++) {
				// delCell(r,c);
				tb[r][c] = setCell(0, 0, 0, "");
			}
		}
		col_size[0] = 4;
		showMatrix();
	}

	// ********************** Basic Cell Operations - Set/Del/Copy
	// ************************************
	static cell setCell(int type, int Format, float value, String Text) {
		cell tmp = new cell();
		tmp.flag = type;// Flag: 0 - Empty, 1 - Number, 2 = Text
		tmp.format = Format;// Float 255 options - Alignment(C = Center,
							// R=Right, L=Left);
		tmp.value = value;// value
		tmp.formula = Text;// Text/Formula
		return tmp;
	}

	public void delCell(int r, int c) {
		if (r >= NROW || c >= NCOL || r < 0 || c < 0) {
			return;
		}
		try {
			tb[r][c].flag = 0;
			tb[r][c].formula = " ";
			tb[r][c].value = 0;
			tb[r][c].format = 0;
		} catch (Exception ex) {
		}
	}

	void copyCell(int rd, int cd, int ro, int co) {
		if (rd > NROW || ro > NROW || cd > NCOL || co > NCOL) {
			return;
		}
		try {
			col_size[cd] = col_size[co];
			col_hide[cd] = col_hide[cd];
			row_hide[rd] = row_hide[ro];

			tb[rd][cd].flag = tb[ro][co].flag;
			tb[rd][cd].format = tb[ro][co].format;
			tb[rd][cd].value = tb[ro][co].value;
			// tb[rd][cd].formula = tb[ro][co].formula;
			copyFormula(rd, cd, ro, co);

			if (rd > lastRow) {
				lastRow = rd;
			}
			if (cd > lastCol) {
				lastCol = cd;
			}
		} catch (Exception ex) {
		}

	}

	static String getCell(int r, int c) {
		String txt = "";
		String cl = getCol(c);
		String rw = String.valueOf(r);
		txt = cl + rw;
		return txt;
	}

	static float getCellVal(String txt1) {
		float val = 0;
		char[] txt = txt1.toCharArray();

		if (txt[0] >= 48 && txt[0] <= 57) {
			val = Float.parseFloat(txt1);
		} else {
			RC t1 = getRC(txt1);// Cell

			if (t1.r != 0 && t1.c != 0) {
				val = tb[t1.r][t1.c].value;
			} else {
				val = 0;
			}
		}
		return val;
	}

	static RC getRC(String txt) {
		String x1 = "", tmp1 = txt.toUpperCase().replace(" ", "");

		String t_c = "";
		String t_r = "";

		RC t = new RC(0, 0);
		int i, i1 = 0, r = 0, c = 0, p = 1;

		int len = tmp1.length();

		for (i = 0; i < len; i++) {
			x1 = tmp1.substring(i, i + 1);
			char[] ch = x1.toCharArray();
			if (ch[0] >= 65 && ch[0] <= 65 + 25) {
				t_c = t_c + String.valueOf(ch[0]);
				i1++;
			}
			if (ch[0] >= 48 && ch[0] <= 57) {
				t_r = t_r + String.valueOf(ch[0]);
			}
		}

		char[] tc = t_c.toCharArray();
		for (i = 0; i < i1; i++) {// column
			if (tc[i] == 0) {
				break;
			}

			p = 26 * (i1 - i - 1);
			if (p == 0) {
				p = 1;
			}

			c = c + (tc[i] - 64) * p;// *(pow(10,i));
		}

		t.c = c;
		if (t_r.length() > 0) {
			t.r = Integer.valueOf(t_r);
		} else {
			t.r = 0;
		}
		return t;
	}

	// ********************** Edit Operations - Insert/Delete/Exclude/Copy Range
	// ******************
	// ************** Insert, Exclude, Delete Delete Rows and Columns
	// ****************************
	void excludeRows(int r1, int r2) {
		int r, c, lc, lr;
		lc = lastCol;
		lr = lastRow;

		for (r = r1; r <= lr + 1; r++) {
			for (c = 0; c <= lc + 1; c++) {
				copyCell(r, c, r2 + 1, c);
			}
			r2++;
		}
	}

	void excludeColumns(int c1, int c2) {
		int r, c, lc, lr;
		lc = lastCol;
		lr = lastRow;
		for (c = c1; c <= lc + 1; c++) {
			for (r = 0; r <= lr + 1; r++) {
				copyCell(r, c, r, c2 + 1);
			}
			c2++;
		}
	}

	void deleteRows(int r1, int r2) {
		int r, c, lr, lc;
		lc = lastCol;
		lr = lastRow;

		for (r = r1; r <= r2; r++) {
			for (c = 0; c <= lc + 1; c++) {
				delCell(r, c);
			}
		}

	}

	void deleteColumns(int c1, int c2) {
		int r, c, lr, lc;
		lc = lastCol;
		lr = lastRow;
		for (c = c1; c <= c2; c++) {
			for (r = 0; r <= lr + 1; r++) {
				delCell(r, c);
			}
		}
	}

	void insertRow(int r1) {
		int r, c, lr, lc;
		lc = lastCol;
		lr = lastRow;
		for (r = lr + 1; r >= r1; r--) {
			for (c = 0; c <= lc + 1; c++) {
				copyCell(r, c, r - 1, c);
			}
		}
		for (c = 0; c <= lc + 1; c++) {// Cleaning the Current Row;
			delCell(r1, c);
		}

	}

	void insertCol(int c1) {
		int r, c, lr, lc;
		lc = lastCol;
		lr = lastRow;
		for (c = lc + 1; c >= c1; c--) {
			for (r = 1; r <= lr + 1; r++) {
				copyCell(r, c, r, c - 1);
			}
		}
		for (r = 1; r <= lr + 1; r++) {// Cleaning the Current Column;
			delCell(r, c1);
		}

	}

	void copyRange(int r1, int c1, int r2, int c2, int r3, int c3) {
		int r, c, dr, dc;
		dr = r3 - r1;
		dc = c3 - c1;

		for (r = r1; r <= r2; r++) {
			for (c = c1; c <= c2; c++) {
				copyCell(r + dr, c + dc, r, c);
			}
		}
	}

	void delRange(int r1, int c1, int r2, int c2) {
		int r, c;
		for (r = r1; r <= r2; r++) {
			for (c = c1; c <= c2; c++) {
				delCell(r, c);
			}
		}
	}

	void HideRC(String str1, String str2, String s1, String s2) {
		// Function to Hide/Unhide, columns or Rows
		int flag = 0,n1=0, n2=0, i=0;
		if (str1.equals("H")) {flag = 1;} else {flag = 0;}
		
		if (str2.equals("R")) {//row
			n1 = Integer.valueOf(s1);
			n2 = Integer.valueOf(s2);
			for (i=n1;i<=n2;i++) {
				row_hide[i]=flag;
			}
		} else if (str2.equals("C")) {//column
			RC t1 = getRC(s1); if (t1.c==0) {n1 = Integer.valueOf(s1);} else {n1=t1.c;}
			RC t2 = getRC(s2); if (t2.c==0) {n2 = Integer.valueOf(s2);} else {n2=t2.c;}
			for (i=n1;i<=n2;i++) {
				col_hide[i]=flag;
			}
			
		}
		
		
	}

	// ***********************************[MENUS]******************************************************
	void help() {
		String msg = "";
		msg = msg + "\n" + (" ========= HELP MENU============");
		msg = msg + "\n" + (" [H] - Help Menu                ");
		msg = msg + "\n" + (" [L] - List of Functions        ");
		msg = msg + "\n" + (" [F] - Menu Files Options       ");
		msg = msg + "\n" + (" [C] - Edit Cell's Contents     ");
		msg = msg + "\n" + (" [V] - View Cell's Properties   ");
		msg = msg + "\n" + (" [E] - Menu Edit Options        ");
		msg = msg + "\n" + (" [S] - Edit Columns Size        ");
		msg = msg + "\n" + (" [U] - Hide/Unhide Rows/Cols    ");
		msg = msg + "\n" + (" [T] - Format Range             ");
		msg = msg + "\n" + (" [X] - Test RC Function         ");
		msg = msg + "\n" + (" ================================");
		msgbox(msg, "Basic VisiCalc");

	}

	void formatRange() {
		int op = 0;
		String txt1 = "", txt2 = "";
		int r, c, r1, c1, r2, c2, fmt;

		String msg = "";
		msg = msg + "\n" + ("  ==== FORMAT OPTIONS ==== ");
		msg = msg + "\n" + ("  [1XX] - Align Left     ");
		msg = msg + "\n" + ("  [2XX] - Align Right    ");
		msg = msg + "\n" + ("  [3XX] - Align Center   ");
		msg = msg + "\n" + ("  [X0X] - 0 Dec. Places  ");
		msg = msg + "\n" + ("  [X2X] - 2 Dec. Places  ");
		msg = msg + "\n" + ("  [X9X] - 9 Dec. Places  ");
		msg = msg + "\n" + ("  [XX0] - Number Format  ");
		msg = msg + "\n" + ("  [XX1] - Currency BRL   ");
		msg = msg + "\n" + ("  [XX2] - Currency USD   ");
		msg = msg + "\n" + ("  [XX3] - Currency EUR   ");
		msg = msg + "\n" + ("  [XX4] - Currency YEN   ");
		msg = msg + "\n" + ("  [XX9] - Percent Format ");
		msg = msg + "\n" + ("  ========================");
		msg = msg + "\n\n" + ("Inform the Range [CR1 CR2] and format [XXX]: ");

		String[] pr = inputBox("Basic VisiCalc", msg).split(" ");

		RC t1 = getRC(pr[0]);
		r1 = t1.r;
		c1 = t1.c;
		RC t2 = getRC(pr[1]);
		r2 = t2.r;
		c2 = t2.c;
		fmt = Integer.valueOf(pr[2]);

		for (r = r1; r <= r2; r++) {
			for (c = c1; c <= c2; c++) {
				tb[r][c].format = fmt;
			}
		}

	}

	void listFunctions() {
		int op;
		String msg = "";
		msg = msg + "\n" + ("  ===== LIST OF FUNCTIONS ====   ");
		msg = msg + "\n" + ("                                 ");
		msg = msg + "\n" + ("  =SUM(RG1:RG2)                  ");
		msg = msg + "\n" + ("      Sums the Range (RG1:RG2)   ");
		msg = msg + "\n" + ("                                 ");
		msg = msg + "\n" + ("  =SUMIF(CR1:CR2; CDX; RG1:RG2)  ");
		msg = msg + "\n" + ("    Sums the Range (RG1:RG2)     ");
		msg = msg + "\n" + ("    If CDX is true in (CD1:CD2)  ");
		msg = msg + "\n" + ("                                 ");
		msg = msg + "\n" + ("  =IF(CL1=VL1;CL2;CL3)           ");
		msg = msg + "\n" + ("   IF(CD=1) THEN ELSE            ");
		msg = msg + "\n" + ("                                 ");
		msg = msg + "\n" + ("  =A1+B2/C3*C4-C5 - Basic Math   ");
		msg = msg + "\n" + ("                                 ");
		msg = msg + "\n" + ("  =VLOOKUP(VL1;RG1:RG2;DSL)      ");
		msg = msg + "\n" + ("  =HLOOKUP(VL1;RG1:RG2;DSL)      ");
		msg = msg + "\n" + ("   LOOKUP in Columns or Rows     ");
		msg = msg + "\n" + ("                                 ");
		msg = msg + "\n" + (" ================================= ");
		msgbox(msg, "Basic VisiCalc");

	}

	private int msgbox(String msg, String title) {
		// JOptionPane.showMessageDialog(null, msg);
		int n = JOptionPane.showOptionDialog(null, msg, title, JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, null, null);
		return n;
	}

	// *********************[CELL FUNCTIONS - CALCULATE, DECODE AND COPY
	// FUNCTIONS]********************
	static void calculateCells() {
		int r, c, lr, lc;
		float vl;
		lr = lastRow;
		lc = lastCol;

		for (r = 0; r <= lr; r++) {
			for (c = 0; c <= lc; c++) {
				try {
					if (tb[r][c].flag != 0) {
						if (tb[r][c].formula.length() > 0) {
							vl = cellFunctions(r, c);
							// System.out.println("cell ("+r+","+c+") = "+
							// tb[r][c].value);
							tb[r][c].value = vl;
						}
					}
				} catch (Exception ex) {
					//System.out.println("ERROR[CalculateCells]: cell (" + getCol(c) + "" + r + ") - " + ex.getMessage());

				}
			}
		}
	}

	static float cellFunctions(int r, int c) {
		// System.out.println("Cell Functionss: (R="+r+"C="+c+")"+
		// tb[r][c].formula);

		char[] tmp = new char[50];
		int i, j, len, i1 = 0, i2 = 0;
		float tf = 0;
		String[] strx = new String[20];
		for (i = 0; i < 20; i++)
			strx[i] = "";

		String tmpx2 = tb[r][c].formula;
		char tmp2[] = tmpx2.toCharArray();
		len = tmpx2.length();

		int ix = 0;
		for (i = 0; i < len; i++) { // REMOVING SPACES / ToUpper
			if (tmp2[i] != ' ') {
				tmp[ix] = Character.toUpperCase(tmp2[i]);
				ix++;
			}
		}

		tmp[len] = 0;
		len = ix;
		strx[0] = "";
		i2 = 0;
		for (i = 0; i < len; i++) {// Formating the Formula
			if (tmp[i] == 0) {
				break;
			}
			if (tmp[i] == '=' || tmp[i] == '+' || tmp[i] == '-' || tmp[i] == '/' || tmp[i] == '*' || tmp[i] == '!'
					|| tmp[i] == '>' || tmp[i] == '<') {
				if (i2 > 0)
					i1++;
				strx[i1] = String.valueOf(tmp[i]);
				i1++;
				strx[i1] = "";
				i2 = 0;
			} else if (tmp[i] == ' ' || tmp[i] == '\n' || tmp[i] == '(' || tmp[i] == ')' || tmp[i] == ':'
					|| tmp[i] == ';') {
				i1++;
				strx[i1] = "";
				i2 = 0;
			} else {
				strx[i1] = strx[i1] + String.valueOf(tmp[i]);
				i2++;
			}
		}
		if (strx[0].equals("=")) {
		} else {
			return 0;
		}

		// ----------- Checking ---------------
		int ft = 0; // Flag Test
		if (ft == 1) {
			if (strx[0].equals("=")) {
				System.out.printf("\nDiv: ");
				for (i = 0; i <= i1; i++) {
					System.out.printf("'%s' /", strx[i]);
				}
				System.out.println("");
			}
		}
		// -------------------------------------

		// =SUM (R1C1 R2C2)
		if (strx[1].equals("SUM")) {
			// printf("\nFormula: ",tmp);

			RC t1 = getRC(strx[2]);
			RC t2 = getRC(strx[3]);
			// Sum Range
			tf = 0;
			for (r = t1.r; r <= t2.r; r++) {
				for (c = t1.c; c <= t2.c; c++) {
					tf = tf + tb[r][c].value;
				}
			}
			return tf;
		}
		// =SUMIF (CD1:CD2;CDX;RG1:RG2)
		if (strx[1].equals("SUMIF")) {
			float a1;
			int d1, d2;
			// = SUM(CD1:CD2;CDX;RG1:RG2)
			// 0 1 2 3 4 5 6

			RC t1 = getRC(strx[2]);
			RC t2 = getRC(strx[3]);
			float cdx = getCellVal(strx[4]);
			RC t3 = getRC(strx[5]);
			RC t4 = getRC(strx[6]);

			d1 = t3.r - t1.r;
			d2 = t3.c - t1.c;

			// Sum Range
			tf = 0;
			for (r = t1.r; r <= t2.r; r++) {
				for (c = t1.c; c <= t2.c; c++) {
					a1 = tb[r][c].value;
					if (a1 == cdx) {
						tf = tf + tb[r + d1][c + d2].value;
					}
				}
			}
			return tf;
		}

		// =VLOOKUP(VS;X1:X2;C)
		if (strx[1].equals("VLOOKUP")) {
			float a1, a2;
			int dsl = 0;
			// =VLOOKUP(VS;X1:X2;C)
			// 0 1 2 3 4 5
			tf = 0;
			a1 = getCellVal(strx[2]);// Value Searched
			RC t1 = getRC(strx[3]); // Range X1
			RC t2 = getRC(strx[4]); // Range X2
			dsl = (int) getCellVal(strx[5]);// DSL Column

			// Sum Range
			tf = 0;
			for (r = t1.r; r <= t2.r; r++) {
				a2 = tb[r][t1.c].value;
				if (a1 == a2) {
					tf = tb[r][t1.c + dsl - 1].value;
					break;
				}
			}
			return tf;
		}
		// =HLOOKUP(VS;X1:X2;C)
		if (strx[1].equals("HLOOKUP")) {
			float a1, a2;
			int dsl = 0;
			// =HLOOKUP(VS;X1:X2;C)
			// 0 1 2 3 4 5
			tf = 0;
			a1 = getCellVal(strx[2]);// Value Searched
			RC t1 = getRC(strx[3]); // Range X1
			RC t2 = getRC(strx[4]); // Range X2
			dsl = (int) getCellVal(strx[5]);// DSL Column

			tf = 0;
			for (c = t1.c; c <= t2.c; c++) {
				a2 = tb[t1.r][c].value;
				if (a1 == a2) {
					tf = tb[t1.r + dsl - 1][c].value;
					break;
				}
			}
			return tf;
		}
		if (strx[1].equals("IF")) {
			// =IF(A = B):THEN:ELSE
			// 0 1 2 3 4 5 6
			float a, b;
			int flag = 0;

			a = getCellVal(strx[2]);
			b = getCellVal(strx[4]);

			if (strx[3].equals("=")) {
				if (a == b) {
					flag = 1;
				}
			}
			;
			if (strx[3].equals("!")) {
				if (a != b) {
					flag = 1;
				}
			}
			;
			if (strx[3].equals(">")) {
				if (a > b) {
					flag = 1;
				}
			}
			;
			if (strx[3].equals("<")) {
				if (a < b) {
					flag = 1;
				}
			}
			;

			if (flag == 1) {
				tf = getCellVal(strx[5]);
			} else {
				tf = getCellVal(strx[6]);
			}
			return tf;
		}

		// = R1C1 + R2C2 - R3C2 - R4C4 * R5C5 / R6C6
		if (strx[0].equals("=")) {
			int op = 1; // 0 = Equal 1 Addition, 2 - Subtraction , 3 =
						// Multiplication 4 = Division
			float val = 0;
			tf = 0;

			for (i = 1; i <= i1 + 1; i++) {
				try {
					if (strx[i].length() == 1) {
						if (strx[i].equals("="))
							op = 0;
						if (strx[i].equals("+"))
							op = 1;
						if (strx[i].equals("-"))
							op = 2;
						if (strx[i].equals("*"))
							op = 3;
						if (strx[i].equals("/"))
							op = 4;
					}
					if (strx[i].length() > 1) {
						try {
							val = getCellVal(strx[i]);

							if (op == 0) {
								tf = val;
							}
							if (op == 1) {
								tf = tf + val;
							}
							if (op == 2) {
								tf = tf - val;
							}
							if (op == 3) {
								tf = tf * val;
							}
							if (op == 4) {
								tf = tf / val;
							}
						} catch (Exception ex) {
							val = 0;
						}
						// System.out.printf("P= %.2f / T= %.2f \n", val, tf);
					}
				} catch (Exception ex) {
				}

			}
			return tf;
		}
		return 0;
	}

	void copyFormula(int rd, int cd, int ro, int co) {
		char[] tmp = new char[50];
		int i, j, len, i1 = 0, i2 = 0;
		float tf = 0;
		String[] strx = new String[20];
		for (i = 0; i < 20; i++)
			strx[i] = "";

		String tmpx2 = tb[ro][co].formula;
		char[] tmp2 = tmpx2.toCharArray();
		//
		len = tmpx2.length();
		int ix = 0;
		for (i = 0; i < len; i++) { // REMOVING SPACES / ToUpper
			if (tmp2[i] != ' ') {
				tmp[ix] = Character.toUpperCase(tmp2[i]);
				ix++;
			}
		}
		tmp[len] = 0;

		if (tmp[0] != '=') {
			tb[rd][cd].formula = tb[ro][co].formula;
		}

		// System.out.println("Formula = " + String.valueOf(tmp));

		len = ix;
		strx[0] = "";
		for (i = 0; i < len; i++) {// Formating the Formula
			if (tmp[i] == 0) {
				break;
			}
			if (tmp[i] == '=' || tmp[i] == '+' || tmp[i] == '-' || tmp[i] == '/' || tmp[i] == '*' || tmp[i] == '!'
					|| tmp[i] == '>' || tmp[i] == '<' || tmp[i] == ' ' || tmp[i] == '\n' || tmp[i] == '('
					|| tmp[i] == ')' || tmp[i] == ':' || tmp[i] == ';') {
				if (i2 > 0)
					i1++;
				strx[i1] = String.valueOf(tmp[i]);
				i1++;
				strx[i1] = "";
				i2 = 0;
			} else {
				strx[i1] = strx[i1] + String.valueOf(tmp[i]);
				i2++;
			}
		}

		// ----------- Checking ---------------
		int ft = 0; // Flag Test
		if (ft == 1) {
			// if (strx[0].equals("=")) {
			System.out.printf("\nDiv: ");
			for (i = 0; i < i1; i++) {
				System.out.printf("'%s' /", strx[i]);
			}
			System.out.println("");
			// }
		}
		// -------------------------------------

		// = R1C1 + R2C2 - R3C2 - R4C4 * R5C5 / R6C6
		int dr, dc, r, c;
		if (strx[0].equals("=")) {
			try {
				for (i = 1; i <= i1; i++) {
					if (strx[i].length() > 1) {
						char[] tc = strx[i].toCharArray();
						if (tc[0] >= 48 && tc[0] <= 57 || tc[0] == '[') {
							// Literal - Keep it as it is
						} else {
							RC t1 = getRC(strx[i]);// Cell
							if (t1.r != 0 && t1.c != 0) {
								// Change Reference
								dr = t1.r - ro; // Ref - Orige
								dc = t1.c - co; // Ref - Orige
								r = rd + dr;
								c = cd + dc;
								strx[i] = getCell(r, c);
							}
						}
					}
				}
			} catch (Exception ex) {
			}

			String Tmpx = "";
			for (i = 0; i <= i1 + 1; i++) {
				Tmpx = Tmpx + strx[i];
			}

			// Copying the Changed String to the New Cell
			// System.out.println("New Formula = " + Tmpx);
			tb[rd][cd].formula = Tmpx;
		} else {
			tb[rd][cd].formula = tb[ro][co].formula;
		}

	}

	// ***********************************[PRINT AND FORMAT CELLS
	// ]************************************
	static String printCell(cell c, int col) {

		int size = col_size[col];
		if (size == 0) {
			return "";
		}

		char[] prt = new char[50];
		String str = "";
		String fnbr = "";

		int fmt = 0, al = 0, pl = 0, cur = 0;// Formats fmt, al = Alignment, pl
												// = places, cur = Currency
		// Extracting the formats
		if (c.flag != 0) {
			fmt = c.format;
			al = fmt / 100; // Extracting the Alignment
			pl = (fmt % 100) / 10;
			cur = (fmt % 10);
			if (cur == 1) {
				fnbr = "BRL ";
			}
			if (cur == 2) {
				fnbr = "USD ";
			}
			if (cur == 3) {
				fnbr = "EUR ";
			}
			if (cur == 4) {
				fnbr = "YEN ";
			}
			if (cur == 9) {
				fnbr = "%";
			}
		}

		if (c.flag == 0) { // cell empty
			str = "";
		} else if (c.flag == 1) { // Number
			float nb = c.value;
			if (cur == 9) {
				nb = nb * 100;
			} // %

			if (pl == 0) {
				str = String.format("%.0f%n", nb);
			}
			if (pl == 1) {
				str = String.format("%.1f%n", nb);
			}
			if (pl == 2) {
				str = String.format("%.2f%n", nb);
			}
			if (pl == 3) {
				str = String.format("%.3f%n", nb);
			}
			if (pl == 4) {
				str = String.format("%.4f%n", nb);
			}
			if (pl == 5) {
				str = String.format("%.5f%n", nb);
			}
			if (pl == 6) {
				str = String.format("%.6f%n", nb);
			}
			if (pl == 7) {
				str = String.format("%.7f%n", nb);
			}
			if (pl == 8) {
				str = String.format("%.8f%n", nb);
			}
			if (pl == 9) {
				str = String.format("%.9f%n", nb);
			}

			if (cur >= 1 && cur <= 4) {
				str = fnbr + str;
			} // Currency
			if (cur == 9) {
				str = str + fnbr;
			} // %

		} else if (c.flag == 2) { // Text
			str = c.formula;
		}

		int i = 0, len = str.length();

		int pos = 0;// Default Align Left
		for (i = 0; i < 50; i++)
			prt[i] = ' ';

		// default Align Left;
		pos = 0;

		// --------- Format Alignment -----------------
		if (al == 1) {
			pos = 0;
		} // Align-Left
		if (al == 2) {
			pos = size - len;
		} // Align-Right
		if (al == 3) {
			pos = size / 2 - len / 2;
		} // Align-Center
		// ----------------------------------------------
		char strx[] = str.toCharArray();
		for (i = 0; i < len; i++) {
			prt[i + pos] = strx[i];
		}
		prt[size] = 0;
		String str2 = "";
		try {
			for (i = 0; i < size; i++) {
				if (prt[i] == 0)
					break;
				str2 = str2 + String.valueOf(prt[i]);
			}
		} catch (Exception ex) {
		}
		return str2;

	}

	String printRow(int row) {
		String buffer = String.valueOf(row);
		return printData(buffer, 4, 0);

	}

	String printCol(int col) {
		return printData(getCol(col), col_size[col], 2);// center
	}

	String printData(String strx, int size, int align) {// Commom code
		if (size == 0) {
			return "";
		}
		char[] prt = new char[50];
		char[] str = strx.toCharArray();

		int i = 0, len = str.length;
		int pos = 0;// Default Align Left
		for (i = 0; i < 50; i++)
			prt[i] = ' ';
		// default Align Left;
		if (align == 0) {
			pos = 0;
		} // Align-Left
		if (align == 1) {
			pos = size - len;
		} // Align-Right
		if (align == 2) {
			pos = size / 2 - len / 2;
		} // Align-Center

		for (i = 0; i < len; i++) {
			prt[i + pos] = str[i];
		}
		String str2 = "";
		for (i = 0; i < size; i++) {
			if (prt[i] == 0)
				break;
			str2 = str2 + String.valueOf(prt[i]);

		}

		prt[size] = 0;
		return str2;

	}

	// ******************************[FILES: OPEN, SAVE, SAVE AS,
	// CLOSE]*******************************

	void files(int op) {
		if (op == 1) {
			newSpreadsheet();
		}
		if (op == 2) {
			openFile();
		}
		if (op == 3) {
			saveFile();
		}
		if (op == 4) {
			saveFileAs();
		}
		if (op == 5) {
			btnExit();
		}
	}

	void saveFile() {
		// char currFile[50] = "c:\temp\vcalc1.vc";
		String cFile = currPath + currFile;

		// lastCol = NCOL;
		// lastRow = NROW;

		BufferedWriter file1 = null;
		try {
			FileWriter fstream = new FileWriter(cFile, false); // true tells to
																// append data.
			file1 = new BufferedWriter(fstream);
			int r, c;

			file1.write("VisicalcProjectFile:\r\n");
			file1.write("RC:\r\n " + lastRow + 1 + " " + lastCol + 1 + " \r\n");
			for (c = 0; c < lastCol + 1; c++) {
				file1.write("col:\r\n " + c + " " + col_size[c] + " " + col_hide[c] + " \r\n");
			}

			for (r = 0; r < lastRow + 1; r++) {
				file1.write("row:\r\n " + r + " " + row_hide[r] + " \r\n");
			}

			int nr = 0;
			for (r = 0; r <= lastRow + 1; r++) {
				for (c = 0; c <= lastCol + 1; c++) {
					if (tb[r][c].flag != 0) {
						nr++;
					}
				}
			}

			file1.write("nr= " + nr + "\r\n");// number of cells

			for (r = 0; r <= lastRow + 1; r++) {
				for (c = 0; c <= lastCol + 1; c++) {
					if (tb[r][c].flag != 0) {
						file1.write("data1:\r\n " + r + " " + c + " " + tb[r][c].flag + " " + tb[r][c].format + " "
								+ tb[r][c].value + " \r\n");
						if (tb[r][c].formula.length() > 0) {
							file1.write("data2:\r\n" + tb[r][c].formula + "\r\n");
						}
					}
				}
			}
			file1.write("End:\r\n");
			file1.close();

			String msg = "";
			msg = msg + "\n" + (" ********************** ");
			msg = msg + "\n" + (" ** ==File SAVED! == ** ");
			msg = msg + "\n" + (" ********************** ");
			msgbox(msg, "Basic VisiCalc");
		} catch (IOException e) {
			System.out.println("Writing to File Error: " + e.getMessage());
		}

	}

	void saveFileAs() {
		String tmp = inputBox("Save File As", "\n Inform the new name for the file: [" + currFile + "]:");
		if (tmp.length() > 0) {
			currFile = tmp;
		}
		saveFile();

	}

	void openFile() {
		String tmp = inputBox("Open File", "Open the Current File [" + currFile + "] ? [Y/N]");
		if (tmp.equals("N") || tmp.equals("n")) {
			tmp = inputBox("Open File", "\nInform the Name of the File: ");
			if (tmp.length() > 0) {
				currFile = tmp;
			}
		}
		newSpreadsheet();
		// ****************** Opening the File ******************
		String cFile = "";
		String tmpf = "";
		cFile = currPath + currFile;
		int r = 0, c = 0, a = 0, b = 0, ch = 0;
		float f;
		// printf("\n%s\n", cFile);

		try {

			BufferedReader file2 = new BufferedReader(new FileReader(cFile));

			while ((tmpf = file2.readLine()) != null) {

				if (tmpf.equals("RC:")) {
					String pr[] = file2.readLine().split(" ");
					lastRow = 0;//Integer.valueOf(pr[1]);
					lastCol = 0;//Integer.valueOf(pr[2]);

				}
				if (tmpf.equals("col:")) {
					String pr[] = file2.readLine().split(" ");
					a = Integer.valueOf(pr[1]);
					b = Integer.valueOf(pr[2]);
					c = Integer.valueOf(pr[3]);
					col_size[a] = b;
					col_hide[a] = c;

				}
				if (tmpf.equals("row:")) {
					String pr[] = file2.readLine().split(" ");
					a = Integer.valueOf(pr[1]);
					b = Integer.valueOf(pr[2]);
					row_hide[a] = b;
				}
				if (tmpf.equals("data1:")) {
					// fscanf(file2," %i %i %i %i %f \n", &r, &c, &a, &ch, &f);
					String pr[] = file2.readLine().split(" ");
					r = Integer.valueOf(pr[1]);
					c = Integer.valueOf(pr[2]);
					a = Integer.valueOf(pr[3]);
					ch = Integer.valueOf(pr[4]);
					f = Float.valueOf(pr[5]);
					if (r != 0 && c != 0) {
						tb[r][c].flag = a;
						tb[r][c].format = ch;
						tb[r][c].value = f;
						if (r>lastRow) lastRow = r;
						if (c>lastRow) lastCol = c;
					}
				}
				if (tmpf.equals("data2:")) {
					String str = file2.readLine();
					if (r != 0 && c != 0) {
						if (str.length() > 0) {
							tb[r][c].formula = str;
						}
					}
				}
				if (tmpf.equals("End:")) {
					System.out.println("\n**End of File**\n");
					break;
				}
			}
			file2.close();

		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}
		showMatrix();

	}

	// ************************************************************************************************

	public void updateCell() {

		// String str = inputBox("Basic VisiCalc", "Edit Cells \n\n [Cell]
		// [Value] [Format/Text]");
		String txt1 = t1.getText();
		String txt2 = t2.getText();
		String txt3 = t3.getText();

		RC tx = new RC(0, 0);
		tx = getRC(txt1);

		if (tx.r > 0 && tx.c > 0) {// Formula/Text
			if (txt3.length() > 0) {
				String x = txt3.substring(0, 1);
				if (x.equals("=")) {
					tb[tx.r][tx.c] = setCell(1, 0, 0, txt3);
				} else {
					tb[tx.r][tx.c] = setCell(2, 0, 0, txt3);
				}
				// System.out.println("Cell: "+ txt1 + ", (r= " + tx.r + ", c=
				// "+ tx.c+") Text = "+ tb[tx.r][tx.c].formula);
				// System.out.println("(r= " + tx.r + ", c= "+ tx.c+") getCell =
				// " + getCell(tx.r, tx.c) + ", getCellValue = " +
				// getCellVal(txt1));
			} else {// Value
				float f = Float.valueOf(txt2);
				tb[tx.r][tx.c] = setCell(1, 0, f, "");
				// System.out.println("Cell: "+ txt1 + ", (r= " + tx.r + ", c=
				// "+ tx.c+") Value = "+ tb[tx.r][tx.c].value);
				// System.out.println("(r= " + tx.r + ", c= "+ tx.c+") getCell =
				// " + getCell(tx.r, tx.c) + ", getCellValue = " +
				// getCellVal(txt1));
			}
			if (tx.r > lastRow)
				lastRow = tx.r;
			if (tx.c > lastCol)
				lastCol = tx.c;
			t2.setText("");
			t3.setText("");
			showMatrix();

		}
	}

	void editOptions(int op) {
		int r1, c1, r2, c2, r3, c3;
		String txt1 = "";
		String txt2 = "";
		String txt3 = "";
		String msg = "";

		if (op == 0) {

			msg = msg + "\n" + (" =========== EDIT OPTIONS ===========");
			msg = msg + "\n" + (" 1 - Insert Row   \t2 - Insert Col   ");
			msg = msg + "\n" + (" 3 - Delete Rows  \t4 - Delete Cols  ");
			msg = msg + "\n" + (" 5 - Excl.  Rows  \t6 - Excl. Cols   ");
			msg = msg + "\n" + (" 7 - Delete Range \t8 - Copy Range   ");
			msg = msg + "\n" + (" 9 - Format Range \t10- Resize Cols  ");
			msg = msg + "\n" + (" 11- Edit Cells   \t12- (Un)Hide RCs ");
			msg = msg + "\n" + (" 13- View Cells   \t                 ");
			msg = msg + "\n" + (" ====================================");

			msg = msg + "\n\n" + ("Enter an Option: ");
			op = Integer.valueOf(inputBox("Edit Options", msg));

			// lastCol = NCOL-2;
			// lastRow = NROW-2;

		}

		if (op == 1) {
			r1 = Integer.valueOf(inputBox("Insert Row", "\nEnter the Row number:"));
			insertRow(r1);
		}
		if (op == 2) {
			txt1 = (inputBox("Insert Col", "\nEnter the Column:"));
			RC t = getRC(txt1);
			c1 = t.c;
			insertCol(c1);
		}
		if (op == 3) {
			String[] pr = (inputBox("Delete Rows [R1 R2]", "\nEnter [r1] [r2]:")).split(" ");
			r1 = Integer.valueOf(pr[0]);
			r2 = Integer.valueOf(pr[1]);

			deleteRows(r1, r2);
		}
		if (op == 4) {
			String[] pr = (inputBox("Delete Cols [C1 C2]", "\nEnter [C1] [C2]:")).split(" ");
			RC t1 = getRC(pr[0]);
			c1 = t1.c;
			RC t2 = getRC(pr[1]);
			c2 = t2.c;
			deleteColumns(c1, c2);
		}

		if (op == 5) {
			String[] pr = (inputBox("Exclude Rows [R1 R2]", "\nEnter [r1] [r2]:")).split(" ");
			r1 = Integer.valueOf(pr[0]);
			r2 = Integer.valueOf(pr[1]);
			if (r1 != 0 && r2 != 0) {
				excludeRows(r1, r2);
			}
		}
		if (op == 6) {
			String[] pr = (inputBox("Exclude Cols [C1 C2]", "\nEnter [C1] [C2]:")).split(" ");
			RC t1 = getRC(pr[0]);
			c1 = t1.c;
			RC t2 = getRC(pr[1]);
			c2 = t2.c;
			if (c1 != 0 && c2 != 0) {
				excludeColumns(c1, c2);
			}
		}

		if (op == 7) {
			String[] pr = (inputBox("Delete Range[RC1 RC2]", "\nEnter [RC1] [RC2]:")).split(" ");
			RC t1 = getRC(pr[0]);
			r1 = t1.r;
			c1 = t1.c;
			RC t2 = getRC(pr[1]);
			r2 = t2.r;
			c2 = t2.c;
			delRange(r1, c1, r2, c2);
		}
		if (op == 8) {
			String[] pr = (inputBox("Copy Range (RC1 RC2] to [RC3]", "\nEnter [RC1] [RC2] [RC3]:")).split(" ");

			RC t1 = getRC(pr[0]);
			r1 = t1.r;
			c1 = t1.c;
			RC t2 = getRC(pr[1]);
			r2 = t2.r;
			c2 = t2.c;
			RC t3 = getRC(pr[2]);
			r3 = t3.r;
			c3 = t3.c;

			copyRange(r1, c1, r2, c2, r3, c3);
		}

		if (op == 9) {
			formatRange();
		}
		if (op == 10) {
			otherOptions('S');
		}
		if (op == 11) {
			otherOptions('C');
		}
		if (op == 12) {
			otherOptions('U');
		}
		if (op == 13) {
			otherOptions('V');
		}

		showMatrix();
	}

	void otherOptions(char kx) {
		// Format Columns, Resize Columns, Edit Cells
		String txt1 = "";
		String txt2 = "";
		String msg = "";

		if (kx == 'U' || kx == 'u') {// Hide and Unhide
			String[] pr = (inputBox("Hide/UnHide RC", "\nEnter [H/U] [R/C] [N1 N2]:")).split(" ");
			if (pr[0].length() >0) {HideRC(pr[0].toUpperCase(), pr[1].toUpperCase(), pr[2], pr[3]);}

		}
		if (kx == 'S' || kx == 's') {
			int c, c1, c2, sz;
			String[] pr = (inputBox("Coluns Size [C1..C2] ", "\nEnter [C1] [C2] [size]:")).split(" ");

			RC t1 = getRC(pr[0]);
			c1 = t1.c;
			RC t2 = getRC(pr[1]);
			c2 = t2.c;
			sz = Integer.valueOf(pr[2]);
			for (c = c1; c <= c2; c++) {
				col_size[c] = sz;
			}
		}
		if (kx == 'C' || kx == 'c') {
			updateCell();
		}
		if (kx == 'V' || kx == 'v') {// View Cell's Properties

			txt1 = inputBox("View Cell", "\nEnter [Cell]:");

			while (txt1.length() > 0) {

				msg = "";
				RC t = getRC(txt1);
				msg = msg + "\nCell   : " + txt1.toUpperCase() + " [R=" + t.r + ", C=" + t.c + "]";
				msg = msg + "\nFlag   : " + tb[t.r][t.c].flag;
				msg = msg + "\nFormat : " + tb[t.r][t.c].format;
				msg = msg + "\nValue  : " + tb[t.r][t.c].value;
				msg = msg + "\nFormula: " + tb[t.r][t.c].formula;
				msg = msg + "\n\nEnter [Cell]:";

				txt1 = inputBox("View Cell", msg);
			}
		}

	}

	static String inputBox(String title, String prompt) {
		String str = "";
		// str = (JOptionPane.showInputDialog(prompt));
		str = JOptionPane.showInputDialog(null, prompt, title, JOptionPane.PLAIN_MESSAGE);
		return str;
	}

}// End of Class
