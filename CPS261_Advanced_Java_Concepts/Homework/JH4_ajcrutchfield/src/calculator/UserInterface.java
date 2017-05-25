package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserInterface extends JFrame implements ActionListener{
	public static final int WIDTH = 500;
	public static final int HEIGHT = 600;
	Font font = new Font("SERIF", Font.BOLD, 32);
	
	public StringBuilder calculation = new StringBuilder();
	JTextField output = new JTextField();
	
	Calculations calEval = new Calculations();
	

	
	public UserInterface() {
		super();
		setSize(WIDTH, HEIGHT);
		setTitle("Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		//TextField output
		output.setBackground(Color.YELLOW);
		output.setFont(font);
		output.setEditable(false);
		add(output, BorderLayout.NORTH);
		
		//GridLayout calculator buttons
		JPanel calcButtons = new JPanel();
		calcButtons.setBackground(Color.LIGHT_GRAY);
		calcButtons.setLayout(new GridLayout(4, 0, 4, 4));
		
		//create number buttons
		JButton num;
		for (int i = 0; i < 10; i++) {
			String str = Integer.toString(i);
			num = new JButton(str);
			num.setBackground(Color.CYAN);
			num.setFont(font);
			num.addActionListener(this);
			calcButtons.add(num);
		}

		//create other buttons
		ArrayList<JButton> otherButtons = new ArrayList<>(); 
		otherButtons.add(new JButton("+"));
		otherButtons.add(new JButton("-"));
		otherButtons.add(new JButton("*"));
		otherButtons.add(new JButton("/"));
		otherButtons.add(new JButton("="));
		otherButtons.add(new JButton("clear"));
		
		for (int i = 0; i < otherButtons.size(); i++) {
			JButton b = otherButtons.get(i);
			b.setBackground(Color.RED);
			b.setFont(font);
			calcButtons.add(b);
			b.addActionListener(this);
		}
		
		add(calcButtons, BorderLayout.CENTER);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonString = e.getActionCommand();
		
		switch (buttonString) {
		case "0":	case "1":	case "2":	case "3":	
		case "4":	case "5":	case "6":	case "7":	
		case "8":	case "9":
			calculation.append(buttonString);
			output.setText(calculation.toString());
			break;
		case "clear":
			int len = calculation.length();
			calculation.delete(0, len);
			calEval.clear();
			output.setText("");
			break;
		case "+":	case "-":	case "*":	case "/":
			
			String currentDisplay = calculation.toString();
			String operator = buttonString;
			output.setText(operator);
			
			calEval.update(operator, currentDisplay);
			
			len = calculation.length();
			calculation.delete(0, len);
			break;
		case "=":
			currentDisplay = calculation.toString();
            String answer = calEval.equalsCalculation(currentDisplay);

            output.setText(answer);

            len = calculation.length();
            calculation.delete(0, len);
            break;
		}
	}
	
	public static void main(String[] args) {
		UserInterface gui = new UserInterface();
		gui.setVisible(true);
	}
}
