package net.wcc.ajcrutchfield.dialogs;

/**
 * Created by AaronC on 10/19/2015.
 */
import android.app.Activity;
import android.os.Bundle;
public class MyCalledActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.called_activity);
    }
}
