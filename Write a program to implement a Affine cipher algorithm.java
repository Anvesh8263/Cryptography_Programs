import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("\nWelcome to the Affine Cipher Program!");

        int choice = 0;
        do {
            System.out.println("\nSelect an option:");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Brute Force Decryption");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = input.nextInt();
                input.nextLine();  
                switch (choice) {
                    case 1:
                        encryption();
                        break;
                    case 2:
                        decryption();
                        break;
                    case 3:
                        bruteForceDecryption();
                        break;
                    case 4:
                        System.out.println("\nGoodbye!");
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please choose from 1-4.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid choice (1-4).");
                input.nextLine(); // Consume the invalid input
            }

        } while (choice != 4);
        input.close();
    }

    public static void encryption() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nYou selected Encryption.");

        String plainText;
        boolean validInput;

        do {
            System.out.print("Enter the plaintext (lowercase alphabetic characters only): ");
            plainText = input.nextLine();
            validInput = plainText.matches("[a-z ]+");  
            if (!validInput) {
                System.out.println("Error: The plaintext should only contain lowercase alphabetic characters.");
            }
        } while (!validInput);

        int keyA, keyB;
        do {
            System.out.print("Enter key A (an integer coprime to 26): ");
            while (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                input.next();
            }
            keyA = input.nextInt();
            input.nextLine();
        } while (!isCoprime(keyA, 26));

        System.out.print("Enter key B (an integer): ");
        while (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            input.next();
        }
        keyB = input.nextInt();
        input.nextLine();

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char ch = plainText.charAt(i);
            if (Character.isLetter(ch)) {
                char base = 'a';
                ch = (char) (((ch - base) * keyA + keyB) % 26 + base);
            }
            cipherText.append(ch);
        }
        System.out.println("The ciphertext is: " + cipherText.toString().toUpperCase());

        // Close the scanner when done
   return;
    }

    public static void decryption() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nYou selected Decryption.");
        String cipherText;

        boolean validInput;

        do {
            System.out.print("Enter the ciphertext (uppercase alphabetic characters only): ");
            cipherText = input.nextLine();
            validInput = cipherText.matches("[A-Z ]+"); // Check if input is valid
            if (!validInput) {
                System.out.println("Error: The ciphertext should only contain uppercase alphabetic characters.");
            }
        } while (!validInput);

        int keyA, keyB;
        do {
            System.out.print("Enter key A (an integer coprime to 26): ");
            while (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                input.next();
            }
            keyA = input.nextInt();
            input.nextLine();
        } while (!isCoprime(keyA, 26));

        System.out.print("Enter key B (an integer): ");
        while (!input.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            input.next();
        }
        keyB = input.nextInt();
        input.nextLine();

        int inverseKeyA = findInverse(keyA, 26);

        if (inverseKeyA == -1) {
            System.out.println("Error: Key A has no multiplicative inverse in the alphabet size.");
            return;
        }

        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            char ch = cipherText.charAt(i);
            if (Character.isLetter(ch)) {
                char base = 'A';
                ch = (char) (((ch - base - keyB + 26) * inverseKeyA) % 26 + base);
            }
            plainText.append(ch);
        }
        System.out.println("The plaintext is: " + plainText);

        // Close the scanner when done
        return;
    }

    public static void bruteForceDecryption() {
    Scanner input = new Scanner(System.in);
    System.out.println("\nYou selected Brute Force Decryption.");

    String cipherText;
    boolean validInput;

    do {
        System.out.print("Enter the ciphertext (uppercase alphabetic characters only): ");
        cipherText = input.nextLine();
        validInput = cipherText.matches("[A-Z ]+"); // Check if input is valid
        if (!validInput) {
            System.out.println("Error: The ciphertext should only contain uppercase alphabetic characters.");
        }
    } while (!validInput);

    System.out.print("Enter the known plaintext: ");
    String knownPlainText = input.nextLine();

    System.out.println("Attempting brute force decryption...");

    for (int keyA = 1; keyA < 26; keyA++) {
        for (int keyB = 0; keyB < 26; keyB++) {
            int inverseKeyA = findInverse(keyA, 26);
            StringBuilder plainText = new StringBuilder();

            for (int i = 0; i < cipherText.length(); i++) {
                char ch = cipherText.charAt(i);
                if (Character.isLetter(ch)) {
                    char base = 'A';
                    ch = (char) (((ch - base - keyB + 26) * inverseKeyA) % 26 + base);
                }
                plainText.append(ch);
            }
            if (plainText.toString().equals(knownPlainText)) {
                System.out.println("Key A: " + keyA + ", Key B: " + keyB + " - " + knownPlainText);
            }
        }
    }
      return;
}
    public static boolean isCoprime(int a, int b) {
        // Check if two numbers are coprime (have gcd = 1)
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a == 1;
    }
        public static int findInverse(int a, int m) {
                for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1; // No modular inverse exists
    }
}
