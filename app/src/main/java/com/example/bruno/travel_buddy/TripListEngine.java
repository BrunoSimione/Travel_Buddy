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
    private int user_id;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void addTripJson(JSONObject jsonObj){

        try {
            Trip t = new Trip(jsonObj.getInt("id") ,jsonObj.getString("title"), jsonObj.getString("location"), "", jsonObj.getString("start_date"),
                    jsonObj.getString("end_date"), 12000);
            this.trip_list.add(t);

            JSONArray jsonPlaces = jsonObj.getJSONArray("places");

            if(jsonPlaces != null && jsonPlaces.length() > 0){
                for(int a = 0; a < jsonPlaces.length(); a++){
                    JSONObject jsonObjCost = jsonPlaces.getJSONObject(a);
                    Place p = new Place(jsonObjCost.getInt("id"),jsonObjCost.getString("name"), jsonObjCost.getString("date"), jsonObjCost.getString("category"),  jsonObjCost.getString("details"), jsonObjCost.getString("location"), jsonObjCost.getInt("visited"));
                    t.getPlace_list().add(p);
                }
            }

            JSONArray jsonCosts = jsonObj.getJSONArray("costs");

            if(jsonCosts != null && jsonCosts.length() > 0){
                for(int a = 0; a < jsonCosts.length(); a++){
                    JSONObject jsonObjCost = jsonCosts.getJSONObject(a);
                    Cost c = new Cost(jsonObjCost.getString("name"), jsonObjCost.getString("date"), jsonObjCost.getDouble("amount"), jsonObjCost.getString("category"), jsonObjCost.getInt("rate"), jsonObjCost.getString("details"));
                    t.getCost_list().add(c);
                }
            }

            Log.e("EngineJSON", "Added");

        } catch (JSONException e) {
            // If there is an error then output this to the logs.
            Log.e("AddTripJson", "Invalid JSON Object. ");
            Log.e("AddTripJson", e.getMessage());

        }
    }
}
