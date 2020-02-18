package com.alexandreseneviratne.mareu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandreseneviratne.mareu.model.Meeting;
import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.ui.listener.OnActionListener;
import com.alexandreseneviratne.mareu.utils.DateHelper;
import com.alexandreseneviratne.mareu.utils.StringHelper;

import java.util.List;

/**
 * Created by Alexandre SENEVIRATNE on 1/19/2020.
 */
public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.ViewHolder> {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Meeting selectedMeeting = mMeetingList.get(position);
        holder.titleInfo.setText(
                StringHelper.setMeetingListTitle(mContext,
                        selectedMeeting.getSubject(),
                        DateHelper.setTimetoString(
                                mContext,
                                selectedMeeting.getScheduleTime().getHours(),
                                selectedMeeting.getScheduleTime().getMinutes()
                        ),
                        selectedMeeting.getHall())
        );

        holder.textInfo.setText(StringHelper.getParticipantsDetail(selectedMeeting.getParticipants()));

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

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemContainer;
        TextView titleInfo;
        TextView textInfo;
        ImageView trashButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemContainer = itemView.findViewById(R.id.item_container);
            titleInfo = itemView.findViewById(R.id.item_main_info);
            textInfo = itemView.findViewById(R.id.item_contact_info);
            trashButton = itemView.findViewById(R.id.item_trash);
        }
    }
}
