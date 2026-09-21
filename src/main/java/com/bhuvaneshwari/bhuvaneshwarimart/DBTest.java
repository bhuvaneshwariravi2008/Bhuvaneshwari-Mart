package com.bhuvaneshwari.bhuvaneshwarimart;

import com.bhuvaneshwari.bhuvaneshwarimart.config.DBConnection;
import java.sql.Connection;

public class DBTest {

    public static void main(String[] args) {

        try {
            Connection connection = DBConnection.getConnection();

            System.out.println("MySQL Database Connected Successfully!");

            connection.close();

        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
    }
}