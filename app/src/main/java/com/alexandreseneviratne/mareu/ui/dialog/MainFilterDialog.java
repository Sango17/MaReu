package com.alexandreseneviratne.mareu.ui.dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.utils.FilterHelper;
import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.ui.listener.OnFilterListener;

import java.util.Calendar;

/**
 * Created by Alexandre SENEVIRATNE on 1/27/2020.
 */
public class MainFilterDialog extends DialogFragment {

    private Button hallButton, dateButton, cancelButton;
    private OnFilterListener mListener;

    public MainFilterDialog(OnFilterListener listener) {
        this.mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_filter_main, container, false);
        setView(view);
        initListeners();

        return view;
    }

    /**
     * Set MainFilterDialog's view
     *
     * @param view MainFilterDialog's view
     */
    private void setView(View view) {
        hallButton = view.findViewById(R.id.dialog_filter_hall);
        dateButton = view.findViewById(R.id.dialog_filter_schedule);
        cancelButton = view.findViewById(R.id.dialog_filter_cancel);
    }

    /**
     * Initialize click listeners
     */
    private void initListeners() {
        hallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog dialog = new FilterDialog(FilterHelper.FILTER_TYPE_HALL, mListener, null);
                if (getFragmentManager() != null) {
                    dialog.show(getFragmentManager(), "FilterDialogHall");
                }

                getDialog().dismiss();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        getDialog().dismiss();

                        Date setScheduleDate = new Date(dayOfMonth, month, year);

                        FilterDialog dialog = new FilterDialog(FilterHelper.FILTER_TYPE_DATE, mListener, setScheduleDate);
                        if (getFragmentManager() != null) {
                            dialog.show(getFragmentManager(), "FilterDialogDate");
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();
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
