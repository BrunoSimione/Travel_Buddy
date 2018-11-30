package com.example.bruno.travel_buddy;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trip implements Parcelable{
    private int id;
    private String title;
    private String location;
    private String details;
    private String date_start;
    private String date_end;
    private double budget_initial;
    private List<Cost> cost_list;
    private List<Place> place_list;

    public Trip(int id, String title, String location, String details, String date_start, String date_end, double budget_initial) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.details = details;
        this.date_start = date_start;
        this.date_end = date_end;
        this.budget_initial = budget_initial;
        this.cost_list = new ArrayList<>();
        this.place_list = new ArrayList<>();

        createDummyCost(3);
        createDummyPlaces(3);
    }

    protected Trip(Parcel in) {
        id = in.readInt();
        title = in.readString();
        location = in.readString();
        details = in.readString();
        date_start = in.readString();
        date_end = in.readString();
        budget_initial = in.readDouble();
        cost_list = in.createTypedArrayList(Cost.CREATOR);
        place_list = in.createTypedArrayList(Place.CREATOR);
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getCombinedDate(){
        return getDate_start() + " - " +  getDate_end();
    }
    public double getBudget_initial() {
        return budget_initial;
    }

    public void setBudget_initial(double budget_initial) {
        this.budget_initial = budget_initial;
    }

    public List<Cost> getCost_list() {
        return cost_list;
    }

    public void setCost_list(List<Cost> cost_list) {
        this.cost_list = cost_list;
    }

    public List<Place> getPlace_list() {
        return place_list;
    }

    public void setPlace_list(List<Place> place_list) {
        this.place_list = place_list;
    }

    public double getTotalCost(){
        double total_cost = 0.0;
        for(int i = 0; i < cost_list.size(); i++){
            total_cost += cost_list.get(i).getAmount();
        }
        return total_cost;
    }

    public double getRemainingBudget(){
        return this.budget_initial - getTotalCost();
    }

    public String getPlacesInLine(){
        String line = "";
        for(int i = 0; i < this.place_list.size(); i++){
                line += this.place_list.get(i).getTitle() + "\n";
        }
        System.out.println(line);
        return line;
    }

    public int countPlacesVisited(){
        int count = 0;
        for(int i = 0; i < this.place_list.size(); i++){
            if(this.place_list.get(i).isVisited()){
                count++;
            }
        }
        return count;
    }

    public void createDummyCost(int index){
        for(int i=1; i<=index; i++){
            Cost c = new Cost("Cost" + i, "08-11-2018", 120.00 + i, "Restaurant", 2, "");
            this.cost_list.add(c);
        }
    }

    public void createDummyPlaces(int index){
        for(int i=1; i<=index; i++){
            ////Cost c = new Cost("Cost" + i, "08-11-2018", 120.00, "Restaurant", 2, "");
            Place p = new Place("Place "+ i, "01-11-2018", "Restaurant", "", "", i%2==0 ? true : false);
            this.place_list.add(p);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(location);
        dest.writeString(details);
        dest.writeString(date_start);
        dest.writeString(date_end);
        dest.writeDouble(budget_initial);
        dest.writeTypedList(cost_list);
        dest.writeTypedList(place_list);
    }
}
