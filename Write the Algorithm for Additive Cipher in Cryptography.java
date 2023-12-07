import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
      Scanner input = new Scanner(System.in);

      System.out.println("\nWelcome to the Additive Cipher Program!");

      int choice = 0;
      do {
          System.out.println("\nSelect an option:");
          System.out.println("1. Encryption");
          System.out.println("2. Decryption");
          System.out.println("3. Brute Force");
          System.out.println("4. Exit");
          System.out.print("Enter your choice: ");

          try {
              choice = input.nextInt();
              input.nextLine(); // Consume the newline character
              switch (choice) {
                  case 1:
                      encryption();
                      break;
                  case 2:
                      decryption();
                      break;
                  case 3:
                      bruteForce();
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
      System.out.print("Enter the plaintext: ");
      String plainText = input.nextLine();

      if (!plainText.matches("[a-z ]+")) {
          System.out.println("Error: The plaintext should only contain lower letters.");
          return;
      }
      plainText = plainText.toUpperCase(); // Convert input to uppercase
      int key = getValidKey(input);
      String cipherText = "";
      for (int i = 0; i < plainText.length(); i++) {
          char ch = plainText.charAt(i);
          ch = (char) ((ch - 'A' + key) % 26 + 'A');
          cipherText += ch;
      }
      System.out.println("The ciphertext is: " + cipherText
)
}
  public static void decryption() {
      Scanner input = new Scanner(System.in);
      System.out.println("\nYou selected Decryption.");
      System.out.print("Enter the ciphertext: ");
      String cipherText = input.nextLine();

      // Check if the input contains non-uppercase letters
      if (!cipherText.matches("[A-Z ]+")) {
          System.out.println("Error: The ciphertext should only contain uppercase letters.");
          return;
      }
      cipherText = cipherText.toUpperCase(); // Convert input to uppercase
      int key = getValidKey(input);
      String plainText = "";
      for (int i = 0; i < cipherText.length(); i++) {
          char ch = cipherText.charAt(i);
          ch = (char) ((ch - 'A' - key + 26) % 26 + 'A');
          plainText += ch;
      }
      System.out.println("The plaintext is: " + plainText);
  }
  public static void bruteForce() {
      Scanner input = new Scanner(System.in);
      System.out.println("\nYou selected Brute Force.");
      System.out.print("Enter the ciphertext: ");
      String cipherText = input.nextLine();
      if (!cipherText.matches("[A-Z ]+")) {
          System.out.println("Error: The ciphertext should only contain uppercase letters.");
          return;
      }
      cipherText = cipherText.toUpperCase(); // Convert input to uppercase

      for (int key = 0; key < 26; key++) {
          String plainText = "";
          for (int i = 0; i < cipherText.length(); i++) {
              char ch = cipherText.charAt(i);
              ch = (char) ((ch - 'A' - key + 26) % 26 + 'A');
              plainText += ch;
          }
          System.out.println("Key: " + key + " ==> Plaintext: " + plainText);
      }
  }
  public static int getValidKey(Scanner input) {
      int key;
      do {
          System.out.print("Enter the key (an integer): ");
          while (!input.hasNextInt()) {
              System.out.println("Invalid input. Please enter an integer.");
              input.next(); // Consume the non-integer input
          }
          key = input.nextInt();
          input.nextLine(); // Consume the newline character
      } while (key < 0); // You can adjust the validation condition as needed
      return key;
  }
}
