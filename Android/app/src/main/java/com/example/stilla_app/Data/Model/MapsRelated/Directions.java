package com.example.stilla_app.Data.Model.MapsRelated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Directions {

    private List<Routes> routes;

    public List<Routes> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Routes> routes) {
        this.routes = routes;
    }


// HELPER FUNCTIONS

    public List<Location> getAllLocations() {
        int n = this.routes.get(0).getLegs().get(0).getSteps().size();
        List<Steps> stepsList = this.routes.get(0).getLegs().get(0).getSteps();
        List<Location> locationList = new ArrayList<>();

        for(int i=0; i<n; i++) {
            Steps currentStep = stepsList.get(i);
            locationList.add(currentStep.getStart_location());// either start or end is enough
            //locationList.add(currentStep.getEnd_location());
        }

        return locationList;
    }

    public List<LatLng> getAllLatLng() {
        int n = this.routes.get(0).getLegs().get(0).getSteps().size();
        List<Steps> stepsList = this.routes.get(0).getLegs().get(0).getSteps();
        List<LatLng> latLngList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Steps currentStep = stepsList.get(i);
            latLngList.add(currentStep.getStart_location().getLatLng());
            if (i > 0) {
                if (!(latLngList.get(i).equals(latLngList.get(i - 1))))
                    latLngList.add(currentStep.getEnd_location().getLatLng());
            }
            // either start or end is enough
        }

        return latLngList;
    }
}
