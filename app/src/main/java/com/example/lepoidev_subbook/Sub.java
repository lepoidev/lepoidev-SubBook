package com.example.lepoidev_subbook;

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
