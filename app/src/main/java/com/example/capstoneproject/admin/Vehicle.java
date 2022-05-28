package com.example.capstoneproject.admin;

import java.io.Serializable;

public class Vehicle implements Serializable {
    String name, category, type, imageUrl, location;
    boolean automatic;
    int people, luggage, count;
    double tax, rent;

    public Vehicle(){

    }

    public Vehicle(boolean automatic, String category,int count,String imageUrl,int luggage,String name,int people,double rent,double tax, String type, String location) {
        this.name = name;
        this.category = category;
        this.automatic = automatic;
        this.type = type;
        this.imageUrl = imageUrl;
        this.people = people;
        this.luggage = luggage;
        this.count = count;
        this.tax = tax;
        this.rent = rent;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean getAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getLuggage() {
        return luggage;
    }

    public void setLuggage(int luggage) {
        this.luggage = luggage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }
}
