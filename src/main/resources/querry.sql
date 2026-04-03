/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  erich
 * Created: Mar 24, 2026
 */

CREATE TABLE IF NOT EXISTS workSessions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    session_date DATE NOT NULL,
    session_startTime DATETIME NOT NULL, 
    session_endTime DATETIME
);

CREATE TABLE IF NOT EXISTS breaks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    session_id INTEGER NOT NULL,
    duration_sec INTEGER NOT NULL,
    FOREIGN KEY(session_id) REFERENCES workSessions(id) ON DELETE CASCADE
);