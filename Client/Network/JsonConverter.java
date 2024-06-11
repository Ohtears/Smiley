package Client.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import Client.Models.Message;
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
        requestJson.put("requestType", type.toString());
        


        JSONArray jsonArray = new JSONArray();
        for (User user : users) {
            JSONObject userJson = new JSONObject();
            userJson.put("userid", user.getID());
            userJson.put("Username", user.Username);
            userJson.put("Name", user.getName());
            userJson.put("Email", user.getEmail());
            userJson.put("Password", user.getPassword());
            userJson.put("Birthday", user.getBirthday().toString());
            userJson.put("bio", user.getBio());
            
            jsonArray.put(userJson);
        }
        
        requestJson.put("users", jsonArray);
        if (content != null) {
            requestJson.put("content", content);
        }
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
                int senderId = messageJson.getInt("senderId");
                int receiverId = messageJson.getInt("receiverId");
                String content = messageJson.getString("content");
                Timestamp timestamp = Timestamp.valueOf(messageJson.getString("timestamp"));

                Message message = new Message(messageId, senderId, receiverId, content, timestamp);
                messages.add(message);
            }

        return messages;
    }
    public static boolean jsonToBoolean(JSONObject jsonObject) {
        return jsonObject.getBoolean("value");
    }
}