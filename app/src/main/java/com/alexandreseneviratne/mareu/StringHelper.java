package com.alexandreseneviratne.mareu;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alexandre SENEVIRATNE on 1/19/2020.
 */
public class StringHelper {
    public static String setMeetingListTitle(Context context, String subject, String schedule, String hall) {
        return context.getResources().getString(R.string.item_list_title, subject, schedule, hall);
    }

    public static String setMeetingListParticipantsList(ArrayList<String> participants) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < participants.size(); i++) {
            if (i == participants.size()-1) {
                stringBuilder.append(participants.get(i));
            } else {
                stringBuilder.append(participants.get(i) + ", ");
            }
        }

        return stringBuilder.toString();
    }
}
