package Client.Models;

import java.sql.Timestamp;

public class Comment {
    private int commentId;
    private Post originalPost;
    private String content;
    private User user;
    private Timestamp timestamp;
    private Comment originalComment;

    public Comment(int commentId, User user, String content, Timestamp timestamp, Post originalPost, Comment originalComment) {
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

    public Comment getOriginalComment() {
        return originalComment;
    }
}
