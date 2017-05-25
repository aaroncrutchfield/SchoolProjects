package net.wcc.ajcrutchfield.summaryactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.wcc.ajcrutchfield.JH7_ajcrutchfield.R;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {

    private TableLayout summaryTable;
    private Intent intentFromPicklist;
    private ArrayList<SummaryHelper> summaryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_layout);

        summaryTable = (TableLayout) findViewById(R.id.summaryTable);
        intentFromPicklist = getIntent();
        summaryArray = (ArrayList<SummaryHelper>) intentFromPicklist.getSerializableExtra("summaryArray");
        if (summaryArray != null){
            //views for summary_layout
            TextView partNum;
            TextView fullToteCount;
            TextView partialCount;
            TextView totalCount;
            TableRow row;

            //will use to send text via email
            StringBuilder count = new StringBuilder();
            count.append("PartNumber" + "\t\t"
                    + "Full Totes" + "\t\t"
                    + "Partials" + "\t\t"
                    + "Total" + "\t\t\n");

            for (int i = 0; i < summaryArray.size(); i++) {
                SummaryHelper temp = summaryArray.get(i);
                partNum = new TextView(this);
                fullToteCount = new TextView(this);
                partialCount = new TextView(this);
                totalCount = new TextView(this);
                row = new TableRow(this);

                //setText for each view
                partNum.setText(temp.getPartNumber());
                fullToteCount.setText(temp.getFullTotes());
                partialCount.setText(temp.getPartialCount());

                //prevent partial field from going off-screen
                partialCount.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
                totalCount.setText(temp.getTotalCount());

                //set textColor to black
                partNum.setTextColor(Color.BLACK);
                fullToteCount.setTextColor(Color.BLACK);
                partialCount.setTextColor(Color.BLACK);
                totalCount.setTextColor(Color.BLACK);

                //add views to row then table
                row.addView(partNum);
                row.addView(fullToteCount);
                row.addView(partialCount);
                row.addView(totalCount);
                summaryTable.addView(row);

                //will use to send via email
                count.append(temp.getPartNumber() + "\t\t"
                        + temp.getFullTotes() + "\t\t"
                        + temp.getPartialCount() + "\t\t"
                        + temp.getTotalCount() + "\t\t\n");

//                Log.d("row", temp.getPartNumber() + "\t\t"
//                        + temp.getFullTotes() + "\t\t"
//                        + temp.getPartialCount() + "\t\t"
//                        + temp.getTotalCount() + "\t\t");

            }
            Log.d("row", count.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
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
}
