package Client.GUI.MainApp;

import javax.swing.*;
import org.json.JSONObject;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Client.GUI.MainApp.Dashboard.PanelSwitchListener;
import Client.Models.CurrentUser;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestHandler.Callback;
import Client.Network.RequestType;
import Client.Network.Refreshable;

public class DMListPanel extends JPanel implements Refreshable {

    private PanelSwitchListener listener;

    public DMListPanel(PanelSwitchListener listener) {
        this.listener = listener;
        setBorder(BorderFactory.createTitledBorder("DM List"));
        setBackground(new Color(44, 47, 51));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        refreshDMList(); 
    }

    @Override
    public void refresh() {
        refreshDMList();
    }

    private void refreshDMList() {
        SwingUtilities.invokeLater(() -> {
            removeAll(); 
            User currentUser = CurrentUser.getInstance().getUser();
            List<User> userList = new ArrayList<>();
            userList.add(currentUser);
            JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.GETCHATLIST);
            RequestHandler requestHandler = new RequestHandler();

            requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
                @Override
                public void onSuccess(String response) {
                    JSONObject responseServer = new JSONObject(response);
                    List<User> users = JsonConverter.jsonToUsers(responseServer);
                    for (User user : users) {
                        ChatPanel chatPanel = new ChatPanel(user, listener);
                        Dimension maxPanelSize = new Dimension(300, 50);
                        chatPanel.setMaximumSize(maxPanelSize);
                        add(chatPanel);
                    }
                    revalidate();
                    repaint();
                }

                @Override
                public void onFailure(IOException e) {
                    JOptionPane.showMessageDialog(null, "Request failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }
}
