package com.alexandreseneviratne.mareu;

import android.content.Context;

import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.model.Time;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandre SENEVIRATNE on 1/19/2020.
 */
public class Utils {
    public static String setMeetingListTitle(Context context, String subject, String schedule, String hall) {
        return context.getResources().getString(R.string.item_list_title, subject, schedule, hall);
    }

    public static String setTimetoString(Context context, int hour, int minute) {
        return context.getResources().getString(R.string.time_format, String.valueOf(hour), minutesInString(context,minute));
    }

    public static String setFilterDialogTitle(Context context, String filterType) {
        return context.getResources().getString(R.string.dialog_filter_title, filterType);
    }

    public static Boolean checkHallAvailability(String hall, Time selectedTime, List<Meeting> meetings) {
        if (!meetings.isEmpty()) {
            for (Meeting meeting : meetings) {
                if (hall.equals(meeting.getHall()) && compareSchedule(selectedTime, meeting.getScheduleTime())) {
                    return true;
                } else if (!hall.equals(meeting.getHall())) {
                    return true;
                }
            }

            return false;
        }

        return true;
    }

    private static Boolean compareSchedule(Time selectedTime, Time storedTime) {
        int selectedTimeInMinutes;
        int storedTimeInMinutes;

        selectedTimeInMinutes = (selectedTime.getHours() * 60) + selectedTime.getMinutes();
        storedTimeInMinutes = (storedTime.getHours() * 60) + storedTime.getMinutes() + 45;

        return selectedTimeInMinutes > storedTimeInMinutes;
    }

    public static Boolean isOnOpeningHours(Time selectedTime) {
        int selectedTimeInMinutes = (selectedTime.getHours()*60) + selectedTime.getMinutes();

        return selectedTimeInMinutes >= COMPANY_OPENING_HOUR && selectedTimeInMinutes <= COMPANY_CLOSING_HOUR;
    }

    private static String minutesInString(Context context, int minute) {
        if (minute >= 0 && minute < 10) {
            return context.getResources().getString(R.string.minute_format, minute);
        }
        return String.valueOf(minute);
    }

    public static ArrayList<Meeting> getFilteredList(String filterType, int selectedItemPosition, List<Meeting> meetings) {
        ArrayList<Meeting> filteredMeetings = new ArrayList<>();

        if (filterType.equals(FILTER_TYPE_HALL)){
            for (Meeting meeting : meetings) {
                if (meeting.getHall().equals(getHallFilter(selectedItemPosition))) {
                    filteredMeetings.add(meeting);
                }
            }
        } else if (filterType.equals(FILTER_TYPE_HOUR)) {
            for (Meeting meeting : meetings) {
                if (meeting.getScheduleTime().getHours() == getHourFilter(selectedItemPosition)) {
                    filteredMeetings.add(meeting);
                }
            }
        }

        return filteredMeetings;
    }

    public static String getHallFilter(int selectedItemPosition) {
        String filter = "";

        switch(selectedItemPosition) {
            case 0: filter = "Mario";
            break;
            case 1: filter = "Peach";
                break;
            case 2: filter = "Luigi";
                break;
            case 3: filter = "Toad";
                break;
            case 4: filter = "Bowser";
                break;
            case 5: filter = "Wario";
                break;
            case 6: filter = "Daisy";
                break;
            case 7: filter = "Yoshi";
                break;
            case 8: filter = "Donkey";
                break;
            case 9: filter = "Diddy";
                break;
        }

        return filter;
    }

    public static int getHourFilter(int selectedItemPosition) {
        int filter = -1;

        switch(selectedItemPosition) {
            case 0: filter = 8;
                break;
            case 1: filter = 9;
                break;
            case 2: filter = 10;
                break;
            case 3: filter = 11;
                break;
            case 4: filter = 12;
                break;
            case 5: filter = 13;
                break;
            case 6: filter = 14;
                break;
            case 7: filter = 15;
                break;
            case 8: filter = 16;
                break;
            case 9: filter = 17;
                break;
            case 10: filter = 18;
                break;
            case 11: filter = 19;
                break;
        }

        return filter;
    }


    // FILTER TYPE
    public static final String FILTER_TYPE_HOUR = "heure";
    public static final String FILTER_TYPE_HALL = "salle";
    // 8h00
    private static final int COMPANY_OPENING_HOUR = 480;
    // 19h15 because 15h15 + 45min = 20h00
    private static final int COMPANY_CLOSING_HOUR = 1155;
}
