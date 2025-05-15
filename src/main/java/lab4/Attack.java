package lab4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Attack {
    private static final String RUS_KEYBOARD = "ёйцукенгшщзхъфывапролджэячсмитьбю";
    private static final String ENG_KEYBOARD = "`qwertyuiop[]asdfghjkl;'zxcvbnm,.";
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    private List<String> dictionary;
    private int dictIndex = 0;
    private boolean dictionaryInitialized = false;
    private long dictAttempts = 0;

    private char[] currents;
    private int[] indexes;
    private int bruteForceLength = 1;
    private boolean bruteForceInitialized = false;
    private long bruteForceAttempts = 0;

    private String changeKeyboardLayout(String text) {
        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int rusIndex = RUS_KEYBOARD.indexOf(c);
            if (rusIndex >= 0) {
                newText.append(ENG_KEYBOARD.charAt(rusIndex));
            }
        }
        return newText.toString();
    }

    public String dictionaryAttack() throws IOException {
        if (!dictionaryInitialized) {
            dictionary = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/lab4/dictionary.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    dictionary.add(line.trim());
                }
            }
            dictIndex = 0;
            dictAttempts = 0;
            dictionaryInitialized = true;
        }
        if (dictIndex >= dictionary.size()) {
            dictionaryInitialized = false;
            return null;
        }
        String word = dictionary.get(dictIndex);
        String attempt = changeKeyboardLayout(word);
        dictAttempts++;
        dictIndex++;

        System.out.println("Попытка " + dictAttempts + ": " + word + " -> " + attempt);

        return attempt;
    }

    public String bruteForceAttack(int maxLength) {
        if (!bruteForceInitialized) {
            currents = new char[maxLength];
            indexes = new int[maxLength];
            bruteForceLength = 1;
            Arrays.fill(indexes, 0);
            bruteForceAttempts = 0;
            bruteForceInitialized = true;
        }
        if (bruteForceLength > maxLength) {
            bruteForceInitialized = false;
            return null;
        }
        for (int i = 0; i < bruteForceLength; i++) {
            currents[i] = CHARACTERS[indexes[i]];
        }
        String currentPass = new String(currents, 0, bruteForceLength);
        bruteForceAttempts++;
        System.out.println("Попытка " + bruteForceAttempts + ": " + currentPass);

        int pos = bruteForceLength - 1;
        while (pos >= 0 && ++indexes[pos] == CHARACTERS.length) {
            indexes[pos] = 0;
            pos--;
        }
        if (pos < 0) {
            bruteForceLength++;
            if (bruteForceLength <= maxLength) {
                Arrays.fill(indexes, 0, bruteForceLength, 0);
            }
        }
        return currentPass;
    }
}