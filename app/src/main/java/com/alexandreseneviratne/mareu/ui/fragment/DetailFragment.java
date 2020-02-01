package com.alexandreseneviratne.mareu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.Utils;
import com.alexandreseneviratne.mareu.ui.MainActivity;

/**
 * Created by Alexandre SENEVIRATNE on 1/22/2020.
 */
public class DetailFragment extends Fragment {
    private MainActivity mainActivity;

    private Toolbar toolbar;
    private ImageView toolBarBack;

    private Meeting meetingDetail;

    private TextView detailHall;
    private TextView detailSchedule;
    private TextView detailSubject;
    private TextView detailParticipants;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        setToolbar(view);
        setView(view);

        return view;
    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolBarBack = (ImageView) view.findViewById(R.id.navigate_up);
        // Get the actionbar
        if (mainActivity != null) {
            mainActivity.setSupportActionBar(toolbar);
            mainActivity.getSupportActionBar().setTitle("");
        }

        toolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.removeFragment();
            }
        });
    }

    private void setView(View view) {
        detailHall = view.findViewById(R.id.detail_meeting_hall);
        detailSchedule = view.findViewById(R.id.detail_meeting_schedule);
        detailSubject = view.findViewById(R.id.detail_meeting_subject);
        detailParticipants = view.findViewById(R.id.detail_meeting_participants);

        detailHall.setText(meetingDetail.getHall());
        detailSchedule.setText(
                Utils.setTimetoString(
                        getContext(),
                        meetingDetail.getScheduleTime().getHours(),
                        meetingDetail.getScheduleTime().getMinutes()
                )
        );
        detailSubject.setText(meetingDetail.getSubject());
        detailParticipants.setText(meetingDetail.getParticipants());
    }


    public void setMeetingDetail(Meeting selectedMeeting) {
        meetingDetail = selectedMeeting;
    }
}
