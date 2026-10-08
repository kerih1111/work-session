# Work Session & Focus Tracker (Java / SQLite)

A desktop productivity tracking application built with Java Swing and SQLite. Made to log work in a period of time and store it to improve productivity of a user and hit targets.

## Architectural Highlights

- **Data Access Object (DAO) Pattern:** Encapsulates all relational database operations inside `WorkSessionDAO`, cleanly isolating data persistence logic from business and UI layers.
- **Relational Persistence:** Uses SQLite with DDL definitions (`querry.sql`) to track session start/end timestamps, duration metrics, and associated break events.
- **State & Event Management:** Decoupled `WorkManager` orchestration handling state transitions (Active, On Break, Completed) tied to a reactive Swing GUI (`TrackerFrame`).

## Tech Stack
- **Language:** Java
- **Build System:** Apache Maven
- **Database:** SQLite / JDBC
- **Architecture:** DAO Pattern, MVC-style separation

## Project Structure
```text
WorkTracker-v2/
├── src/
│   ├── main/
│   │   ├── java/com/erich/worktracker/v2/
│   │   │   ├── DataBase.java
│   │   │   ├── TrackerFrame.java
│   │   │   ├── WorkManager.java
│   │   │   ├── WorkSessionDAO.java
│   │   │   └── WorkTrackerV2.java
│   │   └── resources/
│   │       └── querry.sql
├── pom.xml
└── README.md
