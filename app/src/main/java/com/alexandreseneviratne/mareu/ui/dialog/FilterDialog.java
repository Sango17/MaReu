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
import com.alexandreseneviratne.mareu.utils.FilterHelper;
import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.ui.listener.OnFilterListener;

/**
 * Created by Alexandre SENEVIRATNE on 1/29/2020.
 */
public class FilterDialog extends DialogFragment {
    private String filterType;
    private Date selectedDate;

    private TextView titleTextView;
    private Spinner spinner;
    private Button acceptButton;
    private Button cancelButton;
    private OnFilterListener mListener;

    public FilterDialog(String filterType, OnFilterListener listener, Date selectedDate) {
        this.filterType = filterType;
        this.mListener = listener;

        if (selectedDate != null) {
            this.selectedDate = selectedDate;
        }
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

    /**
     * Set FilterDialog's view
     *
     * @param view FilterDialog's view
     */
    private void setView(View view) {
        titleTextView = view.findViewById(R.id.dialog_filter_title);
        spinner = view.findViewById(R.id.dialog_filter_spinner);
        acceptButton = view.findViewById(R.id.dialog_filter_accept);
        cancelButton = view.findViewById(R.id.dialog_filter_cancel);

        titleTextView.setText(FilterHelper.setFilterDialogTitle(getContext(), filterType));
    }

    /**
     * Set the spinner according to the filterType
     *
     * @param filterType (ex: FILTER_TYPE_DATE or FILTER_TYPE_HALL)
     */
    private void setSpinner(String filterType) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> meetingHallAdapter = ArrayAdapter.createFromResource(getContext(),
                (filterType.equals(FilterHelper.FILTER_TYPE_HALL)) ? R.array.meeting_hall : R.array.meeting_schedule, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        meetingHallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(meetingHallAdapter);
    }

    /**
     * Initialize click listeners
     */
    private void initListeners() {
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.setFilteredList(filterType, spinner.getSelectedItemPosition(), selectedDate);
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
