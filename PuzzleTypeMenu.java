import java.awt.Component;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
	private JLabel puzzleSelectionLabel, puzzleSizeLabel, currentPuzzleSizeLabel, thumbnailLabel;
	private SpinnerModel puzzleSizeSpinnerModel;
	private JSpinner puzzleSizeSpinner;
	private JDialog presetDialog = new JDialog();
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
		imageChooser.setFileFilter(fileFilter);
		
		puzzleSelectionLabel = new JLabel("Pick an puzzle type");
		puzzleSelectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		customImageButton = new JButton("Custom Image");
		customImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		customImageButton.addActionListener(buttonListener);
		
		presetImageButton = new JButton("Preset Image");
		presetImageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		presetImageButton.addActionListener(buttonListener);
		
		puzzleSizeLabel = new JLabel("Select a puzzle size");
		
		puzzleSizeSpinnerModel = new SpinnerNumberModel(3, 3, 10, 1);
		puzzleSizeSpinner = new JSpinner(puzzleSizeSpinnerModel);
		puzzleSizeSpinner.addChangeListener(spinnerListener);
		
		currentPuzzleSizeLabel = new JLabel("x 3");
		
		thumbnailLabel = new JLabel("No image selected");
		

		backButton = new JButton("Back");
		backButton.addActionListener(buttonListener);
		
		startButton = new JButton("Create Puzzle");
		startButton.addActionListener(buttonListener);
		
		puzzleSelectionPanel.add(puzzleSelectionLabel);
		puzzleSelectionPanel.add(customImageButton);
		puzzleSelectionPanel.add(presetImageButton);
		
		sizePanel.add(puzzleSizeLabel);
		sizePanel.add(puzzleSizeSpinner);
		sizePanel.add(currentPuzzleSizeLabel);
		
		previewPanel.add(thumbnailLabel);

		bottomMenuPanel.add(backButton);
		bottomMenuPanel.add(startButton);

		mainPanel.add(puzzleSelectionPanel);
		mainPanel.add(sizePanel);
		mainPanel.add(previewPanel);
		mainPanel.add(bottomMenuPanel);

		this.add(mainPanel);
		this.setVisible(true);
	}
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == customImageButton) {
			    int returnVal = imageChooser.showOpenDialog(PuzzleTypeMenu.this);
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	try {
						thumbnailLabel.setText(imageChooser.getSelectedFile().getName());
			    		originalImage = ImageIO.read(imageChooser.getSelectedFile());
						ImageIcon imageIcon = new ImageIcon(originalImage);
						Rectangle bounds = previewPanel.getBounds();
						Rectangle imageBounds = Resizer.resize(originalImage.getWidth(), originalImage.getHeight(), bounds.width, bounds.height);
						thumbnailImage = imageIcon.getImage().getScaledInstance(imageBounds.width, imageBounds.height, Image.SCALE_SMOOTH);
						thumbnailLabel.setIcon(new ImageIcon(thumbnailImage));
					} catch (Exception e1) {
						thumbnailLabel.setText("Error uploading image.");
					}
			    }
			} else if (e.getSource() == presetImageButton) {
				
			} else if (e.getSource() == backButton) {
				Rectangle bounds = getBounds();
				new MainMenu(bounds.width, bounds.height, bounds.x, bounds.y);
				dispose();
			} else if (e.getSource() == startButton) {
				System.out.println("Creating puzzle");
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
