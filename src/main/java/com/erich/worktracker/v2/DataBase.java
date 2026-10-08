/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.erich.worktracker.v2;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author erich
 */
public class DataBase {

private final String URL = "jdbc:sqlite:/home/erich/NetBeansProjects/WorkTracker-v2/tracker.db";
    private Connection connect() {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }
    
    public void initialise() {
        try (InputStream in = DataBase.class.getResourceAsStream("/querry.sql");
             Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            
            if(in == null) {
                System.err.println("Couldnt find querry.sql");
                return;
            }
            
            Scanner scanner = new Scanner(in);
            
            StringBuilder sb = new StringBuilder();
            while(scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
            
            stmt.executeUpdate(sb.toString());
            System.out.println("Table CREATED!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int execute(String sql, Object... params) {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            for(int i = 0; i < params.length; i++) {
                pstmt.setObject(i+1, params[i]);
            }
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) return rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
}
