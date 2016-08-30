import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HighScoresReader {
	public static List<String> HighScores() {
		List<String> highScores = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File("highscores.csv"));
			while(scanner.hasNextLine()) {
				highScores.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			highScores.add("Error retrieving high scores.");
		}
		return highScores;
	}
}
