package com.pratham.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {

    private int bookingId;
    private int customerId;
    private long customerContact;
    private int roomNo;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private long noOfDays;
    private BookingStatus status;

    public Booking(int bookingId, int customerId, long customerContact, int roomNo, LocalDate checkinDate, LocalDate checkoutDate) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.customerContact = customerContact;
        this.roomNo = roomNo;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        noOfDays = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        this.status = BookingStatus.BOOKED;
    }

    //Getters
    public int getBookingId() { return bookingId; }
    public int getCustomerId() { return customerId; }
    public long getCustomerContact() { return customerContact; }
    public int getRoomNo() { return roomNo; }
    public LocalDate getCheckinDate() { return checkinDate; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public long getNoOfDays() { return noOfDays; }
    public BookingStatus getStatus() { return status;}

    //Setter
    public void setStatus(BookingStatus status){
        this.status = status;
    }

}
