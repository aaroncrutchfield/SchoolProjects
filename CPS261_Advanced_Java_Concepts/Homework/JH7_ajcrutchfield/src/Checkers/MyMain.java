package Checkers;
import java.io.IOException;

import gameNet.GameCreator;
import gameNet.GameNet_CoreGame;
import gameNet.GameNet_UserInterface;



// ***************************************************
public class MyMain extends GameCreator{

	public GameNet_CoreGame createGame()
	{
		return new MyGame();
	}


	public static void main(String[] args) throws IOException
	{
		MyMain myMain = new MyMain();
		MyUserInterface myUserInterface = new MyUserInterface();

		myMain.enterGame((GameNet_UserInterface)myUserInterface);
	}// end of main
}// end of class
