package net.wcc.ajcrutchfield.jh3_ajcrutchfield;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


import org.w3c.dom.Text;

import static net.wcc.ajcrutchfield.jh3_ajcrutchfield.HangmanLogic.*;

public class MainActivity extends AppCompatActivity implements OnClickListener, HangmanUpdate {

    public TextView display = null;
    HangmanLogic hangmanLogic = new HangmanLogic(this);
    MyDrawHangmanView myDrawHangmanView;
    String[] words = {"orange", "apple", "peach", "strawberry", "pear"};
    int[] alphabet = new int[26];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] alphabet = {R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g,
                R.id.h, R.id.i, R.id.j, R.id.k, R.id.l, R.id.m, R.id.n,
                R.id.o, R.id.p, R.id.q, R.id.r, R.id.s, R.id.t, R.id.u,
                R.id.v, R.id.w, R.id.x, R.id.y, R.id.z};
        this.alphabet = alphabet;

        for (int i = 0; i < alphabet.length; i++) {
            Button b = (Button) findViewById(alphabet[i]);
            b.setOnClickListener(this);
        }

        display = (TextView) findViewById(R.id.display);
        hangmanLogic.newGame(10, words);
        myDrawHangmanView = (MyDrawHangmanView) findViewById(R.id.myDrawHangmanView);
    }

    //TODO watch menu video again
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        Log.d("Mine", "onCreateOptionsMenu");
        menu.add(Menu.NONE, R.menu.mymenu, Menu.NONE, "New Game");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("Mine", "onOptionsItemSelected: " + item.getItemId());
//        long id = item.getItemId();
        switch (item.getItemId()) {
            case R.menu.mymenu:
                hangmanLogic.newGame(10, words);
                myDrawHangmanView.reset();
                for (int i = 0; i < alphabet.length; i++) {
                    Button b = (Button) findViewById(alphabet[i]);
                    b.setVisibility(View.VISIBLE);
                    b.setClickable(true);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        Button d = (Button) v;

        String strLetter = d.getText().toString();
        char cLetter = strLetter.charAt(0);
        if (!hangmanLogic.buttonClicked(cLetter)) {
            myDrawHangmanView.increment();
        }
        v.setVisibility(View.INVISIBLE);


    }

    @Override
    public void updateMessage(String s) {
        display.setText(s);
    }

    @Override
    public void gameIsDone(boolean winner) {
        for (int i = 0; i < alphabet.length; i++) {
            Button b = (Button) findViewById(alphabet[i]);
            b.setClickable(false);
        }
    }
}
