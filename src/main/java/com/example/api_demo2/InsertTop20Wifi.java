package com.example.api_demo2;

import java.awt.geom.Point2D;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InsertTop20Wifi {
    public static void insertData(Double LAT, Double LNT) {
        double lat = 32.1916;
        double lnt = 123.3325;
        List<List> distances = WifiDistanceCalculator.calculateDistance(lat, lnt);
        System.out.println(distances.size());

        String url = "jdbc:mariadb://localhost:3306/testdb3";
        String dbUserId = "testuser3";
        String dbPassword = "zerobase";

        try {
            Class.forName("org.mariadb.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        PreparedStatement pstm2 = null;

        try {
            //table 생성.
            conn = DriverManager.getConnection(url, dbUserId, dbPassword);
            String tName1 = String.valueOf(LAT).replace(".","") + "x" +String.valueOf(LNT).replace(".","");

            String sql2 = String.format(" insert into TOP20_%s (DISTANCE, LAT, LNT) values (?,?,?); ", tName1);
            pstm2 = conn.prepareStatement(sql2);
            int cnt = 0;
            for (List A : distances) {
                double d1 = (double) A.get(0);
                Point2D p1 = (Point2D) A.get(1);
                pstm2.setDouble(1, d1);
                pstm2.setDouble(2, p1.getX());
                pstm2.setDouble(3, p1.getY());
                pstm2.executeUpdate();
                cnt++;
                if(cnt == 20 ){
                    break;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (pstm2 != null && !pstm2.isClosed()){
                    pstm2.close();
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
