package Server.Database;

public enum QueryEnum {

    INSERTUSER("INSERT INTO users (username, name, email, password, birthday) VALUES (?, ?, ?, ?, ?)"),
    PARSEUSERS("SELECT user_id, username, name, email, password, birthday, bio FROM users"),
    DELETEUSER("DELETE FROM users WHERE user_id = ?"),
    FETCHPASS("SELECT user_id, username, name, email, password, birthday, bio FROM users WHERE email = ?"),
    ADDFOLLOWER("INSERT INTO followers (user_id, follower_id) VALUES (?, ?)"),
    FETCHFOLLOWERS("SELECT u.user_id, u.username, u.name, u.email, u.birthday, u.bio " + //
                         "FROM users u " + //
                         "JOIN followers f ON u.user_id = f.follower_id " + //
                         "WHERE f.user_id = ?"),
    FETCHWITHID("SELECT username, name, email, password, birthday, bio FROM users WHERE user_id = ?"),
    FETCHCHATS("SELECT u.user_id, u.username, u.name, u.bio, u.birthday\n" + //
                "FROM users u\n" + //
                "JOIN chats c ON (u.user_id = c.user1_id OR u.user_id = c.user2_id)\n" + //
                "WHERE (c.user1_id = ? OR c.user2_id = ?)\n" + //
                "ORDER BY c.last_message_time DESC"),
    
    STARTCHAT("INSERT INTO chats (user1_id, user2_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE last_message_time = CURRENT_TIMESTAMP"),
    INSERTCHAT("INSERT INTO chatmessages (sender_id, receiver_id, message_content) VALUES (?, ?, ?) "),
    FETCHCHAT("SELECT * FROM chatmessages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)"),
    FETCHSTATUS("SELECT status FROM user_status WHERE user_id = ?"),
    UPDATESTATUS("UPDATE user_status SET status = ? WHERE user_id = ?"),
    UPDATESTATUSOFFLINE("UPDATE user_status SET status = 'offline' WHERE status = 'offline', AND last_activity < ? "),
    INSERTSTATUS("INSERT INTO user_status (user_id, status) VALUES (?, ?)"),
    GETUSERID("SELECT * FROM users WHERE user_id = ?"),
    GETALLPOSTS("SELECT \n" + //
                "    posts.post_id,\n" + //
                "    posts.content,\n" + //
                "    posts.created_at,\n" + //
                "    users.user_id,\n" + //
                "    users.username,\n" + //
                "    users.name,\n" + //
                "    users.email,\n" + //
                "    users.birthday,\n" + //
                "    users.bio\n" + //
                "FROM \n" + //
                "    posts\n" + //
                "JOIN \n" + //
                "    users ON posts.user_id = users.user_id"),
                
    INSERTPOST("INSERT INTO posts (user_id, content) VALUES (?, ?)"),
    GETALLFOLLOWING("SELECT u.user_id, u.username, u.name, u.email, u.birthday, u.bio " + //
                            "FROM users u " + //
                            "JOIN followers f ON u.user_id = f.user_id " + //
                            "WHERE f.follower_id = ?")
    ;
    public String query;

    QueryEnum(String query) {
        
        this.query = query;

    }


}
