package calculator;

public class Calculations {
	int currentValue=0;
    String lastOperator="";
    String firstOperandString ="0";

    // Called when any of the +, -, *, / buttons pressed
    public void update(String operator, String currentDisplay)
    {
        /*first string passed in will be the operator ie +, -
        * second string will be the first operand
        */
        lastOperator = operator;
        firstOperandString = currentDisplay;
    }

    // Called when the "=" button pressed ... answer is returned
    public String equalsCalculation(String currentDisplay)
    {
        //The string passed in will be the 2nd operand.
        //The two operands will be acted upon by the operator
        int firstOperand =0, secondOperand =0;
        try
        {
            firstOperand = Integer.parseInt(firstOperandString);
            secondOperand = Integer.parseInt(currentDisplay);
        }
        catch (NumberFormatException e)
        {
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
                System.out.println("Divide by zero error");
        }
        else
        {
        	System.out.println("Bad Operator: "+ lastOperator);
        }
        return "";
    }

    // Called when the clear button pressed

    public void clear() {
        currentValue = 0;
        lastOperator="";
    }
}