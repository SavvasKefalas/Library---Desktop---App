# Library Desktop Application

## Overview
A Java desktop application for managing a small library system. The application allows users to manage books and loans through a graphical user interface while storing data in a relational database.

The project demonstrates object-oriented design, database integration, and separation of concerns between the GUI, business logic, and data access layers.

## Features
- Book management (create, update, and manage records)
- Loan management for library members
- Graphical user interface (GUI)
- Persistent data storage using a SQL database
- Modular architecture with separate GUI and database layers
- Unit tests for core components

## Project Structure
The application follows a layered architecture:

- **GUI layer** – User interaction through a desktop interface
- **Application layer** – Core business logic and workflows
- **Database layer** – Database connections and data operations
- **Model classes** – Entities such as books, loans, etc.

## Technologies
- Java
- Maven
- SQL database (JDBC)
- Java Swing (GUI)
- JUnit (testing)

## How to Run
1. Clone the repository
2. Configure your database connection settings
3. Build the project using Maven:

   ```bash
   mvn clean package
