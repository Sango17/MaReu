package com.alexandreseneviratne.mareu.ui.listener;

import com.alexandreseneviratne.mareu.model.Date;

/**
 * Created by Alexandre SENEVIRATNE on 1/29/2020.
 */
public interface OnFilterListener {
    void setFilteredList(String filterType, int selectedItemPosition, Date selectedDate);
}
