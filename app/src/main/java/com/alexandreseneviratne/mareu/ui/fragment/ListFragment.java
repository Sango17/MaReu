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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandreseneviratne.mareu.model.Date;
import com.alexandreseneviratne.mareu.service.MeetingApiService;
import com.alexandreseneviratne.mareu.ui.dialog.MainFilterDialog;
import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.utils.DateHelper;
import com.alexandreseneviratne.mareu.utils.FilterHelper;
import com.alexandreseneviratne.mareu.di.DI;
import com.alexandreseneviratne.mareu.ui.MainActivity;
import com.alexandreseneviratne.mareu.ui.adapter.MeetingsRecyclerViewAdapter;
import com.alexandreseneviratne.mareu.ui.listener.OnActionListener;
import com.alexandreseneviratne.mareu.ui.listener.OnFilterListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexandre SENEVIRATNE on 1/19/2020.
 */
public class ListFragment extends Fragment
        implements OnActionListener, OnFilterListener {
    private MainActivity mainActivity;
    private MeetingApiService meetingApiService;

    private Toolbar toolbar;
    private TextView toolBarTitle;
    private ImageView toolBarFilter;
    private ImageView toolBarFilterClose;

    private FloatingActionButton addFab;
    private RecyclerView recyclerView;
    private MeetingsRecyclerViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        meetingApiService = DI.getService();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            setMeetingListView(meetingApiService.getMeetings());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        setToolbar(view);
        setFab(view);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        setMeetingListView(meetingApiService.getMeetings());

        return view;
    }

    /**
     * Set ListFragment's toolbar
     *
     * @param view ListFragment's view
     */
    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolBarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        toolBarFilter = (ImageView) view.findViewById(R.id.navigate_filter);
        toolBarFilterClose = (ImageView) view.findViewById(R.id.navigate_filter_close);

        // Get the actionbar
        if (mainActivity != null) {
            mainActivity.setSupportActionBar(toolbar);
            toolbar.setTitle("");
        }
        toolBarTitle.setText(getString(R.string.main_title));

        toolBarFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFilter();
            }
        });

        toolBarFilterClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolBarTitle.setText(getString(R.string.main_title));
                setFilterButton();
                setMeetingListView(meetingApiService.getMeetings());
            }
        });
    }

    /**
     * Set the Floating Action Button in order to create a new meeting reservation
     *
     * @param view ListFragment's view
     */
    private void setFab(View view) {
        addFab = (FloatingActionButton) view.findViewById(R.id.fab_add);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity != null) {
                    mainActivity.toAdd();
                }
            }
        });
    }

    /**
     * Set the list of meetings into the RecyclerView
     *
     * @param meetings list of meetings which will be displayed in the RecyclerView
     */
    private void setMeetingListView(List<Meeting> meetings) {
        adapter = new MeetingsRecyclerViewAdapter(getContext(), meetings, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * Display a dialog in order to filter the meeting list by date or by hall
     */
    private void selectFilter() {
        MainFilterDialog dialog = new MainFilterDialog(this);
        if (getFragmentManager() != null) {
            dialog.show(getFragmentManager(), "MainFilterDialog");
        }
    }

    /**
     * Display the selected meeting via DetailFragment
     *
     * @param selectedMeeting
     */
    @Override
    public void toDetail(Meeting selectedMeeting) {
        if (mainActivity != null) {
            mainActivity.toDetail(selectedMeeting);
        }
    }

    /**
     * Delete the selected fragment and refresh the RecyclerView
     *
     * @param selectedMeeting
     */
    @Override
    public void toDelete(Meeting selectedMeeting) {
        meetingApiService.deleteMeeting(selectedMeeting);
        if (mainActivity.mIsDualPane) {
            mainActivity.toDetail(null);
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * Display the filtered list of meetings in RecyclerView and modify the toolbar
     *
     * @param filterType           (ex: FILTER_TYPE_DATE or FILTER_TYPE_HALL)
     * @param selectedItemPosition of the spinner
     * @param selectedDate
     */
    @Override
    public void setFilteredList(String filterType, int selectedItemPosition, Date selectedDate) {
        setMeetingListView(FilterHelper.getFilteredList(filterType, selectedItemPosition, meetingApiService.getMeetings(), selectedDate));

        setFilteredToolBar(filterType, selectedItemPosition, selectedDate);
    }

    /**
     * Set the Toolbar when the list of meetings is filtered
     *
     * @param filterType           (ex: FILTER_TYPE_DATE or FILTER_TYPE_HALL)
     * @param selectedItemPosition position of the spinner
     * @param selectedDate         Date
     */
    private void setFilteredToolBar(String filterType, int selectedItemPosition, @Nullable Date selectedDate) {
        if (filterType.equals(FilterHelper.FILTER_TYPE_DATE)) {
            String[] filteredScheduleList = getResources().getStringArray(R.array.meeting_schedule);
            ArrayList<String> scheduleList = new ArrayList<>();
            Collections.addAll(scheduleList, filteredScheduleList);

            toolBarTitle.setText(
                    getContext().getResources().getString(
                            R.string.filter_date,
                            DateHelper.setDateToString(getContext(), selectedDate.getDay(), selectedDate.getMonth(), selectedDate.getYear()),
                            scheduleList.get(selectedItemPosition)
                    )
            );
        } else if (filterType.equals(FilterHelper.FILTER_TYPE_HALL)) {
            toolBarTitle.setText(getString(R.string.filter_hall, FilterHelper.getHallFilter(selectedItemPosition)));
        }

        setFilterButton();
    }

    /**
     * Set Toolbar buttons
     */
    private void setFilterButton() {
        if (toolBarFilter.getVisibility() == View.VISIBLE) {
            toolBarFilter.setVisibility(View.GONE);
            toolBarFilterClose.setVisibility(View.VISIBLE);
        } else {
            toolBarFilter.setVisibility(View.VISIBLE);
            toolBarFilterClose.setVisibility(View.GONE);
        }
    }
}