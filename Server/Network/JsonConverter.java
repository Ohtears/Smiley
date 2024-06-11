package Server.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import Server.Services.TimeDateService;
import Server.Services.UserService;

import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

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
