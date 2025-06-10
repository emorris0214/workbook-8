package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class MainP3 {

    // Database connection details - these should ideally be loaded from a config file
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    public static void main(String[] args) {

        DB_URL = "jdbc:mysql://127.0.0.1:3306/northwind"; // Standard URL for Northwind database
        DB_USER = args[0];
        DB_PASSWORD = args[1];

        Scanner keyboard = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Home Screen Menu
            System.out.println("\nWhat would you like to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");
            String choice = keyboard.nextLine();

            switch (choice) {
                case "1":
                    displayAllProducts(); // Call method to display products
                    break;
                case "2":
                    displayAllCustomers(); // Call method to display customers
                    break;
                case "0":
                    running = false; // Set running to false for an exit
                    System.out.println("Thanks! Goodbye!");
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
        keyboard.close(); // Close the scanner when done
    }

    /**
     * Displays all products from the Products table in the Northwind database.
     * Fetches ProductId, ProductName, UnitPrice, and UnitsInStock.
     */
    private static void displayAllProducts() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        try {
            // Establish connection to the database, prepared statement for query and execution
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            statement = connection.prepareStatement(query);
            results = statement.executeQuery();

            System.out.println("\n--- All Products ---");
            // Print table header with formatting
            System.out.printf("%-10s %-35s %-12s %-15s%n", "ProductId", "ProductName", "UnitPrice", "UnitsInStock");
            System.out.println("------------------------------------------------------------------------");

            // Process the result set, iterating through each row
            while (results.next()) {
                int productId = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");
                // Print product details with formatting
                System.out.printf("%-10d %-35s %-12.2f %-15d%n", productId, productName, unitPrice, unitsInStock);
            }

        } catch (SQLException e) {
            // Catch any SQL exceptions and print the stack trace for debugging
            System.err.println("Error displaying products: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources in reverse order of creation to prevent resource leaks
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static void displayAllCustomers() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        // SQL query to select required customer details, ordered by Country
        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            statement = connection.prepareStatement(query);
            results = statement.executeQuery();

            System.out.println("\n--- All Customers ---");
            // Print table header with formatting
            System.out.printf("%-25s %-40s %-20s %-20s %-20s%n", "Contact Name", "Company Name", "City", "Country", "Phone");
            System.out.println("------------------------------------------------------------------------------------------------------------------------");

            // Process the result set, iterating through each row
            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");
                // Print customer details with formatting
                System.out.printf("%-25s %-40s %-20s %-20s %-20s%n", contactName, companyName, city, country, phone);
            }

        } catch (SQLException e) {
            // Catch any SQL exceptions and print the stack trace for debugging
            System.err.println("Error displaying customers: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources in reverse order of creation to prevent resource leaks
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
