package com.alexandreseneviratne.mareu.utils;

import android.content.Context;

import androidx.annotation.Nullable;

import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;

import static com.alexandreseneviratne.mareu.utils.DateHelper.compareDate;

/**
 * Created by Alexandre SENEVIRATNE on 2/7/2020.
 */
public class FilterHelper {
    // FILTER TYPE
    public static final String FILTER_TYPE_DATE = "date";
    public static final String FILTER_TYPE_HALL = "salle";

    /**
     * Get titles for buttons of {@link com.alexandreseneviratne.mareu.ui.dialog.MainFilterDialog}
     *
     * @param context
     * @param filterType (ex: FILTER_TYPE_DATE or FILTER_TYPE_HALL)
     * @return String
     */
    public static String setFilterDialogTitle(Context context, String filterType) {
        return context.getResources().getString(R.string.dialog_filter_title, filterType);
    }

    /**
     * Get filtered list of meetings according to the selected filterType
     *
     * @param filterType           (ex: FILTER_TYPE_DATE or FILTER_TYPE_HALL)
     * @param selectedItemPosition spinner position
     * @param meetings             whole list of meetings
     * @param selectedDate
     * @return ArrayList<Meeting> of filtered meetings
     */
    public static ArrayList<Meeting> getFilteredList(
            String filterType,
            int selectedItemPosition,
            List<Meeting> meetings,
            @Nullable Date selectedDate)
    {
        ArrayList<Meeting> filteredMeetings = new ArrayList<>();
        if (filterType.equals(FILTER_TYPE_HALL)) {
            for (Meeting meeting : meetings) {
                if (meeting.getHall().equals(getHallFilter(selectedItemPosition))) {
                    filteredMeetings.add(meeting);
                }
            }
        } else if (filterType.equals(FILTER_TYPE_DATE)) {
            for (Meeting meeting : meetings) {
                if (selectedDate != null) {
                    if (meeting.getScheduleTime().getHours() == getHourFilter(selectedItemPosition)
                            && compareDate(meeting.getScheduleDate(), selectedDate)) {
                        filteredMeetings.add(meeting);
                    }
                }
            }
        }

        return filteredMeetings;
    }

    /**
     * Get the meeting hall string from the selectedItemPosition
     *
     * @param selectedItemPosition of spinner
     * @return String meeting hall
     */
    public static String getHallFilter(int selectedItemPosition) {
        String filter = "";

        switch (selectedItemPosition) {
            case 0:
                filter = "Mario";
                break;
            case 1:
                filter = "Peach";
                break;
            case 2:
                filter = "Luigi";
                break;
            case 3:
                filter = "Toad";
                break;
            case 4:
                filter = "Bowser";
                break;
            case 5:
                filter = "Wario";
                break;
            case 6:
                filter = "Daisy";
                break;
            case 7:
                filter = "Yoshi";
                break;
            case 8:
                filter = "Donkey";
                break;
            case 9:
                filter = "Diddy";
                break;
        }

        return filter;
    }

    /**
     * Get the start of the range of period of meeting from the selectedItemPosition
     *
     * @param selectedItemPosition of spinner
     * @return int
     */
    private static int getHourFilter(int selectedItemPosition) {
        int filter = -1;

        switch (selectedItemPosition) {
            case 0:
                filter = 8;
                break;
            case 1:
                filter = 9;
                break;
            case 2:
                filter = 10;
                break;
            case 3:
                filter = 11;
                break;
            case 4:
                filter = 12;
                break;
            case 5:
                filter = 13;
                break;
            case 6:
                filter = 14;
                break;
            case 7:
                filter = 15;
                break;
            case 8:
                filter = 16;
                break;
            case 9:
                filter = 17;
                break;
            case 10:
                filter = 18;
                break;
            case 11:
                filter = 19;
                break;
        }

        return filter;
    }
}
