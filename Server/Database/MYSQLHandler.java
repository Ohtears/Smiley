package Server.Database;

import Server.Services.MessageService;
import Server.Services.UserService;
import Server.Services.TimeDateService;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MYSQLHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/smiley";
    private static final String USER = "tears";
    private static final String PASSWORD = "REMOVED";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertUser(UserService user) {
        String insertSQL = QueryEnum.INSERTUSER.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, user.Username);
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getemail());
            preparedStatement.setString(4, user.getpassword());
            LocalDate birthday = LocalDate.of(user.getBirthday().getYear(), user.getBirthday().getMonth(), user.getBirthday().getDay());
            preparedStatement.setDate(5, Date.valueOf(birthday));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkPassword(UserService user) {
        String selectSQL = QueryEnum.FETCHPASS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setString(1, user.getemail());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return user.getpassword().equals(resultSet.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static UserService getCurrentUser(UserService user) {
        String selectSQL = QueryEnum.FETCHPASS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setString(1, user.getemail());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserService(
                            resultSet.getInt("user_id"),
                            resultSet.getString("username"),
                            resultSet.getString("name"),
                            user.getemail(),
                            resultSet.getString("password"),
                            new TimeDateService(resultSet.getDate("birthday")),
                            resultSet.getString("bio")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<UserService> getAllUsers() {
        List<UserService> userList = new ArrayList<>();
        String selectQuery = QueryEnum.PARSEUSERS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    userList.add(new UserService(
                    resultSet.getInt("user_id"),
                     resultSet.getString("username"),
                      resultSet.getString("name"),
                       resultSet.getString("email"),
                        "",
                         new TimeDateService(resultSet.getDate("birthday")),
                          resultSet.getString("bio")
                          ));
                }
                
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static void addFollower(UserService targetUser, UserService follower) {
        String query = QueryEnum.ADDFOLLOWER.query;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, targetUser.getID());
            statement.setInt(2, follower.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> getAllFollowers(UserService user) {
        List<Integer> followerList = new ArrayList<>();
        String selectQuery = QueryEnum.FETCHFOLLOWERS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, user.getID());
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

    public static List<UserService> getChatList(UserService user) {
        List<UserService> userList = new ArrayList<>();
        String selectQuery = QueryEnum.FETCHCHATS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, user.getID());
            preparedStatement.setInt(2, user.getID());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int userIdResult = resultSet.getInt("user_id");
                    if (userIdResult != user.getID()) {
                        userList.add(new UserService(
                                userIdResult,
                                resultSet.getString("username"),
                                resultSet.getString("name"),
                                "",
                                "",
                                new TimeDateService(resultSet.getDate("birthday")),
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

    public static void startChat(UserService user1, UserService user2) {
        String insertChatQuery = QueryEnum.STARTCHAT.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertChatQuery)) {

            preparedStatement.setInt(1, user1.getID());
            preparedStatement.setInt(2, user2.getID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<MessageService> getChatBetweenUsers(UserService user1, UserService user2) {
        List<MessageService> messages = new ArrayList<>();
        String selectquery = QueryEnum.FETCHCHAT.query;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectquery)) {

            preparedStatement.setInt(1, user1.getID());
            preparedStatement.setInt(2, user2.getID());
            preparedStatement.setInt(3, user2.getID());
            preparedStatement.setInt(4, user1.getID());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int messageId = rs.getInt("message_id");
                    int senderId = rs.getInt("sender_id");
                    int receiverId = rs.getInt("receiver_id");
                    String content = rs.getString("message_content");
                    Timestamp timestamp = rs.getTimestamp("timestamp");

                    MessageService message = new MessageService(messageId, senderId, receiverId, content, timestamp);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public static void sendMessagesChat(UserService user1, UserService user2, String content) {
        String insertChatQuery = QueryEnum.INSERTCHAT.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertChatQuery)) {

            preparedStatement.setInt(1, user1.getID());
            preparedStatement.setInt(2, user2.getID());
            preparedStatement.setString(3, content);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String GetUserStatus(UserService user) {
        String selectSQL = QueryEnum.FETCHSTATUS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            preparedStatement.setInt(1, user.getID());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("status");
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void setUserStatus(UserService user, String status) {
        String updateSQL = QueryEnum.UPDATESTATUSOFFLINE.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
    
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, user.getID());
            preparedStatement.executeUpdate();
    
        } catch (SQLException e) {
        }
    }
    public static void insertUserStatus(UserService user) {
        String insertSQL = QueryEnum.INSERTSTATUS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, user.getID());
            preparedStatement.setString(2, "online");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateUserStatusBasedOnActivity(int timeoutSeconds) {
        String updateSQL = "UPDATE user_status SET status = 'offline' WHERE last_activity < NOW() - INTERVAL ? SECOND";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, timeoutSeconds);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

