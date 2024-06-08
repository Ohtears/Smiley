package Server.Database;

public enum QueryEnum {

    INSERTUSER("INSERT INTO users (username, name, email, password, birthday) VALUES (?, ?, ?, ?, ?)"),
    PARSEUSERS("SELECT user_id, username, name, email, password, birthday, bio FROM users"),
    DELETEUSER("DELETE FROM users WHERE user_id = ?"),
    FETCHPASS("SELECT user_id, username, name, email, password, birthday, bio FROM users WHERE email = ?"),
    ADDFOLLOWER("INSERT INTO followers (user_id, follower_id) VALUES (?, ?)")
    ;
    public String query;

    QueryEnum(String query) {
        
        this.query = query;

    }


}
