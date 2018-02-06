package com.example.mostafa.aman;


import java.io.Serializable;

public class Model implements Serializable {
    String degree = null;
    String PhoneNumber = null;
    String address = null;
    String doc_name = null;
    String government = null;
    String specialist = null;

    public Model(String degree, String phoneNumber, String address, String doc_name , String government , String specialist) {
        this.degree = degree;
        this.PhoneNumber = phoneNumber;
        this.address = address;
        this.doc_name = doc_name;
        this.government = government;
        this.specialist=specialist;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getGovernment() {
        return government;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDegree() {
        return degree;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getDoc_name() {
        return doc_name;
    }
}
