package com.lsa.lsamobileapplication;

import java.util.ArrayList;

public class User {
    private String emoticon;
    private ArrayList<String> array = new ArrayList<String>();
    private int hours;
    private String date,week;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }


    public String getEmoticon() {
        return emoticon;
    }

    public void setEmoticon(String emoticon) {
        this.emoticon = emoticon;
    }

    public ArrayList<String> getArray() {
        return array;
    }

    public void setArray(ArrayList<String> array) {
        this.array = array;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }



    public User() {
        //Empty constructor required by firebase
        // serialize the data retrieved and convert
        // it to an object of this class
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}