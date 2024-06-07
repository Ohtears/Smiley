package Client.Models;

import Server.Database.MYSQLHandler;

public class User{

    @SuppressWarnings("unused")
    private int userid;
    @SuppressWarnings("unused")
    private String Username;
    @SuppressWarnings("unused")
    private String Email;
    @SuppressWarnings("unused")
    private String Password;
    @SuppressWarnings("unused")
    private TimeDate Birthday;

    public User(int userid, String Username, String Email, String Password, TimeDate Birthday){

        this.userid = userid;
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
        this.Birthday = Birthday;
        MYSQLHandler.InsertUserQuery(Username, Email, Password, Birthday);
    }
    

}