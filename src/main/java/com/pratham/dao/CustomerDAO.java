package com.pratham.dao;

import com.pratham.db.DBUtil;
import com.pratham.model.Customer;
import com.pratham.model.Room;
import com.pratham.model.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public static List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();

        String sql = """
            SELECT c.cid, c.fname, c.lname, c.phone_no, c.street_no, c.city, c.state, c.pincode,
                CASE WHEN b.room_no IS NOT NULL THEN true ELSE false END AS is_staying
            FROM customer c
            LEFT JOIN booking b ON c.cid = b.cid
        """;
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
        ) {
            while(rs.next()){

                Customer customer = new Customer(
                        rs.getLong("cid"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getLong("phone_no"),
                        rs.getString("street_no"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getInt("pincode")
                );

                customer.setStaying(rs.getBoolean("is_staying"));
                customers.add(customer);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return customers;
    }

    public static List<Customer> getStayingCustomers(){
        List<Customer> customers = new ArrayList<>();

        String sql = """
                    SELECT c.* 
                    FROM customer c LEFT JOIN booking b 
                    ON c.cid = b.cid 
                    WHERE b.cid IS NOT NULL
                """;
        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
        ) {
            while(rs.next()){

                Customer customer = new Customer(
                        rs.getLong("cid"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getLong("phone_no"),
                        rs.getString("street_no"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getInt("pincode")
                );

                customer.setStaying(true);
                customers.add(customer);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return customers;
    }
}
