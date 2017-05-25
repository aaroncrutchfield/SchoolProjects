package net.wcc.ajcrutchfield.jh4_ajcrutchfield;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

enum WordOrder {Sorted, FrontInsert, Append};

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, FileOperations {
    ArrayList<String> myWordListArray = new ArrayList<String>();
    ArrayAdapter<String> arrayListAdapter;
    ListView myListView;
    WordOrder wordOrder = WordOrder.Sorted;

    WordFileManager wordFileManager = new WordFileManager(this);
    private String currentFileName ;
    TextView fileNameDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (ListView) findViewById(R.id.myListView);
        arrayListAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myWordListArray);

        myListView.setAdapter(arrayListAdapter);
        // Not really going to use ListView OnClickListener, but it could be useful for other applications
        myListView.setOnItemClickListener(this);
        updateFromResource(R.array.countries_array);

        // Constructing the Inner class that handles the EditText box
        // Interesting question on why it's OK that this variable is not used, but why
        // aren't we worrying about the Garbage collector?  Hint: There is an EditText created that
        // has called OnKeyListener and passes this address.
        MyEditText myEditText = new MyEditText();

        registerForContextMenu(myListView);

        fileNameDisplay = (TextView)findViewById(R.id.file_name);
        String lastFileName = wordFileManager.getMyLastFileName();

        open(lastFileName);
    }

    // Standard Options Menu Creation from Resource
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);

        return true;
    }

    // User has selected an item from the Options Menu
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {

            //enum WordOrder {Sorted, FrontInsert, Append};
            case R.id.insert_at_front:
                wordOrder = WordOrder.FrontInsert;
                return true;
            case R.id.insert_at_end:
                wordOrder = WordOrder.Append;
                return true;
            case R.id.sort_words:
                wordOrder = WordOrder.Sorted;
                updateList();
                return true;
            case R.id.save:
                Log.d("Mine", "save option menu");
                saveCategory();
                return true;
            case R.id.open_file:
                Log.d("Mine", "open option menu");
                MyAlertDialogHandler dho = new MyAlertDialogHandler(this, wordFileManager,
                        "Select Category to Open, or hit Back button",
                        this, DIALOG_TYPE.Open);
                return true;
            case R.id.new_file:
                Log.d("Mine", "new option menu");
                NewFileDialogHandler nfdh = new NewFileDialogHandler(this, this);
                nfdh.show();
                return true;
            case R.id.delete_file:
                Log.d("Mine", "delete option menu");
                MyAlertDialogHandler dhd = new MyAlertDialogHandler(this, wordFileManager,
                        "Select Category to Delete, or hit Back button",
                        this, DIALOG_TYPE.Delete);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    // Create Context Menu for the ListView
    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle(R.string.word_context_menu_prompt);
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.word_list_menu, menu);
    }

    // User has done a long press on a particular word in ListView
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        int index = menuInfo.position;
        TextView text = (TextView) menuInfo.targetView;
        String value =text.getText().toString();

        switch (item.getItemId())
        {
            case R.id.remove_word:
                removeWord(index);
                return true;

            case R.id.capitalize_word:
                replaceWord(index, value.toUpperCase());
                return true;

            case R.id.lower_case_word:
                replaceWord(index, value.toLowerCase());
                return true;

            case R.id.capitalize_first_letter:
                char c = value.charAt(0);
                value = Character.toUpperCase(c) + value.substring(1).toLowerCase();
                replaceWord(index, value);
                return true;

            case R.id.edit_word:
                EditWordDialogHandler ewdh = new EditWordDialogHandler(this, index,
                        value, this);
                ewdh.show();
                return true;

        }

        return false;
    }

    public void onPause()
    {
        super.onPause();
        saveCategory();
    }

    // It is Important that we never change the address of our ArrayList.
    private void updateList()
    {
        if (wordOrder == WordOrder.Sorted)
            Collections.sort(myWordListArray);
        arrayListAdapter.notifyDataSetChanged();
    }

    // This is called when on the first program call to initialize words from an array resource
    public void updateFromResource(int resource) {
        String[] words = getResources().getStringArray(resource);
        myWordListArray.clear();
        for (int i = 0; i < words.length; i++)
            myWordListArray.add(words[i]);
        updateList();
    }



    // This is called when when just read in a new category to work with
    public void updateFromArrayList(ArrayList<String> arr) {
        myWordListArray.clear();
        for (int i = 0; i < arr.size(); i++)
            myWordListArray.add(arr.get(i));
        updateList();
    }

    // Not doing anything with the normal Item click ... just logging for now
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // GridView grid = (GridView) parent;
        TextView text = (TextView) view;
        String str = text.getText() + " pos=" + position + " id=" + id;
        Log.d("Mine", "onItemClick: " + str);
    }

    private void changeFileName(String category)
    {
        currentFileName = category;
        if (currentFileName == null)
            currentFileName = "Countries";
        fileNameDisplay.setText(currentFileName);
        wordFileManager.storeMyFileName(currentFileName);
    }

    private void saveCategory()
    {
        ArrayList<String> arr = new ArrayList<>();

        for (int i =0; i < myWordListArray.size(); i++){
            arr.add(myWordListArray.get(i));
        }
        wordFileManager.saveMyWords(arr, currentFileName);
        wordFileManager.storeMyFileName(currentFileName);
    }

    @Override
    public void newFile(String category) {
        myWordListArray.clear();
        changeFileName(category);
        updateList();
    }

    @Override
    public void open(String category) {
        changeFileName(category);
        ArrayList<String> arr;
        Serializable obj = wordFileManager.getSerializable(currentFileName);
        if (obj == null){
            arr = new ArrayList<String>(); // Empty list
            updateFromResource(R.array.countries_array);
        }
        else{
            arr = (ArrayList<String>) obj;
            updateFromArrayList(arr);
        }
        wordFileManager.storeMyFileName(currentFileName);

    }

    @Override
    public void delete(String category) {
        wordFileManager.deleteFile(category);
        updateList();
    }

    // This is called when the user hits the Enter key from the EditText box
    public void addWord(String word)
    {
        if(wordOrder == WordOrder.FrontInsert)
            myWordListArray.add(0, word);
        else
            myWordListArray.add(word);
        updateList();
    }

    // This is called when the user does a Context Menu (Long Press) on a word and selectes "Remove"
    public void removeWord(int index)
    {
        myWordListArray.remove(index);
        updateList();
    }

    // This is called when the user changes a word (Edit, CAPITALIZE, lower case, etc.)
    public void replaceWord(int index, String value)
    {
        myWordListArray.set(index, value);
        updateList();
    }





    // Processing for the EditText widget for entering in new words
    //*****************  Inner class for EditText   **************************
    class MyEditText implements OnKeyListener
    {
        EditText myEdit;
        MyEditText()
        {
            myEdit = (EditText)findViewById(R.id.myEditText);
            myEdit.setOnKeyListener(MyEditText.this);
        }
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
                if (keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    addWord(myEdit.getText().toString());
                    myEdit.setText("");
                    return true;
                }
            return false;
        }
    }
    // *****************  End of Inner class for EditText   *******************

}