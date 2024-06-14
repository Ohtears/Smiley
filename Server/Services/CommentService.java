package Server.Services;

import java.sql.Timestamp;

public class CommentService {
    private PostService originalPost;
    private String content;
    private UserService user;
    private Timestamp timestamp;

    public CommentService(int commentId, UserService user, String content, Timestamp timestamp, PostService originalPost) {
        this.originalPost = originalPost;
        this.content = content;
        this.user = user;
        this.timestamp = timestamp;

    }

    public PostService getOriginalPost() {
        return originalPost;
    }

    public String getCommentContent() {
        return content;
    }

    public String getContent() {
        return content;
    }

    public UserService getUser() {
        return user;
    }   

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
