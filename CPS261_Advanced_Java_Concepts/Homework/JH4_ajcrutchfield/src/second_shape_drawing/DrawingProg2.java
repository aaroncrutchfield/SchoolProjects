package second_shape_drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

//appropriate imports
public class DrawingProg2 extends JFrame implements ActionListener{

	DrawingPanel drawingPanel = new DrawingPanel();
	JCheckBox filled = new JCheckBox("filled");
	
	JRadioButton[] srb= new JRadioButton[4];
	JRadioButton[] crb= new JRadioButton[3];

	DrawingProg2()
	{
		super("My Drawing Program");

		String[] colors = {"red", "green", "blue"};
		String[] shapes1 = {"rectangle", "oval","line","scribble"};

		setSize(800, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		layout(shapes1, colors);
		setVisible(true);
	}

	private void layout(String[] shapes, String[] colors )
	{
		// set defaults
		drawingPanel.drawing.setColor(Color.red);
		drawingPanel.drawing.setDrawType(DrawType.rectangle);
		setLayout(new BorderLayout());
		
		//Drawing panel
		add(drawingPanel, BorderLayout.CENTER);
		
		//Shape selection Panel
		JPanel shapePanel = new JPanel();
		shapePanel.setLayout(new FlowLayout());
		
		ButtonGroup group1 = new ButtonGroup();
		filled = new JCheckBox("filled");
		filled.addActionListener(this);
		shapePanel.add(filled);
		
		String [] shapeButtons = {"rectangle", "oval", "line", "scribble"};
		for (int i = 0; i < shapeButtons.length; i++) {
			JRadioButton rb = new JRadioButton(shapeButtons[i], false);
			srb[i] = rb;
			group1.add(rb);
			shapePanel.add(rb);
			rb.addActionListener(this);
		}
		srb[0].setSelected(true);
		
		add(shapePanel, BorderLayout.NORTH);
		
		//Color selection panel
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new GridLayout(0, 1));
		ButtonGroup group2 = new ButtonGroup();
		
		String [] colorButtons = {"red", "green", "blue"};
		for (int i = 0; i < colorButtons.length; i++) {
			JRadioButton rb = new JRadioButton(colorButtons[i], false);
			crb[i] = rb;
			group2.add(rb);
			colorPanel.add(rb);
			rb.addActionListener(this);
		}
		crb[0].setSelected(true);
	

		add(colorPanel, BorderLayout.WEST);
		/*
      drawingPanel goes in the CENTER
      Create a JPanel with a FlowLayout for the NORTH.
      This JPanel will get the filled JCheckBox and all of the necessary radio buttons
Create a JPanel with a GridLayout(0,1) or GridLayout(3,1) for the WEST.
      This JPanel will get the radio buttons for the colors.
All RadioButtons and the CheckBox will have ActionListeners associated with them.
		 */
	}

	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String action = actionEvent.getActionCommand();
		System.out.println(action);
		
		if (filled.isSelected())
			drawingPanel.drawing.setFilled(true);
		else
			drawingPanel.drawing.setFilled(false);
		
		switch(action)
		{
		case "red":
			drawingPanel.drawing.setColor(Color.red);
			break;
		case "green":
			drawingPanel.drawing.setColor(Color.green);
			break;
		case "blue":
			drawingPanel.drawing.setColor(Color.blue);
			break;
		case "rectangle":
			drawingPanel.drawing.setDrawType(DrawType.rectangle);
			filled.setVisible(true);
			break;
		case "oval":
			drawingPanel.drawing.setDrawType(DrawType.oval);
			filled.setVisible(true);
			break;
		case "line":
			drawingPanel.drawing.setDrawType(DrawType.line);
			filled.setVisible(false);
			break;
		case "scribble":
			drawingPanel.drawing.setDrawType(DrawType.scribble);
			filled.setVisible(false);
			break;
		
			/*
Handle all of the other actions, and make sure you set the visibility of 
the filled CheckBox appropriately.
The other methods to be called are:
       drawingPanel.drawing.setDrawType(....);
       drawingPanel.drawing.setFilled(.....);
			 */

		}
	}
	public static void main(String[] args) {
		DrawingProg2 dp = new DrawingProg2();
		
	}
}
