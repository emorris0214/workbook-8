package com.pluralsight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.dbcp2.*;
import javax.sql.DataSource;

public class DataManager {

    private DataSource dataSource;

    public DataManager(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }

    /*Your DataManager class should include a method to search for actors by name
    and return a list or Actors. You should also add a method to return a list of Films
    by actor id*/

    public List<Actor> getActorsByName() {
        List<Actor> actors = new ArrayList<>();

        String query = "SELECT first_name, last_name FROM actor;";

        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet set = statement.executeQuery(query);

        while (results.next()) {
            int id = set.getInt("actor_id");
            String first = set.getString("first_name");
            String last = set.getString("last_name");
            System.out.println(id + ": " + first + " " + last);


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


