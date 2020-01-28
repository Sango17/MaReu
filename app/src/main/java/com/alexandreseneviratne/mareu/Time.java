package com.alexandreseneviratne.mareu;

/**
 * Created by Alexandre SENEVIRATNE on 1/28/2020.
 */
public class Time {
    private int hour;
    private int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHours() {
        return hour;
    }

    public int getMinutes() {
        return minute;
    }
}
