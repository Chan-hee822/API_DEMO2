package com.example.api_demo2;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class wifiDb {

//    public void wifiDbInsert(){
//
//    }
    public static void main(String[] args) {

        String url = "jdbc:mariadb://localhost:3306/testdb3";
        String dbUserId = "testuser3";
        String dbPassword = "zerobase";

        try {
            Class.forName("org.mariadb.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection conn = null;
        Statement stm = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            GetWifiData.getWifiData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Wifi> wf = GetWifiData.wifis;
        System.out.println(wf.size());
        try {
            conn = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " insert into wifi_list " +
                    "(X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, " +
                    "X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, " +
                    "X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, " +
                    "X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, " +
                    "X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) " +
                    "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";

            pstm = conn.prepareStatement(sql);
            int affected = 0;
            for(Wifi wifi1 : wf){
                pstm.setString(1, wifi1.getX_SWIFI_MGR_NO());
                pstm.setString(2, wifi1.getX_SWIFI_WRDOFC());
                pstm.setString(3, wifi1.getX_SWIFI_MAIN_NM());
                pstm.setString(4, wifi1.getX_SWIFI_ADRES1());
                pstm.setString(5, wifi1.getX_SWIFI_ADRES2());
                pstm.setString(6, wifi1.getX_SWIFI_INSTL_FLOOR());
                pstm.setString(7, wifi1.getX_SWIFI_INSTL_TY());
                pstm.setString(8, wifi1.getX_SWIFI_INSTL_MBY());
                pstm.setString(9, wifi1.getX_SWIFI_SVC_SE());
                pstm.setString(10, wifi1.getX_SWIFI_CMCWR());
                pstm.setString(11, wifi1.getX_SWIFI_CNSTC_YEAR());
                pstm.setString(12, wifi1.getX_SWIFI_INOUT_DOOR());
                pstm.setString(13, wifi1.getX_SWIFI_REMARS3());
                pstm.setString(14, wifi1.getLAT());
                pstm.setString(15, wifi1.getLNT());
                pstm.setString(16, wifi1.getWORK_DTTM());
                affected = pstm.executeUpdate();
            }

            System.out.println(affected);
            if(affected > 0){
                System.out.println("INSERT!");
            } else {
                System.out.println(" T0T");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
//            try {
//                if(rs != null && !rs.isClosed()){
//                    rs.close();
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }

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
