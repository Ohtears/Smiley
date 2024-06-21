package Client.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import Client.Models.Comment;
import Client.Models.Message;
import Client.Models.Post;
import Client.Models.TimeDate;
import Client.Models.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    public static JSONObject usersToJson(List<User> users, RequestType type) {
        return usersToJson(users, null, type);
    }


    public static JSONObject usersToJson(List<User> users, String content, RequestType type) {
        JSONObject requestJson = new JSONObject();
        if (!type.equals(null)){

            requestJson.put("requestType", type.toString());
        }

        JSONArray jsonArray = new JSONArray();
        for (User user : users) {
            JSONObject userJson = new JSONObject();
            userJson.put("userid", user.getID());
            userJson.put("Username", user.Username);
            userJson.put("Name", user.getName());
            userJson.put("Email", user.getEmail());
            userJson.put("Password", user.getPassword());
            if (user.getBirthday() != null) {
                userJson.put("Birthday", user.getBirthday().toString());
            }
            else {
            userJson.put("Birthday", "");
            }
            userJson.put("bio", "");
            
            jsonArray.put(userJson);
        }
        
        requestJson.put("users", jsonArray);
        if (content != null) {
            requestJson.put("content", content);
        }
        return requestJson;
    }

    public static JSONObject PostsToJson(List<Post> posts, User user, String content, RequestType requestType) {
        JSONObject requestJson = new JSONObject();
    
        requestJson.put("requestType", requestType.toString());
        requestJson.put("content", content);
    
        JSONArray jsonArray = new JSONArray();
    
        for (Post post : posts) {
            JSONObject postJson = new JSONObject();
            postJson.put("postId", post.getPostId());
    
            JSONObject userJson = new JSONObject();
            userJson.put("userid", post.getuser().getID());
            userJson.put("Username", post.getuser().Username);
            userJson.put("Name", post.getuser().getName());
            userJson.put("Email", post.getuser().getEmail());
            userJson.put("Password", post.getuser().getPassword());
            if (post.getuser().getBirthday() != null) {
                userJson.put("Birthday", post.getuser().getBirthday().toString());
            } else {
                userJson.put("Birthday", "");
            }
            userJson.put("bio", "");
    
            postJson.put("user", userJson);
            postJson.put("content", post.getContent());
            postJson.put("timestamp", post.getTimestamp().toString());
    
            jsonArray.put(postJson);
        }
    
        requestJson.put("posts", jsonArray);
    
        JSONObject currentUserJson = new JSONObject();
        currentUserJson.put("userid", user.getID());
        currentUserJson.put("Username", user.Username);
        currentUserJson.put("Name", user.getName());
        currentUserJson.put("Email", user.getEmail());
        currentUserJson.put("Password", user.getPassword());
        if (user.getBirthday() != null) {
            currentUserJson.put("Birthday", user.getBirthday().toString());
        } else {
            currentUserJson.put("Birthday", "");
        }
        currentUserJson.put("bio", ""); 
    
        requestJson.put("currentUser", currentUserJson);
    
        return requestJson;
    }
    
    
    public static JSONObject ContentToJson(String content, RequestType type) {
        JSONObject requestJson = new JSONObject();
        requestJson.put("requestType", type.toString());
        requestJson.put("content", content);

        return requestJson;
    }

    public static List<User> jsonToUsers(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("users");

        List<User> users = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userJson = jsonArray.getJSONObject(i);

            int userid = userJson.getInt("userid");
            String Username = userJson.getString("Username");
            String Name = userJson.getString("Name");
            String Email = userJson.getString("Email");
            String Password = userJson.getString("Password");
            String Birthday = userJson.getString("Birthday");
            TimeDate bday = new TimeDate(Birthday);

            String bio = userJson.getString("bio");

            User user = new User(userid, Username, Name, Email, Password, bday, bio);
            users.add(user);
        }

        return users;
    }
    public static List<Message> jsonToMessages(JSONObject jsonObject) {
        List<Message> messages = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("messages");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject messageJson = jsonArray.getJSONObject(i);
            int messageId = messageJson.getInt("messageId");


            List<User> users = jsonToUsers(messageJson.getJSONArray("users"));            String content = messageJson.getString("content");
            Timestamp timestamp = Timestamp.valueOf(messageJson.getString("timestamp"));


            Message message = new Message(messageId, users.get(0), users.get(1), content, timestamp);
            messages.add(message);
        }

        return messages;
    }
    private static List<User> jsonToUsers(JSONArray jsonArray) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userJson = jsonArray.getJSONObject(i);
            int userId = userJson.getInt("userid");
            String username = userJson.getString("Username");
            String name = userJson.getString("Name");
            String email = userJson.getString("Email");
            String password = userJson.getString("Password");
            String birthday = userJson.getString("Birthday");
            TimeDate bday = new TimeDate(birthday);
            String bio = userJson.getString("bio");

            User user = new User(userId, username, name, email, password, bday, bio);
            users.add(user);
        }

        return users;
    }
    public static List<Post> jsonToPosts(JSONObject jsonObject) {
        List<Post> posts = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("posts");
    
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject postJson = jsonArray.getJSONObject(i);
    
            JSONObject userJson = postJson.getJSONObject("user");
    
            int userId = userJson.getInt("userid");
            String username = userJson.getString("Username");
            String name = userJson.getString("Name");
            String email = userJson.getString("Email");
            String password = userJson.getString("Password");
            String birthday = userJson.getString("Birthday");
            TimeDate bday = new TimeDate(birthday);
            String bio = userJson.getString("bio");
    
            User user = new User(userId, username, name, email, password, bday, bio);
    
            int postId = postJson.getInt("postId");
            String content = postJson.getString("content");
            Timestamp timestamp = Timestamp.valueOf(postJson.getString("timestamp"));
    
            Post post = new Post(postId, user, content, timestamp);
            posts.add(post);
        }
    
        return posts;
    }
    
    public static List<Comment> jsonToComments(JSONObject jsonObject) {
        List<Comment> comments = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("comments");
    
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject commentJson = jsonArray.getJSONObject(i);
    
            JSONObject userJson = commentJson.getJSONObject("user");
            int userId = userJson.getInt("userId");
            String username = userJson.getString("username");
            String name = userJson.getString("name");
            String email = userJson.getString("email");
            String birthdayStr = userJson.getString("birthday");
            TimeDate birthday = (birthdayStr.isEmpty() ? null : new TimeDate(birthdayStr));
            String bio = userJson.getString("bio");
    
            User user = new User(userId, username, name, email, null, birthday, bio);
    
            JSONObject postJson = commentJson.getJSONObject("post");
            int postId = postJson.getInt("postId");
            String postContent = postJson.getString("content");
            Timestamp postTimestamp = Timestamp.valueOf(postJson.getString("timestamp"));
    
            Post post = new Post(postId, user, postContent, postTimestamp);
    
            int commentId = commentJson.getInt("commentId");
            String commentContent = commentJson.getString("content");
            Timestamp commentTimestamp = Timestamp.valueOf(commentJson.getString("timestamp"));
    
            Comment comment = new Comment(commentId, user, commentContent, commentTimestamp, post, null);
            comments.add(comment);
        }
    
        return comments;
    }
    public static String JsonToStatus(JSONObject jsonObject) {
        return jsonObject.getString("status");

    }

    public static boolean jsonToBoolean(JSONObject jsonObject) {
        return jsonObject.getBoolean("value");
    }

    public static String jsonToString(JSONObject jsonObject) {
        return jsonObject.getString("status");
    }
    public static String jsonToStat(JSONObject jsonObject) {
        return jsonObject.getString("stat");
    }

}