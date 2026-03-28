package com.pratham.dao;

import com.pratham.db.DBUtil;
import com.pratham.model.Booking;
import com.pratham.model.BookingStatus;
import com.pratham.util.AlertUtil;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static void addBooking(long cid, int roomNo, LocalDate checkin, LocalDate checkout){
        String sql = """
                INSERT INTO booking(cid, room_no, check_in, check_out) VALUES(?, ?, ?, ?)
                """;

        try( Connection connection = DBUtil.getConnection(); ){

            try(PreparedStatement ps = connection.prepareStatement(sql);){

                ps.setLong(1, cid);
                ps.setInt(2, roomNo);
                ps.setDate(3, Date.valueOf(checkin));
                ps.setDate(4, Date.valueOf(checkout));

                ps.executeUpdate();

            }catch (SQLException e){
                String code = e.getSQLState();

                if("23505".equals(code)){
                    AlertUtil.showWarning("Room is already booked or duplicate booking exists.");
                }
                else if("23503".equals(code)){
                    AlertUtil.showWarning("Invalid customer ID or room number.");
                }
                else if("23514".equals(code)){
                    AlertUtil.showWarning("Invalid booking dates. Checkout must be after check-in.");
                }
                else if("23502".equals(code)){
                    AlertUtil.showWarning("All fields are required.");
                }
                else if("22P02".equals(code)){
                    AlertUtil.showWarning("Invalid data format.");
                }
                else if("22003".equals(code)){
                    AlertUtil.showWarning("Numeric value out of range.");
                }
                else{
                    AlertUtil.showWarning("Database error: " + e.getMessage());
                }
            }
        }
        catch (Exception e){ System.out.println(e.getMessage());}
    }

    public static void remBooking(int bookingId){
        String fetchSql = "SELECT * FROM booking WHERE bid = ?";
        String sql = "DELETE FROM booking WHERE bid = ?";

        try(Connection connection = DBUtil.getConnection()){

            //handle corner cases for deleting.
            try(PreparedStatement ps = connection.prepareStatement(fetchSql)){
                 ps.setInt(1, bookingId);
                 ResultSet rs = ps.executeQuery();

                //if not bookingId found
                if(!rs.next()){
                    AlertUtil.showWarning("No booking found.");
                    return;
                }

                //Check if booking status is something other that BOOKED. Disallow removal.
                if(!rs.getString("status").equals("BOOKED")){

                    if(rs.getString("status").equals("CHECKED_OUT")){
                        AlertUtil.showWarning("Cannot remove booking. Customer has already checked out.");
                    }
                    else{
                        AlertUtil.showWarning("Cannot remove booking. Customer has already checked in.");
                    }
                    return;
                }


            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

            //delete booking.
            try(PreparedStatement ps = connection.prepareStatement(sql)){

                ps.setInt(1, bookingId);

                if(AlertUtil.takeConfirmation("Do you want to delete booking?") == ButtonType.OK)  {
                    ps.executeUpdate();
                    AlertUtil.showSuccess("Booking removed.");
                }

            }catch (SQLException e){ System.out.println(e.getMessage()); }
        }
        catch (Exception e){ System.out.println(e.getMessage()); }
    }

    public static void changeStatusToCheckedIn(int bookingId){
        String fetchSql = "select * from booking where bid = ?";
        String updateSql = "update booking set status = ? where bid = ?";

        try(Connection connection = DBUtil.getConnection()){

            //handle corner cases
            try(PreparedStatement ps = connection.prepareStatement(fetchSql)){

                ps.setInt(1, bookingId);
                ResultSet rs = ps.executeQuery();

                //no entry for bookingId
                if(!rs.next()){
                    AlertUtil.showWarning("No entry found.");
                    return;
                }

                String status = rs.getString("status");

                if(status.equals("CHECKED_IN")){
                    AlertUtil.showWarning("Already checked in.");
                    return;
                }else if(status.equals("CHECKED_OUT")){
                    AlertUtil.showWarning("Customer has already checked out.");
                    return;
                }

            }catch (SQLException e){ System.out.println(e.getMessage()); }

            //update booking
            try(PreparedStatement ps = connection.prepareStatement(updateSql)){

                ps.setString(1, "CHECKED_IN");
                ps.setInt(2, bookingId);

                if(AlertUtil.takeConfirmation("Do you want to checkin?") == ButtonType.OK){
                    ps.executeUpdate();
                    AlertUtil.showSuccess("Checked in successfully.");
                }

            }catch (SQLException e){ System.out.println(e.getMessage()); }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void changeStatusToCheckedOut(int bookingId){
        String fetchSql = "select * from booking where bid = ?";
        String updateSql = "update booking set status = ? where bid = ?";

        try(Connection connection = DBUtil.getConnection()){

            //handle corner cases
            try(PreparedStatement ps = connection.prepareStatement(fetchSql)){

                ps.setInt(1, bookingId);
                ResultSet rs = ps.executeQuery();

                //no entry for bookingId
                if(!rs.next()){
                    AlertUtil.showWarning("No entry found.");
                    return;
                }

                String status = rs.getString("status");

                if(status.equals("BOOKED")){
                    AlertUtil.showWarning("Customer has not checked in yet.");
                    return;
                }else if(status.equals("CHECKED_OUT")){
                    AlertUtil.showWarning("Customer has already checked out.");
                    return;
                }

            }catch (SQLException e){ System.out.println(e.getMessage()); }

            //update booking
            try(PreparedStatement ps = connection.prepareStatement(updateSql)){

                ps.setString(1, "CHECKED_OUT");
                ps.setInt(2, bookingId);

                if(AlertUtil.takeConfirmation("Do you want to checkout?") == ButtonType.OK){
                    ps.executeUpdate();
                    AlertUtil.showSuccess("Checked out successfully.");
                }

            }catch (SQLException e){ System.out.println(e.getMessage()); }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

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
