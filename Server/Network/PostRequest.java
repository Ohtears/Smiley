package Server.Network;
import java.util.List;

import Server.Services.PostService;
import Server.Services.UserService;

public class PostRequest extends UserRequest {
    private List<PostService> posts;

    public PostRequest(List<UserService> users, String content, RequestTypeService requestType, List<PostService> posts) {
        super(users, content, requestType);
        this.posts = posts;
    }

    public List<PostService> getPosts() {
        return posts;
    }

    public void setPosts(List<PostService> posts) {
        this.posts = posts;
    }
}