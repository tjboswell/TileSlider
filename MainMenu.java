import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JFrame {
	private JPanel mainPanel;
	private JButton startButton, highScoresButton, exitButton;
	private JLabel gameLabel;
	public MainMenu(int width, int height, Integer x, Integer y) {
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (x == null && y == null) {
			this.setLocationRelativeTo(null);
		} else {
			this.setLocation(x, y);
		}

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		gameLabel = new JLabel("TileSlider!");
		gameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		ButtonListener buttonListener = new ButtonListener();
		startButton = new JButton("Start Game");
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(buttonListener);
		
		highScoresButton = new JButton("High Scores");
		highScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		highScoresButton.addActionListener(buttonListener);
		
		exitButton = new JButton("Exit Game");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(buttonListener);
		
		mainPanel.add(gameLabel);
		mainPanel.add(Box.createGlue());
		mainPanel.add(startButton);
		mainPanel.add(highScoresButton);
		mainPanel.add(exitButton);
		
		this.add(mainPanel);
		this.setVisible(true);
	}
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == startButton) {
				Rectangle bounds = getBounds();
				System.out.println(bounds);
				new PuzzleTypeMenu(bounds.width, bounds.height, bounds.x, bounds.y);
				dispose();
			} else if (e.getSource() == highScoresButton) {
				
			} else if (e.getSource() == exitButton) {
				System.exit(0);
			}
			
		}
	}
}
