package net.wcc.ajcrutchfield.jh1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
        implements OnClickListener {
    /** Called when the activity is first created. */
    TextView display=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set up click listeners for all the buttons

        int[] button_resources={R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4,
                R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9, R.id.clear};

        for(int i=0; i < button_resources.length; i++)
        {
            Button b = (Button)findViewById(button_resources[i]);
            b.setOnClickListener(this);
        }
        display = (TextView) findViewById(R.id.display);
    }

    // ...
    public void onClick(View v) {
        Button b = (Button)v;
        String label = b.getText().toString();

        switch (v.getId()) {
            case R.id.clear:
                display.setText("");
                break;
            default:
                display.append(label);
                break;
        }
    }
}
