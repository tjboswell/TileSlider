import java.awt.Rectangle;

public class Resizer {
	public static Rectangle resize(int originalWidth, int originalHeight, int containerWidth, int containerHeight) {
		int newWidth = originalWidth;
		int newHeight = originalHeight;
		if (originalWidth > containerWidth) {
			newWidth = containerWidth;
			newHeight = (newWidth * originalHeight) / originalWidth;
		}
		if (newHeight > containerHeight) {
			newHeight = containerHeight;
			newWidth = (newHeight * originalWidth) / originalHeight;
		}
		Rectangle bounds = new Rectangle(newWidth, newHeight);
		return bounds;
	}
}
