package com.codeitsuisse.team91.expensetracker;

/**
 * Created by Aditya Sodhiya on 14-09-2015.

*/
public class Data {
    private int _id;
    private int _amount;
    private String bugdet_name;
    private String desc;
    private int day;
    private  int month;
    private  int year;

    public int getDay() {
        return day;

    }

    public String getBugdet_name() {
        return bugdet_name;
    }

    public void setBugdet_name(String bugdet_name) {
        this.bugdet_name = bugdet_name;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void set_amount(int _amount) {
        this._amount = _amount;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public void set_id(int id) {
        this._id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int get_id() {
        return _id;
    }

    public int get_amount() {
        return _amount;
    }

    public String getDesc() {
        return desc;
    }

}

