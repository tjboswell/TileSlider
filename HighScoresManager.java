import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HighScoresManager {
	public static List<String> readHighScores() {
		List<String> highScores = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File("highscores.csv"));
			while(scanner.hasNextLine()) {
				highScores.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			highScores.add("Error retrieving high scores.");
		}
		return highScores;
	}
	public static void writeHighScores(int size, int time, int moves) {
		List<String> highScores = readHighScores();
	}
}
