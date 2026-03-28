package com.pratham.dao;

import com.pratham.db.DBUtil;
import com.pratham.model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public static List<Bill> fetchBills(){
        List<Bill> bills = new ArrayList<>();

        String sql = """
                    SELECT b.bid, b.cid, c.phone_no, b.room_no, b.check_in, b.check_out, b.total_amount
                    FROM bill b JOIN customer c ON b.cid = c.cid
                    """;

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Bill bill = new Bill(
                        rs.getInt("bid"),
                        rs.getLong("cid"),
                        rs.getLong("phone_no"),
                        rs.getInt("room_no"),
                        rs.getDate("check_in").toLocalDate(),
                        rs.getDate("check_out").toLocalDate(),
                        rs.getDouble("total_amount")
                );

                bills.add(bill);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return bills;
    }
}
