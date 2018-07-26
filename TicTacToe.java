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


public class TicTacToe extends JPanel {

	// GUI Variables
	static JLabel l1;
	static JButton b1, b2, b3, b4, b5, b6, b7, b8, b9;
	static JButton bNewGame, bExit;
	static JRadioButton r1, r2, r3;
	static ButtonGroup bG;
	static JFrame jf;
	static TicTacToe stp;
	static GridBagConstraints gbc, gbc2, gbc3;
	static JPanel panel1, panel2, panel3;

	// Project Variables
	static int op = 1, mv_seq = 0, p = 1;
	static String p1 = "Player#2", p2 = "Player#2";
	static char[] mt = new char[10];
	
	
	public TicTacToe() { // Constructor
		setLayout(new GridBagLayout());
		
		//Panels (Group Box)
		  panel1 = new JPanel();
		  panel1.setSize(200,200);
		  panel1.setBorder(new TitledBorder (new LineBorder (Color.black, 1),""));
		  //panel1.setLayout(new FlowLayout());
		  GridBagLayout layout = new GridBagLayout();
		  panel1.setLayout(layout);        
		  
		  
		  panel2 = new JPanel();
		  panel2.setSize(200,200);
		  panel2.setBorder(new TitledBorder (new LineBorder (Color.black, 1),"Game Options"));
		  //panel2.setLayout(new FlowLayout());
		  GridBagLayout layout2 = new GridBagLayout();
		  panel2.setLayout(layout2);        
		
		  panel3 = new JPanel();
		  panel3.setSize(350,150);
		  //panel3.setBorder(new TitledBorder (new LineBorder (Color.black, 1),"Game Options"));
		  panel3.setLayout(new FlowLayout());
		  GridBagLayout layout3 = new GridBagLayout();
		  panel3.setLayout(layout3);
		  //panel3.add(panel1, panel2);
		  
				 
		  
	
		  
		//GridBad (Layout setup)
		  gbc = new GridBagConstraints();
		  gbc2 = new GridBagConstraints();	
		  gbc3 = new GridBagConstraints();
		
		// Labels
		l1 = new JLabel("Player#1 vs Player#2");
		l1.setFont(new Font("Calibri", Font.BOLD, 14));
		l1.setForeground(Color.BLUE);
		
		
		
		// Buttons
		int size = 20;
		b1 = new JButton("1");b1.setFont(new Font("Calibri", Font.BOLD, size));
		b2 = new JButton("2");b2.setFont(new Font("Calibri", Font.BOLD, size));
		b3 = new JButton("3");b3.setFont(new Font("Calibri", Font.BOLD, size));
		b4 = new JButton("4");b4.setFont(new Font("Calibri", Font.BOLD, size));
		b5 = new JButton("5");b5.setFont(new Font("Calibri", Font.BOLD, size));
		b6 = new JButton("6");b6.setFont(new Font("Calibri", Font.BOLD, size));
		b7 = new JButton("7");b7.setFont(new Font("Calibri", Font.BOLD, size));
		b8 = new JButton("8");b8.setFont(new Font("Calibri", Font.BOLD, size));
		b9 = new JButton("9");b9.setFont(new Font("Calibri", Font.BOLD, size));
		
		
		bNewGame = new JButton("New Game");
		bExit = new JButton("Exit");
		
		//Radio Buttons
		r1 = new JRadioButton("Player#1 vs Player#2");
		r2 = new JRadioButton("Player#1 vs Computer");
		r3 = new JRadioButton("Computer vs Player#1");
		bG = new ButtonGroup();
		bG.add(r1); bG.add(r2); bG.add(r3);
		r1.setSelected(true);

		
		// ************ Panel #1 ************************
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(4,4,4,4);
		
		
		gbc.gridwidth =1;
		gbc.gridx = 1;
		gbc.gridy = 1;panel1.add(b1,gbc);
		gbc.gridy = 2;panel1.add(b2,gbc);
		gbc.gridy = 3;panel1.add(b3,gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;panel1.add(b4,gbc);
		gbc.gridy = 2;panel1.add(b5,gbc);
		gbc.gridy = 3;panel1.add(b6,gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;panel1.add(b7,gbc);
		gbc.gridy = 2;panel1.add(b8,gbc);
		gbc.gridy = 3;panel1.add(b9,gbc);
		
		
		
		// ************ Panel #2 ************************
		gbc2.fill = GridBagConstraints.BOTH;
		gbc2.anchor = GridBagConstraints.NORTHWEST;
		gbc2.insets = new Insets(3,3,3,3);
		
		gbc2.gridwidth =2;
		gbc2.gridx = 1;
		gbc2.gridy = 1;panel2.add(r1,gbc2);
		gbc2.gridy = 2;panel2.add(r2,gbc2);
		gbc2.gridy = 3;panel2.add(r3,gbc2);
		gbc2.gridwidth =1;
		gbc2.gridy = 4;panel2.add(bNewGame,gbc2);
		gbc2.gridx = 2;
		gbc2.gridy = 4;panel2.add(bExit,gbc2);
		
		// ************ Panel #3 ************************
		gbc3.fill = GridBagConstraints.BOTH;
		gbc3.anchor = GridBagConstraints.NORTHWEST;
		gbc3.insets = new Insets(3,3,3,3);

		gbc3.gridwidth =1;
		gbc3.gridx = 1;
		gbc3.gridy = 1;panel3.add(l1,gbc3);
		gbc3.gridy = 2;panel3.add(panel1,gbc3);
		gbc3.gridheight =2;
		gbc3.gridx = 2;
		gbc3.gridy = 1;panel3.add(panel2,gbc3);
		//************************************************
		

	}

	public void startM() {
		jf = new JFrame("Tic-Tac-Toe Game");
		stp = new TicTacToe();
		jf.setSize(380,220);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		//jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf.add(stp);
		jf.add(panel3);
		jf.setVisible(true);
		
		b1.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{btnClick("1");}});
		b2.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{btnClick("2");}});
		b3.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{btnClick("3");}});
		b4.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{btnClick("4");}});
		b5.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{btnClick("5");}});
		b6.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{btnClick("6");}});
		b7.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{btnClick("7");}});
		b8.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{btnClick("8");}});
		b9.addActionListener(new ActionListener() {	public void actionPerformed(ActionEvent e) 
		{btnClick("9");}});
		bNewGame.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e)
		{newGame();}});
		bExit.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) 
		{jf.dispose();}});
		r1.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e)
		{newGame();}});
		r2.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e)
		{newGame();}});
		r3.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e)
		{newGame();}});
		
		newGame(); // Starting
	}
	
	private void btnClick(String txt) {
		
		int mv = Integer.valueOf(txt);
		movementControl(mv);
	}
	
	private void newGame() {
		// reset variables and start a new game
		if (r1.isSelected()) {op = 1;p1 = "Player#1"; p2= "Player#2";}
		if (r2.isSelected()) {op = 2;p1 = "Player#1"; p2= "Computer";}
		if (r3.isSelected()) {op = 3;p1 = "Computer"; p2= "Player#1";}
		mv_seq = 0; p = 1;
		clearMatrix();
		l1.setText(p1 + " vs "+ p2);
		if (op==3) {movementControl(0);} // Computer move
	}

	private void clearMatrix() {
		Color cx = Color.BLACK;
		for (int i = 1; i<=9; i++){
			mt[i]='0';
			buttonColor(i, cx);
		}
		showMatrix();
		
	}
	private void showMatrix() {
		if (mt[1]!='0') {b1.setText(String.valueOf(mt[1]));} else {b1.setText("  ");}
		if (mt[2]!='0') {b2.setText(String.valueOf(mt[2]));} else {b2.setText("  ");}
		if (mt[3]!='0') {b3.setText(String.valueOf(mt[3]));} else {b3.setText("  ");}
		if (mt[4]!='0') {b4.setText(String.valueOf(mt[4]));} else {b4.setText("  ");}
		if (mt[5]!='0') {b5.setText(String.valueOf(mt[5]));} else {b5.setText("  ");}
		if (mt[6]!='0') {b6.setText(String.valueOf(mt[6]));} else {b6.setText("  ");}
		if (mt[7]!='0') {b7.setText(String.valueOf(mt[7]));} else {b7.setText("  ");}
		if (mt[8]!='0') {b8.setText(String.valueOf(mt[8]));} else {b8.setText("  ");}
		if (mt[9]!='0') {b9.setText(String.valueOf(mt[9]));} else {b9.setText("  ");}
	}
	
	private void colorABC(int a, int b, int c, Color cx) {
		buttonColor(a, cx);
		buttonColor(b, cx);
		buttonColor(c, cx);
	}

	private void buttonColor(int bt, Color cx) {
		if (bt==1) {b1.setForeground(cx);}
		if (bt==2) {b2.setForeground(cx);}
		if (bt==3) {b3.setForeground(cx);}
		if (bt==4) {b4.setForeground(cx);}
		if (bt==5) {b5.setForeground(cx);}
		if (bt==6) {b6.setForeground(cx);}
		if (bt==7) {b7.setForeground(cx);}
		if (bt==8) {b8.setForeground(cx);}
		if (bt==9) {b9.setForeground(cx);}
	}

	private void movementControl(int move) {
		if ((op==2 && p==2) || (op==3 && p==1)) {
			move = computerMove();
		}
		
		String msg = "";
		if ((move>=1 && move <=9) && mt[move]=='0') {
			if (p==1) {
				mt[move] = 'X';
				msg = p1 + " [X] Turn";
				p = 2;
			} else {
				mt[move] = 'O';
				msg = p2 + " [O] Turn";
				p = 1;
			}
			mv_seq++;
			l1.setText(msg);
			showMatrix();
			EndOfGame_Check();
			if ((op==2 && p==2) || (op==3 && p==1)) {
				movementControl(0);
			}
			
		} else {
			l1.setText("Invalid Move, Try Again!!!");
		}
	}

	private void EndOfGame_Check()
    {
        String msg = ""; ;
        int wc = winnerCheck();

        if (wc != -1)
        {
            if (wc == 0) { msg = "There were NO Winners \n Would you like to Start a New Game?"; }
            else
            {
                if (mt[wc] == 'X') msg = p1 + " [X] has WON!!! \n Would you like to Start a New Game?";
                if (mt[wc] == 'O') msg = p2 + " [O] has WON!!! \n Would you like to Start a New Game?";
                hlightWinner(wc);
            }
            
            //if (MessageBox.Show(msg, "TicTacToe Game", MessageBoxButtons.YesNo, MessageBoxIcon.Error) == System.Windows.Forms.DialogResult.Yes)
            if (msgbox(msg, "TicTacToe Game")==0) 
            {
                newGame();
            };
        }
    }
	
    private  int winnerCheck()
    {

        if (mt[1] == mt[2] && mt[2] == mt[3] && mt[3] != '0') { return 1; }//1st row
        if (mt[4] == mt[5] && mt[5] == mt[6] && mt[6] != '0') { return 4; }//2nd row
        if (mt[7] == mt[8] && mt[8] == mt[9] && mt[9] != '0') { return 7; }//3rd row

        if (mt[1] == mt[4] && mt[4] == mt[7] && mt[1] != '0') { return 1; }//1st column
        if (mt[2] == mt[5] && mt[5] == mt[8] && mt[8] != '0') { return 2; }//2nd column
        if (mt[3] == mt[6] && mt[6] == mt[9] && mt[9] != '0') { return 6; }//3rd column

        if (mt[1] == mt[5] && mt[5] == mt[9] && mt[9] != '0') { return 9; }//1st column
        if (mt[3] == mt[5] && mt[5] == mt[7] && mt[7] != '0') { return 3; }//1st column

        int i = 0;
        for (i = 1; i <= 9; i++)
        {
            if (mt[i] == '0') { return -1; }
        }

        return 0; //Tie
    }

    private void hlightWinner(int wc)
    {
        Color cx = Color.BLUE;

        if (wc == 1) { colorABC(1, 2, 3, cx); }
        if (wc == 4) { colorABC(4, 5, 6, cx); }
        if (wc == 7) { colorABC(7, 8, 9, cx); }
        
        if (wc == 1) { colorABC(1, 4, 7, cx); }
        if (wc == 2) { colorABC(2, 5, 8, cx); }
        if (wc == 6) { colorABC(3, 6, 9, cx); }

        if (wc == 9) { colorABC(1, 5, 9, cx); }
        if (wc == 3) { colorABC(3, 5, 7, cx); }
    }

    private int msgbox(String msg, String title){
    	   //JOptionPane.showMessageDialog(null, msg);
    			int n = JOptionPane.showOptionDialog(null,msg, title,
    								JOptionPane.YES_NO_OPTION,
    								JOptionPane.QUESTION_MESSAGE,
    								null, null, null);
    			return n;
    }
    
    
    int computerMove()
    {
        // It reads the Matrix and change 'mv' based on the mv_seq

        //MessageBox.Show("Op = " + op.ToString() + ", Mv_Seq = " + mv_seq.ToString());
        if (op == 1) { return -1; } // Player#1 vs Player#2

        int t = 0;
        char cp, ad; //cp = computer, ad = adversary

        if (op == 2) { cp = 'O'; ad = 'X'; } else { cp = 'X'; ad = 'O'; }

        if (mv_seq == 0 && mt[5] == '0') { t = 5; return t; }
        if (mv_seq == 1 && mt[5] == '0') { t = 5; return t; }

        //Try to Win Strategy
        t = tryToWinOrBlock(cp); if (t != 0) {return t; }

        // Block Adversary strategy
        t = tryToWinOrBlock(ad); if (t != 0) {return t; }

        // Any Diagonal
        if (mt[1] == '0') { t = 1; return t; }
        if (mt[3] == '0') { t = 3; return t; }
        if (mt[7] == '0') { t = 7; return t; }
        if (mt[9] == '0') { t = 9; return t; }

        // Any movement
        int i = 0;
        for (i = 1; i <= 9; i++)
        {
            if (mt[i] == '0') { t = i; return t; }
        }
        return 99;
    }


    int tryToWinOrBlock(char ck)
    {

        int t;
        t = checkABC(ck, 1, 2, 3); if (t != 0) return t; // 1st row
        t = checkABC(ck, 4, 5, 6); if (t != 0) return t; // 2nd row
        t = checkABC(ck, 7, 8, 9); if (t != 0) return t; // 3rd row

        t = checkABC(ck, 1, 4, 7); if (t != 0) return t; // 1st column
        t = checkABC(ck, 2, 5, 8); if (t != 0) return t; // 2nd column
        t = checkABC(ck, 3, 6, 9); if (t != 0) return t; // 3rd column

        t = checkABC(ck, 3, 5, 7); if (t != 0) return t; // 1st diagonal
        t = checkABC(ck, 1, 5, 9); if (t != 0) return t; // 2nd diagonal

        return 0;

    }

    int checkABC(char ck, int a, int b, int c)
    {
        if (mt[a] == mt[b] && mt[b] == ck && mt[c] == '0') { return c; }
        if (mt[a] == mt[c] && mt[c] == ck && mt[b] == '0') { return b; }
        if (mt[b] == mt[c] && mt[c] == ck && mt[a] == '0') { return a; }
        return 0;
    }
    
	
}// End of Class
