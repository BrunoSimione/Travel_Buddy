package com.example.bruno.travel_buddy;

public class Place {
    private String title;
    private String date;
    private String category;
    private String details;
    private String location;
    private boolean visited;

    public Place(String title, String date, String category, String details, String location, boolean visited) {
        this.title = title;
        this.date = date;
        this.category = category;
        this.details = details;
        this.location = location;
        this.visited = visited;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
