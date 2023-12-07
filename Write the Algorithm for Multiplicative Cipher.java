import java.util.*;
class Main {

  static void bruteforce()
  {
    Scanner sc = new Scanner(System.in);
    System.out.println("You selected Brute force. ");
    System.out.println("Enter the Cipher text. ");
    String ciphertext = sc.next();

    if(!ciphertext.matches("[A-Z]+"))
    {
       System.out.println("Cipher text should contains only Upper letter");
      return;
    }
            System.out.print("Enter the expected plaintext: ");
        String expectedPlainText = sc.next();

        for (int key = 1; key < 26; key++) {
            int inverseKey = findinverse(key);
            if (inverseKey != -1) {
                StringBuilder plaintext = new StringBuilder();
                for (int i = 0; i < ciphertext.length(); i++) {
                    char ch = ciphertext.charAt(i);
                    if (Character.isLetter(ch)) {
                        char base = Character.isUpperCase(ch) ? 'A' : 'a';
                        ch = (char) (base + (ch - base + 26) * inverseKey % 26);
                    }
                    plaintext.append(ch);
                }
                if (plaintext.toString().equalsIgnoreCase(expectedPlainText)) {
                    System.out.println("Key: " + key + " ==> Decrypted Text: " + plaintext);
                }
            }
        }
  }
  static void decryption()
  {
     Scanner sc = new Scanner(System.in);
     System.out.println("You selected Decryption. ");
     System.out.println("Enter the Cipher text");
     String ciphertext = sc.next();
     if(!ciphertext.matches("[A-Z]+"))
     {
        System.out.println("Cipher text should only contains Upper letters");
       return;
     }
    int key = getValidKey(sc);
    int inverseKey = findinverse(key);
    if (inverseKey == -1) {
            System.out.println("Error: The key has no multiplicative inverse in the alphabet size.");
            return;
        }
     String plaintext = "";

    for(int i=0; i<ciphertext.length(); i++)
      {
          char ch = ciphertext.charAt(i);
          ch = (char) ('A' + (ch-'A'+26)*inverseKey%26);
          plaintext+=ch;
      }
     System.out.println("The plaintext is -"+plaintext.toLowerCase());
  }
  static void encryption()
  {
     Scanner sc = new Scanner(System.in);
     System.out.println("You selected Encryption -");
     System.out.println("Enter the plain text ");
     String plaintext = sc.next();

    if(!plaintext.matches("[a-z]+"))
    {
       System.out.println("Plain text should contains only lower Case ");
      return;
    }
    int key = getValidKey(sc);
    plaintext = plaintext.toUpperCase();
            StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            char ch = plaintext.charAt(i);
                ch = (char) ('A' + (ch - 'A') * key % 26);
            cipherText.append(ch);
        }
        System.out.println("The ciphertext is: " + cipherText.toString().toUpperCase());
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
  public static int findinverse(int key)
  {
     for(int i=1; i<26; i++)
       {
          if((key*i)%26==1)
          {
            return i;
          }
       }
    return -1;
  }
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Welcome to the Multiplicative Cipher");
     int choice = 0;
    do
      {
        System.out.println("Select the option - ");
        System.out.println("1. Encytption");
        System.out.println("2. Decryption");
        System.out.println("3. Brute Force");
        System.out.println("4. Exit");
        try
          {
              choice  = sc.nextInt();

              switch(choice)
                {
                  case 1: 
                    encryption();
                    break;
                  case 2:
                    decryption();
                    break;
                  case 3:
                    bruteforce();
                    break;
                  case 4:
                    System.out.println("Good Bye!!!");
                    break;
                  default :
                    System.out.println("Please enter the valid choice.");
                    break;
                }
          }catch(InputMismatchException e)
          {
             System.out.println("Please Enter the valid choice");
          }
      }while(choice!=4);
       sc.close();
  }
}
