package Client.GUI.MainApp;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import Client.GUI.MainApp.Chat.ChatPanel;
import Client.Models.CurrentUser;
import Client.Models.User;
import Server.Database.MYSQLHandler;

public class DMListPanel extends JPanel {

    public DMListPanel(PanelSwitchListener listener) {
        setBorder(BorderFactory.createTitledBorder("DM List"));
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

        User currentUser = CurrentUser.getInstance().getUser();

        List<User> users = MYSQLHandler.getChatList(currentUser.getID());
        for (User user : users) {
            // ImageIcon userIcon = new ImageIcon("Untitled.jpeg");  
            ChatPanel chatPanel = new ChatPanel(user, listener);
            Dimension maxPanelSize = new Dimension(300, 50); 
            chatPanel.setMaximumSize(maxPanelSize);
            add(chatPanel);
        }
    }
}
