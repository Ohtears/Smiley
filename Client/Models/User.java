package Client.Models;

import Server.Database.MYSQLHandler;

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
    
    public void send2db(){

        MYSQLHandler.insertUser(Username, Name, Email, Password, Birthday);

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
}