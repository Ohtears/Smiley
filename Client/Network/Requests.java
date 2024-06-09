package Client.Network;
import java.io.Serializable;
import Client.Models.*;

public class Requests implements Serializable {
    
    private User user;
    private TimeDate timedate;
    private Message message;
    private String handshake;  

    public Requests(String handshake, User user){
        
        this.handshake = handshake;
        this.user = user;
    }

    public Requests(String handshake, TimeDate timedate){
        
        this.handshake = handshake;
        this.timedate = timedate;

    }

    public Requests(String handshake, Message message){

        this.handshake = handshake;
        this.message = message;
    }
}
