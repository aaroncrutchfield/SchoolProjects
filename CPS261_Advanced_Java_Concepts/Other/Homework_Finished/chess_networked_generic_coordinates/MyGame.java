package chess_networked_generic_coordinates;


import java.io.Serializable;
import java.util.ArrayList;

import gameNet.GameNet_CoreGame;


public class MyGame extends GameNet_CoreGame implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<String> currPlayers = new ArrayList<>();
	
	ChessGame chessGame= new ChessGame();


	public Object process(Object ob)
	{
		MyGameInput myGameInput= (MyGameInput)ob;
		
		int clientIndex = getClientIndex(myGameInput.sendersName);
		
		if (clientIndex < 0) {
			System.out.println("Already have 2 players");
			return null;
		}
		switch(myGameInput.cmd)
		{
		case MyGameInput.MOUSE_PRESSED:
			chessGame.mousePressed(myGameInput.dpoint);
			break;
		case MyGameInput.MOUSE_RELEASED:
			chessGame.mouseReleased(myGameInput.dpoint);
			break;
		case MyGameInput.MOUSE_DRAGGED:
			chessGame.mouseDragged(myGameInput.dpoint);
			break;
		case MyGameInput.CONNECTING:
			break;
		}

		return new MyGameOutput(this);
	}
	
	// Returns True if the Game is still going
		public boolean gameInProgress()
		{
			return chessGame.gameInProgress();
		}
		
		// Returns whether you won, lost, or the Game is in progress
		public String getStatus(String myName)
		{
			int index = getYourIndex(myName);
			return chessGame.getGameStatus(index);
		}
		
		
		// Returns Information about whose turn it is
		public String getTurnInfo(String myName)
		{
			if (!gameInProgress())
				return " Game Over ";
			
			int index = getYourIndex(myName);
			if (chessGame.isTurn(index))
				return "Your Turn";
			String otherClient = otherPlayerName(myName);
			return otherClient+"'s Turn";
		}

		// If you have already connected, then this will return your index (0 or 1). 
		// If you are new and we currently have less than 2 players then you are added
		// to the game and your index is returned (0 or 1)
		// If we already have 2 players, then this will return -1
		
		private int getClientIndex(String name)
		{
			// The following will return -1 if the name can't be found
			int retval = currPlayers.indexOf(name);
			
			if (retval < 0 && currPlayers.size() < 2)
			{
				retval = currPlayers.size();
				currPlayers.add(name);
				if (currPlayers.size() == 2)
				{
					// Game ready to go.
				}
			}
			return retval;
		}
		
		// If you are already in the game, your index will be returned (0 or 1)
		// Otherwise -1 is returned ... you are never added with this routine.
		private int getYourIndex(String name)
		{
			return currPlayers.indexOf(name);
		}
		
		// This returns the other Player's name if he exists.  A null is returned if he doesn't exist.
		private String otherPlayerName(String yourName)
		{
			if (currPlayers.size() < 2)
				return null;
			if (yourName.equals(currPlayers.get(0)))
				return currPlayers.get(1);
			else
				return currPlayers.get(0);
		}



}


