package com.example.stilla_app.Data.Network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class StillaClient {

    private static Retrofit retrofit = null;
    private static final String STILLA_API_URL = "http://10.0.2.2:8080"; //localhost
    private static final String VEDUR_API_URL = "https://xmlweather.vedur.is/?op_w=xml&type=forec&lang=is&view=xml&ids=1";

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
        return new Retrofit.Builder()
                .baseUrl(STILLA_API_URL)
                .client(new OkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
