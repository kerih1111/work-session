/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.erich.worktracker.v2;
import com.formdev.flatlaf.FlatDarculaLaf;

/**
 *
 * @author erich
 */
public class WorkTrackerV2 {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        new TrackerFrame().setVisible(true);
        DataBase db = new DataBase();
        db.initialise();
    }
}
