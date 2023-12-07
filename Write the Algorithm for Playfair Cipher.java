Objective – Write the Algorithm for Playfair Cipher.
Code – 
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("\nWelcome to the Playfair Cipher Program!");

        int choice = 0;
        do {
            System.out.println("\nSelect an option:");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

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
                    System.out.println("\nGoodbye!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please choose from 1-3.");
                    break;
            }
        } while (choice != 3);

        input.close();
    }

    public static void encryption() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nYou selected Encryption.");

        System.out.print("Enter the encryption key: ");
        String key = input.nextLine().toUpperCase(); // Convert to uppercase
        char[][] keyMatrix = createKeyMatrix(key);

        // Display the key matrix
        System.out.println("Key Matrix:");
        displayMatrix(keyMatrix);

        System.out.print("Enter the plaintext (letters only): ");
        String plainText = input.nextLine().toUpperCase().replaceAll("[^A-Z]", ""); // Remove non-uppercase letters

        String cipherText = playfairEncrypt(plainText, keyMatrix);
        System.out.println("The ciphertext is: " + cipherText);
    }

    public static void decryption() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nYou selected Decryption.");

        System.out.print("Enter the decryption key: ");
        String key = input.nextLine().toUpperCase(); // Convert to uppercase
        char[][] keyMatrix = createKeyMatrix(key);

        // Display the key matrix
        System.out.println("Key Matrix:");
        displayMatrix(keyMatrix);

        System.out.print("Enter the ciphertext (letters only): ");
        String cipherText = input.nextLine().toUpperCase().replaceAll("[^A-Z]", ""); // Remove non-uppercase letters

        String plainText = playfairDecrypt(cipherText, keyMatrix);
        System.out.println("The plaintext is: " + plainText);
    }

    public static void displayMatrix(char[][] matrix) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static char[][] createKeyMatrix(String key) {
        char[][] matrix = new char[5][5];
        String keyWithoutDuplicates = removeDuplicates(key + "ABCDEFGHIKLMNOPQRSTUVWXYZ");

        int row = 0, col = 0;
        for (int i = 0; i < keyWithoutDuplicates.length(); i++) {
            char ch = keyWithoutDuplicates.charAt(i);
            matrix[row][col] = ch;
            col++;
            if (col == 5) {
                row++;
                col = 0;
            }
        }

        return matrix;
    }

    public static String removeDuplicates(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (result.indexOf(String.valueOf(ch)) == -1) {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String playfairEncrypt(String plainText, char[][] keyMatrix) {
        StringBuilder cipherText = new StringBuilder();

        for (int i = 0; i < plainText.length(); i += 2) {
            char firstChar = plainText.charAt(i);
            char secondChar = (i + 1 < plainText.length()) ? plainText.charAt(i + 1) : 'X';

            int[] firstCharPos = findCharPosition(firstChar, keyMatrix);
            int[] secondCharPos = findCharPosition(secondChar, keyMatrix);

            int row1 = firstCharPos[0];
            int col1 = firstCharPos[1];
            int row2 = secondCharPos[0];
            int col2 = secondCharPos[1];

            if (row1 == row2) {
                col1 = (col1 + 1) % 5;
                col2 = (col2 + 1) % 5;
            } else if (col1 == col2) {
                row1 = (row1 + 1) % 5;
                row2 = (row2 + 1) % 5;
            } else {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }

            cipherText.append(keyMatrix[row1][col1]);
            cipherText.append(keyMatrix[row2][col2]);
        }

        return cipherText.toString();
    }

    public static String playfairDecrypt(String cipherText, char[][] keyMatrix) {
        StringBuilder plainText = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i += 2) {
            char firstChar = cipherText.charAt(i);
            char secondChar = cipherText.charAt(i + 1);

            int[] firstCharPos = findCharPosition(firstChar, keyMatrix);
            int[] secondCharPos = findCharPosition(secondChar, keyMatrix);

            int row1 = firstCharPos[0];
            int col1 = firstCharPos[1];
            int row2 = secondCharPos[0];
            int col2 = secondCharPos[1];
            if (row1 == row2) {
                col1 = (col1 - 1 + 5) % 5;
                col2 = (col2 - 1 + 5) % 5;
            } else if (col1 == col2) {
                row1 = (row1 - 1 + 5) % 5;
                row2 = (row2 - 1 + 5) % 5;
            } else {
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }
            plainText.append(keyMatrix[row1][col1]);
            plainText.append(keyMatrix[row2][col2]);
        }
        return plainText.toString();
    }
    public static int[] findCharPosition(char ch, char[][] keyMatrix) {
        int[] position = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyMatrix[i][j] == ch) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }
}
