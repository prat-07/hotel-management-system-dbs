package com.pratham.dao;

import com.pratham.db.DBUtil;
import com.pratham.model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public void addBooking(Booking booking){

    }


    public void remBooking(int bookingId){

    }

    public List<Booking> fetchAllBookings(){
        List<Booking> bookings = new ArrayList<>();

        return bookings;
    }
}
