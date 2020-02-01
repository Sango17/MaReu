package com.alexandreseneviratne.mareu.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.Utils;
import com.alexandreseneviratne.mareu.ui.OnFilterListener;

/**
 * Created by Alexandre SENEVIRATNE on 1/29/2020.
 */
public class FilterDialog extends DialogFragment {
    private String filterType;

    private TextView titleTextView;
    private Spinner spinner;
    private Button acceptButton;
    private Button cancelButton;
    private OnFilterListener mListener;

    public FilterDialog(String filterType, OnFilterListener listener) {
        this.filterType = filterType;
        this.mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_filter_second, container, false);

        setView(view);
        setSpinner(filterType);
        initListeners();

        return view;
    }

    private void setView(View view) {
        titleTextView = (TextView) view.findViewById(R.id.dialog_filter_title);
        spinner = (Spinner) view.findViewById(R.id.dialog_filter_spinner);
        acceptButton = (Button) view.findViewById(R.id.dialog_filter_accept);
        cancelButton = (Button) view.findViewById(R.id.dialog_filter_cancel);

        titleTextView.setText(Utils.setFilterDialogTitle(getContext(), filterType));
    }

    private void setSpinner(String filterType){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> meetingHallAdapter = ArrayAdapter.createFromResource(getContext(),
                (filterType.equals(Utils.FILTER_TYPE_HALL))? R.array.meeting_hall : R.array.meeting_schedule, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        meetingHallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(meetingHallAdapter);
    }

    private void initListeners() {
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setFilteredList(filterType, spinner.getSelectedItemPosition());
                getDialog().dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }
}
