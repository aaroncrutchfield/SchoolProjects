package net.wcc.ajcrutchfield.myadapter;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {
    String[] country_array;
    ArrayList<String> countryAList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources myResources= getResources();

        country_array = myResources.getStringArray(R.array.countries_array);

        countryAList = new ArrayList<String>();
        for (int i=0; i < country_array.length; i++)
            countryAList.add(country_array[i]);


        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countryAList);

        setContentView(R.layout.listview);
//        setContentView(R.layout.gridview);
//        setContentView(R.layout.spinner); // set the following boolean to true
        MyAdapterView myAdapterView = new MyAdapterView(arrayAdapter, false);
    }


    // ********************  Inner Class    *************************
    class MyAdapterView implements AdapterView.OnItemClickListener
    {
        ArrayAdapter<String> arrayAdapter;

        MyAdapterView(ArrayAdapter<String> arrayAdapter, boolean spinner)
        {
            this.arrayAdapter = arrayAdapter;
            AdapterView adapterView = (AdapterView)findViewById(R.id.myAdapterView);
            if (spinner)
                arrayAdapter.setDropDownViewResource(R.layout.listview);
            else
                adapterView.setOnItemClickListener(MyAdapterView.this);
            adapterView.setAdapter(arrayAdapter);
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView text = (TextView) view;
            String str =text.getText() + " pos="+position + " id=" +id;

            String value = countryAList.get(position);
            countryAList.add(position+1, value +"_x");

            arrayAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            Log.d("Mine", "onItemClickListener: "+str);
        }

    }

    // ********************  End of Inner Class *********************

}
