package Client.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import Client.Models.User;
import Server.Network.RequestTypeService;
import Server.Network.UserRequest;
import Server.Services.TimeDateService;
import Server.Services.UserService;

import java.util.ArrayList;
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
        public static UserRequest jsonToUsers(JSONObject jsonObject) {
        RequestTypeService requestType = RequestTypeService.valueOf(jsonObject.getString("requestType"));
        JSONArray jsonArray = jsonObject.getJSONArray("users");

        List<UserService> users = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userJson = jsonArray.getJSONObject(i);

            int userid = userJson.getInt("userid");
            String Username = userJson.getString("Username");
            String Name = userJson.getString("Name");
            String Email = userJson.getString("Email");
            String Password = userJson.getString("Password");
            String Birthday = userJson.getString("Birthday");
            TimeDateService bday = new TimeDateService(Birthday);

            String bio = userJson.getString("bio");

            UserService user = new UserService(userid, Username, Name, Email, Password, bday, bio);
            users.add(user);
        }

        return new UserRequest(users, requestType);
    }
}