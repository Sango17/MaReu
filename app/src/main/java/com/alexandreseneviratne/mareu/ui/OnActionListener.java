package com.alexandreseneviratne.mareu.ui;

import com.alexandreseneviratne.mareu.model.Meeting;

/**
 * Created by Alexandre SENEVIRATNE on 1/22/2020.
 */
public interface OnActionListener {
    void toDetail(Meeting selectedMeeting);
    void toDelete(Meeting selectedMeeting);
}
