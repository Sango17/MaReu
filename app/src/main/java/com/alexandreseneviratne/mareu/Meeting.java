package com.alexandreseneviratne.mareu;

import java.util.ArrayList;

/**
 * Created by Alexandre SENEVIRATNE on 1/15/2020.
 */
public class Meeting {
    private String subject;
    private String hall;
    private String scheduleTime;
    private String participants;

    public Meeting(String subject, String hall, String scheduleTime, String participants) {
        this.subject = subject;
        this.hall = hall;
        this.scheduleTime = scheduleTime;
        this.participants = participants;
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

    public String getParticipants() {
        return participants;
    }
}
