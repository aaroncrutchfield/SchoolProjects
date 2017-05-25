package Checkers;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import gameNet.GameNet_UserInterface;
import gameNet.GamePlayer;



public class MyUserInterface extends JFrame implements GameNet_UserInterface
{

	MyGame myGame=null;
	String myName="";
	
	JLabel messageLabel = new JLabel("");
	JLabel nameLabel = new JLabel("");

	String msg = "";
	String turnMsg = "";
	CheckersPanel checkersPanel = new CheckersPanel();

//	BoardDimensions boardDimensions = new BoardDimensions();

	MyUserInterface() {
		super("Chess");
		setSize(600, 600);

//		ChessPiece.readInImages();
//
//		MouseClickMonitor ml = new MouseClickMonitor();
//		addMouseListener(ml);
//		addMouseMotionListener(ml);

		addWindowListener(new Termination());

		screenLayout();

//		setVisible(true);
		//setResizable(false);
	}

	private void screenLayout() {
		CheckersPiece.readInImages();

		setLayout(new BorderLayout());
		add(checkersPanel, BorderLayout.CENTER);


		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(0,1));
		northPanel.add(nameLabel);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(messageLabel);
		northPanel.add(topPanel);

		add(northPanel, BorderLayout.NORTH);
	}

	public void startUserInterface (GamePlayer player)
	{
		checkersPanel.myGamePlayer = player;
		myName = player.getPlayerName();

		checkersPanel.myGameInput = new MyGameInput();
		checkersPanel.myGameInput.setName(myName);
		checkersPanel.myGamePlayer.sendMessage(checkersPanel.myGameInput);// Default is the "Connecting command"

		setVisible(true);
	}
	public void receivedMessage(Object ob)
	{
		MyGameOutput myGameOutput = (MyGameOutput)ob;
		checkersPanel.checkersGame = myGameOutput.myGame.checkersGame;
		myGame = myGameOutput.myGame;

		msg= myGame.getStatus(myName);
		turnMsg = myGame.getTurnInfo(myName);
		myGame.setName(myName);

		nameLabel.setText(myName);
		messageLabel.setText(turnMsg + " ------- " + msg);

		repaint();
	}

	//*******************************************
	// Another Inner class 
	//*******************************************
	class Termination extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			checkersPanel.myGamePlayer.doneWithGame();
			System.exit(0);
		}
	}



}

