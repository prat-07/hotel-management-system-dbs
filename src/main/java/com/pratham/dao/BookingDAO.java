package com.pratham.dao;

import com.pratham.db.DBUtil;
import com.pratham.model.Booking;
import com.pratham.model.BookingStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static void addBooking(Booking booking){

    }


    public static void remBooking(int bookingId){

    }

    public static void checkin(int bookingId){

    }

    public static void checkout(int bookingId){

    }

    public static List<Booking> fetchAllBookings(){
        List<Booking> bookings = new ArrayList<>();

        String sql = """
                SELECT b.bid, b.cid, c.phone_no, b.room_no, b.check_in, b.check_out, b.status
                FROM Booking b JOIN Customer c ON b.cid = c.cid
                """;
        try(
                Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ){

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int bid = rs.getInt("bid");
                long cid = rs.getLong("cid");
                long pNo = rs.getLong("phone_no");
                int roomNo = rs.getInt("room_no");
                java.time.LocalDate checkin = rs.getDate("check_in").toLocalDate();
                java.time.LocalDate checkout = rs.getDate("check_out").toLocalDate();
                BookingStatus status = BookingStatus.valueOf(rs.getString("status"));

                Booking booking = new Booking(bid, cid, pNo, roomNo, checkin, checkout);
                booking.setStatus(status);

                bookings.add(booking);


            }

        } catch (Exception e){
            e.getMessage();
        }

        return bookings;
    }
}
