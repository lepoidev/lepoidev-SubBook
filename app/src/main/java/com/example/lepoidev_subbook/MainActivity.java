/*
 * MainActivity
 *
 * Author: Kyle LePoidevin-Gonzales
 *
 * Resources
 * https://stackoverflow.com/questions/26842916/how-can-i-start-a-different-activity-on-item-click-from-a-custom-listview - ListView click
 *
 */

package com.example.lepoidev_subbook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {

    //class variables
    private final String FILENAME = "file.sav";
    private ListView oldSubs;
    private ArrayList<Sub> subList;
    private ArrayAdapter<Sub> adapter;
    private TextView summaryText;// = (TextView) findViewById(R.id.summaryText);

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        summaryText = (TextView) findViewById(R.id.summaryText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        oldSubs = (ListView) findViewById(R.id.oldSubs);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSubActivity.class);
                startActivity(intent);
                adapter.notifyDataSetChanged();
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
            }
        });
        oldSubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String test = Integer.toString(position);
                /* Snackbar.make(view, "Replace with your own action. Index:  " + test, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
                Intent intent = new Intent(MainActivity.this, EditSubActivity.class);
                intent.putExtra("pos", position);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });
    }
    protected void onStart() {

        super.onStart();
        Log.i("LifeCycle --->", "onStart is called");
        //saveInFile();
        loadFromFile();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, subs);
        //adapter = new ArrayAdapter<Sub>(this, R.layout.list_item, subList);
        adapter = new SubArrayAdapter(this, R.layout.two_item_list_view, subList);
        oldSubs.setAdapter(adapter);
        setTitle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu); //this was commented out cause dont need
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

    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Sub>>(){}.getType();
            subList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subList = new ArrayList<Sub>();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy is called");
    }

    private void setTitle(){
        float sum = getSubSum();
        if (sum == 0){
            String newSummary = "No Subscriptions";
            summaryText.setText(newSummary);
        } else {
            String newSummary = "Monthly Cost: $" + String.valueOf(sum);
            summaryText.setText(newSummary);
        }
    }

    private float getSubSum(){
        float sum = 0;
        for(int i = 0; i < subList.size(); i++){
            sum = sum + subList.get(i).getCost();
        }
        return sum;
    }
}
