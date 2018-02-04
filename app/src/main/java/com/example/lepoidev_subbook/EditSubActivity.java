/*
 *  EditSuActivity
 *
 *  Author: Kyle LePoidevin-Gonzales
 *
 *  Resources
 *  http://www.codelocker.net/30205/android-create-a-popup-window-with-buttons/
 *      For popup window
 *      by CodeLocker, published 2/24/2014, no listed licence
 *
 */
package com.example.lepoidev_subbook;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

/**
 * EditSubActivity allows the user to view a subscription and if willing, edit the values. All input
 * is checked for validity just as AddSubActivity. The user may even delete a subscription. A
 * popup prompts the user to confirm deletion as well. This method has similar skeleton to
 * AddSubActivity but with more features and modified functionalities.
 */
public class EditSubActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private EditText name;                              //local EditText variables
    private EditText date;
    private EditText cost;
    private EditText comment;
    private DatePickerDialog datePickerDialog;          //datepicker
    private static ArrayList<Sub> subList;              //local sublist
    private Sub mySub;                                  //Sub instance of interest
    private int myIndex;                                //index of the Sub
    private PopupWindow POPUP_WINDOW_DELETION = null;   //popup for error message

    /**
     *
     * onCreate method for EditSubActivity. This function sets the datePickerDialog to trigger when
     * the date field is clicked.
     *
     * @param savedInstanceState
     */
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

    /**
     * onStart grabs the index of the Sub that was clicked on and then proceeds to
     * load the array, load the Sub and set the EditTexts to show the Sub's parameter
     * Please note this code is from lonelyTwitter with very mild edits
     */
    protected void onStart() {
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
            returnToCaller();
        }
    }

    /**
     * This method simply saves the newly edited subList to the savefile
     *
     * Please note this code is from lonelyTwitter with very mild edits
     */
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
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Method that return to the main screen activity
     */
    private void returnToCaller(){
        Intent intent = new Intent(EditSubActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * This method sets the text in the EditTexts to match the attributes of the selected Sub
     * instance that was clicked on by the user.
     *
     * @param mySub - the Sub that the user clicked on
     */
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

    /**
     * This method returns true of the vales were changed by the user.
     *
     * @param newName - user proposed new name
     * @param newDate - user proposed new date
     * @param newCost - user proposed new cost
     * @param newComment - user proposed new comment
     * @return - True if the values were changed
     */
    private boolean wasChanged(String newName, Date newDate, float newCost, String newComment){
        //TODO implement if time to make prettier code
        return true;
    }

    /**
     * This method verifies that the user input is valid and then calls for the data to be commited
     * and returns. Validation methodology is very similar to verifySub
     *
     * @see AddSubActivity
     * @param view - view passed from the button click
     */
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
        Date newDateConverted = new Date();
        try {
            newDateConverted = format.parse(newDate);
            date.setError(null);
        } catch (ParseException e) {
            //need to print the text

            correct = false;
            date.setError("Tap to open selection or enter date in form yyyy-dd-MM");
        }

        float costInFloat = 0;
        try {
            costInFloat = Float.parseFloat(newCost);
            if (costInFloat < 0){
                String err_str = "a";
                costInFloat = Float.parseFloat(err_str);
                //editText4.setError(null);
                //throw java.lang.NumberFormatException;
            } else {
                cost.setError(null);
            }
        } catch (java.lang.NumberFormatException e){

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
            Log.i("Lifecycle", "attempting to add");
            editSub(newName, newDateConverted, costInFloat, newComment);

        } else {
            Snackbar.make(view, "Could not edit subscription", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    /**
     * Sets the new parameters of the sub of interest and writes to file
     *
     * @param newName - the new name to be assigned
     * @param newDateConverted - the new date to be assigned
     * @param costInFloat - the new cost to be assigned
     * @param newComment - the new comment to be assigned
     */
    private void editSub(String newName, Date newDateConverted, float costInFloat, String newComment) {
        mySub.setName(newName);
        mySub.setDate(newDateConverted);
        mySub.setCost(costInFloat);
        mySub.setComment(newComment);
        saveInFile();
        returnToCaller();
    }

    /**
     * Removes the sub from the subList and writes to file
     *
     * @param view - view passed from the button click
     */
    public void removeSub(View view){
        subList.remove(myIndex);
        saveInFile();
        returnToCaller();
    }

    /**
     * This method provides a popup window for the user to verify their intent of deletion
     * See header for resources used for the code
     *
     * @param view - view passed from the button click
     */
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
