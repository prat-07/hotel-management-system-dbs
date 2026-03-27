package com.pratham.dao;

import com.pratham.db.DBUtil;
import com.pratham.model.Customer;
import com.pratham.util.AlertUtil;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static void addCustomer(Customer customer){
        String checkSql = "SELECT * FROM customer WHERE cid = ?";
        String insertSql = """ 
                    INSERT INTO customer(cid, fname, lname, phone_no, street_no, city, state, pincode)
                    VALUES(?, ?, ?, ?, ?, ?, ?, ?);
            """;

        try( Connection connection = DBUtil.getConnection(); ){

            //check if customer already present in customer table
            try(PreparedStatement ps = connection.prepareStatement(checkSql);){
                ps.setLong(1, customer.getCid());
                ResultSet rs = ps.executeQuery();

                //if there is an entry
                if(rs.next()){
                    if(AlertUtil.takeConfirmation("There is an entry present with the provided Customer ID. Do you wish to update the entry?")
                            == ButtonType.OK){
                        //if OK clicked update entry.
                        String updateSql = """
                                UPDATE customer SET fname = ?, lname = ?, phone_no = ?, street_no = ?, city = ?, state = ?, pincode = ?
                                WHERE cid = ?
                                """;

                        try(PreparedStatement psUpdate = connection.prepareStatement(updateSql);){
                            psUpdate.setString(1, customer.getFName());
                            psUpdate.setString(2, customer.getLName());
                            psUpdate.setLong(3, customer.getPhoneNo());
                            psUpdate.setString(4, customer.getStreetNo());
                            psUpdate.setString(5, customer.getCity());
                            psUpdate.setString(6, customer.getState());
                            psUpdate.setInt(7, customer.getPincode());
                            psUpdate.setLong(8, customer.getCid());

                            psUpdate.executeUpdate();

                            AlertUtil.showSuccess("Customer Updated.");
                        }catch (SQLException e){
                            if("23505".equals(e.getSQLState())){
                                AlertUtil.showWarning("Customer with same phone no exists.");
                            }
                            else{
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    return;
                }



            }catch (SQLException e){
                System.out.println(e.getMessage());
            }

            //add customer
            try(PreparedStatement ps = connection.prepareStatement(insertSql);){
                ps.setLong(1, customer.getCid());
                ps.setString(2, customer.getFName());
                ps.setString(3, customer.getLName());
                ps.setLong(4, customer.getPhoneNo());
                ps.setString(5, customer.getStreetNo());
                ps.setString(6, customer.getCity());
                ps.setString(7, customer.getState());
                ps.setInt(8, customer.getPincode());

                ps.executeUpdate();

                AlertUtil.showSuccess("Customer added.");

            }catch(SQLException e){
                if("23505".equals(e.getSQLState())){
                    AlertUtil.showWarning("Customer with same phone no exists.");
                }
                else{
                    System.out.println(e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void remCustomer(long cid){

        String checkPresentSql = "SELECT * FROM customer where cid = ?";
        String checkBookedSql = "SELECT * FROM booking where cid = ?";
        String remSql = "DELETE FROM customer WHERE cid = ?";

        try(Connection connection = DBUtil.getConnection();){

            //Customer not found
            try(PreparedStatement ps = connection.prepareStatement(checkPresentSql)){
                ps.setLong(1, cid);
                ResultSet rs = ps.executeQuery();

                //if no entry in customer table
                if(!rs.next()){
                    AlertUtil.showWarning("No customer found.");
                    return;
                }

            }catch (SQLException e){ System.out.println(e.getMessage()); }

            //Customer currently staying
            try(PreparedStatement ps = connection.prepareStatement(checkBookedSql)){
                ps.setLong(1, cid);
                ResultSet rs = ps.executeQuery();

                //if entry in booking table
                if(rs.next()){
                    AlertUtil.showWarning("Cannot remove customer. They have booked room(s).");
                    return;
                }

            }catch (SQLException e){ System.out.println(e.getMessage()); }

            //Remove customer
            try(PreparedStatement ps = connection.prepareStatement(remSql)){
                if(AlertUtil.takeConfirmation("You do wish to delete this customer?") == ButtonType.OK){
                    ps.setLong(1, cid);
                    ps.executeUpdate();
                    AlertUtil.showSuccess("Customer removed.");
                }
            }catch (SQLException e){ System.out.println(e.getMessage()); }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
