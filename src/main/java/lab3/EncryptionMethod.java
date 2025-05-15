package lab3;

public class EncryptionMethod {

    private final String ALPHABET = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

    public String cesarMethodEncrypt(String sourceFile, String encryptFile, int N) {
        TextFile textFile = new TextFile();
        StringBuilder encryptedText = new StringBuilder();
        String sourceText = textFile.loadFromFile(sourceFile);
        for (int i = 0; i < sourceText.length(); i++) {
            char sourceChar = sourceText.charAt(i);
            if (ALPHABET.indexOf(sourceChar) != -1) {
                int sourcePos = ALPHABET.indexOf(sourceChar);
                int encryptPos = (sourcePos + N) % ALPHABET.length();
                if (encryptPos < 0) {
                    encryptPos += ALPHABET.length();
                }
                encryptedText.append(ALPHABET.charAt(encryptPos));
            } else {
                encryptedText.append(sourceChar);
            }
        }
        textFile.saveToFile(encryptFile, encryptedText.toString());
        return "Файл зашифрован";
    }

    public String cesarMethodDecrypt(String encryptFile, String decryptFile, int N) {
        cesarMethodEncrypt(encryptFile, decryptFile, -N);
        return "Файл расшифрован";
    }

    public String vigenereMethodEncrypt(String sourceFile, String encryptFile, String keyWord) {
        keyWord = keyWord.toUpperCase();
        TextFile textFile = new TextFile();
        StringBuilder encryptedText = new StringBuilder();
        StringBuilder keyWordText = new StringBuilder();
        String sourceText = textFile.loadFromFile(sourceFile);

        int keyIndex = 0;
        for (int i = 0; i < sourceText.length(); i++) {
            char sourceChar = sourceText.charAt(i);
            if (ALPHABET.indexOf(sourceChar) != -1) {
                keyWordText.append(keyWord.charAt(keyIndex % keyWord.length()));
                keyIndex++;
            } else {
                keyWordText.append(sourceChar);
            }
        }

        for (int i = 0; i < sourceText.length(); i++) {
            char sourceChar = sourceText.charAt(i);
            char keyChar = keyWordText.charAt(i);
            if (ALPHABET.indexOf(sourceChar) != -1) {
                int sourcePos = ALPHABET.indexOf(sourceChar);
                int keyPos = ALPHABET.indexOf(keyChar);
                int encryptPos = (sourcePos + keyPos) % ALPHABET.length();
                encryptedText.append(ALPHABET.charAt(encryptPos));
            } else {
                encryptedText.append(sourceChar);
            }
        }

        textFile.saveToFile(encryptFile, encryptedText.toString());
        return "Файл зашифрован";
    }

    public String vigenereMethodDecrypt(String encryptFile, String decryptFile, String keyWord) {
        keyWord = keyWord.toUpperCase();
        TextFile textFile = new TextFile();
        StringBuilder decryptedText = new StringBuilder();
        StringBuilder keyWordText = new StringBuilder();
        String encryptText = textFile.loadFromFile(encryptFile);

        int keyIndex = 0;
        for (int i = 0; i < encryptText.length(); i++) {
            char sourceChar = encryptText.charAt(i);
            if (ALPHABET.indexOf(sourceChar) != -1) {
                keyWordText.append(keyWord.charAt(keyIndex % keyWord.length()));
                keyIndex++;
            } else {
                keyWordText.append(sourceChar);
            }
        }

        for (int i = 0; i < encryptText.length(); i++) {
            char sourceChar = encryptText.charAt(i);
            char keyChar = keyWordText.charAt(i);
            if (ALPHABET.indexOf(sourceChar) != -1) {
                int encryptPos = ALPHABET.indexOf(sourceChar);
                int keyPos = ALPHABET.indexOf(keyChar);
                int decryptPos = (encryptPos - keyPos + ALPHABET.length()) % ALPHABET.length();
                decryptedText.append(ALPHABET.charAt(decryptPos));
            } else {
                decryptedText.append(sourceChar);
            }
        }

        textFile.saveToFile(decryptFile, decryptedText.toString());
        return "Файл расшифрован";
    }

    public char[][] getVigenereSquare() {
        char[][] vigenereSquare = new char[ALPHABET.length()+1][ALPHABET.length()];
        for (int i = 0; i < ALPHABET.length(); i++) {
            for (int j = 0; j < ALPHABET.length(); j++) {
                vigenereSquare[i][j] = ALPHABET.charAt((j + i) % ALPHABET.length());
            }
        }
        return vigenereSquare;
    }
}
