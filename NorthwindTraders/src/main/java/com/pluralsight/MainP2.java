package com.pluralsight;

import java.sql.*;

public class MainP2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // 1. load the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. Open a connection to the database
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind",
                "root",
                "yearup");

        // 3. Defining my query
        String query = "SELECT ProductID, ProductName, UnitPrice, unitsInStock FROM Products";

        // 4. Create preparedStatement
        PreparedStatement statement = connection.prepareStatement(query);


        // 5. Execution of query
        ResultSet results = statement.executeQuery();

        System.out.printf("%-4s %-25s %7s %6s%n", "Id", "Name", "Price", "Stock");
        System.out.println("---- ------------------------- ------- ------");

        // Processing the results
        while (results.next()) {
            int id = results.getInt("ProductID");
            String name = results.getString("ProductName");
            double price = results.getDouble("UnitPrice");
            int stock = results.getInt("UnitsInStock");
            System.out.printf("%-4d %-25s %7.2f %6d%n", id, name, price, stock);
        }
        // 3. Close the connection
        connection.close();
    }
}
