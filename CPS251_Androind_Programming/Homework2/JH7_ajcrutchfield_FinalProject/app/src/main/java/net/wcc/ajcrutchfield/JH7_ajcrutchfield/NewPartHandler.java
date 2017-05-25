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
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

public class NewPartHandler extends Dialog
        implements  OnKeyListener, OnClickListener
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
        myEditText.setOnKeyListener(this);
        myEditText.setRawInputType(Configuration.KEYBOARD_QWERTY);

        Button a = (Button)findViewById(R.id.okButton);
        Button b = (Button)findViewById(R.id.cancel_new_part);
        Button c = (Button)findViewById(R.id.nextButton);

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);

    }

    //if the enter key is pressed, add the PartNumber in the text field
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
            if (keyCode == KeyEvent.KEYCODE_ENTER)
            {
                String partNumber = myEditText.getText().toString();
                partOperations.newPart(partNumber);
                myEditText.setText("");
                dismiss();
                return true;
            }
        return false;
    }

    //if the ok button is pressed, add the PartNumber in the text field
    //if next is pressed, add the PartNumber and prompt for another
    @Override
    public void onClick(View arg0) {
        Button b = (Button)arg0;
        String partNumber = "";

        if (b == (Button)findViewById(R.id.okButton)){
            partNumber = myEditText.getText().toString();
            partOperations.newPart(partNumber);
            Log.d("Mine", partNumber);
            dismiss();
        }
        if (b == (Button)findViewById(R.id.nextButton)){
            partNumber = myEditText.getText().toString();
            partOperations.newPart(partNumber);
            myEditText.setText("");
            Log.d("Mine", partNumber);
        }
        else
            dismiss();

    }


}

