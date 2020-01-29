package com.alexandreseneviratne.mareu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.alexandreseneviratne.mareu.ui.OnFilterListener;
import com.alexandreseneviratne.mareu.ui.fragment.ListFragment;

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

    private void setView(View view) {
        hallButton = view.findViewById(R.id.dialog_filter_hall);
        dateButton = view.findViewById(R.id.dialog_filter_schedule);
        cancelButton = view.findViewById(R.id.dialog_filter_cancel);
    }

    private void initListeners() {
        hallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog dialog = new FilterDialog(Utils.FILTER_TYPE_HALL, mListener);
                if (getFragmentManager() != null) {
                    dialog.show(getFragmentManager(), "FilterDialogHall");
                }

                getDialog().dismiss();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog dialog = new FilterDialog(Utils.FILTER_TYPE_HOUR, mListener);
                if (getFragmentManager() != null) {
                    dialog.show(getFragmentManager(), "FilterDialogDate");
                }

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
