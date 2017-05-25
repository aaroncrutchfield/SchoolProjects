package chess_networked_generic_coordinates;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class ChessGame implements Serializable{
	private static final long serialVersionUID = 1L;

	// The TTT 3 x 3 will be filled with -1 (AVAILABLE) to start with
	// There after, entries will receive either a the player's index (0 or 1). 
	private static final int AVAILABLE = -1;

	// GameControl Status values
	private static final int GAME_TIE = 2;
	private static final int GAME_IN_PROGRESS=-1;

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

	public boolean isTurn(int clientIndex)
	{
		if (clientIndex == nextTurn)
			return true;
		else 
			return false;
	}

	//TODO may need to modify
	public boolean makeSelection(int clientIndex, int row, int col)
	{
		if (!isTurn(clientIndex))
		{
			System.out.println("Out of turn: " + clientIndex);
			return false;
		}

		// change the turn
		nextTurn = (nextTurn +1) % 2;

		return true;
	}


	private ArrayList<Piece> pieces = new ArrayList<>();

	private int selectedIndex = -1;

	private DPoint pressed_point=new DPoint();
	private DPoint dragged_point = new DPoint();


	public ChessGame()
	{
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

		System.out.println(color);

		//king piece movement logic
		if (pieceType == PieceType.King) {
			//red piece movement logic
			if (color == ColorType.red){
				if ((releasedX - pressedX) >= 2 || (releasedX - pressedX) <= -2 || releasedX == pressedX)
					legal = false;
				if ((pressedY - releasedY) <= -2 || (pressedY - releasedY) >= 2 || pressedY == releasedY || color == otherColor)
					legal = false;
			}

			//black piece movement logic
			else if (color == ColorType.black) {
				if ((releasedX - pressedX) <= -2 || (releasedX - pressedX) >= 2 || releasedX == pressedX)
					legal = false;
				if ((pressedY - releasedY) >= 2 || (pressedY - releasedY) <= -2 || pressedY == releasedY || color == otherColor)
					legal = false;
			}
		}
		boolean king = false;
		//regular checker piece movement logic
		if (pieceType == PieceType.Checker) {
			//red piece movement logic
			if (color == ColorType.red){
				if ((releasedX - pressedX) >= 2 || (releasedX - pressedX) <= -2 || releasedX == pressedX)
					legal = false;
				if (releasedY > pressedY || (pressedY - releasedY) >= 2 || pressedY == releasedY || color == otherColor)
					legal = false;
				if (releasedY == 0) {
					king = true;
				}
			}
			//black piece movement logic
			else if (color == ColorType.black) {
				if ((releasedX - pressedX) <= -2 || (releasedX - pressedX) >= 2 || releasedX == pressedX)
					legal = false;
				if (releasedY < pressedY || (pressedY - releasedY) <= -2 || pressedY == releasedY || color == otherColor)
					legal = false;
				if (releasedY == 7) {
					king = true;
				}
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
					if (color != otherColor && otherColor != null && color != null)
						pieces.get(i).moveLoc(-1,-1);
					break;
				}
			}

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
				pieces.get(selectedIndex).moveLoc(xSelectLoc, ySelectLoc);

				try {
					//king logic
					if (king) {
						pieces.get(selectedIndex).moveLoc(-1, -1);
						Thread.sleep(50);
						pieces.add(new Piece(PieceType.King, pieces.get(selectedIndex).color, releasedX, releasedY));
					}
				} 
				catch (InterruptedException e) {

					e.printStackTrace();
				}


			}
			selectedIndex = -1;
		}
	}


}


