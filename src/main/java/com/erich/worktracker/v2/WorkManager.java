/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.erich.worktracker.v2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import javax.swing.Timer;


/**
 *
 * @author erich
 */
public class WorkManager {
    private LocalDate currentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime breakStartTime;


    public WorkManager(int hours, int minutes) {
        this.currentDate = LocalDate.now();
        this.startTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.endTime = startTime.plusHours(hours).plusMinutes(minutes);
        this.breakStartTime = null;
    }
    
    public LocalTime getFinishTime() {
        return this.endTime.truncatedTo(ChronoUnit.SECONDS);
    }
    
    public LocalTime getStartTime() {
        return this.startTime;
    }
    
    public LocalDate getDate() {
        return this.currentDate;
    }
    
    public String getTime() {
        if(this.startTime == null) {
            return "00:00:00";
        }
        Duration time = Duration.between(LocalTime.now(), this.endTime);
        
        long s = time.getSeconds();
        return String.format("%02d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));       
    }
    
    public boolean isFinished() {
        if(this.endTime == null) return false;
        return LocalTime.now().isAfter(this.endTime);
    }
    
    public void getBreak() {
        this.breakStartTime = LocalTime.now();
        System.out.println("Break taken at: " + LocalTime.now());
    }
    
    
    public Duration endBreak() {
        Duration breakDuration = Duration.between(this.breakStartTime, LocalTime.now());
        this.endTime = endTime.plus(breakDuration);
        
        this.breakStartTime = null;
        return breakDuration;
    }
   
    
    
}
