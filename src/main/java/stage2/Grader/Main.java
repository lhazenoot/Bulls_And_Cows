package stage2.Grader;

import java.util.Scanner;

// bulls: cijfer is correct en op de juiste positie.
// cows: cijfer is correct, maar niet op de juiste positie.

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.play();

    }
}

class Grade {
    int bulls;
    int cows;

    Grade(int bulls, int cows) {
        this.bulls = bulls;
        this.cows = cows;
    }
}

class Game {

    String getGuess() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    Grade getGrade(String secret, String guess) {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secret.charAt(i)) {
                bulls++;
            }
            else if (secret.indexOf(guess.charAt(i)) != -1) {
                cows++;
            }
        }
        return new Grade(bulls, cows);
    }

    void printGrade(String secret, Grade grade) {
        String gradeString;

        if (grade.bulls != 0 && grade.cows != 0) {
            gradeString = String.format("%d bull(s) and %d cow(s)", grade.bulls, grade.cows);
        }
        else if (grade.bulls != 0) {
            gradeString = String.format("%d bull(s)", grade.bulls);
        }
        else if (grade.cows != 0) {
            gradeString = String.format("%d cow(s)", grade.cows);
        }
        else {
            gradeString = "None";
        }
        System.out.printf("Grade: %s. The secret code is %s.\n", gradeString, secret);
    }

    void play() {
        String secret = "9305";
        String guess = getGuess();
        Grade grade = getGrade(secret, guess);
        printGrade(secret, grade);
    }
}

