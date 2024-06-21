package Server.Network;

import java.util.List;

import Server.Services.PostService;
import Server.Services.UserService;

public class UserRequest {
    public List<UserService> users;
    public RequestTypeService requestType;
    public String content;
    public PostService post;

    public UserRequest(List<UserService> users, RequestTypeService requestType) {

        this(users, null, requestType);

    }

    public UserRequest(List <UserService> users, String content, RequestTypeService requestType){
        this.requestType = requestType;
        this.content = content;
        this.users = users;

    }
    

    public UserRequest(List <UserService> users, String content, PostService post, RequestTypeService requestType){
        this.requestType = requestType;
        this.content = content;
        this.users = users;
        this.post = post;
    }

}
