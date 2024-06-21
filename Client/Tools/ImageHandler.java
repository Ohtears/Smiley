package Client.Tools;

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

    public void drawCircularImage(Graphics g, int x, int y, int diameter) { //TO DO FOR LATER
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setClip(new java.awt.geom.Ellipse2D.Float(x, y, diameter, diameter));
        g2.drawImage(image, x, y, diameter, diameter, this);
        g2.dispose();
    }
    
}
