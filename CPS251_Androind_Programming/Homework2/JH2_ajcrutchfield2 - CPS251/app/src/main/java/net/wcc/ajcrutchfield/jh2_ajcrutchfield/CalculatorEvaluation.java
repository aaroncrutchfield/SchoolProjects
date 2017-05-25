package net.wcc.ajcrutchfield.jh2_ajcrutchfield;

/**
 * Created by AaronC on 9/16/2015.
 */
import android.util.Log;
public class CalculatorEvaluation {


    int currentValue=0;
    String lastOperator="";
    String firstOperandString ="0";

    // Called when any of the +, -, *, / buttons pressed
    public void update(String operator, String currentDisplay)
    {
        /*first string passed in will be the operator ie +, -
        * second string will be the first operand
        */
        Log.d("Mine", "update: "+operator +" "+ currentDisplay);
        lastOperator = operator;
        firstOperandString = currentDisplay;
    }

    // Called when the "=" button pressed ... answer is returned
    public String equalsCalculation(String currentDisplay)
    {
        //The string passed in will be the 2nd operand.
        //The two operands will be acted upon by the operator
        int firstOperand =0, secondOperand =0;
        Log.d("Mine", "equalsCalculation: "+currentDisplay);
        try
        {
            firstOperand = Integer.parseInt(firstOperandString);
            secondOperand = Integer.parseInt(currentDisplay);
        }
        catch (NumberFormatException e)
        {
            Log.d("Mine", "Bad operands:"+firstOperandString +","+ currentDisplay);
            return "";
        }
        if ("+".equals(lastOperator))
        {
            return ""+(firstOperand + secondOperand);
        }
        else if ("-".equals(lastOperator))
        {
            return ""+(firstOperand - secondOperand);
        }
        else if ("*".equals(lastOperator))
        {
            return ""+(firstOperand * secondOperand);
        }
        else if ("/".equals(lastOperator))
        {
            if (secondOperand != 0)
                return ""+(firstOperand / secondOperand);
            else
                Log.d("Mine", "Divide by zero error");
        }
        else
        {
            Log.d("Mine", "Bad Operator: "+ lastOperator);
        }
        return "";
    }

    // Called when the clear button pressed

    public void clear() {
        currentValue = 0;
        lastOperator="";
    }
}