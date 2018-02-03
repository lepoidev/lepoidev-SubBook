//http://www.codelocker.net/30205/android-create-a-popup-window-with-buttons/

package com.example.lepoidev_subbook;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;

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
import java.util.Objects;

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
    private PopupWindow POPUP_WINDOW_DELETION = null;

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
        Intent intent = new Intent(EditSubActivity.this, MainActivity.class);
        startActivity(intent);
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

    public void verifySubEdit(View view){

        String newName = name.getText().toString();
        String newDate = date.getText().toString();
        String newCost = cost.getText().toString();
        String newComment = comment.getText().toString();


        boolean correct = true;

        if((20<newName.length()) || (newName == "")){
            name.setError("Must not be more that 20 characters; cannot be empty");
            correct = false;
        } else {
            name.setError(null);
        }

        DateFormat format = new SimpleDateFormat("yyyy-d-MM", Locale.ENGLISH);
        Date newdateDate = new Date();
        try {
            newdateDate = format.parse(newDate);
            date.setError(null);
        } catch (ParseException e) {
            //need to print the text
            e.printStackTrace();
            correct = false;
            date.setError("Tap to open selection or enter date in form yyyy-dd-MM");
        }

        float f_cost = 0;
        try {
            f_cost = Float.parseFloat(newCost);
            if (f_cost < 0){
                String err_str = "a";
                f_cost = Float.parseFloat(err_str);
                //editText4.setError(null);
                //throw java.lang.NumberFormatException;
            } else {
                cost.setError(null);
            }
        } catch (java.lang.NumberFormatException e){
            e.printStackTrace();
            cost.setError("Must be non-negative number");
            correct = false;
        }

        if(30<newComment.length()){
            correct = false;
            comment.setError("Must not be more that 30 characters");
        } else {
            comment.setError(null);
        }

        if (correct) {
            //System.out.println("Hi");
            //saveInFile(name, date, f_cost, comment);
            Log.i("Lifecycle", "attempting to add");
            //Sub newSub = new Sub(newName, newdateDate, f_cost, newComment);
            //addToFile(newSub);
            editSub(newName, newdateDate, f_cost, newComment);

        } else {
            Snackbar.make(view, "Could not edit subscription", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void editSub(String newName, Date newdateDate, float f_cost, String newComment) {
        mySub.setName(newName);
        mySub.setDate(newdateDate);
        mySub.setCost(f_cost);
        mySub.setComment(newComment);
        saveInFile();
        returnToCaller();
    }

    public void removeSub(View view){
        subList.remove(myIndex);
        saveInFile();
        returnToCaller();
    }

    public void confirmDeletion(View view){

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.layout_popup, null);

        POPUP_WINDOW_DELETION = new PopupWindow(this);
        POPUP_WINDOW_DELETION.setContentView(layout);
        POPUP_WINDOW_DELETION.setFocusable(true);

        POPUP_WINDOW_DELETION.setBackgroundDrawable(null);

        POPUP_WINDOW_DELETION.showAtLocation(layout, Gravity.CENTER, 1, 1);

        Button cancelBtn = (Button) layout.findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                POPUP_WINDOW_DELETION.dismiss();
            }
        });

        Button confirmBtn = (Button) layout.findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                removeSub(v);
                POPUP_WINDOW_DELETION.dismiss();
            }
        });
    }
}
