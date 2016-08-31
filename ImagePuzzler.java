import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImagePuzzler {
	public static BufferedImage[] puzzlify(BufferedImage image, int puzzleSize) {
		int numTiles = puzzleSize * puzzleSize;
		int tileWidth = image.getWidth() / puzzleSize;
		int tileHeight = image.getHeight() / puzzleSize;
		int imageType = image.getType();
		BufferedImage tiles[] = new BufferedImage[numTiles];
		int count = 0;
		for (int i = 0; i < puzzleSize; i++) {
			for (int j = 0; j < puzzleSize; j++) {
				tiles[count] = new BufferedImage(tileWidth, tileHeight, imageType);
				int horizontalOffset = tileWidth * i;
				int verticalOffset = tileHeight * j;

				Graphics2D graphics = tiles[count].createGraphics();
				graphics.drawImage(image, 0, 0, tileWidth, tileHeight, horizontalOffset, verticalOffset, horizontalOffset + tileWidth, verticalOffset + tileHeight, null);
				graphics.dispose();
				count++;
			}
		}
		return tiles;
	}
	
	public static TilesAndOrder shuffle(BufferedImage[] tiles) {
		int numTiles = tiles.length;
		List<Integer> list = new ArrayList<>();
		BufferedImage[] shuffledTiles = new BufferedImage[numTiles];
		for (int i = 0; i < numTiles; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		Graphics2D graphics = tiles[tiles.length - 1].createGraphics();
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle(0, 0, tiles[tiles.length - 1].getWidth(), tiles[tiles.length - 1].getHeight()));
		int count = 0;
		for (int j : list) {
			shuffledTiles[count] = tiles[j];
			count++;
		}
		
		graphics.dispose();
		return new TilesAndOrder(shuffledTiles, list);
	}
	
	public static boolean checkWin(List<Integer> order) {
		List<Integer> sorted = new ArrayList<Integer>(order);
		Collections.sort(sorted);
		return order.equals(sorted);
	}
	public static class TilesAndOrder {
		public BufferedImage[] tiles;
		public List<Integer> order;
		
		public TilesAndOrder(BufferedImage[] tiles, List<Integer> order) {
			this.tiles = tiles;
			this.order = order;
		}
	}
}
