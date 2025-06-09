package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // load the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        /* 1. Open a connection to the database
        use the database URL to point to the correct database */
        Connection connection;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind",
                "root",
                "yearup");

        /* Create statement
        the statement is tied to the open connection */
        Statement statement = connection.createStatement();

        // Defining my query
        String query = "SELECT * FROM products";

        // 2. Execution of query
        ResultSet results = statement.executeQuery(query);

        // Processing the results
        while (results.next()) {
            String name = results.getString("ProductName");
            System.out.println(name);
        }
        // 3. Close the connection
        connection.close();
    }
}
