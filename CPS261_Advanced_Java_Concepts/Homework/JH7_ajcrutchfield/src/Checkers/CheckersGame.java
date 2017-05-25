package Checkers;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public class CheckersGame implements Serializable{
	private static final long serialVersionUID = 1L;

	// GameControl Status values
	private static final int GAME_TIE = 2;
	private static final int GAME_IN_PROGRESS=-1;
	MyGame myGame;

	int redLoses = 0;
	int blackLoses = 0;

	// Note that gameWinner will take on the values of:
	// GAME_TIE
	// 0 if the first player is the Winner
	// 1 if the second player is the Winner
	private int gameWinner = GAME_IN_PROGRESS;

	private int nextTurn = 0;
	private int startingTurn = 1;

	// Used to find out if the game is complete
	public boolean gameInProgress()
	{
		if (gameWinner == GAME_IN_PROGRESS)
			return true;
		else
			return false;
	}

	public String getGameStatus(int yourIndex)
	{
		if (yourIndex == gameWinner)
			return "You Win !!!";
		if (gameWinner >= 0 && gameWinner <=1)
			return "You Lose !!!";
		if (gameWinner == GAME_TIE)
			return "Tie";
		return "Game in Progress";
	}

	void clearBoard()
	{
		gameWinner = GAME_IN_PROGRESS;
		// Alternate who starts if multiple games are played
		if (startingTurn != 0)
			startingTurn = 0;
		else
			startingTurn = 1;

		nextTurn = startingTurn;

	}

	public boolean isTurn(int clientIndex)
	{
		if (clientIndex == nextTurn)
			return true;
		else 
			return false;
	}




	private ArrayList<Piece> pieces = new ArrayList<>();

	private int selectedIndex = -1;

	private DPoint pressed_point=new DPoint();
	private DPoint dragged_point = new DPoint();


	public CheckersGame(MyGame myGame)
	{
		this.myGame = myGame;

//		for (int i=0; i < 8; i++){
//			for(int j=0; i< 8; i++){
//				pieces.get
//			}
//		}
		// Fill in all the pieces and their locations

		//black pieces
		for (int i=0; i < 8; i+=2) // black pieces in row 0
		{
			pieces.add(new Piece(PieceType.Checker, ColorType.black, i, 0));
		}
		for (int i=1; i < 8; i+=2) // black pieces in row 1
		{
			pieces.add(new Piece(PieceType.Checker, ColorType.black, i, 1));
		}
		for (int i=0; i < 8; i+=2) // black pieces in row 2
		{
			pieces.add(new Piece(PieceType.Checker, ColorType.black, i, 2));
		}

		//red pieces
		for (int i=1; i < 8; i+=2) // red pieces in row 5
		{
			pieces.add(new Piece(PieceType.Checker, ColorType.red, i, 5));
		}
		for (int i=0; i < 8; i+=2) // red pieces in row 6
		{
			pieces.add(new Piece(PieceType.Checker, ColorType.red, i, 6));
		}
		for (int i=1; i < 8; i+=2) // red pieces in row 7
		{
			pieces.add(new Piece(PieceType.Checker, ColorType.red, i, 7));
		}

	}

	// Draws all pieces into their current location.
	public void drawInPosition(Graphics g, BoardDimensions chessBoard)
	{
		for (int i=0; i < pieces.size(); i++)
		{
			if (i != selectedIndex)
				pieces.get(i).drawInPosition(g, chessBoard);
			else
			{
				// The selected piece is still being dragged around
				DPoint delta = pressed_point.deltaPoint(dragged_point);
				pieces.get(i).dragDraw(g, chessBoard, delta.x, delta.y);
			}
		}
	}

	int pressedX;
	int pressedY;

	int releasedX;
	int releasedY;

	void mousePressed(DPoint dpoint)
	{
		pressed_point = dpoint;
		pressedX = (int)dpoint.x;
		pressedY = (int)dpoint.y;

		dragged_point = dpoint;

		int xSelectLoc = (int)dpoint.x;
		int ySelectLoc = (int)dpoint.y;

		for (int i=0; i < pieces.size(); i++)
		{
			if (pieces.get(i).areYouHere(xSelectLoc, ySelectLoc))
			{
				selectedIndex = i;
				break;
			}
		}

	}
	void mouseDragged(DPoint dpoint)
	{
		dragged_point = dpoint;
	}
	void mouseReleased(DPoint dpoint)
	{
		releasedX = (int)dpoint.x;
		releasedY = (int)dpoint.y;

		ColorType color = null;
		ColorType otherColor = null;
		PieceType pieceType = null;
		boolean legal = true;
		if (selectedIndex > 0) {
			color = pieces.get(selectedIndex).color;
			pieceType = pieces.get(selectedIndex).pieceType;
		}

		for (int i=0; i < pieces.size(); i++) {
			if (pieces.get(i).areYouHere(releasedX, releasedY)) {
				otherColor = pieces.get(i).color;
			}
		}

		System.out.println("Color: "+color);
		System.out.println("OtherColor: "+otherColor);


		//can't move if it isn't your turn
		if (!isTurn(myGame.clientIndex))
			legal = false;

		//king piece movement logic
		if (pieceType == PieceType.King) {
			//red piece movement logic
			if (color == ColorType.red && nextTurn == 1)
				legal = false;

			if (color == ColorType.black && nextTurn == 0)
				legal = false;

			if ((releasedX - pressedX) >= 2 || (releasedX - pressedX) <= -2
					|| releasedX == pressedX)
				legal = false;
			if ((pressedY - releasedY) <= -2 || (pressedY - releasedY) >= 2
					|| pressedY == releasedY || color == otherColor)
				legal = false;

		}

		boolean king = false;
		//regular checker piece movement logic
		if (pieceType == PieceType.Checker) {
			//red piece movement logic
			if (color == ColorType.red){
				if ((releasedX - pressedX) >= 2 || (releasedX - pressedX) <= -2
						|| releasedX == pressedX)
					legal = false;
				if (releasedY > pressedY || (pressedY - releasedY) >= 2
						|| pressedY == releasedY || color == otherColor)
					legal = false;
				if (releasedY == 0)
					king = true;
				if(nextTurn == 1)
					legal = false;

			}
			//black piece movement logic
			if (color == ColorType.black) {
				if ((releasedX - pressedX) <= -2 || (releasedX - pressedX) >= 2
						|| releasedX == pressedX)
					legal = false;
				if (releasedY < pressedY || (pressedY - releasedY) <= -2
						|| pressedY == releasedY || color == otherColor)
					legal = false;
				if (releasedY == 7)
					king = true;
				if(nextTurn == 0)
					legal = false;

			}
		}
		if (legal)
			//jump piece logic
			for (int i=0; i < pieces.size(); i++)
			{
				if (pieces.get(i).areYouHere(releasedX, releasedY))
				{
					otherColor = pieces.get(i).color;
					//remove jumped piece from play
					if (color != otherColor && otherColor != null && color != null) {
						pieces.get(i).moveLoc(-1, -1);
						if (otherColor == ColorType.black){
							blackLoses += 1;
						}
						else{
							redLoses += 1;
						}
					}
					break;
				}
			}

		gameWinner = scoreGame();

		System.out.println(legal);
		System.out.println("relX="+releasedX + "pressX="+pressedX);
		System.out.println("relY="+releasedY + "pressY="+pressedY);

		pressed_point = dpoint;
		if (selectedIndex >= 0)
		{
			int xSelectLoc = (int)dpoint.x;
			int ySelectLoc = (int)dpoint.y;
			if (xSelectLoc >= 0 && ySelectLoc >= 0 && legal) //added legal to make sure move is legal
			{
				//change whose turn it is
				nextTurn = (nextTurn + 1) % 2;
				pieces.get(selectedIndex).moveLoc(xSelectLoc, ySelectLoc);
				try {
					//king logic
					if (king) {
						pieces.get(selectedIndex).moveLoc(-1, -1);
						Thread.sleep(50);
						pieces.add(new Piece(PieceType.King, pieces.get(selectedIndex).color, releasedX, releasedY));
					}
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}
			selectedIndex = -1;
		}
	}

	private int scoreGame() {
		if (redLoses == 12)
			return 1;
		if(blackLoses == 12)
			return 0;
		return GAME_IN_PROGRESS;
	}

}


