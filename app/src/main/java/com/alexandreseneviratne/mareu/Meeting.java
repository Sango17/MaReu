package com.alexandreseneviratne.mareu;

import java.util.ArrayList;

/**
 * Created by Alexandre SENEVIRATNE on 1/15/2020.
 */
public class Meeting {
    private int id;
    private String subject;
    private String hall;
    private String scheduleTime;
    private ArrayList<String> participants;

    public Meeting(int id, String subject, String hall, String scheduleTime, ArrayList<String> participants) {
        this.id = id;
        this.subject = subject;
        this.hall = hall;
        this.scheduleTime = scheduleTime;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getHall() {
        return hall;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }
}
