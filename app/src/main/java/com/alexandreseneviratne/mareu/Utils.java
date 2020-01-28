package com.alexandreseneviratne.mareu;

import android.content.Context;

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

        return selectedTimeInMinutes > COMPANY_OPENING_HOUR && selectedTimeInMinutes <= COMPANY_CLOSING_HOUR;
    }

    private static String minutesInString(Context context, int minute) {
        if (minute >= 0 && minute < 10) {
            return context.getResources().getString(R.string.minute_format, minute);
        }
        return String.valueOf(minute);
    }

    // 8h00
    private static final int COMPANY_OPENING_HOUR = 480;
    // 19h15 because 15h15 + 45min = 20h00
    private static final int COMPANY_CLOSING_HOUR = 1155;
}
