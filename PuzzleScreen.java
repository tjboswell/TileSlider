import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PuzzleScreen extends JFrame {
	private JPanel mainPanel, puzzlePanel, infoPanel;
	private JLabel[][] puzzleTileLabel;
	private JLabel timerLabel, movesLabel, clickedLabel, enteredLabel;
	private JButton quitButton;
	private int size, seconds, moves;
	private GridBagConstraints constraints = new GridBagConstraints();
	private Timer timer;
	private BufferedImage[] originalImages, images;
	public PuzzleScreen(int width, int height, Integer x, Integer y, BufferedImage image, int puzzleSize) {
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (x == null && y == null) {
			this.setLocationRelativeTo(null);
		} else {
			this.setLocation(x, y);
		}
		this.size = puzzleSize;

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		puzzlePanel = new JPanel();
		puzzlePanel.setLayout(new GridBagLayout());
		
		infoPanel = new JPanel();
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		TileListener tileListener = new TileListener();
		ButtonListener buttonListener = new ButtonListener();
		TimerListener timerListener = new TimerListener();
		
		timer = new Timer(1000, timerListener);
		timer.start();

		quitButton = new JButton("Quit");
		quitButton.addActionListener(buttonListener);
		
		timerLabel = new JLabel("Time: " + String.format("%02d:%02d", (seconds%3600)/60, seconds%60));
		
		movesLabel = new JLabel("Moves: " + moves);
		originalImages = ImagePuzzler.puzzlify(image, puzzleSize);
		images = ImagePuzzler.shuffle(originalImages);
		puzzleTileLabel = new JLabel[puzzleSize][puzzleSize];
		
		//Goes through the tiles that have been created
		//1. Creates a new label in puzzleTileLabel[x][y] with the image
		//2. Add a mouse listener to the tile
		//3. If it's the last tile, set its tool tip to "empty" (used later)
		int count = 0;
		for (int i = 0; i < puzzleSize; i++) {
			for (int j = 0; j < puzzleSize; j++) {
				constraints.gridx = i;
				constraints.gridy = j;
				puzzleTileLabel[i][j] = new JLabel();
				puzzleTileLabel[i][j].setIcon(new ImageIcon(images[count]));
				puzzleTileLabel[i][j].addMouseListener(tileListener);
				if (count == (puzzleSize * puzzleSize) - 1) {
					puzzleTileLabel[i][j].setToolTipText("empty");
				}
				puzzlePanel.add(puzzleTileLabel[i][j], constraints);
				count++;
			}
		}

		infoPanel.add(quitButton);
		infoPanel.add(timerLabel);
		infoPanel.add(movesLabel);
		mainPanel.add(infoPanel);
		mainPanel.add(puzzlePanel);
		this.add(mainPanel);
		this.setVisible(true);
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == quitButton) {
				//Show a confirmation dialog with yes and no options
				int dialogOptions = JOptionPane.YES_NO_OPTION;
				int dialogResponse = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit? Your progress will not be saved.", "Warning", dialogOptions);
				if (dialogResponse == 0) { //if yes, return to the main menu
					Rectangle bounds = getBounds();
					new MainMenu(bounds.width, bounds.height, bounds.x, bounds.y);
					dispose();
				}
			}
		}
	}
	
	private class TimerListener implements ActionListener {

		@Override
		//Increment the timer by 1 second and update the timer label
		public void actionPerformed(ActionEvent e) {
			seconds++;
			String time = String.format("%02d:%02d", (seconds%3600)/60, seconds%60);
			timerLabel.setText("Time: " + time);
		} 
	}
	private class TileListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			boolean clicked = false;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (!clicked && puzzleTileLabel[i][j].equals(e.getSource())) {
						System.out.println("Clicked tile at row: " + j + ", column: " + i);
						Coordinate top = new Coordinate(i, j - 1);
						Coordinate right = new Coordinate(i + 1, j);
						Coordinate bottom = new Coordinate(i, j + 1);
						Coordinate left = new Coordinate(i - 1, j);
						Swap swapper = new Swap();
						//Check the 4 (or fewer) valid tiles around the clicked tile.
						//If the adjacent tile has the tooltip text "empty", swap the tiles
						if (top.isValid()) {
							if (puzzleTileLabel[top.x][top.y].getToolTipText() == "empty") {
								swapper.swap(i, j, top.x, top.y);
								incrementMoves();
								clicked = true;
							}
						}
						if (right.isValid()) {
							if (puzzleTileLabel[right.x][right.y].getToolTipText() == "empty") {
								swapper.swap(i, j, right.x, right.y);
								incrementMoves();
								clicked = true;
							}
						}
						if (bottom.isValid()) {
							if (puzzleTileLabel[bottom.x][bottom.y].getToolTipText() == "empty") {
								swapper.swap(i, j, bottom.x, bottom.y);
								incrementMoves();
								clicked = true;
							}
						}
						if (left.isValid()) {
							if (puzzleTileLabel[left.x][left.y].getToolTipText() == "empty") {
								swapper.swap(i, j, left.x, left.y);
								incrementMoves();
								clicked = true;
							}
						}
					}
				}
			}
		}
		//Increment moves and update the label text
		public void incrementMoves() {
			moves++;
			movesLabel.setText("Moves: " + moves);
			ImagePuzzler.checkWin(originalImages, puzzleTileLabel, size);
		}
		@Override
		//Set the clickedLabel, aka the original tile
		public void mousePressed(MouseEvent e) {
			clickedLabel = (JLabel) e.getSource();
		}

		@Override
		
		//Check to see if the currently hovered tile is empty. If it is, act like we clicked the original tile
		public void mouseReleased(MouseEvent e) {
			if (enteredLabel.getToolTipText() == "empty") {
				mouseClicked(e);
			}
		}

		@Override
		//Set the enteredLabel, aka the tile that the mouse is hovering
		public void mouseEntered(MouseEvent e) { 
			enteredLabel = (JLabel) e.getSource();
		}

		@Override
		public void mouseExited(MouseEvent e) {	}
	}
	private class Swap {
		public void swap (int originalX, int originalY, int newX, int newY) {
			Component[] components = puzzlePanel.getComponents();
			int swapFrom = 0, swapTo = 0;
			for (int k = 0; k < components.length; k++) {
				if (components[k] instanceof JLabel) {
					if (puzzleTileLabel[originalX][originalY] == components[k]) {
						swapFrom = k;
					} else if (puzzleTileLabel[newX][newY] == components[k])
						swapTo = k;
				}
			}
			puzzlePanel.removeAll();
			JLabel tempLabel = puzzleTileLabel[originalX][originalY];
			puzzleTileLabel[originalX][originalY] = puzzleTileLabel[newX][newY];
			puzzleTileLabel[newX][newY] = tempLabel;
			Component temp = components[swapFrom];
			components[swapFrom] = components[swapTo];
			components[swapTo] = temp;
			int count = 0;
			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					constraints.gridx = x;
					constraints.gridy = y;
					puzzlePanel.add(components[count], constraints);
					count++;
				}
			}
			puzzlePanel.revalidate();
			puzzlePanel.repaint();
		}
	}
	private class Coordinate {
		int x;
		int y;
		private Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
		private boolean isValid() {
			return (x > -1 && x < size && y > -1 && y < size);
		}
	}
}
