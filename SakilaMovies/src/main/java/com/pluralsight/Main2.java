package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

public class Main2 {
    public static void main(String[] args) {

        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/sakila");
        yadda.setUser(username);
        yadda.setPassword(password);

        DataManager dataManager = new DataManager(dataSource);

        List<Actor> actors = dataManager.getActorsByName();

        products.forEach(System.out::println);
    }
}
