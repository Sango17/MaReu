package com.alexandreseneviratne.mareu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.ui.MainActivity;

/**
 * Created by Alexandre SENEVIRATNE on 1/26/2020.
 */
public class AddFragment extends Fragment {
    private MainActivity mainActivity;
    private Toolbar toolbar;
    private ImageView toolBarBack;

    private Spinner meetingHall;
    private Spinner meetingSchedule;
    private EditText meetingSubject;
    private EditText meetingParticipants;
    private Button meetingAddButton;

    private ArrayAdapter<CharSequence> meetingHallAdapter;
    private ArrayAdapter<CharSequence> meetingScheduleAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
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
        meetingHall = (Spinner) view.findViewById(R.id.add_meeting_hall);
        meetingSchedule = (Spinner) view.findViewById(R.id.add_meeting_schedule);
        meetingSubject = (EditText) view.findViewById(R.id.add_meeting_subject);
        meetingParticipants = (EditText) view.findViewById(R.id.add_meeting_participants);
        meetingAddButton = (Button) view.findViewById(R.id.add_meeting_button);

        // Create an ArrayAdapter using the string array and a default spinner layout
        meetingHallAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.meeting_hall, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        meetingHallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        meetingHall.setAdapter(meetingHallAdapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        meetingScheduleAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.meeting_schedule, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        meetingScheduleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        meetingSchedule.setAdapter(meetingScheduleAdapter);
    }

    private void setListeners() {
        meetingHall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        meetingSchedule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        meetingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.removeFragment();
            }
        });
    }
}