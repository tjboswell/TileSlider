import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	public static boolean writeHighScores(int size, int time, int moves) {
		boolean isHighScore = false;
		List<String> highScores = readHighScores();
		for (int i = 0; i < highScores.size(); i++) {
			String[] splitHighScore = highScores.get(i).split(",");
			if (Integer.parseInt(splitHighScore[0]) == size) { //find matching line
				if (splitHighScore[1].equals("null") || splitHighScore[2].equals("null")) { //if there's no high score, set it
					highScores.set(i, size + "," + time + "," + moves);
					isHighScore = true;
				} else { //if there is a high score, compare it to our time
					if (Integer.parseInt(splitHighScore[1]) > time) {
						highScores.set(i, size + "," + time + "," + moves);
						isHighScore = true;
					}
				}
			}
		}
		if (isHighScore) {
			try {
				FileOutputStream file = new FileOutputStream("highscores.csv");
				
				for (int i = 0; i < highScores.size(); i++) {
					file.write(highScores.get(i).getBytes());
					file.write('\n');
				}
				file.close();
			} catch (IOException e) {
				System.out.println("Error saving to high scores!");
			}
		}
		
		return isHighScore;
	}
	public static void reset() {
		try {
			FileOutputStream file = new FileOutputStream("highscores.csv");
			
			for (int i = 3; i <= 10; i++) {
				file.write((Integer.toString(i) + ",null,null").getBytes());
				file.write('\n');
			}
			file.close();
		} catch (IOException e) {
			System.out.println("Error saving to high scores!");
		}
	}
}
