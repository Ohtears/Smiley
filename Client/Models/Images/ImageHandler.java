package Client.Models.Images;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImageHandler extends JPanel {
    private final Image image;

    public ImageHandler(String imagePath) {
        image = new ImageIcon(getClass().getResource(imagePath)).getImage();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
