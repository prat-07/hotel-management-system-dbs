package com.pratham.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Bill {

    private int bid;
    private long cid;
    private long phoneNo;
    private int roomNo;
    private LocalDate checkin;
    private LocalDate checkout;
    private long daysStayed;
    private double totalPrice;

    public Bill(int bid, long cid, long phoneNo, int roomNo, LocalDate checkin, LocalDate checkout, double totalPrice) {
        this.bid = bid;
        this.cid = cid;
        this.phoneNo = phoneNo;
        this.roomNo = roomNo;
        this.checkin = checkin;
        this.checkout = checkout;
        this.daysStayed = ChronoUnit.DAYS.between(checkin, checkout);
        this.totalPrice = totalPrice;
    }

    //Getters
    public int getBid() { return bid; }
    public long getCid() { return cid; }
    public long getPhoneNo() { return phoneNo; }
    public int getRoomNo() { return roomNo; }
    public LocalDate getCheckin() { return checkin; }
    public LocalDate getCheckout() { return checkout; }
    public long getDaysStayed() { return daysStayed; }
    public double getTotalPrice() { return totalPrice; }

}
