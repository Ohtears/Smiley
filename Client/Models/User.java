package Client.Models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestType;

public class User{

    private int userid;
    public String Username;
    private String Email;
    private String Password;
    private TimeDate Birthday;
    private String Name;
    private String bio;

    public User(int userid, String Username, String Name, String Email, String Password, TimeDate Birthday, String bio){

        this.userid = userid;
        this.Username = Username;
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
        this.Birthday = Birthday;
        this.bio = bio;
        
    }
    
    public static void send2db(User user){

        // MYSQLHandler.insertUser(Username, Name, Email, Password, Birthday);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.INSERTUSER);
        RequestHandler.call(jsonRequest);

    }

    public int getID(){

        return userid;
    }

    public String getName() {

        return Name;
    }

    public TimeDate getBirthday() {

        return Birthday;
    }

    public String getBio() {

        return bio;
    }

    public String getEmail() {
        
        return Email;

    }

    public String getPassword() {

        return Password;

    }


}