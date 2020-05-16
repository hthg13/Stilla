package is.hi.lokaverkefni.stilla.stilla_backend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class WeatherForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne//(fetch = FetchType.LAZY)
    //@JoinColumn(name ="TRIPOWNER_ID")
    private Trip weatherforecasttrip;

    private String ftime;
    private int F;
    private String D;
    private int T;
    private String W;
    private String V;
    private String N;
    private String TD;
    private String R;

    public WeatherForecast() {
    }

    public WeatherForecast(Trip trip, String ftime, int f, String d, int t, String w, String v, String n, String TD, String r) {
        this.weatherforecasttrip = trip;
        this.ftime = ftime;
        this.F = f;
        this.D = d;
        this.T = t;
        this.W = w;
        this.V = v;
        this.N = n;
        this.TD = TD;
        this.R = r;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public int getF() {
        return F;
    }

    public void setF(int f) {
        F = f;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public int getT() {
        return T;
    }

    public void setT(int t) {
        T = t;
    }

    public String getW() {
        return W;
    }

    public void setW(String w) {
        W = w;
    }

    public String getV() {
        return V;
    }

    public void setV(String v) {
        V = v;
    }

    public String getN() {
        return N;
    }

    public void setN(String n) {
        N = n;
    }

    public String getTD() {
        return TD;
    }

    public void setTD(String TD) {
        this.TD = TD;
    }

    public String getR() {
        return R;
    }

    public void setR(String r) {
        R = r;
    }
}
