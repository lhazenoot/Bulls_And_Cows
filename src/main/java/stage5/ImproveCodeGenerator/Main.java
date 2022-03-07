package stage5.ImproveCodeGenerator;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Play play = new Play();
        play.playGame();
    }
}

class Input {
    public int getLengthOfSecretNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please, enter the secret code's length: ");
        return scanner.nextInt();
    }

    public String getGuess() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}

class Generate {
    public String generateSecretNumber(int length) {
        Set<Integer> seenDigits = new HashSet<>();
        StringBuilder secretNumber = new StringBuilder();
        int randomDigit;

        for (int i = 0; i < length; i++) {
            randomDigit = (int) (Math.random() * 10);
            while (seenDigits.contains(randomDigit)) {
                randomDigit = new Random().nextInt(10);
            }
            seenDigits.add(randomDigit);
            secretNumber.append(randomDigit);
        }
        return secretNumber.toString();
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

    void checkGrade(int length, Grade grade) {
        String gradeString;

        if (grade.bulls == length) {
            System.out.printf("Grade: %d bulls\n", grade.bulls);
            System.out.println("Congratulations! You guessed the secret code.");
            return;
        }
        else if (grade.bulls <= 1 && grade.cows <= 1) {
            gradeString = String.format("%d bull and %d cow", grade.bulls, grade.cows);
        }
        else if (grade.bulls > 1 && grade.cows <= 1) {
            gradeString = String.format("%d bulls and %d cow", grade.bulls, grade.cows);
        }
        else if (grade.bulls > 1 && grade.cows > 1) {
            gradeString = String.format("%d bull and %d cows", grade.bulls, grade.cows);
        }
        else {
            gradeString = String.format("%d bull and %d cow", grade.bulls, grade.cows);
        }
        System.out.printf("Grade: %s\n", gradeString);
    }
}

class Play {
    Input input = new Input();
    Generate generate = new Generate();
    Game game = new Game();

    int length;
    String secretNumber;
    String guess;
    Grade grade;

    void playGame() {
        int turnCounter = 1;
        length = input.getLengthOfSecretNumber();
        if (length > 10) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.\n", length);
            return;
        }
        secretNumber = generate.generateSecretNumber(length);

        System.out.println("Okay, let's start a game!");

        do {
            System.out.printf("Turn %d:\n", turnCounter);
            guess = input.getGuess();
            grade = game.getGrade(secretNumber, guess);

            game.checkGrade(length, grade);

            turnCounter++;
        }
        while (!guess.equals(secretNumber));
    }
}
