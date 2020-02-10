package com.alexandreseneviratne.mareu;

import android.os.Handler;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.alexandreseneviratne.mareu.ui.MainActivity;
import com.alexandreseneviratne.mareu.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static com.alexandreseneviratne.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;


/**
 * Created by Alexandre SENEVIRATNE on 2/9/2020.
 */

@RunWith(AndroidJUnit4.class)
public class AddMeetingInstrumentedTest {
    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        mActivity = mActivityRule.getActivity();
        ViewMatchers.assertThat(mActivity, notNullValue());
    }

    @Test
    public void addFragment_addAction_shouldAddMeetingToTheList() {
        // Click on the FAB (create meeting button) in order to get the Adding View
        Espresso.onView(ViewMatchers.withId(R.id.fab_add)).perform(ViewActions.click());

        // Select the meeting hall
        Espresso.onView(ViewMatchers.withId(R.id.add_meeting_hall)).perform(ViewActions.click());
        Espresso.onView(withText("Mario")).perform(click());

        // Select date of meeting
        Espresso.onView(withId(R.id.add_meeting_schedule_date)).perform(click());
        Espresso.onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 2, 16));
        Espresso.onView(withText("OK")).perform(click());

        // Select time of meeting
        Espresso.onView(withId(R.id.add_meeting_schedule_time)).perform(click());
        Espresso.onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(9, 30));
        Espresso.onView(withText("OK")).perform(click());

        // Add meeting's topic
        Espresso.onView(withId(R.id.add_meeting_subject)).perform(typeText("InstrumentedTest"));
        Espresso.closeSoftKeyboard();

        // Add meeting's participant
        Espresso.onView(withId(R.id.add_meeting_participants)).perform(typeText("instrumented@test.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.add_meeting_participants_button)).perform(click());

        // Click on the create button
        Espresso.onView(withId(R.id.add_meeting_button)).perform(click());

        // If the meeting is added to the list

        Espresso.onView(allOf(ViewMatchers.withId(R.id.list_recycler_view), isDisplayed())).check(matches(hasMinimumChildCount(1)));

    }
}
