package Server.Database;

public enum QueryEnum {

    INSERTUSER("INSERT INTO users (username, name, email, password, birthday) VALUES (?, ?, ?, ?, ?)"),
    PARSEUSERS("SELECT user_id, username, name, email, password, birthday, bio FROM users"),
    DELETEUSER("DELETE FROM users WHERE user_id = ?"),
    FETCHPASS("SELECT user_id, username, name, email, password, birthday, bio FROM users WHERE email = ?"),
    ADDFOLLOWER("INSERT INTO followers (user_id, follower_id) VALUES (?, ?)"),
    FETCHFOLLOWERS("SELECT user_id WHERE follower_id = ? "),
    FETCHWITHID("SELECT username, name, email, password, birthday, bio FROM users WHERE user_id = ?"),
    FETCHCHATS("SELECT u.user_id, u.username, u.name, u.bio, u.birthday\n" + //
                "FROM users u\n" + //
                "JOIN chats c ON (u.user_id = c.user1_id OR u.user_id = c.user2_id)\n" + //
                "WHERE (c.user1_id = ? OR c.user2_id = ?)\n" + //
                "ORDER BY c.last_message_time DESC"),
    STARTCHAT("INSERT INTO chats (user1_id, user2_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE last_message_time = CURRENT_TIMESTAMP"),
    INSERTCHAT("INSERT INTO ChatMessages (sender_id, receiver_id, message_content) VALUES (?, ?, ?) "),
    FETCHCHAT("SELECT * FROM ChatMessages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)")
    ;
    public String query;

    QueryEnum(String query) {
        
        this.query = query;

    }


}
