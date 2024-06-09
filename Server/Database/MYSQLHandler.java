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
    
    public static User currentuser(String email) {

        String selectSQL = QueryEnum.FETCHPASS.query;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

                preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedPassword = resultSet.getString("password");
                        int userId = resultSet.getInt("user_id");
                        String username = resultSet.getString("username");
                        String name = resultSet.getString("name");
                        String bio = resultSet.getString("bio");
                        Date birthday = resultSet.getDate("birthday");
                        TimeDate birthdaydate = new TimeDate(birthday);

                        User user = new User(userId, username, name, email, storedPassword, birthdaydate, bio);

                        return user;
                    }
                }
            
            } 
            catch (SQLException e) {
                System.out.println("Failed to check password.");
            }
        return null;
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
                String email = resultSet.getString("email");
                String bio = resultSet.getString("bio");
                Date birthday = resultSet.getDate("birthday");
                TimeDate birthdaydate = new TimeDate(birthday);

                userList.add(new User(userId, username, name, email, null, birthdaydate, bio));
            }
        } catch (SQLException e) {
        }

        return userList;
    }

    public static void addFollower(int targetuserfollow, int followerId) {
        String query = QueryEnum.ADDFOLLOWER.query;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, targetuserfollow);
            statement.setInt(2, followerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public static List<Integer> getAllFollowers(int follower_id) {
        List<Integer> followerlist = new ArrayList<>();
        String selectQuery = QueryEnum.FETCHFOLLOWERS.query;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, follower_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    followerlist.add(userId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return followerlist;
    }

    public static User fetchuserswithID(int user_id) {

        String selectSQL = QueryEnum.FETCHPASS.query;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

                preparedStatement.setLong(0, user_id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String username = resultSet.getString("username");
                        String name = resultSet.getString("name");
                        String bio = resultSet.getString("bio");
                        Date birthday = resultSet.getDate("birthday");
                        TimeDate birthdaydate = new TimeDate(birthday);

                        User user = new User(user_id, username, name, null, null, birthdaydate, bio);

                        return user;
                    }
                }

            } 
            catch (SQLException e) {
            }
        return null;
    }
    public static List<User> getChatList(int userId) {
        List<User> userList = new ArrayList<>();

        String selectQuery = QueryEnum.FETCHCHATS.query;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int userIdResult = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String name = resultSet.getString("name");
                    String bio = resultSet.getString("bio");
                    Date birthday = resultSet.getDate("birthday");

                    TimeDate birthdaydate = new TimeDate(birthday);

                    if (userIdResult != userId) {  
                        User user = new User(userIdResult, username, name, null, null , birthdaydate, bio);
                        userList.add(user);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static void startChat(int user1Id, int user2Id) {
        String insertChatQuery = QueryEnum.STARTCHAT.query;
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertChatQuery)) {

            preparedStatement.setInt(1, user1Id);
            preparedStatement.setInt(2, user2Id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
