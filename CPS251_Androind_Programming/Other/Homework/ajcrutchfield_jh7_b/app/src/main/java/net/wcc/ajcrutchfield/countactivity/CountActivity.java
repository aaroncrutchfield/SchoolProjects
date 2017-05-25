package net.wcc.ajcrutchfield.countactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import net.wcc.ajcrutchfield.JH7_ajcrutchfield.PartHelper;
import net.wcc.ajcrutchfield.JH7_ajcrutchfield.R;
import net.wcc.ajcrutchfield.filemanager.DIALOG_TYPE;
import net.wcc.ajcrutchfield.filemanager.FileManager;
import net.wcc.ajcrutchfield.filemanager.FileOperations;
import net.wcc.ajcrutchfield.filemanager.MyAlertDialogHandler;
import net.wcc.ajcrutchfield.filemanager.NewFileDialogHandler;
import net.wcc.ajcrutchfield.summaryactivity.SummaryHelper;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CountActivity extends AppCompatActivity implements FileOperations {

    private static final String SUMMARY_ACTIVITY = "android.intent.action.summaryActivity";
    private ArrayList<CountHelper> countHelperArray = new ArrayList<>();
    ArrayList<PartHelper> partHelperArray = new ArrayList<>();
    ArrayList<SummaryHelper> summaryArray = new ArrayList<>();
    private Intent intent;
    private String currentFilename;
    private FileManager fileManager = new FileManager(this);
    private boolean justCreated = false;

    TableLayout countTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_layout);

        countTable = (TableLayout) findViewById(R.id.countTable);

        Intent intentFromDemand = getIntent();
        partHelperArray = (ArrayList<PartHelper>) intentFromDemand.getSerializableExtra("partHelperArray");

        //use ArrayLists of helper classes to generate TableRows of data
        updateList();
        newFileName();

        //if onCreate is called, this is a new file
        justCreated = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_count, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.save:
                saveFile();
                return true;
            case R.id.open_file:
                MyAlertDialogHandler dho = new MyAlertDialogHandler(this, fileManager,
                        "Select File to Open, or hit Back button",
                        this, DIALOG_TYPE.Open);
                return true;
            case R.id.new_file:
                newFileName();
                return true;
            case R.id.delete_file:
                Log.d("Mine", "delete option menu");
                MyAlertDialogHandler dhd = new MyAlertDialogHandler(this, fileManager,
                        "Select File to Delete, or hit Back button",
                        this, DIALOG_TYPE.Delete);
                return true;
            case R.id.summary:
                generateSummary();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void newFileName() {
        NewFileDialogHandler nfdh = new NewFileDialogHandler(this, this);
        nfdh.show();
    }

    public void onPause(){
        super.onPause();
        saveFile();
    }

    public void onResume() {
        super.onResume();
        if (!justCreated){
            String lastFileName = fileManager.getMyLastFileName();
            open(lastFileName);
        }
    }

    //store important info to arrayList to pass to summaryActivity
    public void generateSummary(){
        getSummaryHelpers();

        intent = new Intent(SUMMARY_ACTIVITY);
        intent.putExtra("summaryArray", summaryArray);
        startActivity(intent);
    }


    public void getSummaryHelpers() {
        SummaryHelper summaryHelper;
        int total;
        Log.d("Mine", "****getSummaryHelpers****");
//        Log.d("Mine", "countHelperarray size= "+String.valueOf(countHelperArray.size()));
//        Log.d("Mine", "summaryArray size= "+String.valueOf(summaryArray.size()));
        summaryArray.clear();
        for (int i = 0; i < countHelperArray.size(); i++) {
            //everything doubles up without this

            CountHelper temp = countHelperArray.get(i);
            summaryHelper = new SummaryHelper();

            Log.d("Mine", temp.getPartNumber());
            summaryHelper.setPartNumber(temp.getPartNumber());
            summaryHelper.setFullTotes(String.valueOf(temp.getFullToteCount() + "@"
                    + temp.getStandardCount()));
            summaryHelper.setPartialCount(temp.getPartialString().toString());
            total = temp.getPartialTotal() + (temp.getFullToteCount()*temp.getStandardCount());
            summaryHelper.setTotalCount(String.valueOf(total));

            summaryHelper.setStandardCount(temp.getStandardCount());
            summaryHelper.setTotalDemand(temp.getTotalDemand());
            summaryHelper.setPartialTotal(temp.getPartialTotal());

            summaryArray.add(summaryHelper);
        }
//        Log.d("Mine", "countHelperarray size= "+String.valueOf(countHelperArray.size()));
//        Log.d("Mine", "summaryArray size= "+String.valueOf(summaryArray.size()));
        Log.d("Mine", "****End getSummaryHelpers****");
    }

    private void changeFilename(String filename) {
        //http://stackoverflow.com/questions/5369682/get-current-time-and-date-on-android
        DateFormat df = new SimpleDateFormat("EEE, MMM d yyyy | HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        Log.d("Mine", date);

        if (filename == null) {
            currentFilename = date;
        }
        else if (filename.length() < 20) {
            currentFilename = filename + "  |  " + date;
        }
        else{
            currentFilename = filename;
        }
    }


    private void saveFile(){
        ArrayList<SummaryHelper> arr = new ArrayList<>();
        getSummaryHelpers();
        changeFilename(currentFilename);

        Log.d("mine", "****Saving File****");
        Toast.makeText(CountActivity.this, "Saving "+currentFilename, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < summaryArray.size(); i++){
            SummaryHelper temp = summaryArray.get(i);
            Log.d("mine", temp.getPartNumber());
        }
        Log.d("mine", "****Done Saving File****");
        fileManager.saveSerializable(summaryArray, currentFilename);
        fileManager.storeMyFileName(currentFilename);
        justCreated = false;
    }

    @Override
    public void newFile(String filename) {
        changeFilename(filename);
    }


    @Override
    public void open(String filename) {
        changeFilename(filename);
        ArrayList<SummaryHelper> arr;
        Toast.makeText(CountActivity.this, "Opening "+currentFilename, Toast.LENGTH_SHORT).show();
        Serializable obj = fileManager.getSerializable(currentFilename);

        arr = (ArrayList<SummaryHelper>) obj;
        updateFromArrayList(arr);
    }

    private void updateFromArrayList(ArrayList<SummaryHelper> arr) {
        //clear the view of old info
        countTable.removeViewsInLayout(1, countHelperArray.size());

        //clear all information stored on PartNumbers
        partHelperArray.clear();
        countHelperArray.clear();
        summaryArray.clear();

        if (summaryArray.isEmpty())
            Log.d("Mine", "**summaryArray isEmpty**");

        //read in data from saved file to summaryArray
        for (int i = 0; i < arr.size(); i++){
            summaryArray.add(arr.get(i));
        }
        loadFile();
    }

    //use ArrayLists of helper classes to generate TableRows of data
    private void updateList() {
        if (partHelperArray != null) {
            Log.d("Mine", "*****Loading Intent*****");

            for (int i = 0; i < partHelperArray.size(); i++) {
                PartHelper temp = partHelperArray.get(i);
                CountHelper countHelper = new CountHelper(
                        temp.getPartNumber(), temp.getStandardCount(), /*temp.getToteDemand(),*/
                        temp.getDemand(), this, countTable);

                countHelper.initiate();
                //give each view an id
                countHelper.getPartChecked().setId(i+101);
                countHelper.getCountButton().setId(i+201);
                countHelper.getPartialEdit().setId(i+301);
                countHelper.getDemandText().setId(i + 401);

                //add each CountHelper class to an array
                countHelperArray.add(countHelper);
            }
            Log.d("Mine", "*****End of Intent*****");
        }

    }

    private void loadFile() {
        if (summaryArray != null){
            Log.d("Mine", "*****Loading file******");

            for (int i = 0; i < summaryArray.size(); i++) {
                SummaryHelper sumTemp = summaryArray.get(i);

                CountHelper countHelper = new CountHelper(
                        sumTemp.getPartNumber(), sumTemp.getStandardCount(),
                        sumTemp.getTotalDemand(), this, countTable);

                int index = sumTemp.getFullTotes().indexOf("@");
                countHelper.setFullToteCount(Integer.parseInt(sumTemp.getFullTotes().substring(0, index)));
                countHelper.partialAppend(sumTemp.getPartialCount());
                countHelper.setPartialTotal(sumTemp.getPartialTotal());

                countHelper.initiate();
                //give each view an id
                countHelper.getPartChecked().setId(i + 101);
                countHelper.getCountButton().setId(i + 201);
                countHelper.getPartialEdit().setId(i + 301);
                countHelper.getDemandText().setId(i + 401);

                //add each CountHelper class to an array
                countHelperArray.add(countHelper);
                Log.d("Mine", countHelper.getPartNumber());
            }
            Log.d("Mine", "*****End of file******");
        }
    }

    @Override
    public void delete(String filename) {
        fileManager.deleteFile(filename);
    }
}

//helpful for voice input later
//http://stackoverflow.com/questions/12003507/can-a-voice-action-button-be-added-next-to-edittext-in-android
