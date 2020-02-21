package com.alexandreseneviratne.mareu;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.model.Time;
import com.alexandreseneviratne.mareu.ui.MainActivity;
import com.alexandreseneviratne.mareu.utils.DateHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Alexandre SENEVIRATNE on 2/17/2020.
 */

@RunWith(AndroidJUnit4.class)
public class detailMeetingInstrumentedTest {
    private MainActivity mActivity;

    private static String MEETING_SUBJECT = "Hello";
    private static String MEETING_HALL = "Mario";
    private static int MEETING_DAY = 14;
    private static int MEETING_MONTH = 2;
    private static int MEETING_YEAR = 2020;
    private static int MEETING_MINUTE = 30;
    private static int MEETING_HOUR = 9;
    private static String MEETING_PARTICIPANT = "alexandre@gmail.com";

    private Date dateTestMeeting1 = new Date(MEETING_DAY, MEETING_MONTH, MEETING_YEAR);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());

        // Creating a participant list
        ArrayList<String> participant = new ArrayList<>();
        participant.add("alexandre@gmail.com");

        // Creating a test Meeting reservation
        Meeting testMeetingReservation = new Meeting(
                "Hello",
                "Mario",
                new Date(
                        dateTestMeeting1.getDay(),
                        dateTestMeeting1.getMonth(),
                        dateTestMeeting1.getYear()
                ),
                new Time(MEETING_HOUR, MEETING_MINUTE),
                participant);

        // Adding testMeetingReservation to the main list of meeting's reservation
        mActivity.meetingApiService.addMeeting(testMeetingReservation);
    }

    @Test
    public void detailMeeting_checkHall_showMeetingHall() {
        // Click on the first and only
        onView(withId(R.id.list_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check Meeting detail's subject
        onView(withId(R.id.detail_meeting_hall)).check(matches(withText(MEETING_HALL)));
    }

    @Test
    public void detailMeeting_checkDate_showMeetingDate() {
        // Click on the first and only
        onView(withId(R.id.list_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check Meeting detail's date (14/02/2020)
        onView(withId(R.id.detail_meeting_schedule_date))
                .check(matches(withText(DateHelper.setDateToString(mActivity.getApplicationContext(), MEETING_DAY, MEETING_MONTH, MEETING_YEAR))));
    }

    @Test
    public void detailMeeting_checkTime_showMeetingTime() {
        // Click on the first and only
        onView(withId(R.id.list_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check Meeting detail's time
        onView(withId(R.id.detail_meeting_schedule_time))
                .check(matches(withText(DateHelper.setTimetoString(mActivity.getApplicationContext(),MEETING_HOUR, MEETING_MINUTE))));
    }

    @Test
    public void detailMeeting_checkSubject_showMeetingSubject() {
        // Click on the first and only
        onView(withId(R.id.list_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check Meeting detail's subject
        onView(withId(R.id.detail_meeting_subject))
                .check(matches(withText(MEETING_SUBJECT)));
    }

    @Test
    public void  detailMeeting_checkParticipant_showMeetingParticipant() {
        // Click on the first and only
        onView(withId(R.id.list_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check Meeting detail's participant
        onView(withId(R.id.detail_meeting_participants))
                .check(matches(withText(MEETING_PARTICIPANT)));
    }
}
