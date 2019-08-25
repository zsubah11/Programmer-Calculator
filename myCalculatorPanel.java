import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;


public class myCalculatorPanel extends JPanel implements ActionListener {
	JButton [] jbt = new JButton[47]; //array of buttons
	JLabel title; //the title "Programmer"
	JTextField jtfDisplay; //this text field shows the numbers when they are being pressed
	JTextField answer; //this text field 
	
	
	static boolean hexFlag = false, decFlag = false, octFlag = false, binFlag = false; 
	static boolean div = false, mod = false, divZero = false, modZero = false; 
	
	int holdHex = 0, holdOct = 0, holdBin = 0; //these variables will hold onto the hex and convert them 
	
	String tempDisp = ""; //adds to the string which is sent for evaluation 
	String holdString = "";  //holds onto numbers and displays them to the screen 
	String display = ""; //manages the display for numbers 
	int state = 0; 
	
	
	public myCalculatorPanel()
	{
		//this is the display field that displays the result 
		jtfDisplay = new JTextField("0", 20); 
		jtfDisplay.setPreferredSize(new Dimension(200, 100)); //setting its dimensions 
		jtfDisplay.setBorder(BorderFactory.createEmptyBorder()); //making it have no border 
        jtfDisplay.setHorizontalAlignment(JTextField.RIGHT); //setting the alignment so it starts from the right 
        jtfDisplay.setEditable(false); //not making it editable 
		jtfDisplay.setFont(new Font("Arial", Font.BOLD, 40)); //change its font 
		
		
		//this is the smaller text field above the display that shows the operations as they are being inputted 
		answer = new JTextField("", 20); 
		answer.setPreferredSize(new Dimension(200, 40)); //setting dimension
		answer.setBorder(BorderFactory.createEmptyBorder()); //making it have no border 
		answer.setHorizontalAlignment(JTextField.RIGHT); //aligning it to the right 
		answer.setEditable(false); //not making it be edited 
		answer.setFont(new Font("Arial", Font.PLAIN, 25)); //changing its font
		
		
		//TITLE 
		title = new JLabel("Programmer"); //title of the calculator 
		title.setFont(new Font("Arial", Font.PLAIN, 30)); //set its font size, type and style 
				
		//TOP ROW OF BUTTONS
		jbt[0] = new JButton("\u2328"); //the keypad
		jbt[1] = new JButton("\u2059"); //the five dots
		jbt[2] = new JButton(""); //just an emoty button 
		jbt[3] = new JButton("QWORD"); //qword (the bit conversion button) 
		jbt[4] = new JButton("MS"); //MS
		jbt[5] = new JButton("M"); //M
		

		//SECOND ROW OF BUTTONS
		jbt[6] = new JButton("Lsh"); //Lsh
		jbt[7] = new JButton("Rsh"); //Rsh
		jbt[8] = new JButton("Or"); //Or
		jbt[9] = new JButton("Xor"); //Xor
		jbt[10] = new JButton("Not"); //Not
		jbt[11] = new JButton("And"); //And
		
		//THIRD ROW OF BUTTONS
		jbt[12] = new JButton("↑"); //arrow
		jbt[13] = new JButton("Mod"); //Mod
		jbt[14] = new JButton("CE"); //CE
		jbt[15] = new JButton("C"); //C
		jbt[16] = new JButton("⌫"); //backspace 
		jbt[17] = new JButton("÷"); //divide 
		
		//FOURTH ROW OF BUTTONS
		jbt[18] = new JButton("A"); //A
		jbt[19] = new JButton("B"); //B
		jbt[20] = new JButton("7"); //7
		jbt[21] = new JButton("8"); //8
		jbt[22] = new JButton("9"); //9
		jbt[23] = new JButton("x"); //multiply 
		
		//FIFTH ROW OF BUTTONS
		jbt[24] = new JButton("C"); //C
		jbt[25] = new JButton("D"); //D
		jbt[26] = new JButton("4"); //4
		jbt[27] = new JButton("5"); //5
		jbt[28] = new JButton("6"); //6
		jbt[29] = new JButton("-"); //subtract 
		
		
		//SIXTH ROW OF BUTTONS
		jbt[30] = new JButton("E"); //E
		jbt[31] = new JButton("F"); //F
		jbt[32] = new JButton("1"); //1
		jbt[33] = new JButton("2"); //2
		jbt[34] = new JButton("3"); //3
		jbt[35] = new JButton("+"); //Add
		
		
		//SEVENTH (LAST) ROW OF BUTTONS
		jbt[36] = new JButton("("); //Parentheses open
		jbt[37] = new JButton(")"); //Parentheses close 
		jbt[38] = new JButton("±"); //negate 
		jbt[39] = new JButton("0"); //0
		jbt[40] = new JButton("."); //dot
		jbt[41] = new JButton("="); //equals
		
		
		jbt[42] = new JButton("   HEX 0"); //hexadecimal representation 
		jbt[43] = new JButton("\u2590 DEC 0"); //decimal representation 
		jbt[44] = new JButton("   OCT 0"); //octal representation 
		jbt[45] = new JButton("   BIN 0"); //binary representation 
		
		
		jbt[46] = new JButton("\u2630"); //the three lines button  
		
		
		//fixes the outlook of the buttons as necessary 
		for(int i = 0; i < 47; i++)
		{
			//set the top row buttons' background to clear, remove their borders and their areas 
			//to make them look like the actual calculator 
			if (i >= 0 && i <= 5 || (i >= 42 && i <= 46))
			{
				jbt[i].setBorderPainted(false);
				jbt[i].setOpaque(false);
				jbt[i].setContentAreaFilled(false);	
			
			}
			
			//set all the other buttons to a gray background 
			else
			{
				jbt[i].setBackground(Color.WHITE);
			}
			
			
			//if the buttons are the ones with symbols don't do anything 
			if(i == 0 || i == 1 || i == 12 || 
			   i == 16 ||  i == 46)
				continue; 
			
			//for the rest of the buttons, change their font size, style and type 
			jbt[i].setFont(new Font("Arial", Font.BOLD, 20));
		}
		
	
		//the first sub panel consists of a title and the three lines menu button 
		//buttons that have to be disabled and are just for display
		//adds the three lines button and the title to the top left of the frame 
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		p1.add(jbt[46]); 
		p1.add(title);
				
		
		//the second sub panel consists of the two text fields and the conversion buttons
		//theu are laid out using a grid layout that allows us to have one component per row with no columns 
		JPanel p2 = new JPanel(new GridLayout(6, 0, 10, 10));
		jbt[42].setHorizontalAlignment(SwingConstants.LEFT);;
		jbt[43].setHorizontalAlignment(SwingConstants.LEFT);
		jbt[44].setHorizontalAlignment(SwingConstants.LEFT);
		jbt[45].setHorizontalAlignment(SwingConstants.LEFT);
		
		jbt[42].setFont(new Font("Arial", Font.BOLD, 20));
		jbt[43].setFont(new Font("Arial", Font.BOLD, 20));
		jbt[44].setFont(new Font("Arial", Font.BOLD, 20));
		jbt[45].setFont(new Font("Arial", Font.BOLD, 20));
		
		//adding them to the subpanel 
		p2.add(answer); 
		p2.add(jtfDisplay);
		p2.add(jbt[42]);
		p2.add(jbt[43]);
		p2.add(jbt[44]);
		p2.add(jbt[45]);
				
				
		//the third sub panel consists of all the other buttons 
		//adds all the rest of the buttons 
		JPanel p3 = new JPanel(new GridLayout(0, 6, 5, 5));
		p3.setPreferredSize(new Dimension(600, 350));
		for (int k = 0; k < 42; k++)
		{
			p3.add(jbt[k]); 
		}
		
				
		
		//the M(Memory) and decimal point (.) button are always disabled 
		jbt[5].setEnabled(false); 
		jbt[40].setEnabled(false);
		
		//this is because the mode is decimal by default and so these buttons stay disabled until the mode is changed
		jbt[18].setEnabled(false); //A
		jbt[19].setEnabled(false); //B
		jbt[24].setEnabled(false); //C
		jbt[25].setEnabled(false); //D
		jbt[30].setEnabled(false); //E
		jbt[31].setEnabled(false); //F
		
			
		//the main panel layout is a border layout 
		//adds the sub panels to the main panel using Border Layout 
		this.setLayout(new BorderLayout(10, 40));
		this.add(p1, BorderLayout.NORTH); //add the title and three lines to the north top
		this.add(p2, BorderLayout.CENTER); //add the text fields and conversion buttons to the center 
		this.add(p3, BorderLayout.SOUTH); //add all the other buttons to the south or bottom 
				
		
		//adds action listener for all the buttons 
		//"this" refers to this class 
		for (int l = 0; l < 47; l++)
		{
			jbt[l].addActionListener(this); 
		}
	}

