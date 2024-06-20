package Server.Network;
import Server.Database.MYSQLHandler;
import Server.Services.MessageService;
import Server.Services.PostService;
import Server.Services.UserService;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class RequestProcessor {
    

    public static JSONObject processRequests(UserRequest userRequest) {
        
        RequestTypeService type = userRequest.requestType;
        UserService user = null;
        UserService user2 = null;
        
        if (userRequest.users.size() >= 1) {
            user = userRequest.users.get(0);
        }
        
        if (userRequest.users.size() >= 2) {
            user2 = userRequest.users.get(1);
        }
        switch (type) {
            case INSERTUSER:
                MYSQLHandler.insertUser(user);
                UserService cur_user = MYSQLHandler.getCurrentUser(user);
                MYSQLHandler.insertUserStatus(cur_user);
                //needs further implementation


            break;
            case CHECKPASSWORD:
                boolean validation = MYSQLHandler.checkPassword(user);
                return JsonConverter.booleanToJson(validation);
                
            case GETCURRENTUSER:

                UserService currentuser = MYSQLHandler.getCurrentUser(user);
                List <UserService> tempuserlist = new ArrayList<>();
                tempuserlist.add(currentuser);
                return JsonConverter.usersToJson(tempuserlist);
            case GETALLUSERS:

                List<UserService> getallusers = MYSQLHandler.getAllUsers();
                return JsonConverter.usersToJson(getallusers);
            case ADDFOLLOWER:

                MYSQLHandler.addFollower(user2, user);

            break;
            case GETCHATLIST: 
                List<UserService> chatlist = MYSQLHandler.getChatList(user);
                
                return JsonConverter.usersToJson(chatlist);

            case STARTCHAT:
            
                MYSQLHandler.startChat(user, user2);
            break;
            case GETCHATBETWEENUSERS:

                List<MessageService> chatbetweenusers = MYSQLHandler.getChatBetweenUsers(user, user2);
                
                return JsonConverter.messagesToJson(chatbetweenusers);

            case SENDMESSAGESCHAT:
                
                String content_msg = userRequest.content;

                MYSQLHandler.sendMessagesChat(user, user2, content_msg);
                String status = MYSQLHandler.GetUserStatus(user2);
                //needs further implementation

                if (status.equals("online")) {
                
                    Socket userSocket = ResponseHandler.userSocketMap.get(user2);
                    try {
                        ResponseHandler.sendResponse(userSocket, "100");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case GETALLFOLLOWERS:
                
                List<UserService> allfollowers = MYSQLHandler.getAllFollowers(user);

                System.out.println(allfollowers);
                System.out.println(allfollowers);
                System.out.println(allfollowers);
                System.out.println(allfollowers);
                System.out.println(allfollowers);
                System.out.println(allfollowers);
                System.out.println(allfollowers);


                return JsonConverter.usersToJson(allfollowers);

            case HEARTBEAT:

                JSONObject jsonreq = JsonConverter.HeartbeatToJson();
                return jsonreq;

            
            case CREATEPOST:

                String content_post = userRequest.content;

                MYSQLHandler.insertPost(user, content_post);


            break;

            case CREATECOMMENT:

            break;

            case GETALLPOSTS:

                List<PostService> listallposts = MYSQLHandler.getAllPosts();

                return JsonConverter.postsToJson(listallposts);

            case GETALLCOMMENTS:

            break;

            case GETALLFOLLOWING:

                List<UserService> listallfollowing = MYSQLHandler.getAllFollowing(user);
                
                return JsonConverter.usersToJson(listallfollowing);



            default:
                break;

        }
    return null;
}
}
