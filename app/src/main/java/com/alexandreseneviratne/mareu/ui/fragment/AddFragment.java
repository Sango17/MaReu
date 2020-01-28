package com.alexandreseneviratne.mareu.ui.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.alexandreseneviratne.mareu.Meeting;
import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.Time;
import com.alexandreseneviratne.mareu.Utils;
import com.alexandreseneviratne.mareu.di.DI;
import com.alexandreseneviratne.mareu.service.FakeMeetingApiService;
import com.alexandreseneviratne.mareu.ui.MainActivity;

import java.util.Calendar;

/**
 * Created by Alexandre SENEVIRATNE on 1/26/2020.
 */
public class AddFragment extends Fragment {
    private MainActivity mainActivity;
    private FakeMeetingApiService meetingApiService;

    private Toolbar toolbar;
    private ImageView toolBarBack;

    private Spinner meetingHall;
    private TextView meetingSchedule;
    private EditText meetingSubject;
    private EditText meetingParticipants;
    private Button meetingAddButton;

    private Time setSchedule;

    private Calendar calendar = Calendar.getInstance();
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

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolBarBack = (ImageView) view.findViewById(R.id.navigate_up);
        // Get the actionbar
        if (mainActivity != null) {
            mainActivity.setSupportActionBar(toolbar);
            mainActivity.getSupportActionBar().setTitle("");
        }
    }

    private void setView(View view) {
        meetingHall = (Spinner) view.findViewById(R.id.add_meeting_hall);
        meetingSchedule = (TextView) view.findViewById(R.id.add_meeting_schedule);
        meetingSubject = (EditText) view.findViewById(R.id.add_meeting_subject);
        meetingParticipants = (EditText) view.findViewById(R.id.add_meeting_participants);
        meetingAddButton = (Button) view.findViewById(R.id.add_meeting_button);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> meetingHallAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.meeting_hall, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        meetingHallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        meetingHall.setAdapter(meetingHallAdapter);
    }

    private void setListeners() {
        toolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.removeFragment();
            }
        });

        meetingSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        setSchedule = new Time(hour, minute);

                        Log.d("Alex Test", String.valueOf((hour*60) + minute));

                        meetingSchedule.setText(Utils.setTimetoString(getContext(), setSchedule.getHours(), setSchedule.getMinutes()));
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        meetingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isOnOpeningHours(setSchedule)) {
                    if (Utils.checkHallAvailability(
                            meetingHall.getSelectedItem().toString(),
                            setSchedule,
                            meetingApiService.getMeetings()))
                    {
                        if (meetingSubject.getText().toString().isEmpty() ||
                                meetingParticipants.getText().toString().isEmpty()) {
                            Toast.makeText(
                                    getContext(),
                                    "N'oubliez pas d'indiquer le sujet et/ou les noms des participants",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {

                            Meeting newMeeting = new Meeting(
                                    meetingSubject.getText().toString(),
                                    meetingHall.getSelectedItem().toString(),
                                    setSchedule,
                                    meetingParticipants.getText().toString());

                            meetingApiService.addMeeting(newMeeting);

                            mainActivity.removeFragment();
                        }
                    } else {
                        Toast.makeText(
                                getContext(),
                                "Désolé la salle de réunion est déja réservée.",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Toast.makeText(
                            getContext(),
                            "Les horaires d\'ouverture sont entre 8h00 et 20h00, veuillez choisir un autre horaire.",
                            Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }
}