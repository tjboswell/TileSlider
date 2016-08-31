import java.awt.Component;
import java.awt.FlowLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HighScoresMenu extends JFrame {
	private JPanel mainPanel, buttonPanel, highScoresPanel;
	private JButton backButton, resetButton;
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
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		highScoresPanel = new JPanel();
		highScoresPanel.setLayout(new BoxLayout(highScoresPanel, BoxLayout.PAGE_AXIS));
		
		ButtonListener buttonListener = new ButtonListener();
		
		backButton = new JButton("Back");
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.addActionListener(buttonListener);
		
		resetButton = new JButton("Reset");
		resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		resetButton.addActionListener(buttonListener);
		
		highScoresLabel = new JLabel("High Scores");
		highScoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font highScoresLabelFont = highScoresLabel.getFont();
		highScoresLabel.setFont(new Font(highScoresLabelFont.getName(), Font.PLAIN, highScoresLabelFont.getSize()*3));
		
		
		JLabel highScoresHeaderLabel = new JLabel("     Size   |   Time  |  Moves");
		Font highScoresHeaderFont = highScoresHeaderLabel.getFont();
		highScoresHeaderLabel.setFont(new Font(highScoresHeaderFont.getName(), Font.BOLD, highScoresHeaderFont.getSize()));
		highScoresHeaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		highScoresPanel.add(highScoresHeaderLabel);
		
		highScores = HighScoresManager.readHighScores();
		int length = 0;
		for (int i = 0; i < highScores.size(); i++) {
			String[] splitHighScore = highScores.get(i).split(",");
			String puzzleSize = splitHighScore[0];
			try {
				int seconds = Integer.parseInt(splitHighScore[1]);
				int moves = Integer.parseInt(splitHighScore[2]);
				String time = String.format("%02d:%02d", (seconds%3600)/60, seconds%60);
				JLabel highScore = new JLabel(puzzleSize + "x" + puzzleSize + "  |  " + time + "  |  " + moves);
				highScore.setAlignmentX(Component.CENTER_ALIGNMENT);
				highScoresPanel.add(highScore);
				length++;
			}
			catch (NumberFormatException e) {
				//no valid high score, skip this line
			}
		}
		if (length == 0) {
			JLabel noHighScores = new JLabel("No high scores!");
			noHighScores.setAlignmentX(Component.CENTER_ALIGNMENT);
			highScoresPanel.add(noHighScores);
		}
		
		buttonPanel.add(backButton);
		buttonPanel.add(resetButton);
		mainPanel.add(highScoresLabel);
		mainPanel.add(highScoresPanel);
		mainPanel.add(buttonPanel);
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
			} else if (e.getSource() == resetButton) {
				int dialogOptions = JOptionPane.YES_NO_OPTION;
				int dialogResponse = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the high scores? This will permanently delete all high scores!", "Warning", dialogOptions);
				if (dialogResponse == 0) { //if yes, reset high scores
					HighScoresManager.reset();
					Rectangle bounds = getBounds();
					new HighScoresMenu(bounds.width, bounds.height, bounds.x, bounds.y);
					dispose();
				}
			}
		}
	}
}
