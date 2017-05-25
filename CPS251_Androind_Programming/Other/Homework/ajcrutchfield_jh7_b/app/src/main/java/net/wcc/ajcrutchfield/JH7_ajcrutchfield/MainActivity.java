package net.wcc.ajcrutchfield.JH7_ajcrutchfield;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PartOperations, View.OnLongClickListener {

    private static final String COUNT_ACTIVITY = "android.intent.action.countActivity";
    TableLayout table;

    ArrayList<PartNumber> partArray = new ArrayList<>();
    ArrayList<PartHelper> helperArray = new ArrayList<>();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = (TableLayout) findViewById(R.id.table1);
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
        switch (id){
            case R.id.pickList:
                generatePickList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //prompt for a new PartNumber
    public void addPart(View view) {

        NewPartHandler nph = new NewPartHandler(this, this);
        nph.show();
    }

    //create a TableRow for each partNumber. store its info in an arrayList
    @Override
    public void newPart(String partNumber) {
        PartNumber partNumber1 = new PartNumber(partNumber, this, table);

        //store each partNumber in an array for later use
        partArray.add(partNumber1);
    }

    //create the picklist
    //store the demand, partnumber, standardCount, and toteCount for later use
    //send data to the CountActivity
    public void generatePickList(){
        if (partArray != null) {
            helperArray.clear();

            int tempDemand = 0;
            int tempCount = 0;
            boolean stop = false;
            for (int i = 0; i < partArray.size(); i++) {
                PartNumber temp = partArray.get(i);
                PartHelper partHelper = new PartHelper();

                partHelper.setPartNumber(temp.getPartNumber());

                tempDemand = partHelper.setDemand(temp.getDemand());
                tempCount = partHelper.setStandardCount(temp.getStandardCount());

                Log.d("Mine", String.valueOf(tempDemand));
                Log.d("Mine", String.valueOf(tempCount));

                if (tempDemand == 0 || tempCount == 0) {
                    stop = true;
                    break;
                }

                partHelper.setToteDemand(temp.getToteDemand(partHelper.getDemand(), partHelper.getStandardCount()));
                helperArray.add(partHelper);


                Log.d("Mine", helperArray.toString());
            }
            if (!stop) {
                intent = new Intent(COUNT_ACTIVITY);
                intent.putExtra("partHelperArray", helperArray);
                startActivity(intent);
                Log.d("Mine", "PickList generated. Starting COUNT_ACTIVITY");
            }
            else
                Toast.makeText(this, "Check input fields for invalid inputs", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onLongClick(View v) {
        //find row in tableLayout and remove
        TextView t = (TextView)v;
        String number = (String) t.getText();

        for (int i = 0; i < partArray.size(); i++) {
            PartNumber temp = partArray.get(i);
            if(temp.getPartNumber() == number)
                partArray.remove(i);
        }
        TableRow row = (TableRow)v.getParent();
//        row.removeAllViews();
        TableLayout table = (TableLayout)row.getParent();
        table.removeView(row);

        return false;
    }
}
