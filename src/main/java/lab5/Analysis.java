package lab5;

import lab3.EncryptionMethod;
import lab3.TextFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analysis {
    private static final String ALPHABET = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final double[] LETTER_PROBABILITIES = {
            0.062, 0.014, 0.038, 0.013, 0.025, 0.072, 0.007, 0.016,
            0.062, 0.010, 0.028, 0.035, 0.026, 0.053, 0.090, 0.023,
            0.040, 0.045, 0.053, 0.021, 0.002, 0.009, 0.004, 0.012,
            0.006, 0.003, 0.014, 0.016, 0.014, 0.003, 0.006, 0.018
    };

    public void analyseCaesar() {
        TextFile tf = new TextFile();
        EncryptionMethod decryption = new EncryptionMethod();
        String encPath = "C:\\Users\\mazur\\Documents\\for_lab5\\encC_text.txt";
        String decPath = "C:\\Users\\mazur\\Documents\\for_lab5\\decC_text.txt";
        String encText = tf.loadFromFile(encPath);
        System.out.println("\nЗашифрованный текст\n-------------------------------------------");
        System.out.println(encText);
        int key = findCaesarKey(encText);
        System.out.println("Найденный ключ шифра: " + key + "\n");
        decryption.cesarMethodDecrypt(encPath, decPath, key);
        String decText = tf.loadFromFile(decPath);
        System.out.println("Расшифрованный текст\n------------------------------------------");
        System.out.println(decText);
    }

    public void analyseVigenere() {
        TextFile tf = new TextFile();
        EncryptionMethod decryption = new EncryptionMethod();
        String encPath = "C:\\Users\\mazur\\Documents\\for_lab5\\encV_text.txt";
        String decPath = "C:\\Users\\mazur\\Documents\\for_lab5\\decV_text.txt";
        String encText = tf.loadFromFile(encPath);
        System.out.println("\nЗашифрованный текст\n-------------------------------------------");
        System.out.println(encText);
        encText = encText.replaceAll("[^" + ALPHABET + "]", ""); // Удаляем все не-алфавитные символы
        int keyLength = findVigenereKeyLength(encText);
        System.out.println("Предполагаемая длина ключа: " + keyLength);
        String key = findVigenereKey(encText, keyLength);
        System.out.println("Найденный ключ: " + key + "\n");
        decryption.vigenereMethodDecrypt(encPath, decPath, key);
        String decText = tf.loadFromFile(decPath);
        System.out.println("Расшифрованный текст\n------------------------------------------");
        System.out.println(decText);
    }

    private static int findCaesarKey(String encryptedText) {
        double[] encryptedLetterProbability = calculateLetterProbabilityInText(encryptedText);
        int key = 0;
        double bestDelta = 1.0;
        for (int N = 0; N < ALPHABET.length(); N++) {
            double delta = 0.0;

            for (int i = 0; i < ALPHABET.length(); i++) {
                int encryptedPosRight = (i + N) % ALPHABET.length();
                double differenceRight = encryptedLetterProbability[encryptedPosRight] - LETTER_PROBABILITIES[i];
                delta += Math.pow(differenceRight, 2);
            }
            if (delta < bestDelta) {
                bestDelta = delta;
                key = N;
            }
        }
        return key;
    }

    private static String findVigenereKey(String encryptedText, int keyLength) {
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < keyLength; i++) {
            StringBuilder group = new StringBuilder();

            for (int j = i; j < encryptedText.length(); j += keyLength) {
                char c = encryptedText.charAt(j);
                if (ALPHABET.indexOf(c) != -1) {
                    group.append(c);
                }
            }
            int shift = findCaesarKey(group.toString());
            key.append(ALPHABET.charAt(shift));
        }
        return key.toString();
    }

    private static int findVigenereKeyLength(String encryptedText) {
        int maxKeyLength = 5;
        int bestLength = 1;
        double bestIC = 0.0;

        for (int l = 1; l <= maxKeyLength; l++) {
            double avgIC = 0.0;

            for (int group = 0; group < l; group++) {
                StringBuilder currentGroup = new StringBuilder();
                for (int i = group; i < encryptedText.length(); i += l) {
                    char c = encryptedText.charAt(i);
                    if (ALPHABET.indexOf(c) != -1) {
                        currentGroup.append(c);
                    }
                }
                if (currentGroup.length() > 1) {
                    avgIC += calculateIndexOfCoincidence(currentGroup.toString());
                }
            }
            avgIC /= l;

            if (avgIC > bestIC) {
                bestIC = avgIC;
                bestLength = l;
            }
        }
        return bestLength;
    }

    private static double[] calculateLetterProbabilityInText(String text) {
        double[] probability = new double[ALPHABET.length()];
        int letterCount = 0;

        for (char c : text.toCharArray()) {
            int i = ALPHABET.indexOf(c);
            if (i != -1) {
                probability[i]++;
                letterCount++;
            }
        }
        if (letterCount > 0) {
            for (int i = 0; i < probability.length; i++) {
                probability[i] /= letterCount;
            }
        }
        return probability;
    }

    private static double calculateIndexOfCoincidence(String text) {
        int[] indexOfCoincidence = new int[ALPHABET.length()];

        for (char c: text.toCharArray()) {
            int i = ALPHABET.indexOf(c);
            if (i != -1) {
                indexOfCoincidence[i]++;
            }
        }
        double IC = 0.0;
        int n = text.length();
        for (int f : indexOfCoincidence) {
            IC += f * (f - 1);
        }
        IC = IC / (n * (n - 1));
        return IC;
    }
}
