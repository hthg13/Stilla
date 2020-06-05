package com.example.stilla_app.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stilla_app.Data.Model.Singeltons.AllTripsBase;
import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.R;
import com.example.stilla_app.View.Adapters.RecyclerViewAdapter_Main;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mTripNames = new ArrayList<>();
    private ArrayList<String> mTripStarts = new ArrayList<>();
    private ArrayList<String> mTripEnds = new ArrayList<>();
    private ArrayList<Long> mTripIds = new ArrayList<>();

    private Button createTripButton;
    private Button viewAllTripsButton;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Þínar ferðir");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_stilla_logo_and_name_round);
        actionBar.setDisplayUseLogoEnabled(true);

        createTripButton = (Button) findViewById(R.id.button_new_trip);
        viewAllTripsButton = (Button) findViewById(R.id.button_show_all_trips);

        // initialize with singelton class alltripsbase
        initListItems(AllTripsBase.get().getTripsList());

        createTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TripActivity.class);
                startActivity(intent);
            }
        });

        viewAllTripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initListItems(AllTripsBase.get().getTripsList());
                Toast.makeText(getApplicationContext(),"Tókst að uppfæra listann",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * HELPER METHODS
     */

    public void initRecyclerView(List<Trip> tripsList) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter_Main adapter = new RecyclerViewAdapter_Main(mTripNames,mTripStarts,mTripEnds,mTripIds,tripsList,this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }

    public void initListItems(List<Trip> tripsList) {
        int n = tripsList.size();
        mTripNames.clear();
        mTripStarts.clear();
        mTripEnds.clear();
        mTripIds.clear();
        for (int i=0; i<n; i++) {
            String currentName = tripsList.get(i).getName();
            String currentStart = tripsList.get(i).getStart();
            String currentEnd = tripsList.get(i).getFinish();
            long currentid = tripsList.get(i).getId();

            mTripNames.add(currentName);
            mTripStarts.add(currentStart);
            mTripEnds.add(currentEnd);
            mTripIds.add(currentid);
        }
        initRecyclerView(tripsList);
    }
}
