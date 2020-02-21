package com.alexandreseneviratne.mareu;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.model.Time;
import com.alexandreseneviratne.mareu.ui.MainActivity;
import com.alexandreseneviratne.mareu.utils.DeleteItemViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;

import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Alexandre SENEVIRATNE on 2/16/2020.
 */

@RunWith(AndroidJUnit4.class)
public class listMeetingInstrumentedTest {
    private MainActivity mActivity;

    private Meeting testMeetingReservation1;
    private Meeting testMeetingReservation2;
    private Meeting testMeetingReservation3;
    private Meeting testMeetingReservation4;

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
        testMeetingReservation1 = new Meeting(
                "Hello",
                "Mario",
                new Date(
                        14,
                        2,
                        2020
                ),
                new Time(9, 30), participant);

        // Creating a test Meeting reservation
        testMeetingReservation2 = new Meeting(
                "Hello",
                "Peach",
                new Date(
                        14,
                        2,
                        2020
                ),
                new Time(10, 30), participant);

        // Creating a test Meeting reservation
        testMeetingReservation3 = new Meeting(
                "Hello",
                "Mario",
                new Date(
                        20,
                        2,
                        2020
                ),
                new Time(10, 30), participant);

        // Creating a test Meeting reservation
        testMeetingReservation4 = new Meeting(
                "Hello",
                "Peach",
                new Date(
                        20,
                        2,
                        2020
                ),
                new Time(9, 30), participant);

        // Clean the list of meeting for each test
        mActivity.meetingApiService.getMeetings().clear();
        mActivity.toList();
    }

    @Test
    public void listFragment_showList_showMeeting() {
        // Adding testMeetingReservations to the main list of meeting's reservation
        mActivity.meetingApiService.addMeeting(testMeetingReservation1);
        mActivity.meetingApiService.addMeeting(testMeetingReservation2);
        mActivity.meetingApiService.addMeeting(testMeetingReservation3);
        mActivity.meetingApiService.addMeeting(testMeetingReservation4);

        // Check if there are 4 meeting reservations in the list
        onView(withId(R.id.list_recycler_view)).check(matches(hasMinimumChildCount(4)));
    }

    @Test
    public void listFragment_filterAction_showMeetingFilteredByDate() {
        // Adding testMeetingReservations to the main list of meeting's reservation
        mActivity.meetingApiService.addMeeting(testMeetingReservation1);
        mActivity.meetingApiService.addMeeting(testMeetingReservation2);
        mActivity.meetingApiService.addMeeting(testMeetingReservation3);
        mActivity.meetingApiService.addMeeting(testMeetingReservation4);

        // Check if there are 4 meeting reservations in the list
        onView(withId(R.id.list_recycler_view)).check(matches(hasMinimumChildCount(4)));

        // Click on filter button
        onView(withId(R.id.navigate_filter)).perform(click());

        // Click on "by date" button
        onView(withId(R.id.dialog_filter_schedule)).perform(click());

        // Select date (14/02/2020)
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 2, 14));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("OK")).perform(click());

        // Select period of time (9h00 - 10h00)
        onView(withId(R.id.dialog_filter_spinner)).perform(click());
        //onView(withText("9h00 – 10h00")).perform(click());
        onView(withText("9h00 – 10h00")).inRoot(isPlatformPopup()).perform(click());

        // Click to validate
        onView(withId(R.id.dialog_filter_accept)).perform(click());

        // Check count of filtered list - only one is remaining at 9h30 on 14/02/2020
        onView(withId(R.id.list_recycler_view)).check(matches(hasMinimumChildCount(1)));
    }


    @Test
    public void listFragment_filterAction_showMeetingFilteredByHall() {
        // Adding testMeetingReservations to the main list of meeting's reservation
        mActivity.meetingApiService.addMeeting(testMeetingReservation1);
        mActivity.meetingApiService.addMeeting(testMeetingReservation2);
        mActivity.meetingApiService.addMeeting(testMeetingReservation3);
        mActivity.meetingApiService.addMeeting(testMeetingReservation4);

        // Check if there are 4 meeting reservations in the list
        onView(withId(R.id.list_recycler_view)).check(matches(hasMinimumChildCount(4)));

        // Click on filter button
        onView(withId(R.id.navigate_filter)).perform(click());

        // Click on "by hall" button
        onView(withId(R.id.dialog_filter_hall)).perform(click());

        // Select hall - Peach
        onView(withId(R.id.dialog_filter_spinner)).perform(click());
        //onView(withText("Peach")).perform(click());
        onView(withText("Peach")).inRoot(isPlatformPopup()).perform(click());

        // Click to validate
        onView(withId(R.id.dialog_filter_accept)).perform(click());

        // Check count of filtered list - only two are remaining at Peach hall
        onView(withId(R.id.list_recycler_view)).check(matches(hasMinimumChildCount(2)));
    }

    @Test
    public void listFragment_deleteAction_deleteMeetingFromList() {
        // Adding testMeetingReservations to the main list of meeting's reservation
        mActivity.meetingApiService.addMeeting(testMeetingReservation1);
        mActivity.meetingApiService.addMeeting(testMeetingReservation2);
        mActivity.meetingApiService.addMeeting(testMeetingReservation3);
        mActivity.meetingApiService.addMeeting(testMeetingReservation4);

        // Check if there are 4 meeting reservations in the list
        onView(withId(R.id.list_recycler_view)).check(matches(hasMinimumChildCount(4)));

        // Delete the first meeting of the list
        onView(withId(R.id.list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteItemViewAction()));

        // Check if there are 3 meeting reservations remaining in the list
        onView(withId(R.id.list_recycler_view)).check(matches(hasMinimumChildCount(3)));
    }
}
