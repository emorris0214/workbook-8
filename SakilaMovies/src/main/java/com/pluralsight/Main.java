package com.pluralsight;

import java.sql.*;
import org.apache.commons.dbcp2.*;
import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {

        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/sakila");
    }
}
