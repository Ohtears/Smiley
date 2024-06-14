package Server.Services;

import java.sql.Timestamp;

public class PostService {
    

    public int postId;
    public UserService user;
    public String content;
    public Timestamp timestamp;


    public PostService(int postId, UserService user, String content, Timestamp timestamp) {
        this.postId = postId;
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }


    public int getPostId() {
        return postId;
    }


    public UserService getUser() {
        return user;
    }       


    public String getContent() {
        return content;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    

}