	//this function performs all the necessary actions needed for the calculations to function 
	public void actionPerformed(ActionEvent e) 
	{
		//processing all the numeric and alphabetic characters
		for(int i = 18; i < 40; i++)
		{
			//when A is pressed 
			if(e.getSource() == jbt[i] && i == 18)
			{
				display = jtfDisplay.getText(); //get the text 
				
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("A"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "A");
					
				}
				
				tempDisp += "A"; 
				answer.setText(tempDisp);
			
				//convert A to decimal, oct and binary to display on the conversion buttons 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
			
				
			}
			
			//when B is pressed
			if(e.getSource() == jbt[i] && i == 19)
			{
				display = jtfDisplay.getText(); //get the text 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("B"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "B");
					
				}
				tempDisp += "B"; 
				answer.setText(tempDisp);
			
				//convert B to dec, oct and binary to display on the conversion buttons 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
			
	
			}
			
			//when C is pressed 
			if(e.getSource() == jbt[i] && i == 24)
			{
				display = jtfDisplay.getText(); //get the text 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("C"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "C");
					
				}
				tempDisp += "C"; 
				answer.setText(tempDisp);
				
				
				//convert C to dec, oct and binary to display on the conversion buttons 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
			
			}
			
			//when D is pressed 
			if(e.getSource() == jbt[i] && i == 25)
			{
				display = jtfDisplay.getText(); //get the text 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("D"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "D");
					
				}
				tempDisp += "D"; 
				answer.setText(tempDisp);
			
				
				//convert D to dec, oct and binary to display on the conversion buttons 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
			}
			
			//when E is pressed 
			if(e.getSource() == jbt[i] && i == 30)
			{
				display = jtfDisplay.getText(); //get the text 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("E"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "E");
					
				}
				tempDisp += "E"; 
				answer.setText(tempDisp);
				
				//convert E to dec, oct and binary to display on the conversion buttons 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
			
			}
			
			//when F is pressed 
			if(e.getSource() == jbt[i] && i == 31)
			{
				display = jtfDisplay.getText(); //get the text 
				
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("F"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "F");
					
				}
				tempDisp += "F"; 
				answer.setText(tempDisp);
			
				//convert F to dec, oct and binary to display on the conversion buttons 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
		
			}
			
			//if the 0 button is pressed 
			if(e.getSource() == jbt[i] && i == 39)
			{
				display = jtfDisplay.getText(); //get the text 
				
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("0"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "0");
					
				}
				tempDisp += "0"; 
				answer.setText(tempDisp);
				
				//if the calc is in decimal mode, we display the functions as is and convert them 
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX " + Integer.toHexString(Integer.parseInt(jtfDisplay.getText())));
					jbt[43].setText("\u2590 DEC " + Integer.parseInt(jtfDisplay.getText(), 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(Integer.parseInt(jtfDisplay.getText())));
					jbt[45].setText("   BIN " + Integer.toBinaryString(Integer.parseInt(jtfDisplay.getText())));
				}
				
				
				//if hex pressed, first convert hex number to decimal then display 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				//if oct pressed, convert the oct number to decimal then display 
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 8); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				
				//if binary mode, convert the binary number to decimal then display 
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 2); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				}
			
			}
			
			
			//when 1 is pressed 
			if(e.getSource() == jbt[i] && i == 32)
			{
				
				display = jtfDisplay.getText(); 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("1"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "1");
					
				}
				tempDisp += "1"; 
				answer.setText(tempDisp);
				
				
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX " + Integer.toHexString(Integer.parseInt(jtfDisplay.getText())));
					jbt[43].setText("\u2590 DEC " + Integer.parseInt(jtfDisplay.getText(), 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(Integer.parseInt(jtfDisplay.getText())));
					jbt[45].setText("   BIN " + Integer.toBinaryString(Integer.parseInt(jtfDisplay.getText())));
				}
				
				//if hex pressed, first convert hex number to decimal then display 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				//if oct pressed, convert the oct number to decimal then display 
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 8); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				
				//if binary mode, convert the binary number to decimal then display 
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 2); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				}
		
			}
			
			//when 2 is pressed 
			if(e.getSource() == jbt[i] && i == 33)
			{
				display = jtfDisplay.getText(); 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("2"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "2");
					
				}
				tempDisp += "2"; 
				answer.setText(tempDisp);
				
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX " + Integer.toHexString(Integer.parseInt(jtfDisplay.getText())));
					jbt[43].setText("\u2590 DEC " + Integer.parseInt(jtfDisplay.getText(), 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(Integer.parseInt(jtfDisplay.getText())));
					jbt[45].setText("   BIN " + Integer.toBinaryString(Integer.parseInt(jtfDisplay.getText())));
				}
				
				//if hex pressed, first convert hex number to decimal then display 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				//if oct pressed, convert the oct number to decimal then display 
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 8); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				
				//if binary mode, convert the binary number to decimal then display 
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 2); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				}
		
			}
			
			//when 3 is pressed 
			if(e.getSource() == jbt[i] && i == 34)
			{
				display = jtfDisplay.getText(); 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("3"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "3");
					
				}
				tempDisp += "3"; 
				answer.setText(tempDisp);
				
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX " + Integer.toHexString(Integer.parseInt(jtfDisplay.getText())));
					jbt[43].setText("\u2590 DEC " + Integer.parseInt(jtfDisplay.getText(), 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(Integer.parseInt(jtfDisplay.getText())));
					jbt[45].setText("   BIN " + Integer.toBinaryString(Integer.parseInt(jtfDisplay.getText())));
				}
				
				//if hex pressed, first convert hex number to decimal then display 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				//if oct pressed, convert the oct number to decimal then display 
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 8); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				
				//if binary mode, convert the binary number to decimal then display 
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 2); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				}
			
			}
			
			//when 4 is pressed 
			if(e.getSource() == jbt[i] && i == 26)
			{
				display = jtfDisplay.getText(); 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("4"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "4");
					
				}
				tempDisp += "4"; 
				answer.setText(tempDisp);
				
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX " + Integer.toHexString(Integer.parseInt(jtfDisplay.getText())));
					jbt[43].setText("\u2590 DEC " + Integer.parseInt(jtfDisplay.getText(), 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(Integer.parseInt(jtfDisplay.getText())));
					jbt[45].setText("   BIN " + Integer.toBinaryString(Integer.parseInt(jtfDisplay.getText())));
				}
				
				//if hex pressed, first convert hex number to decimal then display 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				//if oct pressed, convert the oct number to decimal then display 
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 8); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				
				//if binary mode, convert the binary number to decimal then display 
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 2); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				}
			}
			
			//when 5 is pressed 
			if(e.getSource() == jbt[i] && i == 27)
			{
				display = jtfDisplay.getText(); 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("5"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "5");
					
				}
				tempDisp += "5"; 
				answer.setText(tempDisp);
				
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX " + Integer.toHexString(Integer.parseInt(jtfDisplay.getText())));
					jbt[43].setText("\u2590 DEC " + Integer.parseInt(jtfDisplay.getText(), 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(Integer.parseInt(jtfDisplay.getText())));
					jbt[45].setText("   BIN " + Integer.toBinaryString(Integer.parseInt(jtfDisplay.getText())));
				}
				
				//if hex pressed, first convert hex number to decimal then display 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				//if oct pressed, convert the oct number to decimal then display 
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 8); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				
				//if binary mode, convert the binary number to decimal then display 
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 2); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				}
			}
			
			
			//when 6 is pressed 
			if(e.getSource() == jbt[i] && i == 28)
			{
				display = jtfDisplay.getText(); 
				
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("6"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "6");
					
				}
				tempDisp += "6"; 
				answer.setText(tempDisp);
				
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX " + Integer.toHexString(Integer.parseInt(jtfDisplay.getText())));
					jbt[43].setText("\u2590 DEC " + Integer.parseInt(jtfDisplay.getText(), 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(Integer.parseInt(jtfDisplay.getText())));
					jbt[45].setText("   BIN " + Integer.toBinaryString(Integer.parseInt(jtfDisplay.getText())));
				}
				
				//if hex pressed, first convert hex number to decimal then display 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				//if oct pressed, convert the oct number to decimal then display 
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 8); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				
				//if binary mode, convert the binary number to decimal then display 
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 2); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				}
		
			}
			
			
			//when 7 is pressed 
			if(e.getSource() == jbt[i] && i == 20)
			{
				display = jtfDisplay.getText(); 
				
				if(display.equals("0") && jtfDisplay.getText().length() == 1)
				{
					jtfDisplay.setText("7"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "7");
					
				}
	
				tempDisp += "7"; 
				
				
				answer.setText(tempDisp);
				
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX " + Integer.toHexString(Integer.parseInt(jtfDisplay.getText())));
					jbt[43].setText("\u2590 DEC " + Integer.parseInt(jtfDisplay.getText(), 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(Integer.parseInt(jtfDisplay.getText())));
					jbt[45].setText("   BIN " + Integer.toBinaryString(Integer.parseInt(jtfDisplay.getText())));
				}
				
				//if hex pressed, first convert hex number to decimal then display 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				//if oct pressed, convert the oct number to decimal then display 
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 8); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				
				//if binary mode, convert the binary number to decimal then display 
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 2); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				}

			}
			
			
			//when 8 is pressed 
			if(e.getSource() == jbt[i] && i == 21)
			{
				display = jtfDisplay.getText(); 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("8"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "8");
					
				}
				tempDisp += "8"; 
				answer.setText(tempDisp);
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX " + Integer.toHexString(Integer.parseInt(jtfDisplay.getText())));
					jbt[43].setText("\u2590 DEC " + Integer.parseInt(jtfDisplay.getText(), 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(Integer.parseInt(jtfDisplay.getText())));
					jbt[45].setText("   BIN " + Integer.toBinaryString(Integer.parseInt(jtfDisplay.getText())));
				}
				
				//if hex pressed, first convert hex number to decimal then display 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				//if oct pressed, convert the oct number to decimal then display 
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 8); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				
				//if binary mode, convert the binary number to decimal then display 
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 2); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				}
			}
			
			//when 9 is pressed 
			if(e.getSource() == jbt[i] && i == 22)
			{
				display = jtfDisplay.getText(); 
				if(display.equals("0") && display.length() == 1)
				{
					jtfDisplay.setText("9"); 
				}
				
				else
				{
					jtfDisplay.setText(display + "9");
					
				}
				tempDisp += "9"; 
				answer.setText(tempDisp);
				
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX " + Integer.toHexString(Integer.parseInt(jtfDisplay.getText())));
					jbt[43].setText("\u2590 DEC " + Integer.parseInt(jtfDisplay.getText(), 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(Integer.parseInt(jtfDisplay.getText())));
					jbt[45].setText("   BIN " + Integer.toBinaryString(Integer.parseInt(jtfDisplay.getText())));
				}
				
				//if hex pressed, first convert hex number to decimal then display 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 16); 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				//if oct pressed, convert the oct number to decimal then display 
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 8); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				
				//if binary mode, convert the binary number to decimal then display 
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(jtfDisplay.getText(), 2); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + Integer.parseInt(dec + "", 10));
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				}
		
			}
			
		}
		
		
		//if the equals button is pressed 
		if(e.getSource() == jbt[41])	
		{
			String ans = ""; 
			int result = 0; 
			
			if(jbt[43].getText().matches("\u2590 DEC.*"))
			{
				ans = evaluateExpression(tempDisp) + ""; //this will always be in decimal form 
				jtfDisplay.setText(ans); 
				tempDisp = ans; 
				answer.setText("");
				result = Integer.parseInt(tempDisp); 
				jbt[42].setText("   HEX " + Integer.toHexString(result));
				jbt[43].setText("\u2590 DEC " + ans);
				jbt[44].setText("   OCT " + Integer.toOctalString(result));
				jbt[45].setText("   BIN " + Integer.toBinaryString(result));
			}
			
			if(jbt[42].getText().matches("\u2590 HEX.*"))
			{
				
				ans = evaluateExpression(tempDisp) + ""; //decimal answer 
				System.out.println(ans); 
				
				int dec = Integer.parseInt(ans); 
				ans = Integer.toHexString(dec); //changing it to hexadecimal 
			
				jtfDisplay.setText(ans); 
				tempDisp = ans; 
				answer.setText("");
				
				result = Integer.parseInt(jtfDisplay.getText(), 16); //changing hex to decimal 
			
				jbt[42].setText("\u2590 HEX " + Integer.toHexString(result));
				jbt[43].setText("   DEC " + result);
				jbt[44].setText("   OCT " + Integer.toOctalString(result));
				jbt[45].setText("   BIN " + Integer.toBinaryString(result));
			}
			
			
			if(jbt[44].getText().matches("\u2590 OCT.*"))
			{
				
				ans = evaluateExpression(tempDisp) + ""; //decimal answer 
				
				int dec = Integer.parseInt(ans); 
				ans = Integer.toOctalString(dec); //changing it to octal
			
				jtfDisplay.setText(ans); 
				tempDisp = ans; 
				answer.setText("");
				
				result = Integer.parseInt(jtfDisplay.getText(), 8); //changing octal to decimal 
				
				jbt[42].setText("   HEX " + Integer.toHexString(result));
				jbt[43].setText("   DEC " + result);
				jbt[44].setText("\u2590 OCT " + Integer.toOctalString(result));
				jbt[45].setText("   BIN " + Integer.toBinaryString(result));
			}
			

			if(jbt[45].getText().matches("\u2590 BIN.*"))
			{
				
				ans = evaluateExpression(tempDisp) + ""; //decimal answer 
				
				int dec = Integer.parseInt(ans); //changing it to decimal 
				ans = Integer.toBinaryString(dec); //changing it to octal
			
				jtfDisplay.setText(ans); 
				tempDisp = ans; 
				answer.setText("");
				
				result = Integer.parseInt(jtfDisplay.getText(), 2); //changing binary to decimal 


				jbt[42].setText("   HEX " + Integer.toHexString(result));
				jbt[43].setText("   DEC " + result);
				jbt[44].setText("   OCT " + Integer.toOctalString(result));
				jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(result));
			}			
		}
			
		
		
		//if the divide button is pressed 
		if(e.getSource() == jbt[17])	
		{
			
			jtfDisplay.setText("");
			tempDisp += " / ";
			answer.setText(tempDisp);
			
		}
			
			
		//if the multiplication button is pressed
		if(e.getSource() == jbt[23])	
		{
			jtfDisplay.setText("");
			tempDisp += " * ";
			answer.setText(tempDisp);
			
		}
			
		//if the subtraction button is pressed
		if(e.getSource() == jbt[29])	
		{
			jtfDisplay.setText("");
			tempDisp += " - ";
			answer.setText(tempDisp);
			
		}
			
		//if the addition button is pressed
		if(e.getSource() == jbt[35])	
		{
			jtfDisplay.setText("");
			tempDisp += " + ";
			answer.setText(tempDisp);
			
		}
		
		//if the modulus button is pressed 
		if(e.getSource() == jbt[13])	
		{
			jtfDisplay.setText("");
			tempDisp += " % ";
			answer.setText(tempDisp);
			
		}
		
		//open parentheses 
		if(e.getSource() == jbt[36])
		{
			jtfDisplay.setText("");
			tempDisp += " ( ";
			answer.setText(tempDisp);
			
		}
		
		//close parentheses 
		if(e.getSource() == jbt[37])
		{
			jtfDisplay.setText("");
			tempDisp += " ) ";
			answer.setText(tempDisp);
			
					
		}
		
		
		//the negation button 
		if(e.getSource() == jbt[38])
		{
			//get the number from the display
			int temp = Integer.parseInt(jtfDisplay.getText()); 
			//multiply the number by -1
			//if the number is positive, it becomes negative
			//if the number is negative, it becomes positive 
			temp *= -1; 
			

			jtfDisplay.setText(temp + ""); 
		}
		
		
			
		//if the HEX button is pressed
		if(e.getSource() == jbt[42])
		{
			
			hexFlag = true;
			decFlag = false;
			octFlag = false;
			binFlag = false; 
			//change the text of the Hex button so that it displays a block beside it
			//keep the rest same 
			
			if (jtfDisplay.getText().length() == 1 && jtfDisplay.getText().equals("0"))
			{
				jbt[42].setText("\u2590 HEX 0");
				jbt[43].setText("   DEC 0");
				jbt[44].setText("   OCT 0");
				jbt[45].setText("   BIN 0");
				
				//enable all the buttons 
				//except the dot and M button 
				for(int i = 0; i < 42; i++)
				{
					jbt[i].setEnabled(true);
					
					if(i == 5 || i == 40)
					{
						jbt[i].setEnabled(false); 
					}
				}
			}
			
			
			else 
			{
				
				int dec = Integer.parseInt(jtfDisplay.getText(), 10); //decimal answer 
				
				
				jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec));
				jbt[43].setText("   DEC " + dec);
				jbt[44].setText("   OCT " + Integer.toOctalString(dec));
				jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				
				jtfDisplay.setText(Integer.toHexString(dec)); 
				
				//enable all the buttons 
				//except the dot and M button 
				for(int i = 0; i < 42; i++)
				{
					jbt[i].setEnabled(true);
					
					if(i == 5 || i == 40)
					{
						jbt[i].setEnabled(false); 
					}
				}
			}
			
			
		}
		
		
		
		//if the button DEC is pressed, we switch to decimal mode 
		if (e.getSource() == jbt[43])
		{
			hexFlag = false;
			decFlag = true;
			octFlag = false;
			binFlag = false; 
			
			//change the text of the DEC button to display a block beside it 
			//keep the rest of the text same 
			if (jtfDisplay.getText().length() == 1 && jtfDisplay.getText().equals("0"))
			{
				jbt[42].setText("   HEX 0");
				jbt[43].setText("\u2590 DEC 0");
				jbt[44].setText("   OCT 0");
				jbt[45].setText("   BIN 0");
				
				//disable the A, B, C, D, E and F buttons
				//and dot and M buttons 
				//make them not available for use to public 
				//make rest available 
				for (int i = 0; i < 42; i++) 
				{
					jbt[i].setEnabled(true);
					
					if (i == 18 || i == 19 || i == 24 || 
						i == 25 || i == 30 || i == 31 || 
						i == 5  || i == 40)
					{
						jbt[i].setEnabled(false);
					}
				}
				
				
			}
			
			
			else 
			{
				
				int dec = Integer.parseInt(jtfDisplay.getText(), 10); //decimal answer 
				
				
				jbt[42].setText("   HEX " + Integer.toHexString(dec));
				jbt[43].setText("\u2590 DEC " + dec);
				jbt[44].setText("   OCT " + Integer.toOctalString(dec));
				jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				
				jtfDisplay.setText(dec + ""); 
				
				//enable all the buttons 
				//except the dot and M button 
				for (int i = 0; i < 42; i++) 
				{
					jbt[i].setEnabled(true);
					
					if (i == 18 || i == 19 || i == 24 || 
						i == 25 || i == 30 || i == 31 || 
						i == 5  || i == 40)
					{
						jbt[i].setEnabled(false);
					}
				}
			}
			
		}
		

		
		//if the button OCT is pressed, we switch to octal mode 
		if(e.getSource() == jbt[44])
		{
			hexFlag = false;
			decFlag = false;
			octFlag = true;
			binFlag = false; 
			//change the text of the OCT button to display a block beside it
			//keep the rest of the buttons the same 
			
			if (jtfDisplay.getText().length() == 1 && jtfDisplay.getText().equals("0"))
			{
				jbt[42].setText("   HEX 0");
				jbt[43].setText("   DEC 0");
				jbt[44].setText("\u2590 OCT 0");
				jbt[45].setText("   BIN 0");
				
				//disable the A, B, C, D, E, F, 8 and 9 buttons
				//and dot and M buttons 
				//make them not available for use to public 
				//rest are available 
				for (int i = 0; i < 42; i++)
				{
					jbt[i].setEnabled(true);
					
					if (i == 18 || i == 19 || i == 24 || i == 25 || 
						i == 30 || i == 31 || i == 21 || i == 22 || 
						i == 5  || i == 40)
					{
						jbt[i].setEnabled(false); 
					}
					
				}
			}
			
			else 
			{
				int dec = Integer.parseInt(jtfDisplay.getText(), 10); //decimal answer 
				
				
				jbt[42].setText("   HEX " + Integer.toHexString(dec));
				jbt[43].setText("   DEC " + dec);
				jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
				jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				
				jtfDisplay.setText(Integer.toOctalString(dec)); 
				
				//enable all the buttons 
				//except the dot and M button 
				for (int i = 0; i < 42; i++)
				{
					jbt[i].setEnabled(true);
					
					if (i == 18 || i == 19 || i == 24 || i == 25 || 
						i == 30 || i == 31 || i == 21 || i == 22 || 
						i == 5  || i == 40)
					{
						jbt[i].setEnabled(false); 
					}
					
				}
				
			}

			
			
		}
		
		
		//if the button BIN is pressed
		//we switch to binary mode 
		if(e.getSource() == jbt[45])
		{
			hexFlag = false;
			decFlag = false;
			octFlag = false;
			binFlag = true; 
			
			//change the text of the BIN button to display a block beside it 
			//keep the rest of the buttons the same 
			if (jtfDisplay.getText().length() == 1 && jtfDisplay.getText().equals("0"))
			{
				jbt[42].setText("   HEX 0");
				jbt[43].setText("   DEC 0");
				jbt[44].setText("   OCT 0");
				jbt[45].setText("\u2590 BIN 0");
				
				//disable the A, B, C, D, E, F and 2 through 9 buttons
				//and dot and M buttons
				//make them not available for use to public 
				//rest are available for use 
				for (int i = 0; i < 42; i++)
				{
					jbt[i].setEnabled(true);
					
					if (i == 18 || i == 19 || i == 24 || i == 25 || 
						i == 30 || i == 31 || i == 21 || i == 22 ||
						i == 33 || i == 34 || i == 26 || i == 27 ||
						i == 28 || i == 20 || i == 5  || i == 40)
					{
						jbt[i].setEnabled(false); 
					}
					
				}
			}
			
			else 
			{
				int dec = Integer.parseInt(jtfDisplay.getText(), 10); //decimal answer 
				
				
				jbt[42].setText("   HEX " + Integer.toHexString(dec));
				jbt[43].setText("   DEC " + dec);
				jbt[44].setText("   OCT " + Integer.toOctalString(dec));
				jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));
				
				jtfDisplay.setText(Integer.toBinaryString(dec)); 
				
				//enable all the buttons 
				//except the dot and M button 
				for (int i = 0; i < 42; i++)
				{
					jbt[i].setEnabled(true);
					
					if (i == 18 || i == 19 || i == 24 || i == 25 || 
						i == 30 || i == 31 || i == 21 || i == 22 ||
						i == 33 || i == 34 || i == 26 || i == 27 ||
						i == 28 || i == 20 || i == 5  || i == 40)
					{
						jbt[i].setEnabled(false); 
					}
					
				}
			}
			
			
		}
		
	
		//if the arrow button is pressed
		//we switch the Lsh button to Rol
		//and Rsh button to RoR
		if(e.getSource() == jbt[12])
		{
			jbt[6].setText("RoL");
			jbt[7].setText("RoR");
			
			//this variable will keep track of the state of the button 
			//so that when its clicked again, it can change back and forth 
			//here state is one 
			state++; 
			
			//if clicked again, state is 2
			//which is visible by 2
			//so we can change to the other text
			if(state % 2 == 0)
			{
				jbt[6].setText("Lsh");
				jbt[7].setText("Rsh");
				
			}
		} 
		
		
		//if the C (Clear All) button is pressed
		if(e.getSource() == jbt[15])
		{
			//clear all the displays 
			jtfDisplay.setText("0");
			answer.setText("");
			tempDisp = ""; 
			
			//set the hex, dec, oct and bin buttons to zero 
			if(jbt[42].getText().matches("\u2590 HEX.*"))
			{
				jbt[42].setText("\u2590 HEX 0");
				jbt[43].setText("   DEC 0");
				jbt[44].setText("   OCT 0");
				jbt[45].setText("   BIN 0");
			}
			
			if(jbt[43].getText().matches("\u2590 DEC.*"))
			{
				jbt[42].setText("   HEX 0");
				jbt[43].setText("\u2590 DEC 0");
				jbt[44].setText("   OCT 0");
				jbt[45].setText("    BIN 0");
			}
			
			if(jbt[44].getText().matches("\u2590 OCT.*"))
			{
				jbt[42].setText("   HEX 0");
				jbt[43].setText("   DEC 0");
				jbt[44].setText("\u2590 OCT 0");
				jbt[45].setText("    BIN 0");
			}
			
			
			if(jbt[45].getText().matches("\u2590 BIN.*"))
			{
				jbt[42].setText("   HEX 0");
				jbt[43].setText("   DEC 0");
				jbt[44].setText("   OCT 0");
				jbt[45].setText("\u2590  BIN 0");
			}
		}
				
				
		//if the CE (Clear Entry) button is pressed 
		if(e.getSource() == jbt[14])
		{
			tempDisp = "";	
			jtfDisplay.setText("0");
		}
				
		//if the backspace button is pressed 
		if(e.getSource() == jbt[16])
		{
			//if the length of the display string is less than or equal to one
			if(jtfDisplay.getText().length() <= 1)
			{
				//we set the display to 0 
				//which means we remove everything on the screen 
				tempDisp = ""; 
				jtfDisplay.setText("0");
				answer.setText("");
				
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					jbt[42].setText("\u2590 HEX 0");
					jbt[43].setText("   DEC 0");
					jbt[44].setText("   OCT 0");
					jbt[45].setText("   BIN 0");
				}
				
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					jbt[42].setText("   HEX 0");
					jbt[43].setText("\u2590 DEC 0");
					jbt[44].setText("   OCT 0");
					jbt[45].setText("   BIN 0");
				}
				
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					jbt[42].setText("   HEX 0");
					jbt[43].setText("   DEC 0");
					jbt[44].setText("\u2590 OCT 0");
					jbt[45].setText("   BIN 0");
				}
				
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					jbt[42].setText("   HEX 0");
					jbt[43].setText("   DEC 0");
					jbt[44].setText("   OCT 0");
					jbt[45].setText("\u2590 BIN 0");
				}
				
			
			}
			
			else 
			{
				//however, of the length is greater than 1
				//we remove the last character from the screen
				//do so by taking the substring of the displayed string from index 0 till the second last character 
				tempDisp = answer.getText().substring(0, answer.getText().length() - 1);
				jtfDisplay.setText(tempDisp);
				
				answer.setText(tempDisp);
				
				
				//change the display of the buttons as the numbers are being reduced/backspaced 
				if(jbt[42].getText().matches("\u2590 HEX.*"))
				{
					//convert the hex to a decimal
					int dec = Integer.parseInt(tempDisp, 16); 
					 
					jbt[42].setText("\u2590 HEX " + Integer.toHexString(dec)); //convert it to hex again
					jbt[43].setText("   DEC " + dec); //show its decimal base
					jbt[44].setText("   OCT " + Integer.toOctalString(dec)); //convert it to octal
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec)); //convert it to binary
				}
				
				
				
				if(jbt[43].getText().matches("\u2590 DEC.*"))
				{
					int dec = Integer.parseInt(tempDisp, 10); 
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("\u2590 DEC " + dec);
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				if(jbt[44].getText().matches("\u2590 OCT.*"))
				{
					int dec = Integer.parseInt(tempDisp, 8); 
					
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + dec);
					jbt[44].setText("\u2590 OCT " + Integer.toOctalString(dec));
					jbt[45].setText("   BIN " + Integer.toBinaryString(dec));
				}
				
				if(jbt[45].getText().matches("\u2590 BIN.*"))
				{
					int dec = Integer.parseInt(tempDisp, 2); 
					
					jbt[42].setText("   HEX " + Integer.toHexString(dec));
					jbt[43].setText("   DEC " + dec);
					jbt[44].setText("   OCT " + Integer.toOctalString(dec));
					jbt[45].setText("\u2590 BIN " + Integer.toBinaryString(dec));	
				}
			}
		}
	}
	
	
	//The following functions have been used from the Chapter 24 of the book
	//and have been modified to fit the needs of this project
	//which states how to evaluate expressions using a Stack
	 /** Evaluate an expression */
	  public static int evaluateExpression(String expression) 
	  {
		  //Create operandStack to store operands
		  Stack<Integer> operandStack = new Stack<>();
	 
		  // Create operatorStack to store operators
		  Stack<Character> operatorStack = new Stack<>();
	 
		  // Insert blanks around (, ), +, -, /, and *
		  expression = insertBlanks(expression);
	 
		  // Extract operands and operators
		  String[] tokens = expression.split(" ");
	 
		  // Phase 1: Scan tokens
		  for (String token: tokens) 
		  {
			  if (token.length() == 0) // Blank spac
				  continue; // Back to the while loop to extract the next token
			  
			  else if (token.charAt(0) == '+' || token.charAt(0) == '-') 
			  {
				  // Process all +, -, *, / in the top of the operator stack
				  while (!operatorStack.isEmpty() &&
						  (operatorStack.peek() == '+' ||
						  operatorStack.peek() == '-' ||
						  operatorStack.peek() == '*' ||
						  operatorStack.peek() == '/' ||
						  operatorStack.peek() == '%')) 
				  {
					  processAnOperator(operandStack, operatorStack);
				  }
	 
				  // Push the + or - operator into the operator stack
				  operatorStack.push(token.charAt(0));
			  }
			  
			  else if (token.charAt(0) == '*' || token.charAt(0) == '/' || token.charAt(0) == '%') 
			  {
				  //Process all *, /, % in the top of the operator stack
				  while (!operatorStack.isEmpty() && (operatorStack.peek() == '*' || operatorStack.peek() == '/')) 
				  {
					  processAnOperator(operandStack, operatorStack);
				  }
	 


				  // Push the * or / operator into the operator stack
				  operatorStack.push(token.charAt(0));
			  }
			  
			  else if (token.trim().charAt(0) == '(') 
			  {
				  operatorStack.push('('); // Push '(' to stack
			  }
			  
			  
			  else if (token.trim().charAt(0) == ')') 
			  {
				  //Process all the operators in the stack until seeing '('
				  while (operatorStack.peek() != '(') 
				  {
					  processAnOperator(operandStack, operatorStack);
				  }
				  
				  operatorStack.pop(); // Pop the '(' symbol from the stack
			  }
			  
			  
			  else
			  { 
				  // An operand scanned
				  //Push an operand to the stack
			 
				 
					  //if the calculator is in binary mode 
					  if(binFlag)
					  {
						  operandStack.push(Integer.parseInt(token, 2));  
					  }
					  
					  
					  //if the calculator is in hex mode 
					  else if(hexFlag)
					  {
						  operandStack.push(Integer.parseInt(token, 16));
					  }
					  
					  
					  //if the calculator is in octal mode 
					  else if(octFlag)
					  {
						  operandStack.push(Integer.parseInt(token, 8));
						  
						  
					  }
					  
					  //otherwise the calculator is in decimal mode 
					  else if(decFlag)
					  {	
						  operandStack.push(new Integer(token)); 
					  }
				  
			  }
			
		  }
		  
		  
		  // Phase 2: Process all the remaining operators in the stack
		  while (!operatorStack.isEmpty()) {
			  processAnOperator(operandStack, operatorStack);
		  }
		  
		  
		  //Return the result
		  return operandStack.pop();
	  }
	 
	  /** Process one operator: Take an operator from operatorStack and
	  * apply it on the operands in the operandStack */
	  public static void processAnOperator(Stack<Integer> operandStack, Stack<Character> operatorStack) 
	  {
		  char op = operatorStack.pop(); //get the operator
		  int op1 = operandStack.pop(); //get the first operand
		  int op2 = operandStack.pop(); //get the second operand 
		  
		  //addition 
		  if (op == '+')
		  {
			  operandStack.push(op2 + op1); 
		  }
		
		  //subtraction 
		  else if (op == '-')
			  operandStack.push(op2 - op1);
		  
		  //multiplication 
		  else if (op == '*')
			  operandStack.push(op2 * op1);
		  
		  
		  //division 
		  else if (op == '/')
		  {
			  if(op1 != 0)
			  {
				  operandStack.push(op2 / op1); 
			  }
			  
			  else
			  {
				  operandStack.push(-111); 
				  div = true; 
			  }
		  }
		  
		  
		  //modulus 
		  else if (op == '%')
		  {
			  if(op1 != 0)
			  {
				  operandStack.push(op2 % op1); 
			  }
			  
			  else
			  {
				  operandStack.push(-222); 
				  mod = true; 
			  }; 
		  }
	 }
	  
	  
	  //this function makes sure to insert blanks if needed in the expression 
	  public static String insertBlanks(String s) 
	  {
		  String result = "";
		  
		  for (int i = 0; i < s.length(); i++) {
			  if (s.charAt(i) == '(' || s.charAt(i) == ')' || s.charAt(i) == '+' || s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/' || s.charAt(i) == '%')
				  
				  result += " " + s.charAt(i) + " ";
			  
			  else
				  result += s.charAt(i);
		  }
		  return result;
	  }
}