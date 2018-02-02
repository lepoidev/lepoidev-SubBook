package com.example.lepoidev_subbook;

// https://stackoverflow.com/questions/4772425/change-date-format-in-a-java-string
// http://www.java2s.com/Tutorial/Java/0040__Data-Type/SimpleDateFormat.htm

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kyle on 2018-01-31.
 */

public class Sub {
    private String name;
    private Date date;
    private float cost;
    private String comment;

    Sub(String name, Date date, float cost, String comment){
        this.setName(name);
        this.setDate(date);
        this.setCost(cost);
        this.setComment(comment);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return this.name;
    }

    public Date getDate() {
        return this.date;
    }

    public String getStrDate() {
        String strDate = new SimpleDateFormat("EEEE MMMM yyyy").format(this.date);
        strDate = "Subscribed since " + strDate;
        return strDate;
    }

    public float getCost() {
        return this.cost;
    }

    public String strGetCost() {
        return "$" + String.valueOf(this.cost);
    }

    public String getComment() {
        return this.comment;
    }

    public String toString(){
        return name + " | " + date.toString() + " | " + cost;
    }

}
