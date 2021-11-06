// Security assignment LSFR
// Tony Mansour 20180076


import java.util.*;
import java.io.*;


public class LFSR {

     static int MAX_CASES = 511;
     static int REGISTERS_LENGTH = 9;

    public static void main(String[] args) {

        int[] registers = {1, 0, 0, 1, 1, 0, 0, 0, 1};
        int[] gates =     {0, 0, 0, 1, 1, 0, 0, 1, 1};
        int NUMBER_OF_DISCARD_DIGITS = 2;

        int[] key = generateKey(registers, gates);
        System.out.println("Key without discarding first " + NUMBER_OF_DISCARD_DIGITS + " numbers \n" + Arrays.toString(key)+ "\n");

        int[] newKey = Arrays.copyOfRange(key, NUMBER_OF_DISCARD_DIGITS, key.length);
        System.out.println("Key with discarding first " + NUMBER_OF_DISCARD_DIGITS + " numbers \n" + Arrays.toString(newKey) + "\n");


        int[] plainText = {0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0};
        System.out.println("Original text (plain text) : \n" + Arrays.toString(plainText) + "\n");

        // Encryption
        int[] ciphertext = encrypt(plainText, newKey);
        System.out.println("Encrypted (cipher text) : \n" + Arrays.toString(ciphertext) + "\n");

        // Decryption
        int[] decrypted = decrypt(ciphertext, newKey);
        System.out.println("Decrypted (plain text) : \n" + Arrays.toString(decrypted) + "\n");


    }

    // function to generate the key from given registers & gates : that decide which register is going to be a feedback
    public static int[] generateKey(int[] registers, int[] gates){

        int[] key = new int[MAX_CASES];
        key[0] = registers[REGISTERS_LENGTH - 1];

        for (int i = 1; i < MAX_CASES; i++) {

            int first = getValueOfXOR(registers, gates);
            shift(registers);

            registers[0] = first;
            key[i] = registers[REGISTERS_LENGTH - 1];
        }

        return key;
    }

    // Function to calculate the result of multiple numbers with xor
    public static int getValueOfXOR(int[] regs, int[] gates){

        int first = 0;

        for (int j = 0; j < REGISTERS_LENGTH ; j++) {

            if(gates[j] == 1){
                first = first ^ regs[j];
            }
        }

        return first;
    }

    // Function to shift register by 1
    public static void shift(int[] arr){

        for(int i = 0; i < 1; i++){
            int j, last;

            last = arr[arr.length-1];

            for(j = arr.length-1; j > 0; j--){

                arr[j] = arr[j-1];
            }

            arr[0] = last;
        }

    }

    // Function to encrypt given text with given key
    public static int[] encrypt(int[] plainText, int[] newKey){

        int[] ciphertext = new int[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            ciphertext[i] = plainText[i] ^ newKey[i] % 2;
        }
        return ciphertext;
    }

    // Function to decrypt given cipher with given key
    public static int[] decrypt(int[] ciphertext, int[] newKey){
        int[] decrypted = new int[ciphertext.length];

        for (int i = 0; i < ciphertext.length; i++) {
            decrypted[i] = ciphertext[i] ^ newKey[i] % 2;
        }

        return decrypted;
    }
}

