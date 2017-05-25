package Checkers;

import gameNet.GamePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by AaronC on 12/10/2015.
 */
public class CheckersPanel extends JPanel{

    GamePlayer myGamePlayer;
    MyGameInput myGameInput;
    CheckersGame checkersGame =null;
    BoardDimensions boardDimensions = new BoardDimensions();

    Image offScreenImage=null;
    int lastWidth=-1, lastHeight=-1;

    CheckersPanel(){
        MouseClickMonitor ml = new MouseClickMonitor();
		addMouseListener(ml);
		addMouseMotionListener(ml);
    }

    public void paint(Graphics screen) {

        Dimension d = getSize();
        if (d.width != lastWidth || d.height != lastHeight)
        {
            Component image = null;
            lastWidth = d.width;
            lastHeight = d.height;
            offScreenImage = createImage(lastWidth, lastHeight);
        }
        Graphics g = offScreenImage.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0, d.width, d.height);

        Insets insets = getInsets();
        int top = insets.top;
        int left = insets.left;
        int square_width = (d.width - left - insets.right)/8;
        int square_height = (d.height - top - insets.bottom)/8;
        boardDimensions.setParms(left, top, square_width, square_height);

        // Color in the Board squares
        for (int i=0; i < 8; i++)
        {
            for (int j=0; j < 8; j++)
            {
                int x = left +  j*square_width;
                int y = top + i*square_height;
                if ((i+j)%2==1)
                {
                    g.setColor(Color.white);
                    g.fillRect(x, y, square_width, square_height);
                }
                else
                {
                    g.setColor(Color.gray);
                    g.fillRect(x, y, square_width, square_height);
                }
            }
        }// end of outer for loop

        // Draw pieces in their current location
        if (checkersGame != null)
        {
            checkersGame.drawInPosition(g, boardDimensions);
        }
        // To avoid flicker we copy the offScreenImage to the real screen
        screen.drawImage(offScreenImage, 0,0, this);
    }


    //*******************************************
    // Another Inner class to handle Mouse Events
    //*******************************************
    class MouseClickMonitor extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            System.out.println("****mousePressed****");

            DPoint dpoint =  boardDimensions.getDpoint(e.getX(), e.getY());
            if (dpoint != null)
            {
                myGameInput.setMouseCmd(MyGameInput.MOUSE_PRESSED, dpoint);
                myGamePlayer.sendMessage(myGameInput);
            }

            System.out.println("****mousePressed****");

        }

        public void mouseReleased(MouseEvent e)
        {
            System.out.println("****mouseReleased****");

            DPoint dpoint =  boardDimensions.getDpoint(e.getX(), e.getY());
            if (dpoint != null)
            {
                myGameInput.setMouseCmd(MyGameInput.MOUSE_RELEASED, dpoint);
                myGamePlayer.sendMessage(myGameInput);
            }

            System.out.println("****mouseReleased****");
        }

        public void mouseDragged(MouseEvent e)
        {


            DPoint dpoint =  boardDimensions.getDpoint(e.getX(), e.getY());
            System.out.println("mouseDragged "+ dpoint);
            if (dpoint != null)
            {
                myGameInput.setMouseCmd(MyGameInput.MOUSE_DRAGGED, dpoint);
                myGamePlayer.sendMessage(myGameInput);
            }


        }



    }
}
