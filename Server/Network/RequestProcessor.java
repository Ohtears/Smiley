package Server.Network;
import Server.Database.MYSQLHandler;
import Server.Services.MessageService;
import Server.Services.UserService;

import java.util.List;

public class RequestProcessor {
    

    public void processRequests(UserRequest userRequest) {
        
        RequestTypeService type = userRequest.requestType;
        
        UserService user = userRequest.users.get(0);  
        UserService user2 = userRequest.users.get(1);  


        switch (type) {
            case INSERTUSER:
                MYSQLHandler.insertUser(user);
            break;
            case CHECKPASSWORD:
                boolean validation = MYSQLHandler.checkPassword(user);

            break;
            case GETCURRENTUSER:

                UserService currentuser = MYSQLHandler.getCurrentUser(user);

            break;
            case GETALLUSERS:

                List<UserService> getallusers = MYSQLHandler.getAllUsers();

            break;
            case ADDFOLLOWER:

                MYSQLHandler.addFollower(user, user2);

            break;
            case GETCHATLIST: 
                List<UserService> chatlist = MYSQLHandler.getChatList(user);
            break;
            case GETUSERBYID:
                UserService userbyid = MYSQLHandler.getUserById(user);
            break;
            case STARTCHAT:
            
                MYSQLHandler.startChat(user, user2);
            break;
            case GETCHATBETWEENUSERS:

                List<MessageService> chatbetweenusers = MYSQLHandler.getChatBetweenUsers(user, user2);
                
            break;
            case SENDMESSAGESCHAT:
                MYSQLHandler.sendMessagesChat(user, user2, null); //NEEDS FURTHER WORK
            break;
            case GETALLFOLLOWERS:
                List<Integer> allfollowers = MYSQLHandler.getAllFollowers(user);

            break;

    }
}
}
