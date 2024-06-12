package Server.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import Server.Services.MessageService;
import Server.Services.TimeDateService;
import Server.Services.UserService;

import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    public static UserRequest jsonToUsers(JSONObject jsonObject) {
        RequestTypeService requestType = RequestTypeService.valueOf(jsonObject.getString("requestType"));
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
            userJson.put("bio", user.getBio());
            
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
            messageJson.put("senderId", message.getSenderId());
            messageJson.put("receiverId", message.getReceiverId());
            messageJson.put("content", message.getContent());
            messageJson.put("timestamp", message.getTimestamp().toString()); 

            jsonArray.put(messageJson);
        }

        JSONObject messagesJson = new JSONObject();
        messagesJson.put("messages", jsonArray);

        return messagesJson;
    }

    public static JSONObject booleanToJson(boolean value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("value", value);
        return jsonObject;
    }
}
