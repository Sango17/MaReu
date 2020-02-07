package com.alexandreseneviratne.mareu.utils;

import android.content.Context;
import android.widget.Toast;

import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.model.Time;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexandre SENEVIRATNE on 1/19/2020.
 */
public class Utils {
    /**
     * Check if the selected meeting hall is available
     *
     * @param hall         selected meeting hall
     * @param selectedDate in order to check if its available at that date
     * @param selectedTime in order to check if its available at that time
     * @param meetings     List of meeting that will be checked
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
     * @param storedTime   in order to get the date of the stored meeting
     * @param storedDate   in order to get the date of the stored meeting
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
     * Check if the String is an email address
     *
     * @param emailAddress which is checked
     * @return Boolean
     */
    public static Boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public static void notifyMessage(Context context, int messageStringId) {
        Toast.makeText(context, context.getResources().getString(messageStringId), Toast.LENGTH_SHORT).show();
    }

    // 8h00
    private static final int COMPANY_OPENING_HOUR = 480;
    // 19h15 because 15h15 + 45min = 20h00
    private static final int COMPANY_CLOSING_HOUR = 1155;
}
