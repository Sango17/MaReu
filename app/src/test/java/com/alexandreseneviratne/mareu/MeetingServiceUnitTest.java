package com.alexandreseneviratne.mareu;

import com.alexandreseneviratne.mareu.di.DI;
import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.model.Time;
import com.alexandreseneviratne.mareu.service.MeetingApiService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

/**
 * Created by Alexandre SENEVIRATNE on 2/7/2020.
 */

@RunWith(JUnit4.class)
public class MeetingServiceUnitTest {
    private MeetingApiService service;

    private Meeting testMeeting;

    private static String TEST_SUBJECT = "Meeting UnitTest";
    private static String TEST_HALL = "Meeting Hall UnitTest";
    private static String TEST_MAIL_ADDRESS_1 = "alexandre@gmail.com";
    private static String TEST_MAIL_ADDRESS_2 = "amanda@gmail.com";

    private Date testDate;
    private Time testTime;
    private ArrayList<String> testParticipantList = new ArrayList<>();

    @Before
    public void setup() {
        service = DI.getService();

        // Set a date (ex: 01/01/2020)
        testDate = new Date(1, 1, 2020);
        // Set a time (ex: 8h30)
        testTime = new Time(8, 30);

        // Set a list of participants by adding 2 email addresses
        testParticipantList.add(TEST_MAIL_ADDRESS_1);
        testParticipantList.add(TEST_MAIL_ADDRESS_2);

        // Set a meeting
        testMeeting = new Meeting(
                TEST_SUBJECT,
                TEST_HALL,
                testDate,
                testTime,
                testParticipantList
        );
    }

    @Test
    public void addMeetingWithSuccess() {
        // Add testMeeting to the meeting list
        service.addMeeting(testMeeting);
        // Check if the meeting list contains testMeeting
        Assert.assertTrue(service.getMeetings().contains(testMeeting));
    }

    @Test
    public void getMeetingWithSuccess() {
        // Check if the meeting list has, as expected, 1 meeting in it
        Assert.assertEquals(1, service.getMeetings().size());
    }

    @Test
    public void deleteMeetingWithSuccess() {
        // Delete testMeeting from the meeting list
        service.deleteMeeting(testMeeting);
        // Check if the meeting list has no meetings in it as expected
        Assert.assertEquals(0, service.getMeetings().size());
    }
}
