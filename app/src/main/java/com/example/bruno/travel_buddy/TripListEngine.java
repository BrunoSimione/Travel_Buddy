package com.example.bruno.travel_buddy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripListEngine {
    private List<Trip> trip_list;

    public TripListEngine() {
        this.trip_list = new ArrayList<>();
    }

    public TripListEngine(List<Trip> trip_list) {
        this.trip_list = trip_list;
    }

    public List<Trip> getTrip_list() {
        return trip_list;
    }

    public void setTrip_list(List<Trip> trip_list) {
        this.trip_list = trip_list;
    }

    public Trip getTrip(int index){
        return this.trip_list.get(index);
    }

    public void createDummyData(int count){
        for(int i=1; i<=count; i++){
            Trip t = new Trip("Trip " + i, "Location " + 1, "", "07-11-2018", "12-11-2018", 1000);
            t.createDummyCost(3);
            trip_list.add(t);
        }
    }
}
