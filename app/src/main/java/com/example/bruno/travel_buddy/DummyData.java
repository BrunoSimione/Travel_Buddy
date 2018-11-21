package com.example.bruno.travel_buddy;

import java.util.ArrayList;
import java.util.List;

public class DummyData {
    private static List<Trip> trip_list = new ArrayList<Trip>();

    public static List<Trip> getTrip_list() {
        return trip_list;
    }

    public static void setTrip_list(List<Trip> trip_list) {
        trip_list = trip_list;
    }

    public static Trip getTrip(int index){
        return trip_list.get(index);
    }

    /*
    public static void createDummyData(int count){
        for(int i=1; i<=count; i++){
            Trip t = new Trip("Trip " + i, "Location " + 1, "", "07-11-2018", "12-11-2018", 1000);
            t.createDummyCost(3);
            trip_list.add(t);
        }
    }
    */
}
