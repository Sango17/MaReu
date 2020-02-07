package com.alexandreseneviratne.mareu.utils;

import android.content.Context;

import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.model.Date;

import static com.alexandreseneviratne.mareu.utils.StringHelper.numbersInString;

/**
 * Created by Alexandre SENEVIRATNE on 2/7/2020.
 */
public class DateHelper {
    /**
     * Get String from a date (ex: 01/01/2020)
     *
     * @param context
     * @param day     of meeting
     * @param month   of meeting
     * @param year    of meeting
     * @return String
     */
    public static String setDateToString(Context context, int day, int month, int year) {
        return context.getResources().getString(R.string.date_format, numbersInString(context, day), numbersInString(context, month), String.valueOf(year));
    }

    /**
     * Get String from a time (ex: 13h37)
     *
     * @param context
     * @param hour    of meeting
     * @param minute  of meeting
     * @return String
     */
    public static String setTimetoString(Context context, int hour, int minute) {
        return context.getResources().getString(R.string.time_format, String.valueOf(hour), numbersInString(context, minute));
    }

    /**
     * Check if initialDate and selectedDate are equal
     *
     * @param initialDate
     * @param selectedDate
     * @return Boolean true if initialDate and selectedDate are equal
     */
    public static Boolean compareDate(Date initialDate, Date selectedDate) {
        return initialDate.getDay() == selectedDate.getDay()
                && initialDate.getMonth() == selectedDate.getMonth()
                && initialDate.getYear() == selectedDate.getYear();
    }
}
