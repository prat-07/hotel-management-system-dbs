package com.pratham.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/hotel_management_system";
    private static final String UNAME = "postgres";
    private static final String PWD = "0000";

    public static Connection getConnection() throws Exception{
        return DriverManager.getConnection(URL, UNAME, PWD);
    }
}
