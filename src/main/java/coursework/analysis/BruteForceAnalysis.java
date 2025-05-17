package coursework.analysis;

import coursework.crypto.DES;

public class BruteForceAnalysis {
    private final DES des = new DES();

    public String bruteForce(String knownPlainText, String knownCipherText, String iv) {
        String[] keys = generateAllKeys();
        int tested = 0;

        for (String key : keys) {
            String encrypted = des.encrypt(knownPlainText, key, iv);
            if (encrypted.equals(knownCipherText)) {
                System.out.println("Найден ключ: " + key);
                return key;
            }
            tested++;
            if (tested % 100000 == 0) {
                System.out.println("Проверено ключей: " + tested);
            }
        }
        System.out.println("Ключ не найден");
        return null;
    }

    private String[] generateAllKeys() {
        String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int size = charset.length();

        int total = size + (size * size) + (size * size * size) + (size * size * size * size);
        String[] keys = new String[total];
        int index = 0;

        for (int i = 0; i < size; i++) {
            keys[index++] = "" + charset.charAt(i);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                keys[index++] = "" + charset.charAt(i) + charset.charAt(j);
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    keys[index++] = "" + charset.charAt(i) + charset.charAt(j) + charset.charAt(k);
                }
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        keys[index++] = "" + charset.charAt(i) + charset.charAt(j) + charset.charAt(k) + charset.charAt(l);
                    }
                }
            }
        }
        return keys;
    }
}
