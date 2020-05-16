package com.example.stilla_app.Data.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Forecast {

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
}
