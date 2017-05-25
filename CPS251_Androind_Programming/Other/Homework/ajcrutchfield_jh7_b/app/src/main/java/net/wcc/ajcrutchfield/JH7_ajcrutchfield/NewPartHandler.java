package net.wcc.ajcrutchfield.JH7_ajcrutchfield;

/**
 * Created by AaronC on 10/15/2015.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPartHandler extends Dialog
        implements  /*OnKeyListener,*/ OnClickListener
{

    private EditText myEditText;
    private PartOperations partOperations;

    public NewPartHandler(Context context, PartOperations partOperations) {
        super(context);
        this.partOperations = partOperations;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Enter Part Number");
        setContentView(R.layout.new_part);

        myEditText = (EditText)findViewById(R.id.myNewPartEditText);
//        myEditText.setOnKeyListener(this);
        myEditText.setRawInputType(Configuration.KEYBOARD_QWERTY);

        Button a = (Button)findViewById(R.id.okButton);
        Button b = (Button)findViewById(R.id.cancel_new_part);
        Button c = (Button)findViewById(R.id.nextButton);

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);

        //this will keep the keyboard up after pressing enter
        myEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String partNumber = getPartNumber();
                    myEditText.setText("");
                    Log.d("Mine", partNumber);
                    return true;
                } else {
                    return false;
                }
            }

        });

    }


    //if the ok button is pressed, add the PartNumber in the text field
    //if next is pressed, add the PartNumber and prompt for another
    @Override
    public void onClick(View arg0) {
        Button b = (Button)arg0;
        String partNumber = "";

        if (b == (Button)findViewById(R.id.okButton)){
            partNumber = getPartNumber();
            Log.d("Mine", partNumber);
            dismiss();
        }
        if (b == (Button)findViewById(R.id.nextButton)){
            partNumber = getPartNumber();
            myEditText.setText("");
            Log.d("Mine", partNumber);
        }
        else
            dismiss();

    }

    private String getPartNumber() {
        String partNumber;
        partNumber = myEditText.getText().toString();

        //illegal characters
        Pattern pattern = Pattern.compile("[!@#$%,.&*()=:'?_+/]");
        Matcher matcher = pattern.matcher(partNumber);
        boolean illegal =  matcher.find();

        if (partNumber != null && !illegal  && partNumber.length() > 1)
            partOperations.newPart(partNumber);
        else
            Toast.makeText(getContext(), "Invalid input", Toast.LENGTH_SHORT).show();
        return partNumber;
    }


}

