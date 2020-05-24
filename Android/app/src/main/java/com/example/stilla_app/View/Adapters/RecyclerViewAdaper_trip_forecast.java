package com.example.stilla_app.View.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stilla_app.R;
import java.util.ArrayList;

public class RecyclerViewAdaper_trip_forecast extends RecyclerView.Adapter<RecyclerViewAdaper_trip_forecast.ViewHolder>{

    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> f = new ArrayList<>();
    private ArrayList<String> t = new ArrayList<>();
    private ArrayList<String> d = new ArrayList<>();
    private ArrayList<String> w = new ArrayList<>();
    private ArrayList<String> n = new ArrayList<>();
    private ArrayList<String> r = new ArrayList<>();
    private ArrayList<String> td = new ArrayList<>();
    //private ArrayList<String> dest = new ArrayList<>();
    private ArrayList<String> stationName = new ArrayList<>();
    private ArrayList<String> stationArea = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdaper_trip_forecast(Context context, ArrayList<String> time, ArrayList<String> f,
                                            ArrayList<String> t, ArrayList<String> d, ArrayList<String> w,
                                            ArrayList<String> n, ArrayList<String> r, ArrayList<String> td,
                                            /*ArrayList<String> dest, */ArrayList<String> stationName,
                                            ArrayList<String> stationArea) {
        this.time = time;
        this.f = f;
        this.t = t;
        this.d = d;
        this.w = w;
        this.n = n;
        this.r = r;
        this.td = td;
        //this.dest = dest;
        this.stationName = stationName;
        this.stationArea = stationArea;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdaper_trip_forecast.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trip_forecast, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdaper_trip_forecast.ViewHolder holder, int position) {
        holder.time.setText(time.get(position));
        holder.f.setText(f.get(position));
        holder.t.setText(t.get(position));
        holder.d.setText(d.get(position));
        holder.w.setText(w.get(position));
        holder.n.setText(n.get(position));
        holder.r.setText(r.get(position));
        holder.td.setText(r.get(position));
        //holder.dest.setText(dest.get(position));
        holder.stationName.setText(stationName.get(position));
        holder.stationArea.setText(stationArea.get(position));
    }

    @Override
    public int getItemCount() {
        return time.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView f;
        TextView t;
        TextView d;
        TextView w;
        TextView n;
        TextView r;
        TextView td;
        //TextView dest;
        TextView stationName;
        TextView stationArea;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.forecast_time);
            f= itemView.findViewById(R.id.forecast_f);
            t= itemView.findViewById(R.id.forecast_t);
            d= itemView.findViewById(R.id.forecast_d);
            w= itemView.findViewById(R.id.forecast_w);
            n= itemView.findViewById(R.id.forecast_n);
            r= itemView.findViewById(R.id.forecast_r);
            td= itemView.findViewById(R.id.forecast_td);
            //dest= itemView.findViewById(R.id.f);
            stationName= itemView.findViewById(R.id.forecast_station_name);
            stationArea= itemView.findViewById(R.id.forecast_station_area);
        }
    }
}
