package com.alexandreseneviratne.mareu.service;

import com.alexandreseneviratne.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandre SENEVIRATNE on 1/27/2020.
 */
public class FakeMeetingApiService implements MeetingApiService {
    private List<Meeting> meetings = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting selectedMeeting) {
        meetings.remove(selectedMeeting);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetings.add(0, meeting);
    }
}
