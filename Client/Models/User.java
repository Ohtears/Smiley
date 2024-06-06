package Client.Models;

public class User{

    private int userid;
    private String Username;
    private String Email;
    private String Password;
    private TimeDate Birthday;

    public User(int userid, String Username, String Email, String Password, TimeDate Birthday){

        this.userid = userid;
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
        this.Birthday = Birthday;
        init2database();
    }
    
    private void init2database(){

        

    }



}