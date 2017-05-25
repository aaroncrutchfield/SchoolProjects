package chess_networked_generic_coordinates;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gameNet.GameCreator;
import gameNet.GameNet_CoreGame;
import gameNet.GameNet_UserInterface;



// ***************************************************
public class MyMainChess extends GameCreator{   
 
  public GameNet_CoreGame createGame()
  {
	  return new MyGame();
  }
  

  public static void main(String[] args) throws IOException 
  {   
  	MyMainChess myMain = new MyMainChess();
  	MyUserInterface myUserInterface = new MyUserInterface();
  	
  	JFrame frame = new JFrame("chess baby");
  	
  	frame.setLayout(new BorderLayout());  

	frame.setSize(600,400);     
	JPanel centerPanel = new JPanel();
	Font myFont = new Font("TimesRoman", Font.BOLD, 48);
	
	
	frame.add (myUserInterface, BorderLayout.CENTER);

	JPanel northPanel = new JPanel();
	northPanel.setLayout(new GridLayout(0,1));
	northPanel.add(myUserInterface.nameLabel);

	JPanel topPanel = new JPanel(); 	  
	topPanel.setLayout(new FlowLayout());
	
	topPanel.add(myUserInterface.messageLabel);
	northPanel.add(topPanel);

	frame.add(northPanel, BorderLayout.NORTH);
	frame.setVisible(true);
  	
    
  	myMain.enterGame((GameNet_UserInterface)myUserInterface); 
  }// end of main
}// end of class
