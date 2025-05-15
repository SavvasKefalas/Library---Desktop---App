package com.kefalas.library.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    public static boolean addBook(String title, String author, String category) {
        String sql = "INSERT INTO books (title, author, category, available) VALUES (?, ?, ?, true)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, category);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error inserting book: " + e.getMessage());
            return false;
        }
    }


    /**
     * Displays all the available books in the library.
     */

    public static List<String[]> getAvailableBooks() {
        List<String[]> books = new ArrayList<>();
        String sql = "SELECT title, author FROM books WHERE available = true";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                books.add(new String[]{title, author});
            }

        } catch (SQLException e) {
            System.out.println("Error fetching available books: " + e.getMessage());
        }

        return books;
    }

    public static boolean searchBook(String title) {
        String sql = "SELECT * FROM books WHERE title = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("Error searching for book: " + e.getMessage());
            return false;
        }
    }

    public static boolean registerMember(String name) {
        String sql = "INSERT INTO members (name) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }


}
