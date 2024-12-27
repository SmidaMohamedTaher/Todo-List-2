package com.example.part2.DataBases;

import com.example.part2.Classes.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Category_Data_Base_methode {

    public static ArrayList<Category> findAll() {

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }

        ArrayList<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM category;";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("id"),
                        resultSet.getString("category")
                );
                categories.add(category);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return categories;
    }

    public static void create(Category category) {

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        String query = "INSERT INTO category(category) VALUES(?);";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void deleteById(int id) {

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        String query = "DELETE FROM category WHERE id = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }



}
