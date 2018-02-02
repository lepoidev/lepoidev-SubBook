package com.example.lepoidev_subbook;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditSubActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private EditText name;
    private EditText date;
    private EditText cost;
    private EditText comment;
    private DatePickerDialog datePickerDialog;
    private static ArrayList<Sub> subList;
    private Sub mySub;
    private int myIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub);

        date = (EditText) findViewById(R.id.editDate);

        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(EditSubActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String newDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                date.setText(newDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        Log.i("LifeCycle --->", "onStart is called");
        //saveInFile();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myIndex = extras.getInt("pos");
        }
        loadFromFile();
        mySub = subList.get(myIndex);
        setEditTexts(mySub);
    }

    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Sub>>(){}.getType();
            subList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            subList = new ArrayList<Sub>();
        }
    }

    private void saveInFile() {
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

    private void returnToCaller(){

    }

    private void setEditTexts(Sub mySub){

        name = (EditText) findViewById(R.id.editName);
        date = (EditText) findViewById(R.id.editDate);
        cost = (EditText) findViewById(R.id.editCost);
        comment = (EditText) findViewById(R.id.editComment);

        String strName = mySub.getName();
        @SuppressLint("SimpleDateFormat") String strDate = new SimpleDateFormat("yyyy-d-MM").format(mySub.getDate());
        String strCost = String.valueOf(mySub.getCost());
        String strComment = mySub.getComment();

        name.setText(strName);
        date.setText(strDate);
        cost.setText(strCost);
        comment.setText(strComment);
    }

    private boolean wasChanged(String newName, Date newDate, float newCost, String newComment){
        return false;
    }

    public void removeSub(View view){

    }
    public void verifySubEdit(View view){
        //return true;
    }
}
