package stage3.SecretCode;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Print print = new Print();
        print.printSecretNumber();
    }
}

class Input {
    public int getLengthOfSecretNumber() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}

class Generate {
    private int generateRandomDigit() {
        Random random = new Random();
        return random.nextInt(10);
    }

    public String generateSecretNumber(int length) {
        StringBuilder secretNumber = new StringBuilder();
        Set<Integer> seenDigits = new HashSet<>();
        int randomDigit;

        for (int i = 0; i < length; i++) {
            randomDigit = this.generateRandomDigit();
            while (seenDigits.contains(randomDigit)) {
                randomDigit = this.generateRandomDigit();
            }
            seenDigits.add(randomDigit);
            secretNumber.append(randomDigit);
        }
        return secretNumber.toString();
    }
}

class Print {
    void printSecretNumber() {
        final int length = new Input().getLengthOfSecretNumber();
        if (length > 10) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.\n", length);
        }
        else {
            final String secretNumber = new Generate().generateSecretNumber(length);
            System.out.printf("The random secret number is %s.\n", secretNumber);
        }
    }
}