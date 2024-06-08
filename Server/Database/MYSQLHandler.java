package Server.Database;

import Client.Models.TimeDate;
import Client.Models.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;


public class MYSQLHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/smiley"; 
    private static final String USER = "tears";
    private static final String PASSWORD = "REMOVED";

    @Deprecated
    public void EstablishConnection() {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to MySQL database.");

            try (Statement statement = connection.createStatement()) {
                String databaseName = "smiley"; 
                String useDatabaseQuery = "USE " + databaseName;
                statement.execute(useDatabaseQuery);
                System.out.println("Using database: " + databaseName);
            } catch (SQLException e) {
                System.out.println("Failed to select database.");
            }


        } catch (SQLException e) {
            System.out.println("Connection to MySQL database failed.");
        }
    }

    public static void InsertUserQuery(String username, String name, String email, String password, TimeDate birth){

        String insertSQL = QueryEnum.INSERTUSER.query;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);

            LocalDate birthday = LocalDate.of(birth.getYear(), birth.getMonth(), birth.getDay());
            Date birthdaysql = Date.valueOf(birthday);

            preparedStatement.setDate(5, birthdaysql);
            preparedStatement.executeUpdate();
        } 
        catch (SQLException e) {
            System.out.println("Failed to insert user.");
            System.out.println(e);
        }

    }
    
    public static boolean Checkpassword(String email, String password) {

        String selectSQL = QueryEnum.FETCHPASS.query;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

                preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedPassword = resultSet.getString("password");
                        return password.equals(storedPassword);
                    }
                }
    
            } 
            catch (SQLException e) {
                System.out.println("Failed to check password.");
                System.out.println(e);
            }
        return false;
    
    }

      public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String SelectQuery = QueryEnum.PARSEUSERS.query;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SelectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                String bio = resultSet.getString("bio");
                Date birthday = resultSet.getDate("birthday");
                TimeDate birthdaydate = new TimeDate(birthday);

                userList.add(new User(userId, username, name, null, null, birthdaydate, bio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

}
