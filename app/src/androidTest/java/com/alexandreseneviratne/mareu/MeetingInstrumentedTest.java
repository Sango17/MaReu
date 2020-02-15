package com.alexandreseneviratne.mareu;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.model.Time;
import com.alexandreseneviratne.mareu.ui.MainActivity;
import com.alexandreseneviratne.mareu.utils.DeleteParticipantViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Created by Alexandre SENEVIRATNE on 2/9/2020.
 */

@RunWith(AndroidJUnit4.class)
public class MeetingInstrumentedTest {
    private MainActivity mActivity;

    private static String SUBJECT_TEST_MEETING_1 = "InstrumentedTest1";

    private static String PARTICIPANT_1 = "instrumented1@test.com";
    private static String PARTICIPANT_2 = "instrumented2@test.com";
    private static String PARTICIPANT_3 = "instrumented3@test.com";
    private static String PARTICIPANT_ERROR_1 = "instrumentedtest.com";
    private static String PARTICIPANT_ERROR_2 = "@test.com";
    private static String PARTICIPANT_ERROR_3 = "instrumented@";
    private static String PARTICIPANT_ERROR_4 = "instrumented@.com";

    private Date dateTestMeeting1 = new Date(14, 2, 2020);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        mActivity = mActivityRule.getActivity();
        ViewMatchers.assertThat(mActivity, notNullValue());

        ArrayList<String> participant = new ArrayList<>();
        participant.add("alexandre@gmail.com");

