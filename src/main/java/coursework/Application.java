package coursework;

import coursework.analysis.BruteForceAnalysis;
import coursework.crypto.DES;

public class Application {
//    public static void main(String[] args) {
//        DES des = new DES();
//
//        // Пример с ключом длиной 1 символ
//        String key = "A"; // Ключ длиной 1 символ
//        String plaintext = "Hello, World!";
//        String iv = "12345678"; // Пример вектора инициализации
//
//        String ciphertext = des.encrypt(plaintext, key, iv);
//        System.out.println("Encrypted: " + ciphertext);
//
//        String decryptedText = des.decrypt(ciphertext, key, iv);
//        System.out.println("Decrypted: " + decryptedText);
//
//        // Пример с ключом длиной 2 символа
//        key = "AB"; // Ключ длиной 2 символа
//        ciphertext = des.encrypt(plaintext, key, iv);
//        System.out.println("Encrypted: " + ciphertext);
//
//        decryptedText = des.decrypt(ciphertext, key, iv);
//        System.out.println("Decrypted: " + decryptedText);
//    }


    public static void main(String[] args) {
        DES des = new DES();
        BruteForceAnalysis bfa = new BruteForceAnalysis();
        String knownPlain = "hello world";
        String knownCipher = des.encrypt(knownPlain, "FJ9", "12345678");
        String key = bfa.bruteForce(knownPlain, knownCipher, "12345678");
        String decrypted = des.decrypt(knownCipher, key, "12345678");
        System.out.println(decrypted);
    }
}