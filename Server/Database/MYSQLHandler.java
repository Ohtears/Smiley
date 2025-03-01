package Server.Database;

import Server.Services.CommentService;
import Server.Services.MessageService;
import Server.Services.PostService;
import Server.Services.UserService;
import Server.Services.TimeDateService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MYSQLHandler {
    private static final String URL = ""; //fill these accordingly
    private static final String USER = "";
    private static final String PASSWORD = "";

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

    public static List<UserService> getAllFollowers(UserService user) {
        List<UserService> followerList = new ArrayList<>();
        String selectQuery = QueryEnum.FETCHFOLLOWERS.query;

        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

       preparedStatement.setInt(1, user.getID());
       try (ResultSet resultSet = preparedStatement.executeQuery()) {
           while (resultSet.next()) {
               int userId = resultSet.getInt("user_id");
               String username = resultSet.getString("username");
               String name = resultSet.getString("name");
               String email = resultSet.getString("email");
               Date birthday = resultSet.getDate("birthday");
               String bio = resultSet.getString("bio");

               UserService follower = new UserService(userId, username, name, email, "", new TimeDateService(birthday), bio);
               followerList.add(follower);
           }
       }
   } catch (SQLException e) {
       e.printStackTrace();
   }
   return followerList;
}
    public static List<UserService> getAllFollowing(UserService user) {
        List<UserService> followingList = new ArrayList<>();
        String selectQuery = QueryEnum.GETALLFOLLOWING.query;

        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, user.getID());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    Date birthday = resultSet.getDate("birthday");
                    String bio = resultSet.getString("bio");

                    UserService following = new UserService(userId, username, name, email, "", new TimeDateService(birthday), bio);
                    followingList.add(following);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followingList;
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
                    UserService sender = getUserById(senderId);
                    UserService receiver = getUserById(receiverId);
                    MessageService message = new MessageService(messageId, sender, receiver, content, timestamp);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
    private static UserService getUserById(int userId) {
        String selectQuery = QueryEnum.GETUSERID.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
    
            preparedStatement.setInt(1, userId);
    
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new UserService(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("name"),
                            rs.getString("email"),
                            "", 
                            new TimeDateService(rs.getDate("birthday")),
                            rs.getString("bio")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return null;
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
        String updateSQL = QueryEnum.UPDATESTATUS.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
    
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, user.getID());
            preparedStatement.executeUpdate();
    
        } catch (SQLException e) {
            System.out.println(e);
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

    public static List<PostService> getAllPosts() {
        List<PostService> posts = new ArrayList<>();
        String selectQuery = QueryEnum.GETALLPOSTS.query;

        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = preparedStatement.executeQuery()) {

       while (resultSet.next()) {
           int postId = resultSet.getInt("post_id");
           String content = resultSet.getString("content");
           Timestamp timestamp = resultSet.getTimestamp("created_at");

           int userId = resultSet.getInt("user_id");
           String username = resultSet.getString("username");
           String name = resultSet.getString("name");
           String email = resultSet.getString("email");
           TimeDateService birthday = new TimeDateService(resultSet.getDate("birthday"));
           String bio = resultSet.getString("bio");

           UserService user = new UserService(userId, username, name, email, " ", birthday, bio);
           PostService post = new PostService(postId, user, content, timestamp);
           posts.add(post);
       }

        } catch (SQLException e) {
            e.printStackTrace();
        }

   return posts;
    }

    public static void insertPost(UserService user, String content) {
        String insertQuery = QueryEnum.INSERTPOST.query;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, user.getID());
            preparedStatement.setString(2, content);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<PostService> GetpostsUser(UserService user) {

        String query = QueryEnum.GETALLPOSTSFROMUSER.query;

        List <PostService> posts = new ArrayList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getID());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int postId = resultSet.getInt("post_id");
                String content = resultSet.getString("content");
                Timestamp timestamp = resultSet.getTimestamp("created_at");
                PostService post = new PostService(postId, user, content, timestamp);

                posts.add(post);

            
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<CommentService> GetcommentsPost(PostService post) {
        String query = QueryEnum.GETCOMMENTSPOST.query;
        List<CommentService> comments = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, post.getPostId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int commentId = resultSet.getInt("comment_id");
                String content = resultSet.getString("comment_content");
                Timestamp timestamp = resultSet.getTimestamp("created_at");

                int userId = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                TimeDateService birthday = new TimeDateService(resultSet.getDate("birthday"));
                String bio = resultSet.getString("bio");

                UserService user = new UserService(userId, username, name, email, " ", birthday, bio);

                CommentService comment = new CommentService(commentId, user, content, timestamp, post, null);
                comments.add(comment);
            }
            return comments;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PostService getPostById(int postId) {
        String selectQuery = QueryEnum.GETPOSTID.query;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
    
            preparedStatement.setInt(1, postId);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve user details
                    int userId = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    Date birthdayDate = resultSet.getDate("birthday");
                    TimeDateService birthday = (birthdayDate != null) ? new TimeDateService(birthdayDate) : null;
                    String bio = resultSet.getString("bio");
    
                    // Retrieve post details
                    String content = resultSet.getString("content");
                    Timestamp timestamp = resultSet.getTimestamp("created_at");
    
                    // Create UserService and PostService objects
                    UserService user = new UserService(userId, username, name, email, " ", birthday, bio);
                    return new PostService(postId, user, content, timestamp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void insertComment(UserService user, String content, PostService post) {
        String insertQuery = QueryEnum.INSERTCOMMENT.query;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, post.getPostId());
            preparedStatement.setInt(2, user.getID());
            preparedStatement.setString(3, content);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