        mActivity.meetingApiService.addMeeting(new Meeting("Hello", "Mario", new Date(dateTestMeeting1.getDay(), dateTestMeeting1.getMonth(), dateTestMeeting1.getYear()), new Time(9, 30), participant));
    }

    @Test
    public void addFragment_addAction_alertNoInputAndDateAdded() {
        // Click on the FAB (create meeting button) in order to get the Adding View
        onView(withId(R.id.fab_add)).perform(click());

        // Click on the create meeting reservation button
        onView(withId(R.id.add_meeting_button)).perform(click());

        // Alert Toast - No input
        onView(withText(R.string.warning_meeting_empty)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void addFragment_addAction_alertNoTimeAdded() {
        // Click on the FAB (create meeting button) in order to get the Adding View
        onView(withId(R.id.fab_add)).perform(click());

        // Select the meeting hall Mario
        onView(withId(R.id.add_meeting_hall)).perform(click());
        onView(withText("Mario")).perform(click());

        // Select date of meeting
        onView(withId(R.id.add_meeting_schedule_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(dateTestMeeting1.getYear(), dateTestMeeting1.getMonth(), dateTestMeeting1.getDay()));
        onView(withText("OK")).perform(click());

        // Click on the create meeting reservation button
        onView(withId(R.id.add_meeting_button)).perform(click());

        // Alert Toast - Wrong time
        onView(withText(R.string.warning_meeting_opening)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void addFragment_addAction_alertWrongTimeAdded() {
        // Click on the FAB (create meeting button) in order to get the Adding View
        onView(withId(R.id.fab_add)).perform(click());

        // Select the meeting hall Mario
        onView(ViewMatchers.withId(R.id.add_meeting_hall)).perform(click());
        onView(withText("Mario")).perform(click());

        // Select date of meeting
        onView(withId(R.id.add_meeting_schedule_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(dateTestMeeting1.getYear(), dateTestMeeting1.getMonth(), dateTestMeeting1.getDay()));
        onView(withText("OK")).perform(click());

        // Select time of meeting - at 7h00
        onView(withId(R.id.add_meeting_schedule_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(7, 0));
        onView(withText("OK")).perform(click());

        // Click on the create meeting reservation button
        onView(withId(R.id.add_meeting_button)).perform(click());

        onView(withId(R.id.add_meeting_subject)).perform(typeText(SUBJECT_TEST_MEETING_1));
        Espresso.closeSoftKeyboard();

        // Add one participant
        onView(withId(R.id.add_meeting_participants)).perform(typeText(PARTICIPANT_1));
        Espresso.closeSoftKeyboard();
        // Confirming by click on the + button
        onView(withId(R.id.add_meeting_participants_button)).perform(click());

        // Click on the create meeting reservation button
        onView(withId(R.id.add_meeting_button)).perform(click());

        // Alert Toast - Wrong time
        onView(withText(R.string.warning_meeting_opening)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void addFragment_addAction_alertNoSubjectAdded() {
        // Click on the FAB (create meeting button) in order to get the Adding View
        onView(withId(R.id.fab_add)).perform(click());

        // Select the meeting hall Mario
        onView(ViewMatchers.withId(R.id.add_meeting_hall)).perform(click());
        onView(withText("Mario")).perform(click());

        // Select date of meeting
        onView(withId(R.id.add_meeting_schedule_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(dateTestMeeting1.getYear(), dateTestMeeting1.getMonth(), dateTestMeeting1.getDay()));
        onView(withText("OK")).perform(click());

        // Select time of meeting - at 7h00
        onView(withId(R.id.add_meeting_schedule_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(8, 0));
        onView(withText("OK")).perform(click());

        // Click on the create meeting reservation button
        onView(withId(R.id.add_meeting_button)).perform(click());

        // Alert Toast - Wrong time
        onView(withText(R.string.warning_subject_participants)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void addFragment_addAction_alertNoParticipantAdded() {
        // Click on the FAB (create meeting button) in order to get the Adding View
        onView(withId(R.id.fab_add)).perform(click());

        // Select the meeting hall Mario
        onView(ViewMatchers.withId(R.id.add_meeting_hall)).perform(click());
        onView(withText("Mario")).perform(click());

        // Select date of meeting
        onView(withId(R.id.add_meeting_schedule_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(dateTestMeeting1.getYear(), dateTestMeeting1.getMonth(), dateTestMeeting1.getDay()));
        onView(withText("OK")).perform(click());

        // Select time of meeting - at 7h00
        onView(withId(R.id.add_meeting_schedule_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(8, 0));
        onView(withText("OK")).perform(click());

        // Add a topic
        onView(withId(R.id.add_meeting_subject)).perform(typeText(SUBJECT_TEST_MEETING_1));
        Espresso.closeSoftKeyboard();

        // Click on the create meeting reservation button
        onView(withId(R.id.add_meeting_button)).perform(click());

        // Alert Toast - Wrong time
        onView(withText(R.string.warning_subject_participants)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void addFragment_addAction_AddAndSuppressParticipant() {
        // Click on the FAB (create meeting button) in order to get the Adding View
        onView(withId(R.id.fab_add)).perform(click());

        // Add one participant
        onView(withId(R.id.add_meeting_participants)).perform(typeText(PARTICIPANT_1));
        Espresso.closeSoftKeyboard();
        // Confirming by click on the + button
        onView(withId(R.id.add_meeting_participants_button)).perform(click());

        // Add one participant
        onView(withId(R.id.add_meeting_participants)).perform(typeText(PARTICIPANT_2));
        Espresso.closeSoftKeyboard();
        // Confirming by click on the + button
        onView(withId(R.id.add_meeting_participants_button)).perform(click());

        // Add one participant
        onView(withId(R.id.add_meeting_participants)).perform(typeText(PARTICIPANT_3));
        Espresso.closeSoftKeyboard();
        // Confirming by click on the + button
        onView(withId(R.id.add_meeting_participants_button)).perform(click());

        // Checking if there are 3 participants
        onView(withId(R.id.add_meeting_participants_recycler_view)).check(matches(hasMinimumChildCount(3)));

        // Suppress one participant
        onView(withId(R.id.add_meeting_participants_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteParticipantViewAction()));
        // Checking if there are still 2 participants
        onView(withId(R.id.add_meeting_participants_recycler_view)).check(matches(hasMinimumChildCount(2)));
    }

    @Test
    public void addFragment_addAction_checkParticipantValidity() {
        // Click on the FAB (create meeting button) in order to get the Adding View
        onView(withId(R.id.fab_add)).perform(click());

        // Add one participant
        onView(withId(R.id.add_meeting_participants)).perform(typeText(PARTICIPANT_ERROR_1));
        Espresso.closeSoftKeyboard();
        // Confirming by click on the + button
        onView(withId(R.id.add_meeting_participants_button)).perform(click());
        // Alert Toast - Not valid email
        onView(withText(R.string.warning_not_email)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));

        // Add one participant
        onView(withId(R.id.add_meeting_participants)).perform(typeText(PARTICIPANT_ERROR_2));
        Espresso.closeSoftKeyboard();
        // Confirming by click on the + button
        onView(withId(R.id.add_meeting_participants_button)).perform(click());
        // Alert Toast - Not valid email
        onView(withText(R.string.warning_not_email)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));

        // Add one participant
        onView(withId(R.id.add_meeting_participants)).perform(typeText(PARTICIPANT_ERROR_3));
        Espresso.closeSoftKeyboard();
        // Confirming by click on the + button
        onView(withId(R.id.add_meeting_participants_button)).perform(click());
        // Alert Toast - Not valid email
        onView(withText(R.string.warning_not_email)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));

        // Add one participant
        onView(withId(R.id.add_meeting_participants)).perform(typeText(PARTICIPANT_ERROR_4));
        Espresso.closeSoftKeyboard();
        // Confirming by click on the + button
        onView(withId(R.id.add_meeting_participants_button)).perform(click());
        // Alert Toast - Not valid email
        onView(withText(R.string.warning_not_email)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void addFragment_addAction_shouldNotAddMeetingAtSameTimeToTheList() {
        // Click on the FAB (create meeting button) in order to get the Adding View
        onView(withId(R.id.fab_add)).perform(click());

        // Select the meeting hall Mario
        onView(withId(R.id.add_meeting_hall)).perform(click());
        onView(withText("Mario")).perform(click());

        // Select date of meeting
        onView(withId(R.id.add_meeting_schedule_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(dateTestMeeting1.getYear(), dateTestMeeting1.getMonth(), dateTestMeeting1.getDay()));
        onView(withText("OK")).perform(click());

        // Select time of meeting - at 9h00
        onView(withId(R.id.add_meeting_schedule_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(9, 30));
        onView(withText("OK")).perform(click());

        // Add a topic
        onView(withId(R.id.add_meeting_subject)).perform(typeText(SUBJECT_TEST_MEETING_1));
        Espresso.closeSoftKeyboard();

        // Add one participant
        onView(withId(R.id.add_meeting_participants)).perform(typeText(PARTICIPANT_1));
        Espresso.closeSoftKeyboard();
        // Confirming by click on the + button
        onView(withId(R.id.add_meeting_participants_button)).perform(click());

        // Click on the create meeting reservation button
        onView(withId(R.id.add_meeting_button)).perform(click());

        // Alert Toast - Already reserved
        onView(withText(R.string.warning_hall_not_free)).inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));

        // Select time of meeting - at 8h00
        onView(withId(R.id.add_meeting_schedule_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(9, 0));
        onView(withText("OK")).perform(click());

        // Click on the create meeting reservation button
        onView(withId(R.id.add_meeting_button)).perform(click());

        // Check if the meeting is added to the meeting reservation list
        onView(withId(R.id.list_recycler_view)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void addFragment_addAction_shouldAddMeetingToTheList() {
        // Click on the FAB (create meeting button) in order to get the Adding View
        onView(withId(R.id.fab_add)).perform(click());

        // Select the meeting hall Mario
        onView(withId(R.id.add_meeting_hall)).perform(click());
        onView(withText("Mario")).perform(click());

        // Select date of meeting
        onView(withId(R.id.add_meeting_schedule_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(dateTestMeeting1.getYear(), dateTestMeeting1.getMonth(), dateTestMeeting1.getDay()));
        onView(withText("OK")).perform(click());

        // Select time of meeting - at 8h00
        onView(withId(R.id.add_meeting_schedule_time)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(8, 0));
        onView(withText("OK")).perform(click());

        // Add a topic
        onView(withId(R.id.add_meeting_subject)).perform(typeText(SUBJECT_TEST_MEETING_1));
        Espresso.closeSoftKeyboard();

        // Add one participant
        onView(withId(R.id.add_meeting_participants)).perform(typeText(PARTICIPANT_1));
        Espresso.closeSoftKeyboard();
        // Confirming by click on the + button
        onView(withId(R.id.add_meeting_participants_button)).perform(click());

        // Click on the create meeting reservation button
        onView(withId(R.id.add_meeting_button)).perform(click());

        // Check if the meeting is added to the meeting reservation list
        onView(withId(R.id.list_recycler_view)).check(matches(hasMinimumChildCount(2)));
    }
}
