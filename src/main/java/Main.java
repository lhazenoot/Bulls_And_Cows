import java.util.*;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}

class Input {
    public int getSecretLength() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please, enter the length of the secret code: ");
        String input = scanner.next();
        int length = 0;

        try {
            length = Integer.parseInt(input);
            if (length > 36) {
                System.out.println("Error: secret length cannot be greater than 36");
                System.exit(0);
            }
            else if (length < 1) {
                System.out.println("Error: secret length cannot be less than 1");
                System.exit(0);
            }
        }
        catch (NumberFormatException e) {
            System.out.printf("Error: %s isn't a valid number.", input);
            System.exit(0);
        }
        return length;
    }

    public int getSymbolLength(int secretLength) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please, enter the number of symbols in the secret code: ");
        String input = scanner.next();
        int symbolSize = 0;

        try {
            symbolSize = Integer.parseInt(input);
            if (symbolSize > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(0);
            }
            else if (symbolSize < 1) {
                System.out.println("Error: symbols range length cannot be less than 1");
                System.exit(0);
            }
            else if (secretLength > symbolSize) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %s unique symbols.", secretLength, symbolSize);
                System.exit(0);
            }
        }
        catch (NumberFormatException e) {
            System.out.printf("Error: %s isn't a valid number.", input);
            System.exit(0);
        }
        return symbolSize;
    }

    public String getGuess() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}

class Generate {
    public List<Character> possibleSymbols(int symbolSize) {
        List<Character> possibleSymbols = new ArrayList<>();
        for (char i = 48; i <= 122; i++) {
            if (i == 58) {
                i = 97;
            }
            possibleSymbols.add(i);
        }
        return possibleSymbols.subList(0, symbolSize);
    }

    public void printPrepared(int secretLength, List<Character> listOfSymbols) {
        String asterisk = "*" + "*".repeat(Math.max(0, secretLength -1));
        String symbols;
        int lastSymbol = listOfSymbols.size()-1;

        if (listOfSymbols.size() <= 10) {
            symbols = String.format("(0-%s).", listOfSymbols.get(lastSymbol));
        }
        else {
            symbols = String.format("(0-9, a-%s).", listOfSymbols.get(lastSymbol));
        }
        System.out.printf("The secret is prepared: %s %s\n", asterisk, symbols);
    }

    public String generateSecretCode(int secretLength, int symbolSize, List<Character> listOfSymbols) {
        Set<Character> seenSymbols = new HashSet<>();
        StringBuilder secretCode = new StringBuilder();
        char randomSymbol;

        for (int i = 0; i < secretLength; i++) {
            randomSymbol = listOfSymbols.get((int) (Math.random() * symbolSize));
            while (seenSymbols.contains(randomSymbol)) {
                randomSymbol = listOfSymbols.get((int) (Math.random() * symbolSize));
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
    public Grade getGrade(String secret, String guess) {
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

    public void checkGrade(int length, Grade grade) {
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
        else if (grade.bulls > 1) {
            gradeString = String.format("%d bull and %d cows", grade.bulls, grade.cows);
        }
        else {
            gradeString = String.format("%d bull and %d cow", grade.bulls, grade.cows);
        }
        System.out.printf("Grade: %s\n", gradeString);
    }
}

class Game {
    Input input = new Input();
    Generate generate = new Generate();
    CheckGrade check = new CheckGrade();

    public void playGame() {
        int secretLength = input.getSecretLength();
        int symbolSize = input.getSymbolLength(secretLength);
        List<Character> listOfSymbols = generate.possibleSymbols(symbolSize);
        String secretCode = generate.generateSecretCode(secretLength, symbolSize, listOfSymbols);

        generate.printPrepared(secretLength, listOfSymbols);

        int turnCounter = 1;
        String guess;

        do {
            System.out.printf("Turn %d:\n", turnCounter);
            guess = input.getGuess();
            Grade grade = check.getGrade(secretCode, guess);
            check.checkGrade(secretLength, grade);

            turnCounter++;
        }
        while (!guess.equals(secretCode));
    }
}


