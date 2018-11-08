package com.example.bruno.travel_buddy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trip {
    private String title;
    private String location;
    private String details;
    private String date_start;
    private String date_end;
    private double budget_initial;
    private List<Cost> cost_list;
    private List<Place> place_list;
    //abc

    public Trip(String title, String location, String details, String date_start, String date_end, double budget_initial) {
        this.title = title;
        this.location = location;
        this.details = details;
        this.date_start = date_start;
        this.date_end = date_end;
        this.budget_initial = budget_initial;
        this.cost_list = new ArrayList<>();
        this.place_list = new ArrayList<>();

        createDummyCost(3);
        createDummyPlaces(5);
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

    public void createDummyCost(int index){
        for(int i=1; i<=index; i++){
            Cost c = new Cost("Cost" + i, "08-11-2018", 120.00, "Restaurant", 2, "");
            this.cost_list.add(c);
        }
    }

    public void createDummyPlaces(int index){
        for(int i=1; i<=index; i++){
            //Cost c = new Cost("Cost" + i, "08-11-2018", 120.00, "Restaurant", 2, "");
            Place p = new Place("Place "+ i, "01-11-2018", "Restaurant", "", "", i%2==0 ? true : false);
            this.place_list.add(p);
        }
    }
}
