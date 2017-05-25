package net.wcc.ajcrutchfield.contextmenu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    TextView state, capital;
    static final int STATE_ID = 0;
    static final int CAPITAL_ID = 100;
    String[] stateNames = {"Michigan", "Ohio", "California", "Wisconsin"};
    String[] capitalNames = {"Lansing", "Columbus", "Sacramento", "Madison"};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        state = (TextView) findViewById(R.id.state);
        capital = (TextView) findViewById(R.id.capital);
        state.setOnClickListener(this);
        capital.setOnClickListener(this);


        registerForContextMenu(state);
        registerForContextMenu(capital);


    }

    // Nothing new in the OnClick routine except to point out that
// anything derived from View class (Even TextView) can work 
// with the OnClickListener Interface.
    @Override
    public void onClick(View v) {
        Toast toast = null;
        switch (v.getId()) {
            case R.id.state:
                toast = Toast.makeText(this /*Needs a Context*/, state.getText(), Toast.LENGTH_SHORT);
                break;
            case R.id.capital:
                toast = Toast.makeText(this /*Needs a Context*/, capital.getText(), Toast.LENGTH_SHORT);
                break;
            default:
                toast = Toast.makeText(this /*Needs a Context*/, "??????", Toast.LENGTH_SHORT);
                break;
        }
        toast.show();
    }

    // Note that onlike onCreateOptionsMenu this call is made for every long press of the associated View.
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Toast.makeText(this, "onCreateContextMenu", Toast.LENGTH_SHORT).show();
        MenuInflater inflater;
        switch (v.getId()) {
            case R.id.state:
                inflater = getMenuInflater();
                inflater.inflate(R.menu.state_menu, menu);
                break;
            case R.id.capital:
                inflater = getMenuInflater();
                inflater.inflate(R.menu.capital_menu, menu);
                break;
            default:
                Log.d("Mine", "???? onCreateContextMenu"); // Shouldn't get here break;
        }
        Log.d("Mine", "onCreateContextMenu");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        Toast.makeText(this, "onContextItemSelected", Toast.LENGTH_SHORT).show();
        int index = item.getItemId();
        String text = item.getTitle().toString();
        switch (item.getItemId()) {
            case R.id.michigan:
            case R.id.california:
            case R.id.ohio:
            case R.id.wisconsin:
                state.setText(text);
                break;
            default:
                capital.setText(text);
                break;
        }
        return true;
    }

    
}



