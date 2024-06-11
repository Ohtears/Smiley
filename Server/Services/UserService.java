package Server.Services;

public class UserService{

    private int userid;
    public String Username;
    private String Email;
    private String Password;
    private TimeDateService Birthday;
    private String Name;
    private String bio;

    public UserService(int userid, String Username, String Name, String Email, String Password, TimeDateService Birthday, String bio){

        this.userid = userid;
        this.Username = Username;
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
        this.Birthday = Birthday;
        this.bio = bio;
        
    }

    public int getID(){

        return userid;
    }

    public String getName() {

        return Name;
    }

    public TimeDateService getBirthday() {

        return Birthday;
    }

    public String getBio() {

        return bio;
    }
    public String getpassword() {

        return Password;
    }
    public String getemail() {

        return Email;
    }
}