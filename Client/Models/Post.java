package Client.Models;

import java.sql.Timestamp;

public class Post {
    

    public int postId;
    public User user;
    public String content;
    public Timestamp timestamp;


    public Post(int postId, User user, String content, Timestamp timestamp) {
        this.postId = postId;
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }


    public int getPostId() {
        return postId;
    }


    public User getuser() {
        return user;
    }       


    public String getContent() {
        return content;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    

}
