import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PuzzleTypeMenu extends JFrame {
	private JPanel mainPanel, puzzleSelectionPanel, sizePanel, previewPanel, bottomMenuPanel;
	private JButton customImageButton, presetImageButton, backButton, startButton;
	private JLabel puzzleOptionsLabel, puzzleSelectionLabel, puzzleSizeLabel, currentPuzzleSizeLabel, thumbnailLabel, thumbnailNameLabel, errorLabel;
	private SpinnerModel puzzleSizeSpinnerModel;
	private JSpinner puzzleSizeSpinner;
	private BufferedImage originalImage;
	private Image thumbnailImage;
	final JFileChooser imageChooser = new JFileChooser();
	final FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
	
	public PuzzleTypeMenu(int width, int height, Integer x, Integer y) {
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (x == null && y == null) {
			this.setLocationRelativeTo(null);
		} else {
			this.setLocation(x, y);
		}

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		puzzleSelectionPanel = new JPanel();
		sizePanel = new JPanel();
		previewPanel = new JPanel();
		bottomMenuPanel = new JPanel();
		
		ButtonListener buttonListener = new ButtonListener();
		SpinnerListener spinnerListener = new SpinnerListener();
		
		puzzleOptionsLabel = new JLabel("Puzzle Options");
		Font puzzleOptionsLabelFont = puzzleOptionsLabel.getFont();
		puzzleOptionsLabel.setFont(new Font(puzzleOptionsLabelFont.getName(), Font.PLAIN, puzzleOptionsLabelFont.getSize()*2));
		puzzleOptionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		puzzleSelectionLabel = new JLabel("Pick an image to use: ");
		puzzleSelectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		customImageButton = new JButton("Custom Image");
		customImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		customImageButton.addActionListener(buttonListener);
		
		presetImageButton = new JButton("Preset Image");
		presetImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		presetImageButton.addActionListener(buttonListener);
		
		thumbnailLabel = new JLabel();
		thumbnailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		thumbnailNameLabel = new JLabel("No image selected");
		thumbnailNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		puzzleSizeLabel = new JLabel("Select a puzzle size: ");
		
		puzzleSizeSpinnerModel = new SpinnerNumberModel(3, 3, 10, 1);
		puzzleSizeSpinner = new JSpinner(puzzleSizeSpinnerModel);
		puzzleSizeSpinner.addChangeListener(spinnerListener);
		
		currentPuzzleSizeLabel = new JLabel("x 3");

		backButton = new JButton("Back");
		backButton.addActionListener(buttonListener);
		
		startButton = new JButton("Create Puzzle");
		startButton.addActionListener(buttonListener);
		
		errorLabel = new JLabel();
		errorLabel.setForeground(Color.RED);
		errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		puzzleSelectionPanel.add(puzzleSelectionLabel);
		puzzleSelectionPanel.add(customImageButton);
		puzzleSelectionPanel.add(presetImageButton);

		previewPanel.add(thumbnailLabel);

		sizePanel.add(puzzleSizeLabel);
		sizePanel.add(puzzleSizeSpinner);
		sizePanel.add(currentPuzzleSizeLabel);
		
		bottomMenuPanel.add(backButton);
		bottomMenuPanel.add(startButton);

		mainPanel.add(puzzleOptionsLabel);
		mainPanel.add(puzzleSelectionPanel);
		mainPanel.add(previewPanel);
		mainPanel.add(thumbnailNameLabel);
		mainPanel.add(sizePanel);
		mainPanel.add(errorLabel);
		mainPanel.add(bottomMenuPanel);

		this.add(mainPanel);
		this.setVisible(true);
	}
	private class ButtonListener implements ActionListener {

		public void setImage() {
			try {
				thumbnailNameLabel.setForeground(Color.BLACK);
				thumbnailNameLabel.setText(imageChooser.getSelectedFile().getName());
	    		originalImage = ImageIO.read(imageChooser.getSelectedFile());
				ImageIcon imageIcon = new ImageIcon(originalImage);
				Rectangle bounds = previewPanel.getBounds();
				Rectangle imageBounds = Resizer.resize(originalImage.getWidth(), originalImage.getHeight(), bounds.width, bounds.height);
				thumbnailImage = imageIcon.getImage().getScaledInstance(imageBounds.width, imageBounds.height, Image.SCALE_SMOOTH);
				thumbnailLabel.setIcon(new ImageIcon(thumbnailImage));
				errorLabel.setText("");
			} catch (Exception e1) {
				thumbnailNameLabel.setForeground(Color.RED);
				thumbnailNameLabel.setText("Error uploading image.");
				thumbnailLabel.setIcon(null);
			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == customImageButton) {
				imageChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			    int returnVal = imageChooser.showOpenDialog(PuzzleTypeMenu.this);
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	this.setImage();
			    }
			} else if (e.getSource() == presetImageButton) {
				File currentDirectory = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "images");
				imageChooser.setCurrentDirectory(currentDirectory);
				int returnVal = imageChooser.showOpenDialog(PuzzleTypeMenu.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					this.setImage();
				}
			} else if (e.getSource() == backButton) {
				Rectangle bounds = getBounds();
				new MainMenu(bounds.width, bounds.height, bounds.x, bounds.y);
				dispose();
			} else if (e.getSource() == startButton) {
				if (originalImage == null) {
					errorLabel.setText("Please select a valid image.");
				} else {
					Rectangle bounds = getBounds();
					new PuzzleScreen(bounds.width, bounds.height, bounds.x, bounds.y, originalImage, (int) puzzleSizeSpinner.getValue());
					dispose();
				}
			}
		}
	}
	private class SpinnerListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			currentPuzzleSizeLabel.setText("x " + String.valueOf(puzzleSizeSpinner.getValue()));
		}		
	}
}
