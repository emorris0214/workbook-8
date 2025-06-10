package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class MainP4 {
    private static String DB_URL;
    private static String DB_User;
    private static String DB_Password;
    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        DB_URL = "jdbc:mysql://127.0.0.1:3306/northwind";
        DB_User = args[0];
        DB_Password = args[1];

        boolean running = true;

        while (running) {
            // Home Screen Menu
            System.out.println("\nWhat would you like to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("3) Display all categories");
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
                case "3":
                    displayCategoriesAndProductsBySelection();
                case "0":
                    running = false; // Set running to false for an exit
                    System.out.println("Thanks! Goodbye!");
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
        keyboard.close();
    }
    private static void displayAllProducts() {
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_User, DB_Password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()) {

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
        }

    }
    private static void displayAllCustomers() {
        String query = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_User, DB_Password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet results = statement.executeQuery()){

            System.out.println("\n--- All Customers ---");
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
        }
    }

    private static void displayCategoriesAndProductsBySelection() {
        System.out.println("\n--- All Categories ---");
        System.out.printf("%-15s %-30s%n", "Category ID", "Category Name");
        System.out.println("--------------------------------------------");

        String categoriesQuery = "SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_User, DB_Password);
             PreparedStatement statement = connection.prepareStatement(categoriesQuery);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                int categoryId = results.getInt("CategoryID");
                String categoryName = results.getString("CategoryName");
                System.out.printf("%-15d %-30s%n", categoryId, categoryName);
            }
        } catch (SQLException e) {
            System.err.println("Error displaying categories: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("\nEnter a Category ID to display products in that category: ");
        int selectedCategoryId;
        try {
            selectedCategoryId = Integer.parseInt(keyboard.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Category ID. Please try again.");
            return;
        }

        displayProductsByCategoryId(selectedCategoryId);
    }

    private static void displayProductsByCategoryId(int categoryId) {
        String productsQuery = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products WHERE CategoryID = ?";

        // Using try-with-resources for resource management
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_User, DB_Password);
             PreparedStatement statement = connection.prepareStatement(productsQuery)) {

            statement.setInt(1, categoryId); // Set the category ID parameter
            try (ResultSet results = statement.executeQuery()) { // ResultSet also managed by try-with-resources

                System.out.printf("\n--- Products in Category ID: %d ---%n", categoryId);
                System.out.printf("%-10s %-35s %-12s %-15s%n", "ProductId", "ProductName", "UnitPrice", "UnitsInStock");
                System.out.println("------------------------------------------------------------------------");

                boolean foundProducts = false;
                while (results.next()) {
                    foundProducts = true;
                    int productId = results.getInt("ProductID");
                    String productName = results.getString("ProductName");
                    double unitPrice = results.getDouble("UnitPrice");
                    int unitsInStock = results.getInt("UnitsInStock");
                    System.out.printf("%-10d %-35s %-12.2f %-15d%n", productId, productName, unitPrice, unitsInStock);
                }

                if (!foundProducts) {
                    System.out.println("No products found for Category ID: " + categoryId);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error displaying products for category: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
