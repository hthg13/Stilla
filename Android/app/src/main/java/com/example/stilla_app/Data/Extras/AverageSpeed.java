package com.example.stilla_app.Data.Extras;

public class AverageSpeed {
    private Double WALKING_KM_H = 5.0;  //wikipedia: https://en.wikipedia.org/wiki/Walking
    private Double RUNNING_KM_H = 12.0; //https://www.kettle100.com/what-is-the-average-human-running-speed/
    private Double DRIVING_KM_H = 85.0; //in Iceland the driving limit is 90 on þjóðvegur1
    private Double CYCLING_KM_H = 23.5; //https://bikecommuterhero.com/whats-the-average-cycling-speed-of-a-bike-commuter/

    public Double getAvgerageSpeed(String transport) {
        switch (transport) {
            case "Walking":
                return WALKING_KM_H;
            case "Running":
                return RUNNING_KM_H;
            case "Driving":
                return DRIVING_KM_H;
            default:
                return CYCLING_KM_H;
        }
    }

    private Double getWALKING_KM_H() {
        return WALKING_KM_H;
    }

    private Double getRUNNING_KM_H() {
        return RUNNING_KM_H;
    }

    private Double getDRIVING_KM_H() {
        return DRIVING_KM_H;
    }

    private Double getCYCLING_KM_H() {
        return CYCLING_KM_H;
    }
}
