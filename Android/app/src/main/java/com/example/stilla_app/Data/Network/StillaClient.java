package com.example.stilla_app.Data.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class StillaClient {

    private static Retrofit retrofit = null;
    private static final String STILLA_API_URL = "http://10.0.2.2:8080"; //localhost
    private static final String VEDUR_API_URL = "https://xmlweather.vedur.is/?op_w=xml&type=forec&lang=is&view=xml&ids=1";
    private static final String GOOGLE_DIRECTIONS_API_CLIENT = "https://maps.googleapis.com/maps/api/directions/json/";

    /**
     * Uses simple converter factory for XML: http://simple.sourceforge.net/home.php
     * @return
     */
    public static Retrofit getVedurstofaClient() {
        return new Retrofit.Builder()
                .baseUrl(STILLA_API_URL)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
    }

    public static Retrofit getStillaClient() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(STILLA_API_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static Retrofit getGoogleDirectionsClient() {
        return new Retrofit.Builder()
                .baseUrl(STILLA_API_URL)
                .client(new OkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

}
