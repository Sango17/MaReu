package com.alexandreseneviratne.mareu.service;

import com.alexandreseneviratne.mareu.Meeting;

import java.util.List;

/**
 * Created by Alexandre SENEVIRATNE on 1/27/2020.
 */
public interface MeetingApiService {

    /**
     * Get the list of Meetings
     *
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Suppress a meeting from the list of Meetings
     *
     * @param selectedMeeting will be suppressed
     */
    void deleteMeeting(Meeting selectedMeeting);

    /**
     * Add meeting to the list of meetings
     *
     * @param meeting will be added to the list of Meetings
     */
    void addMeeting(Meeting meeting);
}
