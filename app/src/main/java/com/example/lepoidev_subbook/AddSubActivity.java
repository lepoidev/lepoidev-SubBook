/*
 *  AddSubActivity
 *
 *  Author: Kyle LePoidevin-Gonzales
 *
 *  Resources
 *  https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
 *      For conversion of string to date
 *      Author BalusC, Nov 18, 2010, no licence
 *
 *  https://stackoverflow.com/questions/8985295/edittext-seterror-with-icon-but-without-popup-message
 *      For setting error message on EditView
 *      Author user3921740, Sep 5 2014, no licence stated
 *
 *  https://stackoverflow.com/questions/18225365/show-error-on-the-tip-of-the-edit-text-android
 *     For more information on setting error message on EditView
 *     Author SilentKiller Aug 14, 2013
 *
 *  http://abhiandroid.com/ui/datepicker
 *     For how to implement calender popup
 *     Author abhiandroid, no publish date, no licence stated
 *
*/

package com.example.lepoidev_subbook;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
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
import java.util.Objects;

/**
 * AddSubActivity lets the user input information for a new subscription. All data is verified
 * before it is committed to the save file. A calender popup aids in inputting the date. The user
 * is also given the option to not make a new subscription and return to the main screen.
 */
public class AddSubActivity extends AppCompatActivity {
    private final String FILENAME = "file.sav";  //String of filename
    private EditText date;                              //EditText identifier for date
    private DatePickerDialog datePickerDialog;                  //datePickerDialogue for Calender

    /**
     * onCreate method for AddSubActivity. This function sets the datePickerDialog to trigger when
     * the date field is clicked.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        date = (EditText) findViewById(R.id.newDateText);         //set EditText identifier

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);               // current year
                int mMonth = c.get(Calendar.MONTH);             // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH);        // current day

                datePickerDialog = new DatePickerDialog(AddSubActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String newDate1 = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                date.setText(newDate1);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }


    /**
     *
     * The verifySub method verifies that the user inputted subscription information is valid.
     * If any data is invalid, an error message appears in its EditText and an error message is
     * displayed in the snackbar.
     *
     * @param view - view passed by the button
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void verifySub(View view){

        Intent intent = new Intent(AddSubActivity.this, MainActivity.class);

        EditText newName = (EditText) findViewById(R.id.newNameText);     //EditText Labels
        EditText newDate = (EditText) findViewById(R.id.newDateText);
        EditText newCost = (EditText) findViewById(R.id.newCostText);
        EditText newComment = (EditText) findViewById(R.id.newCommentText);

        boolean correct = true;                                           //variable to indicate
                                                                          // correct input

        /*
         *  Gets user input. Check name length, set proper error message if applicable
         */
        String name = newName.getText().toString();
        if((20<name.length()) || (Objects.equals(name, ""))){
            newName.setError("Must not be more that 20 characters; cannot be empty");
            correct = false;
        } else {
            newName.setError(null);
        }

        /*
         *  Gets user input. Check date validity, set proper error message if applicable
         */
        String str_date = newDate.getText().toString();
        DateFormat format = new SimpleDateFormat("yyyy-d-MM", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = format.parse(str_date);
            newDate.setError(null);
        } catch (ParseException e) {
            //need to print the text
            correct = false;
            newDate.setError("Tap to open selection or enter date in form yyyy-dd-MM");
        }

        /*
         *  Gets user input. Check if valid float, set proper error message if applicable
         */
        String cost = newCost.getText().toString();
        float costInFloat = 0;
        try {
            costInFloat = Float.parseFloat(cost);
            if (costInFloat < 0){
                String err_str = "a";
                costInFloat = Float.parseFloat(err_str);
            } else {
                newCost.setError(null);
            }
        } catch (java.lang.NumberFormatException e){
            newCost.setError("Must be non-negative number");
            correct = false;
        }

        /*
         *  Gets user input. Check comment length, set proper error message if applicable
         */
        String comment = newComment.getText().toString();
        if(30<comment.length()){
            correct = false;
            newComment.setError("Must not be more that 30 characters");
        } else {
            newComment.setError(null);
        }

        /*
         *  Check for overall validity. If valid we create new entry and save it to the file.
         *  Otherwise we will display a snackbar message
         */
        if (correct) {
            Log.i("Lifecycle", "attempting to add");
            Sub newSub = new Sub(name, date, costInFloat, comment);

            addToFile(newSub);
            startActivity(intent);
        } else {
            Snackbar.make(view, "Could not add subscription", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    /**
     *
     * This function loads the list of Subs from the save file and returns the list of subs.
     * Please note this code is from lonelyTwitter with very mild edits
     *
     * @return subList - the list of subs save in the file
     */
    private ArrayList<Sub> loadFromFile(){
        ArrayList<Sub> subList;

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Sub>>(){}.getType();
            subList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subList = new ArrayList<Sub>();
        }
        return subList;
    }

    /**
     *
     * This method saves the newly modified subList to the savefile. Please note this code
     * is from lonelyTwitter with very mild edits.
     *
     * @param subList - List of subscriptions from the savefile
     */
    private void saveInFile(ArrayList<Sub> subList) {
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
     *
     * Simple method that appends the newly created sub to the list and then calls for it to
     * be saved in file.
     *
     * @param newSub - newly created sub
     */
    private void addToFile(Sub newSub){
        ArrayList<Sub> subList = loadFromFile();
        subList.add(newSub);
        saveInFile(subList);
    }
}
