package net.wcc.ajcrutchfield.jh2_ajcrutchfield;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends Activity implements OnClickListener {

    CalculatorEvaluation calEval = new CalculatorEvaluation();

    //StringBuilder is for our TextView
    public StringBuilder calculation = new StringBuilder();
    public TextView display = null;

    //store button resources in a 3D array
    int[][] buttonRes = {
            {R.id.b7, R.id.b8, R.id.b9, R.id.div},
            {R.id.b4, R.id.b5, R.id.b6, R.id.mult},
            {R.id.b1, R.id.b2, R.id.b3, R.id.minus},
            {R.id.b0, R.id.backSpace, R.id.clear, R.id.plus}
    };

    //create array of buttons for calculator buttons
    Button[][] buttons = new Button[4][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set on click listener for all buttons
        for (int row=0; row <= 3; row++){
            for (int col=0; col <= 3; col++){
                Button b = (Button) findViewById(buttonRes[row][col]);
                b.setOnClickListener(this);
                buttons[row][col] = b;
            }
        }
        //set on click listener for equals button
        Button equalsButton = (Button) findViewById(R.id.equals);
        equalsButton.setOnClickListener(this);

        display = (TextView) findViewById(R.id.display);
    }


    @Override
    public void onClick(View v) {
        //store id number into index variable
        int index = v.getId();



        //if digit is pressed
        if (index == R.id.b7 || index == R.id.b8 || index == R.id.b9 ||
                index == R.id.b4 || index == R.id.b5 || index == R.id.b6 ||
                index == R.id.b1 || index == R.id.b2 || index == R.id.b3 ||
                index == R.id.b0) {
            Button d = (Button) v;
            String digit = d.getText().toString();
            calculation.append(digit);
            display.setText(calculation);
        }

        //if clear button is pressed
        if(index == R.id.clear){
            int len = calculation.length();
            calculation.delete(0, len);
            calEval.clear();
            display.setText("");
        }

        //if backspace is pressed
        if(index == R.id.backSpace){
            int len = calculation.length();

            if(len >= 1) {
                calculation.deleteCharAt(len - 1);
                display.setText(calculation);
            }
        }

        //if operator is pressed
        if (index == R.id.div || index == R.id.mult ||
                index == R.id.minus || index == R.id.plus){
            Button o = (Button) v;
            String operator = o.getText().toString();

            String currentDisplay = calculation.toString();
            calEval.update(operator, currentDisplay);

            int len = calculation.length();
            calculation.delete(0, len);

            display.setText(operator);
        }

        //if equal is pressed
        if(index == R.id.equals){
            String currentDisplay = calculation.toString();
            String answer = calEval.equalsCalculation(currentDisplay);

            display.setText(answer);

            int len = calculation.length();
            calculation.delete(0, len);
        }
    }
}
