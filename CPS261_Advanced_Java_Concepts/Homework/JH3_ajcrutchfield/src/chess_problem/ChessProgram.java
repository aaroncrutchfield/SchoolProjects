package chess_problem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JFrame;

public class ChessProgram extends JFrame{

    ChessProgram(){
        super("Chess Program");
        setSize(600, 600);
        ChessPiece.readInImages();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint(Graphics g)
    {      
        super.paint(g);
        Insets insets = getInsets();
        int top = insets.top;
        int bottom = insets.bottom;
        int left = insets.left;
        int right = insets.right;

        int height = getHeight();
        int width = getWidth();

        int cell_h = (height-top-bottom)/8;
        int cell_w = (width-left-right)/8;

        BoardDimensions boardDimensions = new BoardDimensions(left, top, cell_w, cell_h);
        
        int x,y;

        for (int row=0; row < 8; row++)
        {
            y = top + row*cell_h;
            for (int col=0; col < 8; col++)
            {
                x = left + col*cell_w;
                boolean greenColor = (row+col) %2 == 1;
                if (greenColor)
                {
                    g.setColor(Color.green);
                }
                else
                {
                    g.setColor(Color.white);
                }
                g.fillRect(x, y, cell_w, cell_h);
            }
        }
        
        //Helper array
        PieceType[] kingRow = {PieceType.Rook, PieceType.Knight, PieceType.Bishop, PieceType.Queen,
        		  PieceType.King, PieceType.Bishop, PieceType.Knight, PieceType.Rook};
    
        for (int i = 0; i < kingRow.length; i++) {
        	Piece bkr = new Piece(kingRow[i], ColorType.black, i, 0);
            bkr.drawInPosition(g, boardDimensions);
		}
        
        for (int i = 0; i < kingRow.length; i++) {
        	Piece wkr = new Piece(kingRow[i], ColorType.white, i, 7);
            wkr.drawInPosition(g, boardDimensions);
		}
    
        for(int col=0; col < 8; col++) {
            Piece bp = new Piece(PieceType.Pawn, ColorType.black, col, 1);
            bp.drawInPosition(g, boardDimensions);
            
            Piece wp = new Piece(PieceType.Pawn, ColorType.white, col, 6);
            wp.drawInPosition(g, boardDimensions);
            
            
        }
    }

    public static void main(String[] args) {
        ChessProgram cp = new ChessProgram();

    }
}
