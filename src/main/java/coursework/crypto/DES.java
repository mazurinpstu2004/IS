package coursework.crypto;

import static coursework.crypto.Constants.*;

public class DES {

    private static final int[][][] S = {S1, S2, S3, S4, S5, S6, S7, S8};

    public String encrypt(String plaintext, String key, String iv) {
        String binaryIV = textToBinary(iv);
        String binaryText = textToBinary(padToBlockSize(plaintext));

        StringBuilder result = new StringBuilder();
        String prevBlock = binaryIV;

        for (int i = 0; i < binaryText.length(); i += 64) {
            String block = binaryText.substring(i, i + 64);
            String xored = xor(block, prevBlock);
            String encrypted = encryptBlock(xored, key);
            result.append(encrypted);
            prevBlock = encrypted;
        }
        return result.toString();
    }

    public String decrypt(String ciphertext, String key, String iv) {
        StringBuilder result = new StringBuilder();
        String prevBlock = textToBinary(iv);

        for (int i = 0; i < ciphertext.length(); i += 64) {
            String block = ciphertext.substring(i, i + 64);
            String decrypted = decryptBlock(block, key);
            String xored = xor(decrypted, prevBlock);
            result.append(xored);
            prevBlock = block;
        }
        return binaryToText(result.toString()).trim();
    }

    public String encryptBlock(String input, String key) {
        String permuted = doInitialPermutation(input);
        String L0 = permuted.substring(0, 32);
        String R0 = permuted.substring(32, 64);

        String[] keys = generateKeys(key);

        for (int i = 0; i < keys.length; i++) {
            String expanded = expand(R0).substring(0, 32);
            String xored = xor(expanded, keys[i]);
            String sBoxed = applySBoxes(xored + "000000000000000000000000000000000000000000000000");
            String permutedP = permuteP(sBoxed);
            String newR = xor(L0, permutedP);

            L0 = R0;
            R0 = newR;
        }
        return doFinalPermutation(R0 + L0);
    }

    public String decryptBlock(String input, String key) {
        String permuted = doInitialPermutation(input);
        String L0 = permuted.substring(0, 32);
        String R0 = permuted.substring(32, 64);

        String[] keys = generateKeys(key);

        for (int i = keys.length-1; i >= 0; i--) {
            String expanded = expand(R0).substring(0, 32);
            String xored = xor(expanded, keys[i]);
            String sBoxed = applySBoxes(xored + "000000000000000000000000000000000000000000000000");
            String permutedP = permuteP(sBoxed);
            String newR = xor(L0, permutedP);

            L0 = R0;
            R0 = newR;
        }
        return doFinalPermutation(R0 + L0);
    }

    public String textToBinary(String input) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            String binaryChar = String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
            binary.append(binaryChar);
        }
        return binary.toString();
    }

    private String binaryToText(String binary) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            int charCode = Integer.parseInt(binary.substring(i, i + 8), 2);
            result.append((char) charCode);
        }
        return result.toString();
    }

    private String doInitialPermutation(String bin) {
        char[] binChars = bin.toCharArray();
        char[] result = new char[binChars.length];
        for (int i = 0; i < binChars.length; i++) {
            result[i] = binChars[IP[i] - 1];
        }
        return new String(result);
    }

    private String expand(String input) {
        char[] output = new char[48];
        for (int i = 0; i < output.length; i++) {
            output[i] = input.charAt(E[i] - 1);
        }
        return new String(output);
    }

    private String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                result.append('0');
            } else {
                result.append('1');
            }
        }
        return result.toString();
    }

    private String applySBox(String input, int boxNum) {
        int row = Integer.parseInt("" + input.charAt(0) + input.charAt(5), 2);
        int col = Integer.parseInt(input.substring(1, 5), 2);
        int val = S[boxNum][row][col];

        return String.format("%4s", Integer.toBinaryString(val)).replace(' ', '0');
    }

    private String applySBoxes(String input) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < S.length; i++) {
            String sixBits = input.substring(i * 6, (i + 1) * 6);
            output.append(applySBox(sixBits, i));
        }
        return output.toString();
    }

    private String permuteP(String input) {
        char[] output = new char[32];
        for (int i = 0; i < output.length; i++) {
            output[i] = input.charAt(P[i] - 1);
        }
        return new String(output);
    }

    private String[] generateKeys(String key) {
        String binaryKey = textToBinary(key); // Преобразуем ключ в бинарный формат

        // Убедимся, что длина ключа от 1 до 4 символов, преобразуем в 56 бит
        if (binaryKey.length() < 56) {
            binaryKey = String.format("%-56s", binaryKey).replace(' ', '0');  // Дополняем нулями до 56 бит
        }

        // Генерируем 6 раундовых ключей
        String[] roundKeys = new String[6];
        for (int i = 0; i < roundKeys.length; i++) {
            int shift = i + 1; // Сдвиг в зависимости от раунда
            String shifted = binaryKey.substring(shift) + binaryKey.substring(0, shift);
            roundKeys[i] = shifted.substring(0, 32); // Используем первые 32 бита
        }
        return roundKeys;
    }


    private String doFinalPermutation(String input) {
        char[] output = new char[64];
        for (int i = 0; i < output.length; i++) {
            output[i] = input.charAt(FP[i] - 1);
        }
        return new String(output);
    }

    private String repeatChar(char c, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    private String padToBlockSize(String input) {
        int padLength = 8 - (input.length() % 8);
        if (padLength == 8) return input;
        return input + repeatChar(' ', padLength);
    }
}
