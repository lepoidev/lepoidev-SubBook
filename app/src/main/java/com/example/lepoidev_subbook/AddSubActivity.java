// https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
// https://stackoverflow.com/questions/8985295/edittext-seterror-with-icon-but-without-popup-message

package com.example.lepoidev_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddSubActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        //TextView textview = findViewById(R.id.textView);
        //TextView.setTes
    }


    //private void verifySub(View view){
    public void verifySub(View view){

        Intent intent = new Intent(AddSubActivity.this, MainActivity.class);

        EditText editText3 = (EditText) findViewById(R.id.editText3);
        EditText editText4 = (EditText) findViewById(R.id.editText4);
        EditText editText5 = (EditText) findViewById(R.id.editText5);
        EditText editText6 = (EditText) findViewById(R.id.editText6);

        boolean correct = true;

        String name = editText3.getText().toString();
        if(20<name.length()){
            correct = false;
        }

        String str_date = editText4.getText().toString();
        DateFormat format = new SimpleDateFormat("yyyy-d-MM", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = format.parse(str_date);
        } catch (ParseException e) {
            //need to print the text
            e.printStackTrace();
            correct = false;
        }

        String cost = editText5.getText().toString();
        float f_cost = 0;
        try {
            f_cost = Float.parseFloat(cost);
            if (f_cost < 0){
                String err_str = "a";
                f_cost = Float.parseFloat(err_str);
                //throw java.lang.NumberFormatException;
            }
        } catch (java.lang.NumberFormatException e){
            e.printStackTrace();
            correct = false;
        }

        String comment = editText6.getText().toString();
        if(30<comment.length()){
            correct = false;
        }

        if (correct) {
            //System.out.println("Hi");
            //saveInFile(name, date, f_cost, comment);
            Log.i("Lifecycle", "attempting to add");
            Sub newSub = new Sub(name, date, f_cost, comment);
            addToFile(newSub);
            startActivity(intent);
            //Log.i("Lifecycle", "addSub is called");
        } else {
            Snackbar.make(view, "Could not add subscription", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
    private ArrayList<Sub> loadFromFile(){

        ArrayList<Sub> subList;

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Sub>>(){}.getType();
            subList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            subList = new ArrayList<Sub>();
            //e.printStackTrace();
        }
        return subList;
    }
    private void saveInFile(ArrayList<Sub> subList) {
        //subList.clear();
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(subList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
            //e.printStackTrace();
        }
    }
    private void addToFile(Sub newSub){
        ArrayList<Sub> subList = loadFromFile();
        subList.add(newSub);
        saveInFile(subList);
    }
}
