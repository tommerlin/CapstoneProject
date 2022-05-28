package com.example.capstoneproject;

import java.io.Serializable;
import java.util.Date;

public class UserBasicDetails implements Serializable{
    public String firstName;
    public String lastName;
    public String licenceIssuedBy;
    public String licenceNumber;
    public Date licenceIssueDate;
    public Date licenceExpiryDate;
    public Date dateOfBirth;

    public UserBasicDetails(String firstName, String lastName, String licenceIssuedBy, String licenceNumber, Date licenceIssueDate, Date licenceExpiryDate, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenceIssuedBy = licenceIssuedBy;
        this.licenceNumber = licenceNumber;
        this.licenceIssueDate = licenceIssueDate;
        this.licenceExpiryDate = licenceExpiryDate;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenceIssuedBy() {
        return licenceIssuedBy;
    }

    public void setLicenceIssuedBy(String licenceIssuedBy) {
        this.licenceIssuedBy = licenceIssuedBy;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public Date getLicenceIssueDate() {
        return licenceIssueDate;
    }

    public void setLicenceIssueDate(Date licenceIssueDate) {
        this.licenceIssueDate = licenceIssueDate;
    }

    public Date getLicenceExpiryDate() {
        return licenceExpiryDate;
    }

    public void setLicenceExpiryDate(Date licenceExpiryDate) {
        this.licenceExpiryDate = licenceExpiryDate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
