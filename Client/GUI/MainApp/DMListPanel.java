package Client.GUI.MainApp;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import Client.Models.CurrentUser;
import Client.Models.User;
import Server.Database.MYSQLHandler;

public class DMListPanel extends JPanel {

    public DMListPanel() {
        setBorder(BorderFactory.createTitledBorder("DM List"));
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

        User currentUser = CurrentUser.getInstance().getUser();

        List<User> users = MYSQLHandler.getChatList(currentUser.getID());
        for (User user : users) {
            ImageIcon userIcon = new ImageIcon("Untitled.jpeg");  
            ChatPanel chatPanel = new ChatPanel(user.Username, userIcon);
            add(chatPanel);
        }
    }
}
