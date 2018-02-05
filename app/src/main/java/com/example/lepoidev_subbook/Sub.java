/*
 * EditSuActivity
 *
 * Author: Kyle LePoidevin-Gonzales
 *
 * Resources
 *  https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
 *      For conversion of string to date
 *      Author BalusC, Nov 18, 2010, no licence
 *
*/
package com.example.lepoidev_subbook;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The Sub class models a subscription.
 */
public class Sub {
    private String name;
    private Date date;
    private float cost;
    private String comment;

    /**
     * Constructor for Sub class
     *
     * @param name - name of the subscription
     * @param date - date of the subscription
     * @param cost - cost of the subscription
     * @param comment - comment given to the subscription
     */
    Sub(String name, Date date, float cost, String comment){
        this.setName(name);
        this.setDate(date);
        this.setCost(cost);
        this.setComment(comment);
    }

    /**
     * sets the name of the sub
     *
     * @param name - name of the sub
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the date of the sub
     *
     * @param date - date of the sub
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the cost of the sub
     *
     * @param cost - monthly cost of the subscription
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * Sets the comment of the sub
     *
     * @param comment - comment of the subscription
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the name of the sub
     *
     * @return - name of the sub
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the date of the subscription
     *
     * @return - date of the sub
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Get the date in string form and format to the ListView
     *
     * @return - the date in string form
     */
    public String getStrDate() {
        String strDate = new SimpleDateFormat("EEEE MMMM d, yyyy").format(this.date);
        strDate = "Subscribed since " + strDate;
        return strDate;
    }

    /**
     * Gets the cost of the sub
     *
     * @return - the cost in float
     */
    public float getCost() {
        return this.cost;
    }

    /**
     * Gets the cost of the sub in string form
     *
     * @return - the cost in string
     */
    public String getStrCost() {
        return "$" + String.valueOf(this.cost);
    }

    /**
     * Gets the string of the sub
     *
     * @return - returns the comment in string form
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * For debug of ListView
     *
     * @return - string to be displayed in the ListView
     */
    public String toString(){
        return name + " | " + date.toString() + " | " + cost;
    }

}
