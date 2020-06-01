package com.example.stilla_app.Data.Model.TripRelated;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Forecast implements Parcelable {

    @JsonProperty(required = false)
    private long id;

    @JsonProperty(required = false)
    @JsonAlias({"ftime"})
    @Element(name = "ftime", required = false)
    private String ftime;

    @JsonProperty(required = false)
    @JsonAlias({"F", "f"})
    @Element(name = "F", required = false)
    private int F;

    @JsonProperty(required = false)
    @JsonAlias({"D", "d"})
    @Element(name = "D", required = false)
    private String D;

    @JsonProperty(required = false)
    @JsonAlias({"T", "t"})
    @Element(name = "T", required = false)
    private int T;

    @JsonProperty(required = false)
    @JsonAlias({"W", "w"})
    @Element(name = "W", required = false)
    private String W;

    @JsonProperty(required = false)
    @JsonAlias({"V", "v"})
    @Element(name = "V", required = false)
    private String V;

    @JsonProperty(required = false)
    @JsonAlias({"N", "n"})
    @Element(name = "N", required = false)
    private String N;

    @JsonProperty(required = false)
    @JsonAlias({"TD", "td"})
    @Element(name = "TD", required = false)
    private String TD;

    @JsonProperty(required = false)
    @JsonAlias({"R", "r"})
    @Element(name = "R", required = false)
    private String R;

    // stationEndpointName
    @JsonProperty(required = false)
    @JsonAlias({"stationEndpointName"})
    @Element(name = "stationEndpointName", required = false)
    private String stationEndpointName;

    @JsonIgnore
    private String weatherStationName;

    @JsonIgnore
    private long weatherStationId;

    public Forecast() {
    }

    public String getFtime() {
        return ftime;
    }

    public int getF() {
        return F;
    }

    public String getD() {
        return D;
    }

    public int getT() {
        return T;
    }

    public String getW() {
        return W;
    }

    public String getV() {
        return V;
    }

    public String getN() {
        return N;
    }

    public String getTD() {
        return TD;
    }

    public String getR() {
        return R;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public void setF(int f) {
        F = f;
    }

    public void setD(String d) {
        D = d;
    }

    public void setT(int t) {
        T = t;
    }

    public void setW(String w) {
        W = w;
    }

    public void setV(String v) {
        V = v;
    }

    public void setN(String n) {
        N = n;
    }

    public void setTD(String TD) {
        this.TD = TD;
    }

    public void setR(String r) {
        R = r;
    }

    public String getStationEndpointName() {
        return stationEndpointName;
    }

    public void setStationEndpointName(String stationEndpointName) {
        this.stationEndpointName = stationEndpointName;
    }

    @JsonIgnore
    public String getWeatherStationName() {
        return weatherStationName;
    }

    @JsonIgnore
    public void setWeatherStationName(String weatherStationName) {
        this.weatherStationName = weatherStationName;
    }

    @JsonIgnore
    public long getWeatherStationId() {
        return weatherStationId;
    }

    @JsonIgnore
    public void setWeatherStationId(long weatherStationId) {
        this.weatherStationId = weatherStationId;
    }

    @JsonIgnore
    @Override
    public int describeContents() {
        return 0;
    }

    @JsonIgnore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(ftime);
        dest.writeInt(F);
        dest.writeString(D);
        dest.writeInt(T);
        dest.writeString(W);
        dest.writeString(V);
        dest.writeString(N);
        dest.writeString(TD);
        dest.writeString(R);
        dest.writeString(stationEndpointName);
    }

    @JsonIgnore
    protected Forecast(Parcel in) {
        id = in.readLong();
        ftime = in.readString();
        F = in.readInt();
        D = in.readString();
        T = in.readInt();
        W = in.readString();
        V = in.readString();
        N = in.readString();
        TD = in.readString();
        R = in.readString();
        stationEndpointName = in.readString();
    }

    @JsonIgnore
    public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };
}
