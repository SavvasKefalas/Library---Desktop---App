package com.kefalas.library.database;


import java.sql.*;

public class LoanManager {

    public static boolean loanABook(String bookTitle, int memberId) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            int bookId;

            try (PreparedStatement stmt = conn.prepareStatement("SELECT bookId FROM books WHERE title = ?")) {
                stmt.setString(1, bookTitle);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    bookId = rs.getInt("bookId");
                } else {
                    return false;
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement("SELECT memberID FROM members WHERE memberID = ?")) {
                stmt.setInt(1, memberId);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    return false;
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO loans (MemberId, BookId, startDate) VALUES (?, ?, NOW())")) {
                stmt.setInt(1, memberId);
                stmt.setInt(2, bookId);
                stmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean returnABook(int memberID, String title) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sqlCheckLoan = "SELECT l.loanID, l.bookID FROM loans l " +
                    "JOIN books b ON l.bookID = b.bookID " +
                    "WHERE b.title = ? AND l.memberID = ? AND l.completed = false LIMIT 1";
            PreparedStatement stmtCheckLoan = conn.prepareStatement(sqlCheckLoan);
            stmtCheckLoan.setString(1, title);
            stmtCheckLoan.setInt(2, memberID);
            ResultSet rs = stmtCheckLoan.executeQuery();

            if (rs.next()) {
                int loanID = rs.getInt("loanID");
                int bookID = rs.getInt("bookID");

                String sqlUpdateLoan = "UPDATE loans SET returndate = NOW(), completed = true WHERE loanID = ?";
                PreparedStatement stmtUpdateLoan = conn.prepareStatement(sqlUpdateLoan);
                stmtUpdateLoan.setInt(1, loanID);
                int rowsLoan = stmtUpdateLoan.executeUpdate();

                if (rowsLoan > 0) {

                    String sqlUpdateBook = "UPDATE books SET available = true WHERE bookID = ?";
                    PreparedStatement stmtUpdateBook = conn.prepareStatement(sqlUpdateBook);
                    stmtUpdateBook.setInt(1, bookID);
                    stmtUpdateBook.executeUpdate();

                    return true;
                }
            }

            return false;

        } catch (SQLException e) {
            System.out.println("Return failed: " + e.getMessage());
            return false;
        }
    }
}
