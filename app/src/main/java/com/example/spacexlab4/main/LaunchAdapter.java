package com.example.spacexlab4.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import com.example.spacexlab4.R;
import com.example.spacexlab4.model.Launch;

public class LaunchAdapter extends RecyclerView.Adapter<LaunchAdapter.LaunchViewHolder>
        implements View.OnClickListener {

    private List<Launch> launches = Collections.emptyList();

    private LaunchListener listener;

    public LaunchAdapter(LaunchListener listener) {
        setHasStableIds(true);
        this.listener = listener;
    }

    public void setRepositories(List<Launch> launches) {
        this.launches = launches;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return launches.get(position).getFlightNumber();
    }

    @NonNull
    @Override
    public LaunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.item_launch, parent, false);
        return new LaunchViewHolder(root, this);
    }

    @Override
    public void onBindViewHolder(@NonNull LaunchViewHolder holder, int position) {
        Launch launch = launches.get(position);
        holder.launchYearTextView.setText(String.valueOf(launch.getLaunch_year()));
        holder.launchNumberTextView.setText(String.valueOf(launch.getFlightNumber()));
        holder.missionNameTextView.setText(launch.getMissionName());
        holder.itemView.setTag(launch);
    }

    @Override
    public int getItemCount() {
        return launches.size();
    }

    @Override
    public void onClick(View v) {
        Launch repository = (Launch) v.getTag();
        listener.onLaunchChosen(repository);
    }

    static class LaunchViewHolder extends RecyclerView.ViewHolder {
        private TextView launchNumberTextView;
        private TextView missionNameTextView;
        private TextView launchYearTextView;
        public LaunchViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            launchNumberTextView = itemView.findViewById(R.id.launchNumber);
            missionNameTextView = itemView.findViewById(R.id.missionName);
            launchYearTextView = itemView.findViewById(R.id.launchYear);
            itemView.setOnClickListener(listener);
        }
    }

    public interface LaunchListener {
        void onLaunchChosen(Launch repository);
    }
}
