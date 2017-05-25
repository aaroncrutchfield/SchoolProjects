package net.wcc.ajcrutchfield.myenhancedtextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText myEditText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.normalTextView);
        //uncomment the following to turn on the Enhanced TextView
        textView = (MyDrawTextView) findViewById(R.id.myDrawTextView);

        myEditText1 = (EditText) findViewById(R.id.myEditText1);
        MyEnterKeyStuff myEnterKeyStuff = new MyEnterKeyStuff();
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

    // *******************  Inner Class *************************
    class MyEnterKeyStuff implements OnKeyListener {

        EditText myEditText2;
        MyEnterKeyStuff() {
            myEditText2 = (EditText) findViewById(R.id.myEditText2);
            //Why the MyEnterKeyStuff.this
            myEditText2.setOnKeyListener(MyEnterKeyStuff.this);
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String str;
                    str = myEditText1.getText().toString();
                    if (str.length() > 0)
                        textView.setText(str);
                    else
                        textView.append("\n" + str);

                    myEditText2.setText("");
                    myEditText1.setText("");
                    return true;
                }
            }
            return false;
        }
    }
    // ******************* End of Inner Class *******************
}