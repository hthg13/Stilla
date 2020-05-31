package com.example.stilla_app.View.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.R;
import com.example.stilla_app.View.Activities.TripForecastActivity;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_Main extends RecyclerView.Adapter <RecyclerViewAdapter_Main.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter_Mai";

    private ArrayList<String> mTripNames = new ArrayList<>();
    private ArrayList<String> mTripStarts = new ArrayList<>();
    private ArrayList<String> mTripFinishes = new ArrayList<>();
    private ArrayList<Long> mTripIds = new ArrayList<>();
    private List<Trip> mAllTrips = new ArrayList<>();
    private Context mContext;


    public RecyclerViewAdapter_Main(ArrayList<String> tripNames, ArrayList<String> tripStarts, ArrayList<String> tripFinishes, ArrayList<Long> tripIds, List<Trip> allTrips, Context context) {
        mTripFinishes.clear();
        mTripStarts.clear();
        mTripNames.clear();
        mAllTrips.clear();
        mTripIds.clear();

        mTripNames = tripNames;
        mTripStarts = tripStarts;
        mTripFinishes = tripFinishes;
        mTripIds = tripIds;
        mAllTrips = allTrips;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.tripName.setText(mTripNames.get(position));
        holder.tripStart.setText(mTripStarts.get(position));
        holder.tripEnd.setText(mTripFinishes.get(position));

        holder.mainParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClik: clicked on: " + mTripNames.get(position) + " position: " + position);

                Trip clickedTrip = mAllTrips.get(position);

                Intent intent = new Intent(v.getContext(), TripForecastActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("clickedTrip", clickedTrip);
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        System.out.println("getItemCount4: " + mTripNames.size());
        return mTripNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tripName;
        TextView tripStart;
        TextView tripEnd;
        RelativeLayout mainParentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tripName = itemView.findViewById(R.id.trip_name);
            tripStart = itemView.findViewById(R.id.trip_start);
            tripEnd = itemView.findViewById(R.id.trip_end);
            mainParentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
