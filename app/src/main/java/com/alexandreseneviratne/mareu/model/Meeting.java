package com.alexandreseneviratne.mareu.model;


import java.util.ArrayList;

/**
 * Created by Alexandre SENEVIRATNE on 1/15/2020.
 */
public class Meeting {
    private String subject;
    private String hall;
    private Date scheduleDate;
    private Time scheduleTime;
    private ArrayList<String> participants;

    public Meeting(String subject, String hall, Date scheduleDate, Time scheduleTime, ArrayList<String> participants) {
        this.subject = subject;
        this.hall = hall;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
        this.participants = participants;
    }

    public String getSubject() {
        return subject;
    }

    public String getHall() {
        return hall;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public Time getScheduleTime() {
        return scheduleTime;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }
}
