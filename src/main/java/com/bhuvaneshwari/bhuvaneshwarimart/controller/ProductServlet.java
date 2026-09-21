package com.bhuvaneshwari.bhuvaneshwarimart.controller;

import com.bhuvaneshwari.bhuvaneshwarimart.config.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder json = new StringBuilder();
        json.append("[");

        String sql =
                "SELECT id, name, category, price, unit, rating, emoji " +
                "FROM products ORDER BY id";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            boolean first = true;

            while (resultSet.next()) {

                if (!first) {
                    json.append(",");
                }

                json.append("{")
                        .append("\"id\":")
                        .append(resultSet.getInt("id"))
                        .append(",")

                        .append("\"name\":\"")
                        .append(escapeJson(resultSet.getString("name")))
                        .append("\",")

                        .append("\"category\":\"")
                        .append(escapeJson(resultSet.getString("category")))
                        .append("\",")

                        .append("\"price\":")
                        .append(resultSet.getBigDecimal("price"))
                        .append(",")

                        .append("\"unit\":\"")
                        .append(escapeJson(resultSet.getString("unit")))
                        .append("\",")

                        .append("\"rating\":")
                        .append(resultSet.getBigDecimal("rating"))
                        .append(",")

                        .append("\"emoji\":\"")
                        .append(escapeJson(resultSet.getString("emoji")))
                        .append("\"")
                        .append("}");

                first = false;
            }

            json.append("]");

            response.getWriter().print(json.toString());

        } catch (Exception e) {

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().print(
                    "{\"error\":\"Database error\"}"
            );

            System.out.println(
                    "PRODUCT DATABASE ERROR: " + e.getMessage()
            );

            e.printStackTrace();
        }
    }

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}