package net.wcc.ajcrutchfield.countactivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.wcc.ajcrutchfield.JH7_ajcrutchfield.R;

/**
 * Created by AaronC on 11/28/2015.
 */
public class EditFullTote extends Dialog
        implements View.OnClickListener, View.OnKeyListener{

    private EditText editText;
    private int count =0;
    private CountHelper countHelper;

    public EditFullTote(Context context, CountHelper countHelper) {
        super(context);
        this.countHelper = countHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Full Tote Count");
        setContentView(R.layout.edit_full_totes);

        editText = (EditText)findViewById(R.id.editFullTotes);
        editText.setOnKeyListener(this);

        Button a = (Button)findViewById(R.id.buttonOk);
        Button b = (Button)findViewById(R.id.buttonCancel);

        a.setOnClickListener(this);
        b.setOnClickListener(this);
    }

    //set count on countButton to new entered value
    //update button count and demandTotes count
    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        if (b.getId() == R.id.buttonOk){
            count = Integer.parseInt(editText.getText().toString());
            countHelper.setFullToteCount(count);
            Log.d("mine", "EditFullTote=" + editText.getText().toString());
            count = 0;
            countHelper.editFullTote();
            dismiss();
        }
        else{
            dismiss();
        }

    }

    //set count on countButton to new entered value
    //update button count and demandTotes count
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
            if (keyCode == KeyEvent.KEYCODE_ENTER)
            {

                count = Integer.parseInt(editText.getText().toString());
                countHelper.setFullToteCount(count);
                Log.d("mine", "EditFullTote=" + editText.getText().toString());
                count = 0;
                countHelper.editFullTote();
                dismiss();
                return true;
            }
        return false;
    }
}
