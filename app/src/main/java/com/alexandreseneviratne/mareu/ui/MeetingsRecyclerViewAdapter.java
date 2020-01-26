package com.alexandreseneviratne.mareu.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandreseneviratne.mareu.Meeting;
import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.StringHelper;

import java.util.List;

/**
 * Created by Alexandre SENEVIRATNE on 1/19/2020.
 */
public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<Meeting> mMeetingList;
    private OnActionListener mListener;

    public MeetingsRecyclerViewAdapter(Context context, List<Meeting> meetingList, OnActionListener listener) {
        mContext = context;
        mMeetingList = meetingList;
        mListener = listener;
    }

    @NonNull
    @Override
    public MeetingsRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_meeting, parent, false);
        return new MeetingsRecyclerViewAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingsRecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        final Meeting selectedMeeting = mMeetingList.get(position);
        holder.titleInfo.setText(
                StringHelper.setMeetingListTitle(mContext,
                        selectedMeeting.getSubject(),
                        selectedMeeting.getScheduleTime(),
                        selectedMeeting.getHall())
        );

        holder.textInfo.setText(selectedMeeting.getParticipants());

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.toDetail(selectedMeeting);
            }
        });

        holder.trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.toDelete(selectedMeeting);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemContainer;
        TextView titleInfo;
        TextView textInfo;
        ImageView trashButton;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            itemContainer = (LinearLayout) itemView.findViewById(R.id.item_container);
            titleInfo = (TextView) itemView.findViewById(R.id.item_main_info);
            textInfo = (TextView) itemView.findViewById(R.id.item_contact_info);
            trashButton = (ImageView) itemView.findViewById(R.id.item_trash);
        }
    }
}
