package com.alexandreseneviratne.mareu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.utils.DateHelper;
import com.alexandreseneviratne.mareu.utils.StringHelper;
import com.alexandreseneviratne.mareu.ui.MainActivity;

/**
 * Created by Alexandre SENEVIRATNE on 1/22/2020.
 */
public class DetailFragment extends Fragment {
    private MainActivity mainActivity;

    private Toolbar toolbar;
    private ImageView toolBarBack;

    private Meeting meetingDetail;

    private LinearLayout detailBlock;
    private TextView detailHall;
    private TextView detailScheduleDate;
    private TextView detailScheduleTime;
    private TextView detailSubject;
    private TextView detailParticipants;
    private TextView detailInfo;

    private Boolean isDetailAvailable = false;


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

    /**
     * Set DetailFragment's toolbar
     *
     * @param view DetailFragment's view
     */
    private void setToolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolBarBack = view.findViewById(R.id.navigate_up);
        if (mainActivity.mIsDualPane) {
            toolBarBack.setVisibility(View.GONE);
        }

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

    /**
     * Display meeting details in the view
     *
     * @param view DetailFragment's view
     */
    private void setView(View view) {
        detailHall = view.findViewById(R.id.detail_meeting_hall);
        detailScheduleDate = view.findViewById(R.id.detail_meeting_schedule_date);
        detailScheduleTime = view.findViewById(R.id.detail_meeting_schedule_time);
        detailSubject = view.findViewById(R.id.detail_meeting_subject);
        detailParticipants = view.findViewById(R.id.detail_meeting_participants);
        detailBlock = view.findViewById(R.id.detail_meeting_block);
        detailInfo = view.findViewById(R.id.detail_meeting_info);

        if (meetingDetail != null && isDetailAvailable) {
            detailBlock.setVisibility(View.VISIBLE);
            detailHall.setText(meetingDetail.getHall());
            detailScheduleDate.setText(
                    DateHelper.setDateToString(
                            getContext(),
                            meetingDetail.getScheduleDate().getDay(),
                            meetingDetail.getScheduleDate().getMonth(),
                            meetingDetail.getScheduleDate().getYear()
                    )
            );
            detailScheduleTime.setText(
                    DateHelper.setTimetoString(
                            getContext(),
                            meetingDetail.getScheduleTime().getHours(),
                            meetingDetail.getScheduleTime().getMinutes()
                    )
            );
            detailSubject.setText(meetingDetail.getSubject());
            detailParticipants.setText(StringHelper.getParticipantsDetail(meetingDetail.getParticipants()));

            detailInfo.setVisibility(View.GONE);
        } else {
            detailBlock.setVisibility(View.GONE);
            detailInfo.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Set the selected meeting
     *
     * @param selectedMeeting
     */
    public void setMeetingDetail(@Nullable Meeting selectedMeeting) {
        if (selectedMeeting != null) {
            meetingDetail = selectedMeeting;
            isDetailAvailable = true;
        }
    }
}
