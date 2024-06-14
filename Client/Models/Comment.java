package Client.Models;

import java.sql.Timestamp;

public class Comment {
    private Post originalPost;
    private String content;
    private User user;
    private Timestamp timestamp;

    public Comment(int commentId, User user, String content, Timestamp timestamp, Post originalPost) {
        this.originalPost = originalPost;
        this.content = content;
        this.user = user;
        this.timestamp = timestamp;

    }

    public Post getOriginalPost() {
        return originalPost;
    }

    public String getCommentContent() {
        return content;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }   

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
