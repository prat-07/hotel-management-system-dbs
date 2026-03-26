package com.pratham.dao;

import com.pratham.db.DBUtil;
import com.pratham.model.Room;
import com.pratham.model.RoomType;
import com.pratham.util.AlertUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public static List<Room> getAllRooms(){
        List<Room> rooms = new ArrayList<>();
        //left join room and booking on room_no
        //selecting room_no, type, price, is_booked(new column)
        //if booking.room_no is null then is_booked column value is false, else true
        String sql = """
            SELECT r.room_no, r.type, r.price,
                CASE WHEN b.room_no IS NULL THEN true ELSE false END AS is_available 
            FROM room r 
            LEFT JOIN booking b ON r.room_no = b.room_no 
        """;
        try (
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
        ) {
            while(rs.next()){
                Room room = new Room(
                        rs.getInt("room_no"),
                        RoomType.valueOf(rs.getString("type")),
                        rs.getDouble("price")
                );

                room.setAvailable(rs.getBoolean("is_available"));
                rooms.add(room);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return rooms;
    }

    public static List<Room> getAvailRooms(){
        List<Room> rooms = new ArrayList<>();
        String sql = """
                    SELECT r.* 
                    FROM room r LEFT JOIN booking b 
                    ON r.room_no = b.room_no 
                    WHERE b.room_no IS NULL
                """;
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
        ) {
            while(rs.next()){
                Room room = new Room(
                        rs.getInt("room_no"),
                        RoomType.valueOf(rs.getString("type")),
                        rs.getDouble("price")
                );

                room.setAvailable(true);
                rooms.add(room);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return rooms;
    }

    public static void addRoom(Room room){

        String insertSql = " INSERT INTO room VALUES(?, ?, ?); ";
        String checkSql = "SELECT * FROM room WHERE room_no = ?";

        try( Connection connection = DBUtil.getConnection(); ){
            //check if room already present
            try(PreparedStatement ps = connection.prepareStatement(checkSql);){
                ps.setInt(1, room.getRoomNo());
                ResultSet rs = ps.executeQuery();

                //if there is an entry
                if(rs.next()){
                    AlertUtil.showError("Room already present.");
                    return;
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

            //add room
            try(PreparedStatement ps = connection.prepareStatement(insertSql);){
                ps.setInt(1, room.getRoomNo());
                ps.setString(2, room.getType().name());
                ps.setDouble(3, room.getPrice());

                ps.executeUpdate();

                AlertUtil.showSuccess("Room added.");

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void remRoom(){

    }
}
