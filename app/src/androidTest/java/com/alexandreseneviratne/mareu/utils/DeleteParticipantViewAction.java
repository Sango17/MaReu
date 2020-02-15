package com.alexandreseneviratne.mareu.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.alexandreseneviratne.mareu.R;

import org.hamcrest.Matcher;

/**
 * Created by Alexandre SENEVIRATNE on 2/13/2020.
 */
public class DeleteParticipantViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on a delete button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View deleteButton = view.findViewById(R.id.item_trash);
        deleteButton.performClick();
    }
}
