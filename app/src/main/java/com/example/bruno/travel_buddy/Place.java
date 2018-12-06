package com.example.bruno.travel_buddy;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable{
    private String title;
    private String date;
    private String category;
    private String details;
    private String location;
    private boolean visited;

    public Place(String title, String date, String category, String details, String location, int visited) {
        this.title = title;
        this.date = date;
        this.category = category;
        this.details = details;
        this.location = location;
        if(visited == 1){
            this.visited = true;
        }else{
            this.visited = false;
        }

    }

    //PARCELABLE METHODS
    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.category);
        dest.writeString(this.details);
        dest.writeString(this.location);
        dest.writeByte((byte) (this.visited ? 1 : 0));
    }

    public Place(Parcel in){
        this.title = in.readString();
        this.date = in.readString();
        this.category = in.readString();
        this.details = in.readString();
        this.location = in.readString();
        this.visited = (in.readByte() != 0);
    }

    //GETTERS ASN SETTERS
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
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }


}
