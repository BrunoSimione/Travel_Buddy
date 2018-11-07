package com.example.bruno.travel_buddy;

import java.util.Date;

public class Cost {
    private String title;
    private Date date;
    private double amount;
    private String category;
    private int stars;
    private String details;

    public Cost() {
    }

    public Cost(String title, Date date, double amount, String category, int stars, String details) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
