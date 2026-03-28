package com.pratham.dao;

import com.pratham.db.DBUtil;
import com.pratham.util.AlertUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public static boolean validateLogin(String uname, String pwd){
        String sql = "select * from login where username = ? and password = crypt(?, password)";

        try(Connection connection = DBUtil.getConnection()){

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uname);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery();

            //login failed.
            if(!rs.next()){
                AlertUtil.showWarning("Wrong username/password.");
                return false;
            }
            //login success.
            else{
                return true;
            }

        }catch (Exception e) { System.out.println(e.getMessage()); }

        return false;
    }
}
