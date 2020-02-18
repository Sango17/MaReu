package com.alexandreseneviratne.mareu.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexandreseneviratne.mareu.R;
import com.alexandreseneviratne.mareu.ui.listener.OnParticipantListener;

import java.util.List;

/**
 * Created by Alexandre SENEVIRATNE on 2/7/2020.
 */
public class ParticipantRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantRecyclerViewAdapter.ViewHolder> {
    private List<String> participantList;
    private OnParticipantListener mListener;

    public ParticipantRecyclerViewAdapter(List<String> participantList, OnParticipantListener listener) {
        this.participantList = participantList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_participant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.participantMail.setText(participantList.get(position));
        holder.participantTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteParticipant(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView participantMail;
        ImageView participantTrash;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            participantMail = itemView.findViewById(R.id.item_info);
            participantTrash = itemView.findViewById(R.id.item_trash);
        }
    }
}
