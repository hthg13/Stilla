package com.example.stilla_app.View.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.stilla_app.Data.Model.Singeltons.AllTripsBase;
import com.example.stilla_app.Data.Model.Singeltons.CalculatedTrip;
import com.example.stilla_app.Data.Model.TripRelated.CreateTripAlgo;
import com.example.stilla_app.Data.Model.TripRelated.Forecast;
import com.example.stilla_app.Data.Model.TripRelated.Trip;
import com.example.stilla_app.Data.Model.TripRelated.WeatherStation;
import com.example.stilla_app.Data.Network.MethodsAPI;
import com.example.stilla_app.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TripActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    BroadcastReceiver mReceiver;
    IntentFilter filter;

    private String[] transport = {"Keyrandi", "Gangandi"};
    private String trip_name;
    private String trip_start;
    private String trip_end;
    private String trip_destinations;
    private ArrayList<String> list_trip_destinations = new ArrayList<>();
    private String trip_transportation;

    private MethodsAPI mMethodsAPI = new MethodsAPI();

    List<WeatherStation> mAllStations = new ArrayList<>();
    List<Forecast> mForecasts = new ArrayList<>();
    private List<Trip> allTrips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ný Ferð");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_stilla_logo_and_name_round);
        actionBar.setDisplayUseLogoEnabled(true);

        mAllStations = mMethodsAPI.getAllStations();
        mForecasts = mMethodsAPI.getForecasts(1, "F;D;T;W;V;N;TD;R");
        allTrips = mMethodsAPI.getTripList();

        Button buttonSaveTrip = (Button) findViewById(R.id.button_save_new_trip);

        TextInputEditText input_trip_name = (TextInputEditText) findViewById(R.id.textInput_trip_name);
        TextInputEditText input_trip_start = (TextInputEditText) findViewById(R.id.textInput_trip_start);
        TextInputEditText input_trip_finish = (TextInputEditText) findViewById(R.id.textInput_trip_end);
        TextInputEditText input_trip_destinations = (TextInputEditText) findViewById(R.id.textInput_destinations);

        input_trip_name.setText("Nýjustu prufuferðirnar");
        input_trip_start.setText("03-06-2020 12:00");
        input_trip_finish.setText("05-09-2020 12:00");
        input_trip_destinations.setText("Reykjavík,Akureyri,Höfn");

        TextInputLayout input_trip_name_layout = (TextInputLayout) findViewById(R.id.textInput_trip_name_layout);
        TextInputLayout input_trip_start_layout = (TextInputLayout) findViewById(R.id.textInput_trip_start_layout);
        TextInputLayout input_trip_finish_layout = (TextInputLayout) findViewById(R.id.textInput_trip_end_layout);
        TextInputLayout input_trip_destinations_layout = (TextInputLayout) findViewById(R.id.textInput_destinations_layout);

        Switch input_notify = (Switch) findViewById(R.id.switch_input);

        Spinner spin = (Spinner) findViewById(R.id.spinner_dropdown_transport);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,transport);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_trip);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_trip_name_layout.setError(null);
                input_trip_start_layout.setError(null);
                input_trip_finish_layout.setError(null);
                input_trip_destinations_layout.setError(null);
            }
        });

        input_trip_name_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_trip_name_layout.setError(null);
            }
        });

        input_trip_start_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_trip_start_layout.setError(null);
            }
        });

        input_trip_finish_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_trip_finish_layout.setError(null);
            }
        });

        input_trip_destinations_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_trip_destinations_layout.setError(null);
            }
        });

        input_trip_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_trip_name_layout.setError(null);
            }
        });

        input_trip_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_trip_start_layout.setError(null);
            }
        });

        input_trip_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_trip_finish_layout.setError(null);
            }
        });

        input_trip_destinations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_trip_destinations_layout.setError(null);
            }
        });

        buttonSaveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allOK = true;
                ArrayList<String> transport = new ArrayList<>();

                if (input_trip_name.getText().toString().equals("")) {
                    input_trip_name_layout.setError("Þú þarft að slá inn nafn ferðarinnar");
                    allOK = false;
                } else {
                    setTrip_name(input_trip_name.getText().toString());
                }

                if (input_trip_start.getText().toString().equals("")) {
                    input_trip_start_layout.setError("Þú þarft að slá inn upphafstíma ferðarinnar");
                    allOK = false;
                } else {
                    setTrip_start(input_trip_start.getText().toString());
                }

                if (input_trip_finish.getText().toString().equals("")) {
                    input_trip_finish_layout.setError("Þú þarft að slá inn lokatíma ferðarinnar");
                    allOK = false;
                } else {
                    setTrip_end(input_trip_finish.getText().toString());
                }

                if (input_trip_destinations.getText().toString().equals("")) {
                    input_trip_destinations_layout.setError("Þú þarft að slá inn lokatíma ferðarinnar");
                    allOK = false;
                } else {
                    setTrip_destinations(input_trip_destinations.getText().toString());
                }

                transport.add(getTrip_transportation());

                if (allOK) {
                    createNewTrip(getTrip_name(), getTrip_start(), getTrip_end(), getList_trip_destinations(), transport, input_notify.isChecked());
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setTrip_transportation(transport[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(),"Þú átt eftir að velja ferðamáta",Toast.LENGTH_SHORT).show();
    }

    // HELPER FUNCTIONS ------------------------------------------------------------------------------------------------------------------------------------

    private void createNewTrip(String name, String start, String finish, ArrayList<String> destinations, ArrayList<String> transportation, boolean notify) {
        // save the trip that the user just inserted and calculate the weather forecast + directions
        Intent intent = new Intent(TripActivity.this, CreateTripAlgo.class);
        Trip trip = new Trip(name,start,finish,destinations,transportation,notify);
        CalculatedTrip.get().setTrip(trip);
        intent.putParcelableArrayListExtra("allStations", new ArrayList<>(mAllStations));
        startService(intent);
    }


    // GETTERS AND SETTERS --------------------------------------------------------------------------------------------------------------------------------

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public void setTrip_start(String trip_start) {
        this.trip_start = trip_start;
    }

    public void setTrip_end(String trip_end) {
        this.trip_end = trip_end;
    }

    public void setTrip_destinations(String trip_destinations) {
        ArrayList<String> dest_list = new ArrayList<>(Arrays.asList(trip_destinations.split(",")));
        setList_trip_destinations(dest_list);

        this.trip_destinations = trip_destinations;
    }

    public void setTrip_transportation(String trip_transportation) {
        this.trip_transportation = trip_transportation;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public String getTrip_start() {
        return trip_start;
    }

    public String getTrip_end() {
        return trip_end;
    }

    public String getTrip_destinations() {
        return trip_destinations;
    }

    public String getTrip_transportation() {
        return trip_transportation;
    }

    public String[] getTransport() {
        return transport;
    }

    public void setTransport(String[] transport) {
        this.transport = transport;
    }

    public ArrayList<String> getList_trip_destinations() {
        return list_trip_destinations;
    }

    public void setList_trip_destinations(ArrayList<String> list_trip_destinations) {
        this.list_trip_destinations = list_trip_destinations;
    }
}
