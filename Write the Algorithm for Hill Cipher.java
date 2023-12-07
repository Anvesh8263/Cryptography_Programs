import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hill Cipher Implementation");
        System.out.println("Enter the key matrix size (n xn): ");
        int n = scanner.nextInt();
        int[][] keyMatrix = new int[n][n];
        System.out.println("Enter the key matrix elements:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                keyMatrix[i][j] = scanner.nextInt() % 26;
            }
        }
        int[][] keyInverse = getKeyInverse(keyMatrix, n);
        System.out.println("Key Matrix:");
        displayMatrix(keyMatrix);
        System.out.println("Key Inverse:");
        displayMatrix(keyInverse);
        System.out.println("Enter the plaintext (in uppercase):");
        scanner.nextLine();
        String input = scanner.nextLine();
        String ciphertext = encrypt(input, keyMatrix, n);
        System.out.println("Encrypted Text: " + ciphertext);
        String decryptedText = decrypt(ciphertext, keyInverse, n);
        System.out.println("Decrypted Text:" + decryptedText);
    }

    private static String encrypt(String plaintext, int[][] keyMatrix, int n) {
        StringBuilder ciphertext = new StringBuilder();
        while (plaintext.length() % n != 0) {
            plaintext += 'X';
        }
        for (int i = 0; i < plaintext.length(); i += n) {
            String block = plaintext.substring(i, i + n);
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += (keyMatrix[j][k] * (block.charAt(k) - 'A')) % 26;
                  sum = sum % 26;
                }
                ciphertext.append((char) ('A' + sum));
            }
        }
        return ciphertext.toString();
    }

    private static String decrypt(String ciphertext, int[][] keyInverse, int n) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += n) {
            String block = ciphertext.substring(i, i + n);
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += (keyInverse[j][k] * (block.charAt(k) - 'A' + 26)) % 26;
                  sum%=26;
                }
                plaintext.append((char) ('A' + sum));
            }
        }
        return plaintext.toString();
    }

    private static int[][] getKeyInverse(int[][] keyMatrix, int n) {
        int det = determinant(keyMatrix, n);
        int detInverse = modInverse(det, 26);
        int[][] adjugate = adjugate(keyMatrix, n);
        int[][] keyInverse = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
        keyInverse[i][j] = (adjugate[i][j] * detInverse) % 26;
                if (keyInverse[i][j] < 0) {
                    keyInverse[i][j] += 26;
                }
            }
        }
        return keyInverse;
    }

    private static int determinant(int[][] matrix, int n) {
        if (n == 1) {
            return matrix[0][0];
        }
        if (n == 2) {
            return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0] + 26) % 26;
        }
        int det = 0;
        for (int i = 0; i < n; i++) {
            int[][] submatrix = new int[n - 1][n - 1];
            for (int j = 1; j < n; j++) {
                for (int k = 0, l = 0; k < n; k++) {
                    if (k != i) {
                        submatrix[j - 1][l++] = matrix[j][k];
                    }
                }
            }
            int sign = (i % 2 == 0) ? 1 : -1;
            det = (det + sign * matrix[0][i] * determinant(submatrix, n - 1) + 26) % 26;
        }
        return det;
    }

    private static int[][] adjugate(int[][] matrix, int n) {
        int[][] adjugate = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int[][] submatrix = new int[n - 1][n - 1];
                for (int k = 0, l = 0; k < n; k++) {
                    if (k != i) {
                        for (int m = 0, n1 = 0; m < n; m++) {
                            if (m != j) {
                                submatrix[l][n1++] = matrix[k][m];
                            }
                        }
                        l++;
                    }
                }
                int sign = ((i + j) % 2 == 0) ? 1 : -1;
                adjugate[j][i] = (sign * determinant(submatrix, n - 1) + 26) % 26;
            }
        }
        return adjugate;
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

    private static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
