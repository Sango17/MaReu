package com.alexandreseneviratne.mareu.ui.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.model.Time;
import com.alexandreseneviratne.mareu.utils.DateHelper;
import com.alexandreseneviratne.mareu.utils.Utils;
import com.alexandreseneviratne.mareu.di.DI;
import com.alexandreseneviratne.mareu.service.MeetingApiService;
import com.alexandreseneviratne.mareu.ui.MainActivity;
import com.alexandreseneviratne.mareu.ui.listener.OnParticipantListener;
import com.alexandreseneviratne.mareu.ui.adapter.ParticipantRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alexandre SENEVIRATNE on 1/26/2020.
 */
public class AddFragment extends Fragment
        implements OnParticipantListener {
    private MainActivity mainActivity;
    private MeetingApiService meetingApiService;

    private Toolbar toolbar;
    private ImageView toolBarBack;

    private Spinner meetingHall;
    private TextView meetingScheduleDate;
    private TextView meetingScheduleTime;
    private EditText meetingSubject;

    private EditText meetingParticipants;
    private ImageView meetingAddParticipant;
    private RecyclerView meetingParticipantRecyclerView;
    private ParticipantRecyclerViewAdapter adapter;
    private ArrayList<String> participantList = new ArrayList<>();

    private Button meetingAddButton;

    private Date setScheduleDate;
    private Time setScheduleTime;

    private Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);
    private final int hour = calendar.get(Calendar.HOUR_OF_DAY);
    private final int minute = calendar.get(Calendar.MINUTE);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        meetingApiService = DI.getService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        setToolbar(view);
        setView(view);
        setListeners();

        return view;
    }

    /**
     * Set AddFragment's toolbar
     *
     * @param view AddFragment's view
     */
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

    /**
     * Set AddFragment's view
     *
     * @param view AddFragment's view
     */
    private void setView(View view) {
        meetingHall = (Spinner) view.findViewById(R.id.add_meeting_hall);
        meetingScheduleDate = (TextView) view.findViewById(R.id.add_meeting_schedule_date);
        meetingScheduleTime = (TextView) view.findViewById(R.id.add_meeting_schedule_time);
        meetingSubject = (EditText) view.findViewById(R.id.add_meeting_subject);
        meetingParticipants = (EditText) view.findViewById(R.id.add_meeting_participants);
        meetingAddParticipant = (ImageView) view.findViewById(R.id.add_meeting_participants_button);
        meetingParticipantRecyclerView = (RecyclerView) view.findViewById(R.id.add_meeting_participants_recycler_view);
        meetingAddButton = (Button) view.findViewById(R.id.add_meeting_button);

        if (mainActivity.mIsDualPane) {
            toolBarBack.setVisibility(View.GONE);
        }

        // Create an ArrayAdapter using the string array of meeting hall and a default spinner layout
        ArrayAdapter<CharSequence> meetingHallAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.meeting_hall, android.R.layout.simple_spinner_item);
        meetingHallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetingHall.setAdapter(meetingHallAdapter);

        setMeetingParticipantList(participantList);
    }

    private void setMeetingParticipantList(ArrayList<String> participantList) {
        adapter = new ParticipantRecyclerViewAdapter(participantList, this);
        meetingParticipantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        meetingParticipantRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        meetingParticipantRecyclerView.setAdapter(adapter);
    }

    /**
     * Set all the click listeners
     */
    private void setListeners() {
        meetingAddParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meetingParticipants.getText().toString().length() > 0) {
                    if (Utils.isValidEmailAddress(meetingParticipants.getText().toString())) {
                        participantList.add(meetingParticipants.getText().toString());
                        adapter.notifyDataSetChanged();
                        meetingParticipants.setText("");
                    } else {
                        Utils.notifyMessage(getContext(), R.string.warning_not_email);
                    }
                } else {
                    Utils.notifyMessage(getContext(), R.string.warning_add_email);
                }
            }
        });

        meetingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMeetingProcess();
            }
        });

        setDateAndTimeListener();
    }

    private void setDateAndTimeListener() {
        meetingScheduleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        setScheduleDate = new Date(dayOfMonth, month, year);
                        meetingScheduleDate.setText(
                                DateHelper.setDateToString(
                                        mainActivity.getApplicationContext(),
                                        setScheduleDate.getDay(),
                                        setScheduleDate.getMonth(),
                                        setScheduleDate.getYear()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        meetingScheduleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        setScheduleTime = new Time(hour, minute);
                        meetingScheduleTime.setText(
                                DateHelper.setTimetoString(
                                        mainActivity.getApplicationContext(),
                                        setScheduleTime.getHours(),
                                        setScheduleTime.getMinutes()));
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });
    }

    private void addMeetingProcess() {
        if (setScheduleTime != null && Utils.isOnOpeningHours(setScheduleTime)) {
            if (Utils.checkHallAvailability(
                    meetingHall.getSelectedItem().toString(),
                    setScheduleDate,
                    setScheduleTime,
                    meetingApiService.getMeetings())) {
                if (meetingSubject.getText().toString().isEmpty() ||
                        participantList.isEmpty()) {
                    Utils.notifyMessage(getContext(), R.string.warning_subject_participants);
                } else {
                    Meeting newMeeting = new Meeting(
                            meetingSubject.getText().toString(),
                            meetingHall.getSelectedItem().toString(),
                            setScheduleDate,
                            setScheduleTime,
                            participantList);

                    meetingApiService.addMeeting(newMeeting);

                    mainActivity.removeFragment();

                    if (mainActivity.mIsDualPane) {
                        mainActivity.toDetail(meetingApiService.getMeetings().get(0));
                    }
                }
            } else {
                Utils.notifyMessage(getContext(), R.string.warning_hall_not_free);
            }
        } else {
            Utils.notifyMessage(getContext(), R.string.warning_meeting_opening);
        }
    }

    @Override
    public void deleteParticipant(int position) {
        participantList.remove(position);
        adapter.notifyDataSetChanged();
    }
}