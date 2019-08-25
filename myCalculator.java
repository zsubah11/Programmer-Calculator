import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class myCalculator extends JFrame 
{
	//default constructor 
	public myCalculator() 
	{
		setTitle("Calculator"); //title of the window
		setSize(770, 810); //set the dimensions
		setResizable(false); //do not allow to resize the window 
		setLocationRelativeTo(null); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //handles the exiting from the window 
		JPanel mainPanel = new myCalculatorPanel(); //declare an instance of the panel 
	
		this.add(mainPanel); //add this master panel to the JFrame 
	}
}
