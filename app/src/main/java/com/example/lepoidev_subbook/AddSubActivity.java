// https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
// https://stackoverflow.com/questions/8985295/edittext-seterror-with-icon-but-without-popup-message

package com.example.lepoidev_subbook;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        //TextView textview = findViewById(R.id.textView);
        //TextView.setTes
    }

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
        try {
            Date date = format.parse(str_date);
        } catch (ParseException e) {
            //need to print the text
            e.printStackTrace();
            correct = false;
        }

        String cost = editText5.getText().toString();
        try {
            Float f_cost = Float.parseFloat(cost);
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
            startActivity(intent);
        } else{
            Snackbar.make(view, "Could not add subscription", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
