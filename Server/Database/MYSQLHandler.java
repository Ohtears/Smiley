package Server.Database;

import Client.Models.TimeDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


public class MYSQLHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/smiley"; 
    private static final String USER = "tears";
    private static final String PASSWORD = "traboat12";

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

    public static void InsertUserQuery(String username, String email, String password, TimeDate birth){

        String insertSQL = QueryEnum.INSERTUSER.query;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            LocalDate birthday = LocalDate.of(birth.getYear(), birth.getMonth(), birth.getDay());
            Date birthdaysql = Date.valueOf(birthday);

            preparedStatement.setDate(4, birthdaysql);

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
            }
        return false;
    
    }
}
