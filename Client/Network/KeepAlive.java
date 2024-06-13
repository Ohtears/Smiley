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
    private static final int SCHEDULE_INTERVAL_SECONDS = 1;
    private Refreshable refreshable;

    public KeepAlive(User user, Refreshable refreshable) {
        this.user = user;
        this.refreshable = refreshable;
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
        List<User> userList = new ArrayList<>();
        userList.add(user);
        JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.HEARTBEAT);

        RequestHandler requestHandler = new RequestHandler(); 

        requestHandler.sendRequestAsync(jsonRequest.toString(), new RequestHandler.Callback() {
            @Override
            public void onSuccess(String response) {

                JSONObject responseServer = new JSONObject(response);

                String resp = JsonConverter.jsonToStat(responseServer);

                if (resp.equals("201 refresh") && refreshable != null) {
                    refreshable.refresh();
                }
            }

            @Override
            public void onFailure(IOException e) {
                JOptionPane.showMessageDialog(null, "Lost connection to the server", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
