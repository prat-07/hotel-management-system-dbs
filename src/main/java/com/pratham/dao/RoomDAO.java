package com.pratham.dao;

import com.pratham.db.DBUtil;
import com.pratham.model.Room;
import com.pratham.model.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

}
