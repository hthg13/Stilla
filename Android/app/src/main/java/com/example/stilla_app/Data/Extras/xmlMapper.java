package com.example.stilla_app.Data.Extras;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class xmlMapper {

    // Tákn fyrir mælistærðir í veðurathugunum og veðurspám.
    private String F = "Vindhraði (m/s)";
    private String FX = "Mesti vindhraði (m/s)";
    private String FG = "Mesta vindhviða (m/s)";
    private String D = "Vindstefna (sjá lista af skammstöfunum aftar í skjalinu)";
    private String T = "Hiti (°C)";
    private String W = "Veðurlýsing (sjá lista af lýsingum aftar í skjalinu)";
    private String V = "Skyggni (km)";
    private String N = "Skýjahula (%)";
    private String P = "Loftþrýstingur (hPa)";
    private String RH = "Rakastig (%)";
    private String SNC = "Lýsing á snjó";
    private String SND = "Snjódýpt (cm)";
    private String SED = "Sjólag";
    private String RTE = "Vegahiti (°C)";
    private String TD = "Daggarmark (°C)";
    private String R = "Uppsöfnuð úrkoma (mm / klst) úr sjálfvirkum mælum.";

    // Mögulegar vindstefnur í veðurathugunum og veðurspám
    private String N_DIR = "Norðan";
    private String NNA_DIR = "Norð-norð-austan";
    private String ANA_DIR = "Aust-norð-austan";
    private  String A_DIR = "Austan";
    private String ASA_DIR = "Aust-suð-austan";
    private String SA_DIR = "Suð-austan";
    private String SSA_DIR = "Suð-suð-austan";
    private String S_DIR = "Sunnan";
    private String SSV_DIR = "Suð-suð-vestan";
    private String SV_DIR = "Suð-vestan";
    private String VSV_DIR = "Vest-suð-vestan";
    private String V_DIR = "Vestan";
    private String VNV_DIR = "Vest-norð-vestan";
    private String NV_DIR = "Norð-vestan";
    private String NNV_DIR = "Norð-norð-vestan";


    public void getForecast(/*int weatherStationId*/) {

        String forecastURL = "https://xmlweather.vedur.is/?op_w=xml&type=forec&lang=is&view=xml&ids=1";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(forecastURL).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                alertUserAboutError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String xmlData = response.body().string();
                Log.d("LOG", xmlData);
                xmlToJson(xmlData);
            }
        });
    }

    private void xmlToJson(String xmlData) {
        int INDENTATION = 4;
/*
        try {
            JSONObject jsonObj = XML.toJSONObject(xmlData);
            String json = jsonObj.toString(INDENTATION);
            Log.d("LOG",json);
        } catch (JSONException ex) {
            Log.d("LOG", "json convertion not working)");
        }
        */

    }

    private void alertUserAboutError() {
        //AlertDialogFragment dialog = new AlertDialogFragment();
        //dialog.show(getFragmentManager(), "error_dialog");
        Log.d("LOG", "ALERT USER ABOUT ERROR the get request did not work");
    }
}


