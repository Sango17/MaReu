package com.alexandreseneviratne.mareu;

import android.content.Context;

import androidx.annotation.Nullable;

import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.model.Time;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandre SENEVIRATNE on 1/19/2020.
 */
public class Utils {

    /**
     * Get String for list item's title in item_list_meeting
     *
     * @param context
     * @param subject of the meeting
     * @param schedule of the meeting
     * @param hall where the meeting is set
     * @return String
     */
    public static String setMeetingListTitle(Context context, String subject, String schedule, String hall) {
        return context.getResources().getString(R.string.item_list_title, subject, schedule, hall);
    }

    /**
     * Get String from a date (ex: 01/01/2020)
     *
     * @param context
     * @param day of meeting
     * @param month of meeting
     * @param year of meeting
     * @return String
     */
    public static String setDateToString(Context context, int day, int month, int year) {
        return context.getResources().getString(R.string.date_format, numbersInString(context, day), numbersInString(context, month), String.valueOf(year));
    }

    /**
     * Get String from a time (ex: 13h37)
     *
     * @param context
     * @param hour of meeting
     * @param minute of meeting
     * @return String
     */
    public static String setTimetoString(Context context, int hour, int minute) {
        return context.getResources().getString(R.string.time_format, String.valueOf(hour), numbersInString(context, minute));
    }

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
     * Check if the selected meeting hall is available
     *
     * @param hall selected meeting hall
     * @param selectedDate in order to check if its available at that date
     * @param selectedTime in order to check if its available at that time
     * @param meetings List of meeting that will be checked
     * @return Boolean true if its available, false if it's not
     */
    public static Boolean checkHallAvailability(String hall, Date selectedDate, Time selectedTime, List<Meeting> meetings) {
        if (!meetings.isEmpty()) {
            for (Meeting meeting : meetings) {
                if (hall.equals(meeting.getHall()) && isScheduleAvailable(selectedTime, selectedDate, meeting.getScheduleTime(), meeting.getScheduleDate())) {
                    return true;
                } else if (!hall.equals(meeting.getHall())) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Compare the schedule of the selected meeting to a stored meeting
     *
     * @param selectedTime in order to get the time of the selected meeting
     * @param selectedDate in order to get the date of the selected meeting
     * @param storedTime in order to get the date of the stored meeting
     * @param storedDate in order to get the date of the stored meeting
     * @return Boolean true if its available, false if it's not
     */
    private static Boolean isScheduleAvailable(Time selectedTime, Date selectedDate, Time storedTime, Date storedDate) {
        int selectedDateIndex;
        int storedDateIndex;
        int selectedTimeInMinutes;
        int storedTimeInMinutes;

        selectedDateIndex = selectedDate.getYear() + selectedDate.getMonth() + selectedDate.getDay();
        storedDateIndex = storedDate.getYear() + storedDate.getMonth() + storedDate.getDay();
        selectedTimeInMinutes = (selectedTime.getHours() * 60) + selectedTime.getMinutes();
        storedTimeInMinutes = (storedTime.getHours() * 60) + storedTime.getMinutes() + 45;

        if (selectedDateIndex > storedDateIndex) {
            return true;
        } else {
            if (selectedDateIndex == storedDateIndex) {
                return selectedTimeInMinutes > storedTimeInMinutes;
            } else {
                return selectedTimeInMinutes >= storedTimeInMinutes;
            }
        }
    }

    /**
     * Check if the selected meeting is reserved in the opening hours
     *
     * @param selectedTime
     * @return Boolean true if it's available, false if it's not
     */
    public static Boolean isOnOpeningHours(Time selectedTime) {
        int selectedTimeInMinutes = (selectedTime.getHours() * 60) + selectedTime.getMinutes();

        return selectedTimeInMinutes >= COMPANY_OPENING_HOUR && selectedTimeInMinutes <= COMPANY_CLOSING_HOUR;
    }

    /**
     * Reformat days and minutes (ex: 01 instead of 1)
     *
     * @param context
     * @param number
     * @return String
     */
    private static String numbersInString(Context context, int number) {
        if (number >= 0 && number < 10) {
            return context.getResources().getString(R.string.with_zero_format, number);
        }
        return String.valueOf(number);
    }

    /**
     * Get filtered list of meetings according to the selected filterType
     *
     * @param filterType (ex: FILTER_TYPE_DATE or FILTER_TYPE_HALL)
     * @param selectedItemPosition spinner position
     * @param meetings whole list of meetings
     * @param selectedDate
     * @return ArrayList<Meeting> of filtered meetings
     */
    public static ArrayList<Meeting> getFilteredList(String filterType, int selectedItemPosition, List<Meeting> meetings, @Nullable Date selectedDate) {
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
                    if (meeting.getScheduleTime().getHours() == getHourFilter(selectedItemPosition) && compareDate(meeting.getScheduleDate(), selectedDate)) {
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
    private static String getHallFilter(int selectedItemPosition) {
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

    /**
     * Check if initialDate and selectedDate are equal
     *
     * @param initialDate
     * @param selectedDate
     * @return Boolean true if initialDate and selectedDate are equal
     */
    private static Boolean compareDate(Date initialDate, Date selectedDate) {
        return initialDate.getDay() == selectedDate.getDay()
                && initialDate.getMonth() == selectedDate.getMonth()
                && initialDate.getYear() == selectedDate.getYear();
    }


    // FILTER TYPE
    public static final String FILTER_TYPE_DATE = "date";
    public static final String FILTER_TYPE_HALL = "salle";

    // 8h00
    private static final int COMPANY_OPENING_HOUR = 480;
    // 19h15 because 15h15 + 45min = 20h00
    private static final int COMPANY_CLOSING_HOUR = 1155;
}
