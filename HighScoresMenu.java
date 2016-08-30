import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighScoresMenu extends JFrame {
	private JPanel mainPanel;
	private JButton backButton;
	private JLabel highScoresLabel;
	public HighScoresMenu(int width, int height, Integer x, Integer y) {
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (x == null && y == null) {
			this.setLocationRelativeTo(null);
		} else {
			this.setLocation(x, y);
		}

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		backButton = new JButton("Back");
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		highScoresLabel = new JLabel("High Scores");
		highScoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font highScoresLabelFont = highScoresLabel.getFont();
		highScoresLabel.setFont(new Font(highScoresLabelFont.getName(), Font.PLAIN, highScoresLabelFont.getSize()*3));
		
		mainPanel.add(highScoresLabel);
		mainPanel.add(backButton);
		this.add(mainPanel);
		this.setVisible(true);
	}
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == backButton) {
				Rectangle bounds = getBounds();
				new MainMenu(bounds.width, bounds.height, bounds.x, bounds.y);
				dispose();
			
			}
		}
	}
}
