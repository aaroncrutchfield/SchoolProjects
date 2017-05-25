package net.wcc.ajcrutchfield.countactivity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by AaronC on 11/26/2015.
 */
public class CountHelper
        implements View.OnClickListener, View.OnLongClickListener, View.OnKeyListener, Serializable {


    private String partNumber;
    private int standardCount;
    private int toteDemand;

    private int totalDemand;
    private int currentDemand;

    private int fullToteCount = 0;



    private int partialTotal = 0;
    private StringBuilder partialString = new StringBuilder();

    private Context main;

    //views for CountActivity
    private TableRow row;
    private CheckBox partChecked;
    private Button countButton;
    private EditText partialEdit;
    private TextView demandText;
    TableLayout table;

    public int getTotalDemand() {
        return totalDemand;
    }

    public CountHelper(String part, int standardCount, int totalDemand,
                       Context main, TableLayout table){
        //store important variables
        partNumber = part;
        this.table = table;
        this.main = main;
        this.standardCount = standardCount;
        this.totalDemand = totalDemand;
    }

    public void initiate() {
        //initialize views
        row = new TableRow(this.main);
        partChecked = new CheckBox(this.main);
        partChecked.setChecked(false);
        countButton = new Button(this.main);
        partialEdit = new EditText(this.main);
        demandText = new TextView(this.main);

        //format views
        partChecked.setText(partNumber);
        countButton.setText(fullToteCount + "@" + this.standardCount);
        countButton.setOnClickListener(this);
        countButton.setOnLongClickListener(this);
        demandText.setText(String.valueOf(getToteDemand()) + " totes");
        partialEdit.setRawInputType(Configuration.KEYBOARD_QWERTY);
        partialEdit.setOnKeyListener(this);

        //add views to tableLayout
        row.addView(partChecked);
        row.addView(countButton);
        row.addView(partialEdit);
        row.addView(demandText);
        this.table.addView(row);
    }

    public void editFullTote(){
        updateCountButton();
        int toteDemand;
        toteDemand = getToteDemand();
        Log.d("mine", "fullToteCount=" + String.valueOf(fullToteCount));
        if (toteDemand <= 0) {
            partChecked.setChecked(true);
        }
        demandText.setText(toteDemand + " totes");
    }

    public void partialAppend(String string){
        partialString.append(string);
    }

    //returns total piece count of string passed into it
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

    //returns toteDemand after subtracting total parts pulled
    private int getToteDemand() {
        int totalDemand = this.totalDemand;
        totalDemand = totalDemand - (partialTotal + (fullToteCount*standardCount));
        currentDemand = totalDemand;

        if(currentDemand % standardCount == 0)
            toteDemand = currentDemand/standardCount;
        else
            toteDemand = (currentDemand/standardCount) + 1;

        Log.d("mine", "\tgetToteDemand()");

        Log.d("mine", "temp totalDemand="+String.valueOf(totalDemand));
        Log.d("mine", "currentDemand="+String.valueOf(currentDemand));
        Log.d("mine", "totalDemand="+String.valueOf(this.totalDemand));
        Log.d("mine", "currentToteDemand=" + String.valueOf(toteDemand));
        return toteDemand;
    }

    //when the countButton is clicked, update its count, toteDemand count
    //if toteDemand is satisfied, check checkBox
    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        int id = b.getId();
        Log.d("mine", "onClick="+String.valueOf(id));

        if (b.getId() == countButton.getId()) {
            fullToteCount += 1;
            updateCountButton();
            toteDemand -= 1;
            boolean set = false;
            CheckBox box = (CheckBox) row.findViewById(b.getId() - 100);
            if (toteDemand <= 0){
                box.setChecked(true);
                box.setTextColor(Color.LTGRAY);
                set = true;
            }
            if (toteDemand > 0){
                box.setTextColor(Color.BLACK);
                box.setChecked(false);
            }

            demandText.setText(toteDemand + " totes");
        }
    }

    //long press countButton to correct/update its count
    @Override
    public boolean onLongClick(View v) {
        Button b = (Button)v;
        int id = b.getId();
        if (b.getId() == countButton.getId()) {
            EditFullTote editFullTote = new EditFullTote(main, this);
            editFullTote.show();
            Log.d("mine", "editFullTote.count=" + String.valueOf(fullToteCount));

            CheckBox box = (CheckBox) row.findViewById(b.getId() - 100);
            if (toteDemand > 0){
                box.setTextColor(Color.BLACK);
                box.setChecked(false);
            }
            return true;
        }
        return false;
    }

    //add value from partial field to partialTotal when enter is pressed
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
            if (keyCode == KeyEvent.KEYCODE_ENTER) {

                String temp = partialEdit.getText().toString();
                if (temp.contains("@") || temp.contains("*") || temp.contains("+")){
                    partialTotal += getPieceCount(temp);
                    partialString.append(temp + ", ");
                }
                else{
                    partialTotal += Integer.parseInt(temp);
                    partialString.append("1@"+temp + ",  ");
                }
                toteDemand = getToteDemand();

//                partialEdit.setText(String.valueOf(partialTotal));
                partialEdit.setText("");
                demandText.setText(toteDemand + " totes");
                Log.d("mine", "Partials=" + partialString);
                Log.d("mine", "PartialTotal="+String.valueOf(partialTotal));
                Log.d("mine", "ToteDemand="+String.valueOf(toteDemand));
                Log.d("mine", "TotalDemand="+String.valueOf(totalDemand));
                return true;
            }
        return false;
    }

    public void setFullToteCount(int fullToteCount) {
        this.fullToteCount = fullToteCount;
    }

    private void updateCountButton() {
        countButton.setText(fullToteCount + "@" + standardCount);
    }

    public String getPartNumber() {
        return partNumber;
    }

    public StringBuilder getPartialString() {
        return partialString;
    }

    public int getStandardCount() {
        return standardCount;
    }

    public int getFullToteCount() {
        return fullToteCount;
    }

    public int getPartialTotal() {
        return partialTotal;
    }

    public void setPartialTotal(int partialTotal) {
        this.partialTotal = partialTotal;
    }

    public CheckBox getPartChecked() {
        return partChecked;
    }

    public Button getCountButton() {
        return countButton;
    }

    public EditText getPartialEdit() {
        return partialEdit;
    }

    public TextView getDemandText() {
        return demandText;
    }
}
