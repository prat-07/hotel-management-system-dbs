package com.pratham.model;

public class Customer {

    private long cid;
    private String fName;
    private String lName;
    private long phoneNo;
    private String streetNo;
    private String city;
    private String state;
    private int pincode;
    private boolean isStaying;

    public Customer(long cid, String fName, String lName, long phoneNo, String streetNo, String city, String state, int pincode) {
        this.cid = cid;
        this.fName = fName;
        this.lName = lName;
        this.phoneNo = phoneNo;
        this.streetNo = streetNo;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.isStaying = false;
    }

    //Getters
    public long getCid() { return cid; }
    public String getFName() { return fName; }
    public String getLName() { return lName; }
    public long getPhoneNo() { return phoneNo; }
    public String getStreetNo() { return streetNo; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public int getPincode() { return pincode; }
    public boolean isStaying() { return isStaying; }

    //Setter
    public void setStaying(boolean value){ this.isStaying = value; }
}
