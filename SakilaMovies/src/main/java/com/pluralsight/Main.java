package com.pluralsight;

import java.sql.*;
import java.util.Scanner;
import org.apache.commons.dbcp2.*;
import javax.sql.DataSource;

public class Main {
    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {

        if (args.length < 2){
            System.out.println("Usage: java Main <username> <password>");
            return;
        }
        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/sakila");
        dataSource.setUsername(username);
        dataSource.setPassword(password);


        //Begin by asking the user for a last name of an actor they like. Use that to display
        //a list of all actors with that last name
        System.out.println("Welcome to Sakila Movies Database!");
        System.out.print("What is the last name of an actor you like? ");
        String lastName = keyboard.nextLine().toUpperCase();

        displayActorByLastName(dataSource, lastName);

        System.out.println("\nLet's find the movies by the actor!");
        System.out.println("Enter the actor's first name: ");
        String firstName = keyboard.nextLine().toUpperCase();

        System.out.println("Enter the actor's last name: ");
        String fullLastName = keyboard.nextLine().toUpperCase();

        displayFilmsByActorName(dataSource, firstName, fullLastName);
    }
    private static void displayActorByLastName(DataSource dataSource, String lastName) {
        String query = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name LIKE ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lastName);

            try (ResultSet set = statement.executeQuery()){
                if (set.next()) {
                    System.out.println("\nActors with last name '" + lastName + "':");
                    do {
                        int id = set.getInt("actor_id");
                        String first = set.getString("first_name");
                        String last = set.getString("last_name");
                        System.out.println(id + ": " + first + " " + last);
                    } while (set.next());
                } else {
                    System.out.println("No actors found with last name '" + lastName + "'.");
                }
            }

    } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    private static void displayFilmsByActorName(DataSource dataSource, String firstName, String lastName) {
        String query = """
            SELECT f.title
            FROM film f
            JOIN film_actor fa ON f.film_id = fa.film_id
            JOIN actor a ON a.actor_id = fa.actor_id
            WHERE a.first_name = ? AND a.last_name = ?
        """;

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    System.out.println("\nMovies featuring " + firstName + " " + lastName + ": ");
                    do {
                        String title = set.getString("title");
                        System.out.println(" - " + title);

                    } while (set.next());
                } else {
                    System.out.println("No movies found with actor  '" + firstName + " " + lastName + "'.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}


