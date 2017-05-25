package net.wcc.ajcrutchfield.jh5_ajcrutchfield_hangman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class HangmanMain extends AppCompatActivity implements OnClickListener, HangmanUpdate {

    public static final String MY_WORD_LIST = "myWordList";
    public static final String CATEGORY_NAME = "categoryName";

    static final String WORD_MANAGER = "net.wcc.ajcrutchfield.intent.action.WORD_MANAGER";
    static final int WORD_MANAGER_INTENT_CALL = 1;

    public TextView display;
    public TextView category;

    HangmanLogic hangmanLogic = new HangmanLogic(this);
    MyDrawHangmanView myDrawHangmanView;

    ArrayList<String> words = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    int[] alphabet = new int[26];

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        words.add("one");
        runWordManager();

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
        category = (TextView) findViewById(R.id.categoryView);
        myDrawHangmanView = (MyDrawHangmanView) findViewById(R.id.myDrawHangmanView);

        hangmanLogic.newGame(10, words);
    }

    //TODO watch menu video again
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);

        Log.d("Mine", "onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("Mine", "onOptionsItemSelected: " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.newWord:
                hangmanLogic.newGame(10, words);
                myDrawHangmanView.reset();
                for (int i = 0; i < alphabet.length; i++) {
                    Button b = (Button) findViewById(alphabet[i]);
                    b.setVisibility(View.VISIBLE);
                    b.setClickable(true);
                }
                return true;
            case R.id.newCategory:
                runWordManager();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void runWordManager(){
        intent = new Intent(WORD_MANAGER);
        startActivityForResult(intent, WORD_MANAGER_INTENT_CALL);
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

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if (resCode != Activity.RESULT_OK) {
            Log.d("Mine", "Returning without proper Result code");
            return;
        }

        if(reqCode == WORD_MANAGER_INTENT_CALL){
            list = data.getStringArrayListExtra(MY_WORD_LIST);
            category.setText(data.getStringExtra(CATEGORY_NAME));
            Log.d("Mine", list.toString());
            Log.d("Mine", data.getStringExtra(CATEGORY_NAME));
            words.clear();
            for(int i = 0; i < list.size(); i++){
                words.add(list.get(i));
            }
            hangmanLogic.newGame(10, words);
            myDrawHangmanView.reset();
            for (int i = 0; i < alphabet.length; i++) {
                Button b = (Button) findViewById(alphabet[i]);
                b.setVisibility(View.VISIBLE);
                b.setClickable(true);
            }
        }
    }
}
