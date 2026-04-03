/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.erich.worktracker.v2;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author erich
 */
public class WorkSessionDAO {
    private DataBase dbManager;

    public WorkSessionDAO(DataBase dbManager) {
        this.dbManager = dbManager;
    }
    
    public int startSession(LocalDate date, LocalTime startTime) {
        String SQL = "INSERT INTO workSessions(session_date, session_startTime) Values(?, ?)";
        
        return dbManager.execute(SQL, date, startTime);
    }
    
    public void endSession(LocalTime endTime, int sessionId) {
        String SQL = "UPDATE workSessions SET session_endTime = ? WHERE id = ?";
        
        dbManager.execute(SQL, endTime, sessionId);
    }
    
    public void endBreak(Duration breaktime, int sessionId) {
        String SQL = "INSERT INTO breaks(duration_sec, session_id) VALUES(?, ?)";
        
        dbManager.execute(SQL, breaktime, sessionId);
    }
    
}
