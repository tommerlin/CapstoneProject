package com.example.capstoneproject.admin;

import java.io.Serializable;

public class LocationClass implements Serializable {
    String locName, locPostal;
    float locLat, locLong;
    long locPhone;

    public LocationClass(){

    }

    public LocationClass(String locName, String locPostal, float locLat, float locLong, long locPhone) {
        this.locName = locName;
        this.locPostal = locPostal;
        this.locLat = locLat;
        this.locLong = locLong;
        this.locPhone = locPhone;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocPostal() {
        return locPostal;
    }

    public void setLocPostal(String locPostal) {
        this.locPostal = locPostal;
    }

    public double getLocLat() {
        return locLat;
    }

    public void setLocLat(float locLat) {
        this.locLat = locLat;
    }

    public double getLocLong() {
        return locLong;
    }

    public void setLocLong(float locLong) {
        this.locLong = locLong;
    }
    public long getLocPhone() {
        return locPhone;
    }

    public void setLocPhone(long locPhone) {
        this.locPhone = locPhone;
    }
}
