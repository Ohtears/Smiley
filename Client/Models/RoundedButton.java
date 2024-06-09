package Client.Models;

import javax.swing.*;
import javax.swing.border.*;

public class RoundedButton {
    public static void makeRound(JButton button, int cornerRadius) {

        Border roundedBorder = new LineBorder(button.getForeground(), cornerRadius, true);
        Border compoundBorder = BorderFactory.createCompoundBorder(
            roundedBorder, 
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        );
        button.setBorder(compoundBorder);
        button.setBorderPainted(false); 
    }
}
