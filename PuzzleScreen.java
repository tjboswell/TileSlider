import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PuzzleScreen extends JFrame {
	private JPanel mainPanel, puzzlePanel;
	private JLabel[][] puzzleTileLabel;
	private JButton backButton;
	private int size;
	private GridBagConstraints constraints = new GridBagConstraints();
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
		
		TileListener tileListener = new TileListener();
		ButtonListener buttonListener = new ButtonListener();

		backButton = new JButton("Back");
		backButton.addActionListener(buttonListener);
		
		BufferedImage tiles[] = ImagePuzzler.puzzlify(image, puzzleSize);
		tiles = ImagePuzzler.shuffle(tiles);
		puzzleTileLabel = new JLabel[puzzleSize][puzzleSize];
		
		int count = 0;
		for (int i = 0; i < puzzleSize; i++) {
			for (int j = 0; j < puzzleSize; j++) {
				constraints.gridx = i;
				constraints.gridy = j;
				puzzleTileLabel[i][j] = new JLabel();
				puzzleTileLabel[i][j].setIcon(new ImageIcon(tiles[count]));
				puzzleTileLabel[i][j].addMouseListener(tileListener);
				if (count == (puzzleSize * puzzleSize) - 1) {
					puzzleTileLabel[i][j].setToolTipText("empty");
				}
				puzzlePanel.add(puzzleTileLabel[i][j], constraints);
				count++;
			}
		}

		mainPanel.add(backButton);
		mainPanel.add(puzzlePanel);
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
						if (top.isValid()) {
							if (puzzleTileLabel[top.x][top.y].getToolTipText() == "empty") {
								swapper.swap(i, j, top.x, top.y);
								clicked = true;
							}
						}
						if (right.isValid()) {
							if (puzzleTileLabel[right.x][right.y].getToolTipText() == "empty") {
								swapper.swap(i, j, right.x, right.y);
								clicked = true;
							}
						}
						if (bottom.isValid()) {
							if (puzzleTileLabel[bottom.x][bottom.y].getToolTipText() == "empty") {
								swapper.swap(i, j, bottom.x, bottom.y);
								clicked = true;
							}
						}
						if (left.isValid()) {
							if (puzzleTileLabel[left.x][left.y].getToolTipText() == "empty") {
								swapper.swap(i, j, left.x, left.y);
								clicked = true;
							}
						}
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) { }

		@Override
		public void mouseReleased(MouseEvent e) { }

		@Override
		public void mouseEntered(MouseEvent e) { }

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
