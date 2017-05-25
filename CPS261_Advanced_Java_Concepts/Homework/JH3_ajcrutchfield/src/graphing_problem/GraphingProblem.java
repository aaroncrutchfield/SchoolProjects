package graphing_problem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFrame;

class GBar{
	String text;
	int value;
	GBar(String t, int v){
		text = t;
		value = v;
	}
}

public class GraphingProblem extends JFrame{
	
	StringTokenizer token;
	String appName;
	
	ArrayList<GBar> gbarArr = new ArrayList<>();
	
	GraphingProblem(ArrayList<GBar> garr){
		gbarArr = garr;
		setSize(600, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//Find the maximum width of strings in pixels
	int getMaxTextWidth(ArrayList<GBar> garr, FontMetrics fm) {
		int maxValue = 0;
		for (int i = 0; i < garr.size(); i++) {
			int width = fm.stringWidth(garr.get(i).text);
			if (width > maxValue) {
				maxValue = width;
			}
		}
		return maxValue;
	}
	
	//Find maximum value(weight) in the ArrayList
	int getMaxBarWidth(ArrayList<GBar>garr) {
		int maxValue = 0;
		for (int i = 0; i < garr.size(); i++) {
			int value = garr.get(i).value;
			if (value > maxValue) {
				maxValue = value;
			}
		}
		return maxValue;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Dimension dimen = getSize();
		Insets insets = getInsets();
		
		int myBorder = 10;	 //TODO			
		
		int top = insets.top + myBorder;
		int left = insets.left + myBorder;
		int right = insets.right + myBorder;
		g.setColor(Color.red);
		
		for (int i = 0; i < myBorder; i++) {
			g.drawRect(insets.left + i, insets.top + i, 
					dimen.width - insets.left - insets.right - ((i*2)+1), dimen.height - insets.top - insets.bottom - ((i*2)+1));
		}
		
		Font font = g.getFont();
		FontMetrics fm = getFontMetrics(font);
		int fontHeight = fm.getHeight();
		int maxAscent = fm.getMaxAscent();
		int strMaxWidth = left + getMaxTextWidth(gbarArr, fm);
		int x_bar_start = strMaxWidth +1;
		
		int barMaxValue = getMaxBarWidth(gbarArr);
		double scale = (dimen.width - x_bar_start - right) / (double) barMaxValue;
		
		int y_start = top;
		
		int bar_height = fontHeight;
		
		for (int i = 0; i < gbarArr.size(); i++) {
			String text = gbarArr.get(i).text;
			int strWidth = fm.stringWidth(text);
			int value = gbarArr.get(i).value;
			int scaledValue = (int)(value * scale);
			g.setColor(Color.black);
			g.drawString(text, strMaxWidth - strWidth, y_start + maxAscent);
			g.setColor(Color.green);
			g.fillRect(x_bar_start, y_start, scaledValue - 5, bar_height);
			
			y_start += fontHeight + 10;
		}
		g.setColor(Color.black);
		g.drawLine(strMaxWidth, top, strMaxWidth, dimen.height - (2*myBorder));
	}
	
	private String getNextToken()
	{
		if (token.hasMoreTokens())
			return token.nextToken();
		else
			return null;
	}
	
	private String getNextToken(String symbol)
	{
		if (token.hasMoreTokens())
			return token.nextToken(symbol);
		else
			return null;
	}
	
	public boolean processToken(String line) {
		boolean proceed = true;
    	if(line != null) {
    		token = new StringTokenizer(line);
    		proceed = true;
    	}
    	else
    		proceed = false;
    	return proceed;
	}
	
	void processFile(String filename) {
		String playerName, num;
		int playerNum; 
		
		try {
			FileReader input = new FileReader(filename);
			Scanner scanner = new Scanner(input);
			
			super.setTitle(scanner.nextLine());
			
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				token = new StringTokenizer(line);
				playerName = getNextToken(";");
				num = getNextToken();
				num = num.trim();
				playerNum = Integer.parseInt(num);
				
				gbarArr.add(new GBar(playerName, playerNum));
				
			}
			scanner.close();
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file input");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ArrayList<GBar> garr = new ArrayList<GBar>();

		GraphingProblem gb= new GraphingProblem(garr);
		gb.processFile("graphing.txt");

	}

}
