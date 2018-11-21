package com.example.bruno.travel_buddy;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        //return DummyData.getTrip_list();
    }

    public void setTrip_list(List<Trip> trip_list) {
        this.trip_list = trip_list;
    }

    public Trip getTrip(int index){
        return this.trip_list.get(index);
        //return DummyData.getTrip(index);
    }

    /*
    public void createDummyData(int count){
        for(int i=1; i<=count; i++){
            Trip t = new Trip("Trip " + i, "Location " + 1, "", "07-11-2018", "12-11-2018", 1000);
            t.createDummyCost(3);
            trip_list.add(t);
        }
    }

    public void createDummyData2(int count){
        for(int i=1; i<=count; i++){
            Trip t = new Trip("Trip " + i, "Location " + 1, "", "07-11-2018", "12-11-2018", 1000);
            t.createDummyCost(3);
            DummyData.getTrip_list().add(t);
        }
    }
    */

    public void addTripJson(JSONObject jsonObj){

        try {
            Trip t = new Trip(jsonObj.getInt("id") ,jsonObj.getString("title"), jsonObj.getString("location"), "", jsonObj.getString("start_date"),
                    jsonObj.getString("end_date"), 12000);
            this.trip_list.add(t);
            Log.e("EngineJSON", "Added");

        } catch (JSONException e) {
            // If there is an error then output this to the logs.
            Log.e("AddTripJson", "Invalid JSON Object. ");
            Log.e("AddTripJson", e.getMessage());

        }
    }
}
