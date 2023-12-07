import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
  public static void bruteForce() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the cipher text");
    String ciphertext = sc.nextLine();

    if (!ciphertext.matches("^[A-Z ]+$")) {
        System.out.println("Error: The ciphertext should only contain upper letters.");
        return;
    }
        System.out.print("Enter the expected plaintext: ");
    String expectedPlainText = sc.next();
    for (int key = 1; key < 26; key++) {
         String ans = "";
        int newkey = key;
    for (int i = 0; i < ciphertext.length(); i++) {
          char ch = ciphertext.charAt(i);
         if(ch==' ') {
            ans+=' ';
            continue;
        }
        ch = (char)(ch-'A');
        ch = (char) ('A'+(ch-newkey)%26);
        if((ch-65)<0)
        {
          ch+=26;
        }
        ans+=ch;
        newkey = ch-65;
    }
      if (ans.equalsIgnoreCase(expectedPlainText)) {
          System.out.println("Key: " + key + " ==> Decrypted Text: " + ans);
      }
    }
}
public static void decryption() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the cipher text");
    String ciphertext = sc.nextLine();

    if (!ciphertext.matches("[A-Z ]+")) {
        System.out.println("Error: The ciphertext should only contain upper letters.");
        return;
    }

    int key = 0;
    boolean isValidInput = false;

    while (!isValidInput) {
        try {
            System.out.print("Enter an key: ");
            key = sc.nextInt();
            isValidInput = true;
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            sc.nextLine();
        }
    }
    String ans = "";
    for (int i = 0; i < ciphertext.length(); i++) {
        char ch = ciphertext.charAt(i);
         if(ch==' ') {
            ans+=' ';
            continue;
        }
        ch = (char)(ch-'A');
        ch = (char) ('A'+(ch-key)%26);
        if((ch-65)<0)
        {
          ch+=26;
        }
        ans+=ch;
        key = ch-65;
    }
    System.out.println(ans.toLowerCase());
}
  public static void encryption()
  {
     Scanner sc = new Scanner(System.in);
     System.out.println("Enter the plain text");
     String plaintext = sc.nextLine();

      if (!plaintext.matches("[a-z ]+")) {
        	System.out.println("Error: The plaintext should only contain lower letters.");
        	return;
    	}
       int key = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.print("Enter an key: ");
                key = sc.nextInt();
                isValidInput = true;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine();
                 
            }
        }
        String ans = "";
        for (int i = 0; i <plaintext.length(); i++) {
            int ch = plaintext.codePointAt(i);
             if(ch==' ')
             {
               ans+=' ';
               continue;
             }
            int newkey = plaintext.codePointAt(i);
            ch=ch-97;
            ch=((ch+key)%26);
            char newChar = (char) (ch+97 );
            ans+=newChar;
            key=newkey-97;
        }
        System.out.println(ans.toUpperCase());
     
      }	
	public static void main(String[] args) {
    	Scanner input = new Scanner(System.in);
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
