package Client.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import Client.Models.User;

import java.util.List;

public class JsonConverter {

    public static JSONObject usersToJson(List<User> users, RequestType type) {
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
        return requestJson;
    }
}