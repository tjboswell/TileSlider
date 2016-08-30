import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighScoresMenu extends JFrame {
	private JPanel mainPanel, highScoresPanel;
	private JButton backButton;
	private JLabel highScoresLabel;
	private List<String> highScores;
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
		
		highScoresPanel = new JPanel();
		highScoresPanel.setLayout(new BoxLayout(highScoresPanel, BoxLayout.PAGE_AXIS));
		
		ButtonListener buttonListener = new ButtonListener();
		
		backButton = new JButton("Back");
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.addActionListener(buttonListener);
		
		highScoresLabel = new JLabel("High Scores");
		highScoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font highScoresLabelFont = highScoresLabel.getFont();
		highScoresLabel.setFont(new Font(highScoresLabelFont.getName(), Font.PLAIN, highScoresLabelFont.getSize()*3));
		
		
		JLabel highScoresHeaderLabel = new JLabel("     Size   |   Time  |  Moves");
		Font highScoresHeaderFont = highScoresHeaderLabel.getFont();
		highScoresHeaderLabel.setFont(new Font(highScoresHeaderFont.getName(), Font.BOLD, highScoresHeaderFont.getSize()));
		highScoresHeaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		highScoresPanel.add(highScoresHeaderLabel);
		
		highScores = HighScoresReader.HighScores();
		for (int i = 0; i < highScores.size(); i++) {
			String[] splitHighScore = highScores.get(i).split(",");
			String puzzleSize = splitHighScore[0];
			int seconds = Integer.parseInt(splitHighScore[1]);
			int moves = Integer.parseInt(splitHighScore[2]);
			String time = String.format("%02d:%02d", (seconds%3600)/60, seconds%60);
			JLabel highScore = new JLabel(puzzleSize + "x" + puzzleSize + "  |  " + time + "  |  " + moves);
			highScore.setAlignmentX(Component.CENTER_ALIGNMENT);
			highScoresPanel.add(highScore);
		}
		
		mainPanel.add(highScoresLabel);
		mainPanel.add(backButton);
		mainPanel.add(highScoresPanel);
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
