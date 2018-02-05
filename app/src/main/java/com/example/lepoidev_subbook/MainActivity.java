/*
 * MainActivity
 *
 * Author: Kyle LePoidevin-Gonzales
 *
 * Resources
 * https://stackoverflow.com/questions/26842916/how-can-i-start-a-different-activity-on-item-click-from-a-custom-listview
 *      For on click action on listview
 *      Author eurosecom, Nov 10, 2014, no licence stated
 *
 * https://stackoverflow.com/questions/18202815/passing-listview-data-through-intent
 *      For passing values to a new activity
 *      Author Raghunandan, Aug 13 2013, no licence stated
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

/**
 * MainActivity performs all actions pertaining to the home screen (ie list fo subscriptions and
 * summary). The user may click on an item in the list or press the add button to add to the list.
 *
 */
public class MainActivity extends AppCompatActivity {
    private final String FILENAME = "file.sav";
    private ListView oldSubs;
    private ArrayList<Sub> subList;
    private ArrayAdapter<Sub> adapter;
    private TextView summaryText;

    /**
     * onCreate method for MainActivity. This function sets the on click listeners for the ListView
     * and add button as well as initializes the list of subs and summary text.
     *
     * @param savedInstanceState
     */
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
            }
        });

        oldSubs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String test = Integer.toString(position);
                Intent intent = new Intent(MainActivity.this, EditSubActivity.class);
                intent.putExtra("pos", position);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * This method loads the subList from savefile and sets the array adapter for the ListView
     *
     */
    protected void onStart() {
        super.onStart();
        Log.i("LifeCycle --->", "onStart is called");
        loadFromFile();

        adapter = new SubArrayAdapter(this, R.layout.two_item_list_view, subList);
        oldSubs.setAdapter(adapter);
        setTitle();
    }

    /**
     * Load from file the contents of the subList
     *
     * Please note this code is from lonelyTwitter with very mild edits
     */
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

    /**
     * Close down activity
     *
     * Please note this code is from lonelyTwitter
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "onDestroy is called");
    }

    /**
     * This function sets the summary text at the top of the activity. If there are no subs, the
     * summary text simply states the lack of subscriptions, otherwise the sum is diplayed
     */
    private void setTitle(){
        float sum = getSubSum();
        if ((sum == 0) && (subList.size() == 0)){
        //if (sum == 0) {
                String newSummary = "No Subscriptions";
                summaryText.setText(newSummary);
            } else {
                String newSummary = "Monthly Cost: $" + String.valueOf(sum);
                summaryText.setText(newSummary);
            }
    }
    //}

    /**
     * This method returns the sum of all subscriptions in the subList
     *
     * @return - the sum of all subscription costs
     */
    private float getSubSum(){
        float sum = 0;
        for(int i = 0; i < subList.size(); i++){
            sum = sum + subList.get(i).getCost();
        }
        return sum;
    }

}
