package Server.Network;

import java.util.List;

import Server.Services.UserService;

public class UserRequest {
    public List<UserService> users;
    public RequestTypeService requestType;

    public UserRequest(List<UserService> users, RequestTypeService requestType) {
        this.users = users;
        this.requestType = requestType;
    }

}
