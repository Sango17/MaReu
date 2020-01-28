package com.alexandreseneviratne.mareu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandreseneviratne.mareu.MainFilterDialog;
import com.alexandreseneviratne.mareu.Meeting;
import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.di.DI;
import com.alexandreseneviratne.mareu.service.FakeMeetingApiService;
import com.alexandreseneviratne.mareu.ui.MainActivity;
import com.alexandreseneviratne.mareu.ui.MeetingsRecyclerViewAdapter;
import com.alexandreseneviratne.mareu.ui.OnActionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandre SENEVIRATNE on 1/19/2020.
 */
public class ListFragment extends Fragment implements OnActionListener {
    private MainActivity mainActivity;
    private FakeMeetingApiService meetingApiService;

    private Toolbar toolbar;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflates wanted menu
        inflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            setMeetingListView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_filter:
                selectFilter();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void selectFilter() {
        MainFilterDialog dialog = new MainFilterDialog();
        if (getFragmentManager() != null) {
            dialog.show(getFragmentManager(), "MainFilterDialog");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        setToolbar(view);
        setFab(view);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        setMeetingListView();


        return view;
    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        // Notice there is a menu
        setHasOptionsMenu(true);

        // Get the actionbar
        AppCompatActivity actionBar = ((AppCompatActivity) getActivity());
        if (actionBar != null) {
            actionBar.setSupportActionBar(toolbar);
        }
    }

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

    public void setMeetingListView() {
        adapter = new MeetingsRecyclerViewAdapter(getContext(), meetingApiService.getMeetings(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void toDetail(Meeting selectedMeeting) {
        if (mainActivity != null) {
            mainActivity.toDetail(selectedMeeting);
        }
    }

    @Override
    public void toDelete(Meeting selectedMeeting) {
        meetingApiService.deleteMeeting(selectedMeeting);

        setMeetingListView();
    }


}
