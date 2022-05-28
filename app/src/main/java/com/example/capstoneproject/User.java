package com.example.capstoneproject;

import java.util.Date;

public class User {
    public String firstName, lastName, licenceNumber, licenceIssuedBy, email ;
    public Date licenceIssuedDate, licenceExpiryDate, dob;
    public long phNumber;

    public User(){

    }

    public User(String firstName, String lastName, String licenceNumber, String licenceIssuedBy, String email, Date licenceIssuedDate, Date licenceExpiryDate, Date dob, long phNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenceNumber = licenceNumber;
        this.licenceIssuedBy = licenceIssuedBy;
        this.email = email;
        this.licenceIssuedDate = licenceIssuedDate;
        this.licenceExpiryDate = licenceExpiryDate;
        this.dob = dob;
        this.phNumber = phNumber;
    }
}
