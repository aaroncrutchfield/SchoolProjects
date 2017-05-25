package net.wcc.ajcrutchfield.mytouchscreendrawing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyTouchscreenView myTouchscreenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int [] resources = {R.id.red, R.id.green, R.id.blue, R.id.rectangle, R.id.oval, R.id.line};

        for (int i = 0; i < resources.length; i++) {
            RadioButton rb = (RadioButton)findViewById(resources[i]);
            rb.setOnClickListener(this);
        }

        Button b = (Button)findViewById(R.id.clear);
        b.setOnClickListener(this);
        b = (Button)findViewById(R.id.undo);
        b.setOnClickListener(this);

        myTouchscreenView = (MyTouchscreenView)findViewById(R.id.myTouch1);
        myTouchscreenView.setOnTouchListener(myTouchscreenView);

        myTouchscreenView.update(R.id.red);         //initialize red pen
        myTouchscreenView.update(R.id.rectangle);   //start out drawing rectangles

        RadioButton rb = (RadioButton)findViewById(R.id.red);
        rb.setChecked(true);    //make the red radio button checked
        rb = (RadioButton)findViewById(R.id.rectangle);
        rb.setChecked(true);    //make the rectangle radio button checked
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        myTouchscreenView.update(v.getId());
    }
}
