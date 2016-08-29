import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HintMenu extends JFrame {
	private ImagePanel imagePanel;

	public HintMenu(int width, int height, Integer x, Integer y, BufferedImage hintImage) {
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (x == null && y == null) {
			this.setLocationRelativeTo(null);
		} else {
			this.setLocation(x + width, y);
		}

		imagePanel = new ImagePanel();
		imagePanel.setImage(hintImage);
		this.add(imagePanel);
		this.setVisible(true);
	}
	
	public class ImagePanel extends JPanel {
		Image image;
		public void setImage(Image image) {
			this.image = image;
		}
		public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}
}
