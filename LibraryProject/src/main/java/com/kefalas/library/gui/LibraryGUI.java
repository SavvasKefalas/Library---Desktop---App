package com.kefalas.library.gui;

import com.kefalas.library.database.DatabaseManager;
import com.kefalas.library.database.LoanManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LibraryGUI extends JFrame {

    public LibraryGUI() {
        // Ρυθμίσεις του παραθύρου
        setTitle("Library System");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Δημιουργία κουμπιών
        JButton loanButton = new JButton("Loan a book");
        JButton returnButton = new JButton("Return a book");
        JButton searchButton = new JButton("Search for a book");
        JButton listButton = new JButton("List available books");
        JButton insertButton = new JButton("Insert a new book");
        JButton registerButton = new JButton("Member Registration");
        JButton exitButton = new JButton("Exit");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(loanButton);
        panel.add(returnButton);
        panel.add(searchButton);
        panel.add(listButton);
        panel.add(insertButton);
        panel.add(registerButton);
        panel.add(exitButton);

        add(panel);

        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String bookTitle = JOptionPane.showInputDialog("Enter Book Title:");
                    if (bookTitle == null || bookTitle.isEmpty()) return;

                    String memberIdInput = JOptionPane.showInputDialog("Enter Member ID:");
                    if (memberIdInput == null || memberIdInput.isEmpty()) return;

                    int memberId = Integer.parseInt(memberIdInput.trim());

                    boolean success = LoanManager.loanABook(bookTitle, memberId);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Book loaned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Loan failed: Book or member not found.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Member ID must be a number.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage());
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = JOptionPane.showInputDialog("Enter Book Title:");
                    if (title == null || title.isEmpty()) return;

                    String memberInput = JOptionPane.showInputDialog("Enter Member ID:");
                    if (memberInput == null || memberInput.isEmpty()) return;

                    int memberId = Integer.parseInt(memberInput.trim());

                    boolean success = LoanManager.returnABook(memberId, title);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Book returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Could not return the book.\n" +
                                "Check if it was actually loaned by this member.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Member ID must be a number.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage());
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Enter the title of the book:");

                if (title == null || title.trim().isEmpty()) {
                    return;
                }

                boolean found = DatabaseManager.searchBook(title.trim());

                if (found) {
                    JOptionPane.showMessageDialog(null, "Book found!");
                } else {
                    JOptionPane.showMessageDialog(null, "Book not found!");
                }
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String[]> books = DatabaseManager.getAvailableBooks();

                if (books.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No available books found.");
                    return;
                }

                String[] columnNames = {"Title", "Author"};
                String[][] data = books.toArray(new String[0][]);
                JTable table = new JTable(data, columnNames);

                JScrollPane scrollPane = new JScrollPane(table);

                JOptionPane.showMessageDialog(null, scrollPane, "Available Books", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = JOptionPane.showInputDialog("Title:");
                if (title == null || title.isEmpty()) return;

                String author = JOptionPane.showInputDialog("Author:");
                if (author == null || author.isEmpty()) return;

                String category = JOptionPane.showInputDialog("Genre:");
                if (category == null || category.isEmpty()) return;



                try {

                    boolean success = DatabaseManager.addBook(title, author, category);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Book added successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add the book.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Year must be a number.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(null, "Name");

                if (name == null || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Field name cannot be empty.");
                    return;
                }

                boolean success = DatabaseManager.registerMember(name.trim());

                if (success) {
                    JOptionPane.showMessageDialog(null, "Member registered successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Failed to register the member.");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting...");
                dispose();
            }
        });
    }


}
