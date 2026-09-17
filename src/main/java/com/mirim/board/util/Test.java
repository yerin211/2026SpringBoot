package com.mirim.board.util;

import java.sql.*;

public class Test {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/board";

        try(Connection conn = DriverManager.getConnection(url,"root","password")){
            PreparedStatement pstmt = conn.prepareStatement("select * from posts where id = ?");
            pstmt.setLong(1,1L);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){// 한 줄 한줄 읽기
                System.out.println(rs.getString("title"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
