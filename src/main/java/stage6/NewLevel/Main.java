package stage6.NewLevel;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Play play = new Play();
        play.playGame();
    }
}

class Input {
    public int getLengthOfSecretCode() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please, enter the length of the secret code: ");
        return scanner.nextInt();
    }

    public int getNumberOfSymbols() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please, enter the number of symbols in the secret code: ");
        return scanner.nextInt();
    }

    public String getGuess() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}

class Generate {
    public List<Character> listOfPossibleSymbols(int symbols) {
        List<Character> list = new ArrayList<>();
        for (char i = 48; i <= 122; i++) {
            if (i == 58) {
                i = 97;
            }
            list.add(i);
        }
        return list.subList(0, symbols);
    }

    public void getPrepared(int length, List<Character> symbols) {
        String asterisk = "*" + "*".repeat(Math.max(0, length -1));
        String possibleSymbols;
        int lastSymbol = symbols.size()-1;

        if (symbols.size() <= 10) {
            possibleSymbols = String.format("(0-%s).", symbols.get(lastSymbol));
        }
        else {
            possibleSymbols = String.format("(0-9, a-%s).", symbols.get(lastSymbol));
        }
        System.out.printf("The secret is prepared: %s %s\n", asterisk, possibleSymbols);
    }

    public String generateSecretCode(int length, int symbols, List<Character> symbolsList) {
        Set<Character> seenSymbols = new HashSet<>();
        StringBuilder secretCode = new StringBuilder();
        char randomSymbol;

        for (int i = 0; i < length; i++) {
            randomSymbol = symbolsList.get((int) (Math.random() * symbols));
            while (seenSymbols.contains(randomSymbol)) {
                randomSymbol = symbolsList.get((int) (Math.random() * symbols));
            }
            seenSymbols.add(randomSymbol);
            secretCode.append(randomSymbol);
        }
        return secretCode.toString();
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

class CheckGrade {
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
    CheckGrade check = new CheckGrade();

    void playGame() {
        int length;
        int symbolSize;
        int turnCounter;
        String secretCode;
        String guess;
        List<Character> symbols;

        length = input.getLengthOfSecretCode();
        symbolSize = input.getNumberOfSymbols();

        if (length > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.\n", length);
            return;
        }

        symbols = generate.listOfPossibleSymbols(symbolSize);
        generate.getPrepared(length, symbols);

        secretCode = generate.generateSecretCode(length, symbolSize, symbols);

        turnCounter = 1;

        do {
            System.out.printf("Turn %d:\n", turnCounter);
            guess = input.getGuess();
            Grade grade = check.getGrade(secretCode, guess);
            check.checkGrade(length, grade);

            turnCounter++;
        }
        while (!guess.equals(secretCode));
    }
}




