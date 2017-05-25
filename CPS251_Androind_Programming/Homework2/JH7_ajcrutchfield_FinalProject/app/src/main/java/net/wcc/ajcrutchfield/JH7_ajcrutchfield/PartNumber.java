package net.wcc.ajcrutchfield.JH7_ajcrutchfield;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PartNumber {

    private String partNumber;
    private int toteDemand;

    private EditText demandEdit;
    private EditText countEdit;

    //maybe needs to be in onCreate
    public PartNumber(String part, Context main, TableLayout table){
        partNumber = part;

        TableRow row = new TableRow(main);
        TextView partText = new TextView(main);
        demandEdit = new EditText(main);
        countEdit = new EditText(main);

        //format textView
        partText.setText(part);
        partText.setTextColor(Color.BLACK);
        partText.setTextSize(0x00000001, 20);

        //format editText
        demandEdit.setRawInputType(Configuration.KEYBOARD_QWERTY);
        demandEdit.setMaxLines(1);

        //figure out how to tab down instead of right
//        demandEdit.focusSearch(demandEdit.FOCUS_DOWN);

        demandEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        countEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        countEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        //add everything to the row. add row to table
        row.addView(partText);
        row.addView(demandEdit);
        row.addView(countEdit);
        table.addView(row);
    }

    public int getDemand(){
        String demandStr = demandEdit.getText().toString();
        int demand = 0;
        if (demandStr.contains("@") || demandStr.contains("*") ||
                demandStr.contains("+")){
            demand = getPieceCount(demandStr);
        }
        else
            demand = Integer.parseInt(demandStr);
        return demand ;
    }

    public int getStandardCount(){
        String CountStr = countEdit.getText().toString();
        int standardCount = Integer.parseInt(CountStr);
        return standardCount;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public int getToteDemand (int demand, int standardCount) {
        if (demand % standardCount != 0)
            toteDemand = (demand / standardCount) + 1;
        else
            toteDemand = (demand / standardCount);
        return toteDemand;
    }

    private int getPieceCount(String count) {
        int atIndex=0;
        String firstOperandStr="", secondOperandStr="";
        int firstOperand=0, secondOperand=0, answer=0;

        if(count.contains("@"))
            atIndex = count.indexOf("@");
        if(count.contains("*"))
            atIndex = count.indexOf("*");
        if(count.contains("+"))
            atIndex = count.indexOf("+");

        firstOperandStr = count.substring(0, atIndex);
        secondOperandStr = count.substring(atIndex+1);

        firstOperand = Integer.parseInt(firstOperandStr);
        secondOperand = Integer.parseInt(secondOperandStr);

        if(count.contains("@"))
            answer = firstOperand * secondOperand;
        if(count.contains("*"))
            answer = firstOperand * secondOperand;
        if(count.contains("+"))
            answer = firstOperand + secondOperand;

        return answer;

    }

}
