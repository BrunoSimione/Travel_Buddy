package com.example.bruno.travel_buddy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Cost implements Parcelable{
    private String title;
    private String date;
    private double amount;
    private String category;
    private int stars;
    private String details;


    protected Cost(Parcel in) {
        title = in.readString();
        date = in.readString();
        amount = in.readDouble();
        category = in.readString();
        stars = in.readInt();
        details = in.readString();
    }

    public static final Creator<Cost> CREATOR = new Creator<Cost>() {
        @Override
        public Cost createFromParcel(Parcel in) {
            return new Cost(in);
        }

        @Override
        public Cost[] newArray(int size) {
            return new Cost[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeDouble(amount);
        dest.writeString(category);
        dest.writeInt(stars);
        dest.writeString(details);
    }

    public Cost() {
    }

    public Cost(String title, String date, double amount, String category, int stars, String details) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.stars = stars;
        this.details = details;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


}
