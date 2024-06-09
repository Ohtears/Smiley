package Server.Database;

import Client.Models.Message;
import Client.Models.TimeDate;
import Client.Models.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MYSQLHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/smiley"; 
    private static final String USER = "tears";
    private static final String PASSWORD = "REMOVED";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static void insertUser(String username, String name, String email, String password, TimeDate birth) {
        String insertSQL = QueryEnum.INSERTUSER.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            LocalDate birthday = LocalDate.of(birth.getYear(), birth.getMonth(), birth.getDay());
            preparedStatement.setDate(5, Date.valueOf(birthday));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public static boolean checkPassword(String email, String password) {
        String selectSQL = QueryEnum.FETCHPASS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return password.equals(resultSet.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return false;
    }

    public static User getCurrentUser(String email) {
        String selectSQL = QueryEnum.FETCHPASS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("username"),
                            resultSet.getString("name"),
                            email,
                            resultSet.getString("password"),
                            new TimeDate(resultSet.getDate("birthday")),
                            resultSet.getString("bio")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return null;
    }

    public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = QueryEnum.PARSEUSERS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        null,
                        new TimeDate(resultSet.getDate("birthday")),
                        resultSet.getString("bio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return userList;
    }

    public static void addFollower(int targetUserId, int followerId) {
        String query = QueryEnum.ADDFOLLOWER.query;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, targetUserId);
            statement.setInt(2, followerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public static List<Integer> getAllFollowers(int userId) {
        List<Integer> followerList = new ArrayList<>();
        String selectQuery = QueryEnum.FETCHFOLLOWERS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    followerList.add(resultSet.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return followerList;
    }

    public static User getUserById(int userId) {
        String selectSQL = QueryEnum.FETCHWITHID.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            userId,
                            resultSet.getString("username"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            new TimeDate(resultSet.getDate("birthday")),
                            resultSet.getString("bio")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return null;
    }

    public static List<User> getChatList(int userId) {
        List<User> userList = new ArrayList<>();
        String selectQuery = QueryEnum.FETCHCHATS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int userIdResult = resultSet.getInt("user_id");
                    if (userIdResult != userId) {
                        userList.add(new User(
                                userIdResult,
                                resultSet.getString("username"),
                                resultSet.getString("name"),
                                null,
                                null,
                                new TimeDate(resultSet.getDate("birthday")),
                                resultSet.getString("bio")
                        ));
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
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertChatQuery)) {

            preparedStatement.setInt(1, user1Id);
            preparedStatement.setInt(2, user2Id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public static List<Message> getChatBetweenUsers(int user1Id, int user2Id) {
        List<Message> messages = new ArrayList<>();
        String selectQuery = QueryEnum.FETCHCHAT.query;
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, user1Id);
            preparedStatement.setInt(2, user2Id);
            preparedStatement.setInt(3, user1Id);
            preparedStatement.setInt(4, user2Id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    messages.add(new Message(
                            resultSet.getInt("message_id"),
                            resultSet.getInt("sender_id"),
                            resultSet.getInt("receiver_id"),
                            resultSet.getString("content"),
                            resultSet.getTimestamp("timestamp")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return messages;
    }
}

