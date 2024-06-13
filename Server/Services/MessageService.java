package Server.Services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private int messageId;
    private UserService sender;
    private UserService receiver;
    private String content;
    private Timestamp timestamp;

    public MessageService(int messageId, UserService sender, UserService receiver, String content, Timestamp timestamp) {
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getMessageId() {
        return messageId;
    }

    public UserService getSender() {
        return sender;
    }

    public UserService getReceiver() {
        return receiver;
    }

    public List<UserService> getUsers(){
        List<UserService> users = new ArrayList<>();
        users.add(sender);
        users.add(receiver);
        return users;
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
                ", senderId=" + sender.getID() +
                ", receiverId=" + receiver.getID() +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
