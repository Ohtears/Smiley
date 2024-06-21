package Server.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import Server.Services.CommentService;
import Server.Services.MessageService;
import Server.Services.PostService;
import Server.Services.TimeDateService;
import Server.Services.UserService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    public static UserRequest jsonToUsers(JSONObject jsonObject) {
        RequestTypeService requestType = RequestTypeService.valueOf(jsonObject.getString("requestType"));

        if  (requestType.equals(RequestTypeService.CREATECOMMENT)) {
            return retrieveFromJson(jsonObject);
        }

        JSONArray jsonArray = jsonObject.getJSONArray("users");

        List<UserService> users = new ArrayList<>();
        TimeDateService bday = null; 
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userJson = jsonArray.getJSONObject(i);

            int userid = userJson.getInt("userid");
            String Username = userJson.getString("Username");
            String Name = userJson.getString("Name");
            String Email = userJson.getString("Email");
            String Password = userJson.getString("Password");
            try {
                String Birthday = userJson.getString("Birthday");
                bday = new TimeDateService(Birthday);
                
            }
            catch (Exception e) {
                bday = null;

            }
            String bio = userJson.getString("bio");
            
            UserService user = new UserService(userid, Username, Name, Email, Password, bday, bio);
            users.add(user);
        }

        String content = null;
        try {
            content = jsonObject.getString("content");
        } catch (Exception e) {
        }

        return new UserRequest(users, content, requestType);
    }

    public static UserRequest retrieveFromJson(JSONObject jsonObject) {
        JSONArray postsArray = jsonObject.getJSONArray("posts");
        TimeDateService bday = null; 

        for (int i = 0; i < postsArray.length(); i++) {
            JSONObject postJson = postsArray.getJSONObject(i);

            int postId = postJson.getInt("postId");
            JSONObject userJson = postJson.getJSONObject("user");

            int userId = userJson.getInt("userid");
            String username = userJson.getString("Username");
            String name = userJson.getString("Name");
            String email = userJson.getString("Email");
            String password = userJson.getString("Password");
            
            try {
                String Birthday = userJson.getString("Birthday");
                bday = new TimeDateService(Birthday);
                
            }
            catch (Exception e) {
                bday = null;

            }
            String bio = userJson.getString("bio");

            UserService user = new UserService(userId, username, name, email, password, bday, bio);

            String content = postJson.getString("content");
            Timestamp timestamp = Timestamp.valueOf(postJson.getString("timestamp"));

            PostService post = new PostService(postId, user, content, timestamp);

            RequestTypeService requestType = RequestTypeService.valueOf(jsonObject.getString("requestType"));

            TimeDateService endbday = null;

            JSONObject endUserJson = jsonObject.getJSONObject("currentUser");
            int endUserId = endUserJson.getInt("userid");
            String endUsername = endUserJson.getString("Username");
            String endName = endUserJson.getString("Name");
            String endEmail = endUserJson.getString("Email");
            String endPassword = endUserJson.getString("Password");
            try {
                String Birthday = userJson.getString("Birthday");
                endbday = new TimeDateService(Birthday);
                
            }
            catch (Exception e) {
                endbday = null;

            }
            String endBio = endUserJson.getString("bio");

            String content_comment = jsonObject.getString("content"); 

            UserService endUser = new UserService(endUserId, endUsername, endName, endEmail, endPassword, endbday, endBio);
            List<UserService> users = new ArrayList<>();
            // List<PostService> posts = new ArrayList<>();

            users.add(endUser);
            // posts.add(post);
            return new UserRequest(users, content_comment, post, requestType);
        }
        
    return null;
    }

    // public static UserRequest jsonToRequestAndContent(JSONObject jsonObject) {

    //     RequestTypeService requestType = RequestTypeService.valueOf(jsonObject.getString("requestType"));
    //     String content = jsonObject.optString("content", null);
    
    //     return new UserRequest(requestType, content);
    // }
    public static JSONObject usersToJson(List<UserService> users) {
        JSONObject requestJson = new JSONObject();
        
        JSONArray jsonArray = new JSONArray();
        for (UserService user : users) {
            JSONObject userJson = new JSONObject();
            userJson.put("userid", user.getID());
            userJson.put("Username", user.Username);
            userJson.put("Name", user.getName());
            userJson.put("Email", user.getemail());
            userJson.put("Password", user.getpassword());
            userJson.put("Birthday", user.getBirthday().toString());
            if (user.getBio() != null){
                
                userJson.put("bio", user.getBio());

            }
            else {
                userJson.put("bio", "");

            }
            
            jsonArray.put(userJson);
        }
        
        requestJson.put("users", jsonArray);
        return requestJson;
    }
    public static JSONObject messagesToJson(List<MessageService> messages) {
        JSONArray jsonArray = new JSONArray();

        for (MessageService message : messages) {
            JSONObject messageJson = new JSONObject();
            messageJson.put("messageId", message.getMessageId());
            JSONObject ListUser = usersToJson(message.getUsers());
            messageJson.put("users", ListUser.getJSONArray("users")); 
            messageJson.put("content", message.getContent());
            messageJson.put("timestamp", message.getTimestamp().toString()); 

            jsonArray.put(messageJson);
        }

        JSONObject messagesJson = new JSONObject();
        messagesJson.put("messages", jsonArray);
        return messagesJson;
    }
    public static JSONObject postsToJson(List<PostService> posts) {
        JSONArray jsonArray = new JSONArray();

        for (PostService post : posts) {
            JSONObject postJson = new JSONObject();
            postJson.put("postId", post.getPostId());
            
            List<UserService> userList = new ArrayList<>();
            userList.add(post.getUser());
            
            JSONObject userJson = usersToJson(userList);
            postJson.put("user", userJson.getJSONArray("users").getJSONObject(0)); 

            postJson.put("content", post.getContent());
            postJson.put("timestamp", post.getTimestamp().toString());

            jsonArray.put(postJson);
        }

        JSONObject postsJson = new JSONObject();
        postsJson.put("posts", jsonArray);
        
        return postsJson;
    }

    public static JSONObject commentsToJson(List<CommentService> comments) {
        JSONArray jsonArray = new JSONArray();
    
        for (CommentService comment : comments) {
            JSONObject commentJson = new JSONObject();
            commentJson.put("commentId", comment.getCommentId());
    
            // User JSON Object
            JSONObject userJson = new JSONObject();
            UserService user = comment.getUser();
            userJson.put("userId", user.getID());
            userJson.put("username", user.Username);
            userJson.put("name", user.getName());
            userJson.put("email", user.getemail());
            userJson.put("birthday", (user.getBirthday() != null) ? user.getBirthday().toString() : "");
            userJson.put("bio", "");
            commentJson.put("user", userJson);
    
            // Post JSON Object
            JSONObject postJson = new JSONObject();
            PostService post = comment.getOriginalPost();
            postJson.put("postId", post.getPostId());
            postJson.put("content", post.getContent());
            commentJson.put("post", postJson);
    
            commentJson.put("content", comment.getContent());
            commentJson.put("timestamp", comment.getTimestamp().toString());
    
            jsonArray.put(commentJson);
        }
    
        JSONObject commentsJson = new JSONObject();
        commentsJson.put("comments", jsonArray);
    
        return commentsJson;
    }
    

    public static JSONObject booleanToJson(boolean value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", value);
        return jsonObject;
    }

    public static JSONObject StatusToJson(String status, int user_id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid", user_id);
        jsonObject.put("status", status);
        return jsonObject;
    }

    public static String JsonToStatus(JSONObject jsonObject) {
        return jsonObject.getString("status");

    }

    public static int JsonToUserId(JSONObject jsonObject) {

        return jsonObject.getInt("userid");

    }

    public static JSONObject HeartbeatToJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stat", "201 refresh");
        return jsonObject;
    }

}