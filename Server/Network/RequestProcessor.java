package Server.Network;
import Server.Database.MYSQLHandler;
import Server.Services.MessageService;
import Server.Services.UserService;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class RequestProcessor {
    

    public static JSONObject processRequests(UserRequest userRequest) {
        
        RequestTypeService type = userRequest.requestType;
        
        UserService user = userRequest.users.get(0);  
        UserService user2 = userRequest.users.get(1);  


        switch (type) {
            case INSERTUSER:
                MYSQLHandler.insertUser(user);

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

                MYSQLHandler.addFollower(user, user2);

            break;
            case GETCHATLIST: 
                List<UserService> chatlist = MYSQLHandler.getChatList(user);
                
                return JsonConverter.usersToJson(chatlist);

            case GETUSERBYID:
                UserService userbyid = MYSQLHandler.getUserById(user);

                List <UserService> tempuserlist1 = new ArrayList<>();
                tempuserlist1.add(userbyid);

                return JsonConverter.usersToJson(tempuserlist1);

            case STARTCHAT:
            
                MYSQLHandler.startChat(user, user2);
            break;
            case GETCHATBETWEENUSERS:

                List<MessageService> chatbetweenusers = MYSQLHandler.getChatBetweenUsers(user, user2);
                
                return JsonConverter.messagesToJson(chatbetweenusers);

            case SENDMESSAGESCHAT:
                
                MYSQLHandler.sendMessagesChat(user, user2, null); //NEEDS FURTHER WORK
                

                break;
            case GETALLFOLLOWERS:
                
                // List<Integer> allfollowers = MYSQLHandler.getAllFollowers(user);

                //needs adjustment

            break;

        }
    return null;
}
}
