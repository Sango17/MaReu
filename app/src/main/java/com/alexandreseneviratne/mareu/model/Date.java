package com.alexandreseneviratne.mareu.model;

/**
 * Created by Alexandre SENEVIRATNE on 2/4/2020.
 */
public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() { return day; }

    public int getMonth() { return month; }

    public int getYear() { return year; }
}
