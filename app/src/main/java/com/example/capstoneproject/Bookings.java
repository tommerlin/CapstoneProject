package com.example.capstoneproject;

import java.io.Serializable;
import java.util.Date;

public class Bookings implements Serializable {
    String location_, vType, vName, vAuto, userName, email, img;
    double rental, tax, total;
    long phoneNum;
    Date pickup, returnd;

    public Bookings(){

    }

    public Bookings(String location_, String vType, String vName, String vAuto, double rental, double tax, double total, String userName, String email, long phoneNum, Date pickup, Date returnd, String img) {
        this.location_ = location_;
        this.vType = vType;
        this.vName = vName;
        this.vAuto = vAuto;
        this.rental = rental;
        this.tax = tax;
        this.total = total;
        this.userName = userName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.img = img;
        this.pickup = pickup;
        this.returnd = returnd;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getPickup() {
        return pickup;
    }

    public void setPickup(Date pickup) {
        this.pickup = pickup;
    }

    public Date getReturnd() {
        return returnd;
    }

    public void setReturnd(Date returnd) {
        this.returnd = returnd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTheLocation() {
        return location_;
    }

    public void setTheLocation(String location_) {
        this.location_ = location_;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getvAuto() {
        return vAuto;
    }

    public void setvAuto(String vAuto) {
        this.vAuto = vAuto;
    }

    public double getRental() {
        return rental;
    }

    public void setRental(double rental) {
        this.rental = rental;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
