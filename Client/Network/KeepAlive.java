package Client.Network;

import org.json.JSONObject;

import Client.Models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class KeepAlive {

    private User user;
    private Timer timer;
    private static final int SCHEDULE_INTERVAL_SECONDS = 10;

    public KeepAlive(User user) {
        this.user = user;
        timer = new Timer();
    }

    public void startSendingRequests() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendRequestToServer();
            }
        }, 0, SCHEDULE_INTERVAL_SECONDS * 1000);
    }

    public void stopSendingRequests() {
        timer.cancel();
    }

    private void sendRequestToServer() {
        List<User> userList5 = new ArrayList<>();
        userList5.add(user);
        JSONObject jsonRequest = JsonConverter.usersToJson(userList5, RequestType.HEARTBEAT);

        RequestHandler requestHandler = new RequestHandler(); 

        requestHandler.sendRequestAsync(jsonRequest.toString(), new RequestHandler.Callback() {
            @Override
            public void onSuccess(String response) {
            }

            @Override
            public void onFailure(IOException e) {
                JOptionPane.showMessageDialog(null, "Lost connection to the server", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
