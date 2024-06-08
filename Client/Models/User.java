package Client.Models;

import Server.Database.MYSQLHandler;

public class User{

    @SuppressWarnings("unused")
    private int userid;
    public String Username;
    private String Email;
    private String Password;
    private TimeDate Birthday;
    private String Name;
    @SuppressWarnings("unused")
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

        MYSQLHandler.InsertUserQuery(Username, Name, Email, Password, Birthday);


    }
}