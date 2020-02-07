package com.alexandreseneviratne.mareu.utils;

import android.content.Context;

import com.alexandreseneviratne.mareu.R;

import java.util.ArrayList;

/**
 * Created by Alexandre SENEVIRATNE on 2/7/2020.
 */
public class StringHelper {
    /**
     * Get String for list item's title in item_list_meeting
     *
     * @param context
     * @param subject  of the meeting
     * @param schedule of the meeting
     * @param hall     where the meeting is set
     * @return String
     */
    public static String setMeetingListTitle(Context context, String subject, String schedule, String hall) {
        return context.getResources().getString(R.string.item_list_title, subject, schedule, hall);
    }

    /**
     * Reformat days and minutes (ex: 01 instead of 1)
     *
     * @param context
     * @param number
     * @return String
     */
    public static String numbersInString(Context context, int number) {
        if (number >= 0 && number < 10) {
            return context.getResources().getString(R.string.with_zero_format, number);
        }
        return String.valueOf(number);
    }

    /**
     * Get a list of participant in a simple line of String
     *
     * @param participantList
     * @return String (ex: alexandre@gmail, amanda@gmail.com, katiana@gmail.com, eric@gmail.com)
     */
    public static String getParticipantsDetail(ArrayList<String> participantList) {
        StringBuilder sb = new StringBuilder();
        String participantsDetail = "";
        int index;

        for (index = 0; index < participantList.size(); index++) {
            if (index == participantList.size() - 1) {
                String participant = participantList.get(index);
                sb.append(participant);
            } else {
                String participant = participantList.get(index);
                sb.append(participant + ", ");
            }
        }
        participantsDetail = sb.toString();

        return participantsDetail;
    }
}
