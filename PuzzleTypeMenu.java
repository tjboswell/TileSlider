import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PuzzleTypeMenu extends JFrame {
	private JPanel mainPanel;
	private JButton customImageButton, presetImageButton, backButton;
	private JLabel puzzleSelectionLabel;
	final JFileChooser imageChooser = new JFileChooser();

	public PuzzleTypeMenu(int width, int height, Integer x, Integer y) {
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (x == null && y == null) {
			this.setLocationRelativeTo(null);
		} else {
			this.setLocation(x, y);
		}

		mainPanel = new JPanel();
		
		puzzleSelectionLabel = new JLabel("Pick an puzzle type");
		puzzleSelectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		ButtonListener buttonListener = new ButtonListener();

		customImageButton = new JButton("Custom Image");
		customImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		customImageButton.addActionListener(buttonListener);
		
		presetImageButton = new JButton("Preset Image");
		presetImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		presetImageButton.addActionListener(buttonListener);
		
		backButton = new JButton("Back");
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.addActionListener(buttonListener);
		
		mainPanel.add(puzzleSelectionLabel);
		mainPanel.add(customImageButton);
		mainPanel.add(presetImageButton);
		mainPanel.add(backButton);
		
		this.add(mainPanel);
		this.setVisible(true);
	}
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == customImageButton) {
			    int returnVal = imageChooser.showOpenDialog(PuzzleTypeMenu.this);
			    System.out.println(returnVal);
			} else if (e.getSource() == presetImageButton) {
				
			} else if (e.getSource() == backButton) {
				Rectangle bounds = getBounds();
				new MainMenu(bounds.width, bounds.height, bounds.x, bounds.y);
				dispose();
			}
			
		}
	}
}
