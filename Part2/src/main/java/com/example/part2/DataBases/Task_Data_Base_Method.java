package com.example.part2.DataBases;

import com.example.part2.Classes.*;
import javafx.scene.layout.Priority;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class Task_Data_Base_Method {

    public static ArrayList<Task> findAll() {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }

        ArrayList<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM task;";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new TaskImpl(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDate("dueDate"),
                        Complete.valueOf(resultSet.getString("status")),
                        Priorities.valueOf(resultSet.getString("priority"))
                );


                ArrayList<Category> categories = findTaskCategories(task.getId_T());
                task.setCategories(categories);

                tasks.add(task);
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
        return tasks;
    }

    public static void create(Task task, int userId,Category category) {

        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        String query = "INSERT INTO task(name, description, dueDate, status, priority) VALUES(?, ?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, Utils.getSqlDate(task.getDueDate()));
            preparedStatement.setString(4, task.getStatus().toString());
            preparedStatement.setString(5, task.getPriority().toString());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int taskId = generatedKeys.getInt(1);
                    addCategoryToTask(taskId,category.getIdCategory());
                    String taskUserQuery = "INSERT INTO task_user(task_id, user_id) VALUES(?, ?)";
                    try (PreparedStatement taskUserStatement = con.prepareStatement(taskUserQuery)) {
                        taskUserStatement.setInt(1, taskId);
                        taskUserStatement.setInt(2, userId);

                        taskUserStatement.executeUpdate();
                    }
                }
            }
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

    public static void create(Task task, int userId) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            System.out.println("Database connection error.");
            return;
        }

        String taskQuery = "INSERT INTO task(name, description, dueDate, status, priority) VALUES(?, ?, ?, ?, ?);";
        String taskUserQuery = "INSERT INTO task_user(task_id, user_id) VALUES(?, ?);";

        try {
            con.setAutoCommit(false);

            try (PreparedStatement preparedStatement = con.prepareStatement(taskQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, task.getName());
                preparedStatement.setString(2, task.getDescription());
                preparedStatement.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
                preparedStatement.setString(4, task.getStatus().name());
                preparedStatement.setString(5, task.getPriority().toString());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Creating task failed, no rows affected.");
                }

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int taskId = generatedKeys.getInt(1);

                        try (PreparedStatement taskUserStatement = con.prepareStatement(taskUserQuery)) {
                            taskUserStatement.setInt(1, taskId);
                            taskUserStatement.setInt(2, userId);
                            taskUserStatement.executeUpdate();
                        }
                    } else {
                        throw new SQLException("Creating task failed, no ID obtained.");
                    }
                }
            }

            con.commit();
        } catch (SQLException se) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            se.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static ArrayList<Category> findTaskCategories(int taskId) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }

        ArrayList<Category> categories = new ArrayList<>();
        String query = "SELECT c.id AS category_id, c.category "
                + "FROM category c "
                + "JOIN task_category tc ON c.id = tc.category_id "
                + "WHERE tc.task_id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt("category_id"),
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

    public static Task findById(int id) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }

        String query = "SELECT * FROM task WHERE id = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                Task task = new TaskImpl(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDate("dueDate"),
                        Complete.valueOf(resultSet.getString("status")),
                        Priorities.valueOf(resultSet.getString("priority"))
                );


                ArrayList<Category> categories = findTaskCategories(task.getId_T());
                task.setCategories(categories);

                return task;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return null;
    }

    public static void create(Task task) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        String query = "INSERT INTO task(name, description, dueDate, status, priority) VALUES(?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
            preparedStatement.setString(4, task.getStatus().name());
            preparedStatement.setString(5, task.getPriority().name());
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

    public static void update(Task task) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return;
        }

        String query = "UPDATE task SET name = ?, description = ?, dueDate = ?, status = ?, priority = ? WHERE id = ?;";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
            preparedStatement.setString(4, task.getStatus().name());
            preparedStatement.setString(5, task.getPriority().name());
            preparedStatement.setInt(6, task.getId_T());
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

        String query = "DELETE FROM task WHERE id = ?;";
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


    // Smida taher
    public static ArrayList<Task> findTasksByUserId(int userId) {



        Connection con = DBConnection.getConnection();
        if (con == null) {
            return null;
        }

        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Integer> ids = new ArrayList<Integer>() ;


        String query = "SELECT * " +
                "FROM task_user " +
                "WHERE user_id = ?;";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Integer id = resultSet.getInt("task_id");
                ids.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (int id : ids) {

            tasks.add(findById(id)) ;
        }


        return tasks;

    }


    public static boolean addUserToTask(int taskId, int userId) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return false;
        }


        String checkQuery = "SELECT COUNT(*) FROM task_user " +
                "WHERE task_id = ? AND user_id = ?;";

        try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, taskId);
            checkStmt.setInt(2, userId);

            ResultSet resultSet = checkStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {

                return false;
            }


            String insertQuery = "INSERT INTO task_user (task_id, user_id) " +
                    "VALUES (?, ?);";

            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                insertStmt.setInt(1, taskId);
                insertStmt.setInt(2, userId);

                int rowsAffected = insertStmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static boolean addCategoryToTask(int taskId, int categoryId) {
        Connection con = DBConnection.getConnection();
        if (con == null) {
            return false;
        }

        String checkCategoryQuery = "SELECT COUNT(*) FROM category WHERE id = ?;";
        String checkTaskCategoryQuery = "SELECT COUNT(*) FROM task_category WHERE task_id = ? AND category_id = ?;";
        String insertTaskCategoryQuery = "INSERT INTO task_category (task_id, category_id) VALUES (?, ?);";

        try {

            try (PreparedStatement checkCategoryStmt = con.prepareStatement(checkCategoryQuery)) {
                checkCategoryStmt.setInt(1, categoryId);
                ResultSet categoryResultSet = checkCategoryStmt.executeQuery();
                if (categoryResultSet.next() && categoryResultSet.getInt(1) == 0) {
                    return false;
                }
            }


            try (PreparedStatement checkTaskCategoryStmt = con.prepareStatement(checkTaskCategoryQuery)) {
                checkTaskCategoryStmt.setInt(1, taskId);
                checkTaskCategoryStmt.setInt(2, categoryId);
                ResultSet taskCategoryResultSet = checkTaskCategoryStmt.executeQuery();
                if (taskCategoryResultSet.next() && taskCategoryResultSet.getInt(1) > 0) {
                    return true;
                }
            }


            try (PreparedStatement insertTaskCategoryStmt = con.prepareStatement(insertTaskCategoryQuery)) {
                insertTaskCategoryStmt.setInt(1, taskId);
                insertTaskCategoryStmt.setInt(2, categoryId);
                int rowsAffected = insertTaskCategoryStmt.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


}






