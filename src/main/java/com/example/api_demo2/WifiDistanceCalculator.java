package com.example.api_demo2;

import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WifiDistanceCalculator {
    public static List<List> calculateDistance(Double lat, Double lnt) {

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
        ResultSet rs = null;

        //ArrayList<Wifi> wf = GetWifiData.wifis;
        try {
            Point2D specificLocation = new Point2D.Double(lat, lnt);
            int x1 = (int) specificLocation.getX() - 20;
            int x2 = (int) specificLocation.getX() + 20;
            int y1 = (int) specificLocation.getY() - 20;
            int y2 = (int) specificLocation.getY() + 20;

            conn = DriverManager.getConnection(url, dbUserId, dbPassword);
//            String sql = String.format(
//                    " select X_SWIFI_MGR_NO, LAT, LNT " +
//                            " from wifi_list " +
//                            " where LAT >= %d and LAT <= %d and LNT >= %d and LNT <= %d ",x1,x2,y1,y2);

            pstm = conn.prepareStatement(
                    " select X_SWIFI_MGR_NO, LAT, LNT from wifi_list " +
                            " where LAT >= " + x1 + " and " + " LAT <= " + x2 + " and " +
                            " LNT >= " + y1 + " and " + " LNT <= " + y2 + " ; "
            );
            rs = pstm.executeQuery();


            List<List> distances = new ArrayList<>();

            while (rs.next()) {
                double latitude = rs.getDouble("LAT");
                double longitude = rs.getDouble("LNT");

                Point2D location = new Point2D.Double(latitude, longitude);
                double distance = specificLocation.distance(location);

                List<Object> A = new ArrayList<>();
                A.add(distance);
                A.add(location);

                distances.add(A);

            }
            distances.sort((p1, p2) -> (int) ((double) p1.get(0) - (double) p2.get(0)));

            int size = distances.size();
            for (int i = 0; i < 20 && i < distances.size(); i++) {
                List d = distances.get(i);
                //System.out.println((i+1) + ". " + d.get(1) + ": " + d.get(0) + " km");
            }


            return distances;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(rs != null && !rs.isClosed()){
                   rs.close();
              }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

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
