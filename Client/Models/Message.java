package Client.Models;

import java.sql.Timestamp;

public class Message {
    private int messageId;
    private User sender;
    private User receiver;
    private String content;
    private Timestamp timestamp;

    public Message(int messageId, User sender, User receiver, String content, Timestamp timestamp) {
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getMessageId() {
        return messageId;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", senderId=" + sender +
                ", receiverId=" + receiver +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
