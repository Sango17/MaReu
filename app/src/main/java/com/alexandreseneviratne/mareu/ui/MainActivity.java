package com.alexandreseneviratne.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.alexandreseneviratne.mareu.Meeting;
import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.ui.fragment.AddFragment;
import com.alexandreseneviratne.mareu.ui.fragment.DetailFragment;
import com.alexandreseneviratne.mareu.ui.fragment.ListFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FrameLayout fragmentContainer;
    public ArrayList<Meeting> meetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetings = new ArrayList<>();

        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            toList();
        }
    }

    public void toList() {
        // Create a new Fragment to be placed in the activity layout
        ListFragment listFragment = new ListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        listFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        transaction.add(R.id.fragment_container, listFragment);
        transaction.commit();
    }

    public void toDetail(Meeting selectedMeeting) {
        DetailFragment detailFragment = new DetailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        detailFragment.setMeetingDetail(selectedMeeting);
    }

    public void toAdd() {
        AddFragment addFragment = new AddFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, addFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void removeFragment() {
        getSupportFragmentManager().popBackStack();
    }
}
