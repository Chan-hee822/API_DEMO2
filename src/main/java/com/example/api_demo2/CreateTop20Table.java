package com.example.api_demo2;

import java.awt.geom.Point2D;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateTop20Table {
//    static double lat = 32.1916;
//    static double lnt = 123.3325;
    public static void createTable(Double LAT, Double LNT) {

        String url = "jdbc:mariadb://localhost:3306/testdb3";
        String dbUserId = "testuser3";
        String dbPassword = "zerobase";

        try {
            Class.forName("org.mariadb.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            //table 생성.
            conn = DriverManager.getConnection(url, dbUserId, dbPassword);
            String tName1 = String.valueOf(LAT).replace(".","") + "x" +String.valueOf(LNT).replace(".","");

            String sql1 = String.format(" CREATE TABLE TOP20_%s (DISTANCE DOUBLE, LAT DOUBLE, LNT DOUBLE); ",tName1);
            pstm = conn.prepareStatement(sql1);
            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (pstm != null && !pstm.isClosed()){
                    pstm.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (conn != null && !conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
