package Server.Network;
import Server.Database.MYSQLHandler;
import Server.Services.UserService;


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
                MYSQLHandler.checkPassword(user);

            break;
            case GETCURRENTUSER:

                MYSQLHandler.getCurrentUser(user);

            break;
            case GETALLUSERS:

                MYSQLHandler.getAllUsers();

            break;
            case ADDFOLLOWER:

                MYSQLHandler.addFollower(user, user2);

            break;
            case GETCHATLIST: 
                MYSQLHandler.getChatList(user);
            break;
            case GETUSERBYID:
                MYSQLHandler.getUserById(user);
            break;
            case STARTCHAT:
            
                MYSQLHandler.startChat(user, user2);
            break;
            case GETCHATBETWEENUSERS:

                MYSQLHandler.getChatBetweenUsers(user, user2);
                
            break;
            case SENDMESSAGESCHAT:
                MYSQLHandler.sendMessagesChat(user, user2, null); //NEEDS FURTHER WORK
            break;
            case GETALLFOLLOWERS:
                MYSQLHandler.getAllFollowers(user);

            break;

    }
}
}
