package Server.Services;

import java.sql.Timestamp;

public class CommentService {
    private int commentId;
    private PostService originalPost;
    private String content;
    private UserService user;
    private Timestamp timestamp;
    private CommentService originalComment;

    public CommentService(int commentId, UserService user, String content, Timestamp timestamp, PostService originalPost, CommentService originalComment) {
        this.commentId = commentId;
        this.originalPost = originalPost;
        this.content = content;
        this.user = user;
        this.timestamp = timestamp;
        this.originalComment = originalComment;

    }

    public int getCommentId() {
        return commentId;
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

    public CommentService getOriginalComment() {
        return originalComment;

}
}