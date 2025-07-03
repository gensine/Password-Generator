import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PasswordGenerator {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int length = 0;
        while (length < 4) {
            System.out.print("Password length (minimum 4): ");
            if (scanner.hasNextInt()) {
                length = scanner.nextInt();
                if (length < 4) {
                    System.out.println("Password length should be at least 4.");
                }
            } else {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }

        boolean useUpper = ask(scanner, "Include uppercase letters? (y/n): ");
        boolean useLower = ask(scanner, "Include lowercase letters? (y/n): ");
        boolean useDigits = ask(scanner, "Include digits? (y/n): ");
        boolean useSpecial = ask(scanner, "Include special characters? (y/n): ");

        if (!useUpper && !useLower && !useDigits && !useSpecial) {
            System.out.println("At least one character type must be selected.");
            return;
        }

        int count = 1;
        System.out.print("How many passwords to generate? ");
        if (scanner.hasNextInt()) {
            count = scanner.nextInt();
            if (count < 1) count = 1;
        } else {
            scanner.next(); // Clear invalid input
        }

        for (int i = 1; i <= count; i++) {
            String password = generatePassword(length, useUpper, useLower, useDigits, useSpecial);
            System.out.println("Generated Password " + i + ": " + password);
        }
    }

    private static boolean ask(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.next();
        return input.equalsIgnoreCase("y");
    }

    public static String generatePassword(int length, boolean useUpper, boolean useLower, boolean useDigits, boolean useSpecial) {
        StringBuilder charPool = new StringBuilder();
        List<Character> passwordChars = new ArrayList<>();
        SecureRandom random = new SecureRandom();

        // Ensure at least one character from each selected set
        if (useUpper) {
            charPool.append(UPPER);
            passwordChars.add(UPPER.charAt(random.nextInt(UPPER.length())));
        }
        if (useLower) {
            charPool.append(LOWER);
            passwordChars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        }
        if (useDigits) {
            charPool.append(DIGITS);
            passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        if (useSpecial) {
            charPool.append(SPECIAL);
            passwordChars.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));
        }

        // Fill the rest of the password
        for (int i = passwordChars.size(); i < length; i++) {
            passwordChars.add(charPool.charAt(random.nextInt(charPool.length())));
        }

        // Shuffle to avoid predictable patterns
        Collections.shuffle(passwordChars, random);

        // Convert list to string
        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }
        return password.toString();
    }
}
