package com.example.stilla_app.View.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stilla_app.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_Main extends RecyclerView.Adapter <RecyclerViewAdapter_Main.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter_Mai";

    private ArrayList<String> mTripNames = new ArrayList<>();
    private ArrayList<String> mTripStarts = new ArrayList<>();
    private ArrayList<String> mTripFinishes = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter_Main(ArrayList<String> tripNames, ArrayList<String> tripStarts, ArrayList<String> tripFinishes, Context context) {
        mTripNames = tripNames;
        mTripStarts = tripStarts;
        mTripFinishes = tripFinishes;
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
        holder.tripStart.setText(mTripNames.get(position));
        holder.tripEnd.setText(mTripFinishes.get(position));

        holder.mainParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClik: clicked on: " + mTripNames.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTripNames.size();
    }

    /**
     * holds the widgets in memory and recycles them
     */
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
