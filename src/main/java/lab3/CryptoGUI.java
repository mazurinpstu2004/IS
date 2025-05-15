package lab3;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CryptoGUI {

    public void startWindow() {
        JFrame start = new JFrame("Encrypt app");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setSize(700, 450);
        start.setLocationRelativeTo(null);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.setBorder(BorderFactory.createEmptyBorder(130, 0, 0, 0));
        startPanel.setBackground(Color.DARK_GRAY);

        JLabel startLabel = new JLabel("Choose encrypt mode");
        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startLabel.setFont(new Font("Arial", Font.BOLD, 16));
        startLabel.setForeground(Color.WHITE);

        JButton cesarButton = new JButton("Cesar method");
        cesarButton.setMaximumSize(new Dimension(200, 30));
        cesarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cesarButton.setFont(new Font("Arial", Font.BOLD, 16));
        cesarButton.setBackground(Color.WHITE);
        cesarButton.setForeground(Color.BLACK);

        cesarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                start.dispose();
                cesarWindow();
            }
        });

        JButton vigenereButton = new JButton("Vigenere method");
        vigenereButton.setMaximumSize(new Dimension(200, 30));
        vigenereButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        vigenereButton.setFont(new Font("Arial", Font.BOLD, 16));
        vigenereButton.setBackground(Color.WHITE);
        vigenereButton.setForeground(Color.BLACK);

        vigenereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                start.dispose();
                vigenereWindow();
            }
        });

        startPanel.add(startLabel);
        startPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        startPanel.add(cesarButton);
        startPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        startPanel.add(vigenereButton);
        start.add(startPanel);
        start.setVisible(true);
    }

    public void cesarWindow() {
        JFrame cesar = new JFrame("Cesar method");
        cesar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cesar.setSize(700, 450);
        cesar.setLocationRelativeTo(null);

        JPanel leftCesarPanel = new JPanel();
        leftCesarPanel.setLayout(new BoxLayout(leftCesarPanel, BoxLayout.Y_AXIS));
        leftCesarPanel.setBorder(BorderFactory.createEmptyBorder(110, 150, 0, 25));
        leftCesarPanel.setBackground(Color.DARK_GRAY);

        JLabel offsetLabel = new JLabel("N");
        offsetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        offsetLabel.setFont(new Font("Arial", Font.BOLD, 14));
        offsetLabel.setForeground(Color.WHITE);

        JTextField offsetField = new JTextField();
        offsetField.setAlignmentX(Component.CENTER_ALIGNMENT);
        offsetField.setMaximumSize(new Dimension(60, 25));
        offsetField.setFont(new Font("Arial", Font.BOLD, 14));
        offsetField.setHorizontalAlignment(JTextField.CENTER);
        offsetField.setForeground(Color.BLACK);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setPreferredSize(new Dimension(100, 25));
        encryptButton.setFont(new Font("Arial", Font.BOLD, 14));
        encryptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        encryptButton.setBackground(Color.WHITE);
        encryptButton.setForeground(Color.BLACK);

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.setPreferredSize(new Dimension(100, 25));
        decryptButton.setFont(new Font("Arial", Font.BOLD, 14));
        decryptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        decryptButton.setBackground(Color.WHITE);
        decryptButton.setForeground(Color.BLACK);

        JButton resultButton = new JButton("Result");
        resultButton.setMaximumSize(new Dimension(89, 25));
        resultButton.setFont(new Font("Arial", Font.BOLD, 14));
        resultButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultButton.setBackground(Color.WHITE);
        resultButton.setForeground(Color.BLACK);

        JPanel rightCesarPanel = new JPanel();
        rightCesarPanel.setLayout(new BoxLayout(rightCesarPanel, BoxLayout.Y_AXIS));
        rightCesarPanel.setBorder(BorderFactory.createEmptyBorder(75, 25, 0, 50));
        rightCesarPanel.setBackground(Color.DARK_GRAY);

        JTextField sourceFileField = new JTextField();
        sourceFileField.setPreferredSize(new Dimension(350, 30));
        sourceFileField.setMaximumSize(new Dimension(350, 30));
        sourceFileField.setMinimumSize(new Dimension(350, 30));
        sourceFileField.setFont(new Font("Arial", Font.BOLD, 12));

        JButton sourceFileButton = new JButton("Source file");
        sourceFileButton.setPreferredSize(new Dimension(100, 30));
        sourceFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sourceFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        sourceFileButton.setBackground(Color.WHITE);
        sourceFileButton.setForeground(Color.BLACK);

        sourceFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(cesar);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    sourceFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        JTextField encryptFileField = new JTextField();
        encryptFileField.setPreferredSize(new Dimension(350, 30));
        encryptFileField.setMaximumSize(new Dimension(350, 30));
        encryptFileField.setMinimumSize(new Dimension(350, 30));
        encryptFileField.setFont(new Font("Arial", Font.BOLD, 12));

        JButton encryptFileButton = new JButton("Encrypt file");
        encryptFileButton.setPreferredSize(new Dimension(100, 30));
        encryptFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        encryptFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        encryptFileButton.setBackground(Color.WHITE);
        encryptFileButton.setForeground(Color.BLACK);

        encryptFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(cesar);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    encryptFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        JTextField decryptFileField = new JTextField();
        decryptFileField.setPreferredSize(new Dimension(350, 30));
        decryptFileField.setMaximumSize(new Dimension(350, 30));
        decryptFileField.setMinimumSize(new Dimension(350, 30));
        decryptFileField.setFont(new Font("Arial", Font.BOLD, 12));

        JButton decryptFileButton = new JButton("Decrypt file");
        decryptFileButton.setPreferredSize(new Dimension(100, 30));
        decryptFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        decryptFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        decryptFileButton.setBackground(Color.WHITE);
        decryptFileButton.setForeground(Color.BLACK);

        decryptFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(cesar);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    decryptFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                EncryptionMethod encryptText = new EncryptionMethod();
                String source = sourceFileField.getText();
                String encrypt = encryptFileField.getText();
                int N = Integer.parseInt(offsetField.getText());
                String message = encryptText.cesarMethodEncrypt(source, encrypt, N);
                JOptionPane.showMessageDialog(null, message);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                EncryptionMethod decryptText = new EncryptionMethod();
                String source = encryptFileField.getText();
                String encrypt = decryptFileField.getText();
                int offset = Integer.parseInt(offsetField.getText());
                String message = decryptText.cesarMethodDecrypt(source, encrypt, offset);
                JOptionPane.showMessageDialog(null, message);
            }
        });

        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                String source = sourceFileField.getText();
                String encrypt = encryptFileField.getText();
                String decrypt = decryptFileField.getText();
                String sourceText;
                String encryptText;
                String decryptText;
                try {
                    FileReader fileReader = new FileReader(source);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    sourceText = bufferedReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    FileReader fileReader = new FileReader(encrypt);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    encryptText = bufferedReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    FileReader fileReader = new FileReader(decrypt);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    decryptText = bufferedReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sourceFileField.setText(sourceText);
                encryptFileField.setText(encryptText);
                decryptFileField.setText(decryptText);
            }
        });

        leftCesarPanel.add(offsetLabel);
        leftCesarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftCesarPanel.add(offsetField);
        leftCesarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftCesarPanel.add(encryptButton);
        leftCesarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftCesarPanel.add(decryptButton);
        leftCesarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftCesarPanel.add(resultButton);
        cesar.add(leftCesarPanel, BorderLayout.WEST);

        rightCesarPanel.add(sourceFileButton);
        rightCesarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        rightCesarPanel.add(sourceFileField);
        rightCesarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightCesarPanel.add(encryptFileButton);
        rightCesarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        rightCesarPanel.add(encryptFileField);
        rightCesarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightCesarPanel.add(decryptFileButton);
        rightCesarPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        rightCesarPanel.add(decryptFileField);
        cesar.add(rightCesarPanel, BorderLayout.EAST);

        cesar.setVisible(true);
    }

    public void vigenereWindow() {
        JFrame vigenere = new JFrame("Vigenere method");
        vigenere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vigenere.setSize(700, 450);
        vigenere.setLocationRelativeTo(null);

        JPanel leftVigenerePanel = new JPanel();
        leftVigenerePanel.setLayout(new BoxLayout(leftVigenerePanel, BoxLayout.Y_AXIS));
        leftVigenerePanel.setBorder(BorderFactory.createEmptyBorder(85, 150, 0, 25));
        leftVigenerePanel.setBackground(Color.DARK_GRAY);

        JLabel keywordLabel = new JLabel("Keyword");
        keywordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        keywordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        keywordLabel.setForeground(Color.WHITE);

        JTextField keywordField = new JTextField();
        keywordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        keywordField.setPreferredSize(new Dimension(125, 25));
        keywordField.setMinimumSize(new Dimension(125, 25));
        keywordField.setMaximumSize(new Dimension(125, 25));
        keywordField.setFont(new Font("Arial", Font.BOLD, 14));
        keywordField.setHorizontalAlignment(JTextField.CENTER);
        keywordField.setForeground(Color.BLACK);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setMaximumSize(new Dimension(125, 25));
        encryptButton.setFont(new Font("Arial", Font.BOLD, 14));
        encryptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        encryptButton.setBackground(Color.WHITE);
        encryptButton.setForeground(Color.BLACK);

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.setMaximumSize(new Dimension(125, 25));
        decryptButton.setFont(new Font("Arial", Font.BOLD, 14));
        decryptButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        decryptButton.setBackground(Color.WHITE);
        decryptButton.setForeground(Color.BLACK);

        JPanel rightVigenerePanel = new JPanel();
        rightVigenerePanel.setLayout(new BoxLayout(rightVigenerePanel, BoxLayout.Y_AXIS));
        rightVigenerePanel.setBorder(BorderFactory.createEmptyBorder(75, 25, 0, 50));
        rightVigenerePanel.setBackground(Color.DARK_GRAY);

        JTextField sourceFileField = new JTextField();
        sourceFileField.setPreferredSize(new Dimension(350, 30));
        sourceFileField.setMaximumSize(new Dimension(350, 30));
        sourceFileField.setMinimumSize(new Dimension(350, 30));
        sourceFileField.setFont(new Font("Arial", Font.BOLD, 12));

        JButton sourceFileButton = new JButton("Source file");
        sourceFileButton.setPreferredSize(new Dimension(100, 30));
        sourceFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sourceFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        sourceFileButton.setBackground(Color.WHITE);
        sourceFileButton.setForeground(Color.BLACK);

        sourceFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(vigenere);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    sourceFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        JTextField encryptFileField = new JTextField();
        encryptFileField.setPreferredSize(new Dimension(350, 30));
        encryptFileField.setMaximumSize(new Dimension(350, 30));
        encryptFileField.setMinimumSize(new Dimension(350, 30));
        encryptFileField.setFont(new Font("Arial", Font.BOLD, 12));

        JButton encryptFileButton = new JButton("Encrypt file");
        encryptFileButton.setPreferredSize(new Dimension(100, 30));
        encryptFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        encryptFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        encryptFileButton.setBackground(Color.WHITE);
        encryptFileButton.setForeground(Color.BLACK);

        encryptFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(vigenere);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    encryptFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        JTextField decryptFileField = new JTextField();
        decryptFileField.setPreferredSize(new Dimension(350, 30));
        decryptFileField.setMaximumSize(new Dimension(350, 30));
        decryptFileField.setMinimumSize(new Dimension(350, 30));
        decryptFileField.setFont(new Font("Arial", Font.BOLD, 12));

        JButton decryptFileButton = new JButton("Decrypt file");
        decryptFileButton.setPreferredSize(new Dimension(100, 30));
        decryptFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        decryptFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        decryptFileButton.setBackground(Color.WHITE);
        decryptFileButton.setForeground(Color.BLACK);

        decryptFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(vigenere);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    decryptFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                EncryptionMethod encryptText = new EncryptionMethod();
                String source = sourceFileField.getText();
                String encrypt = encryptFileField.getText();
                String keyword = keywordField.getText();
                String message = encryptText.vigenereMethodEncrypt(source, encrypt, keyword);
                JOptionPane.showMessageDialog(null, message);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EncryptionMethod decryptText = new EncryptionMethod();
                String encrypt = encryptFileField.getText();
                String decrypt = decryptFileField.getText();
                String keyword = keywordField.getText();
                String message = decryptText.vigenereMethodDecrypt(encrypt, decrypt, keyword);
                JOptionPane.showMessageDialog(null, message);
            }
        });

        JButton vigenereSquareButton = new JButton("Square");
        vigenereSquareButton.setMaximumSize(new Dimension(125, 30));
        vigenereSquareButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        vigenereSquareButton.setFont(new Font("Arial", Font.BOLD, 14));
        vigenereSquareButton.setBackground(Color.WHITE);
        vigenereSquareButton.setForeground(Color.BLACK);

        vigenereSquareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                JFrame vigenereSquare = new JFrame("Vigenere Square");
                vigenereSquare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                vigenereSquare.setSize(600, 550);

                JPanel vigenereSquarePanel = new JPanel(new GridBagLayout());

                EncryptionMethod encryptionMethod = new EncryptionMethod();
                char[][] square = encryptionMethod.getVigenereSquare();

                Object[][] data = new Object[square.length - 1][square.length];
                for (int i = 0; i < square.length - 1; i++) {
                    data[i][0] = square[i][0];
                    for (int j = 1; j < square.length; j++) {
                        data[i][j] = square[i][j - 1];
                    }
                }

                String[] columnNames = new String[square.length];
                columnNames[0] = "";
                for (int i = 1; i < columnNames.length; i++) {
                    columnNames[i] = String.valueOf((char) ('A' + i - 1));
                }

                JTable vigenereSquareTable = new JTable(data, columnNames);
                vigenereSquareTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < vigenereSquareTable.getColumnCount(); i++) {
                    vigenereSquareTable.getColumnModel().getColumn(i).setPreferredWidth(20);
                    vigenereSquareTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                JScrollPane scrollPane = new JScrollPane(vigenereSquareTable);
                scrollPane.setPreferredSize(new Dimension(545, 445));

                vigenereSquarePanel.add(scrollPane);
                vigenereSquare.add(vigenereSquarePanel, BorderLayout.CENTER);
                vigenereSquare.setLocationRelativeTo(null);
                vigenereSquare.setVisible(true);
            }
        });

        JButton resultButton = new JButton("Result");
        resultButton.setMaximumSize(new Dimension(125, 30));
        resultButton.setFont(new Font("Arial", Font.BOLD, 14));
        resultButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultButton.setBackground(Color.WHITE);
        resultButton.setForeground(Color.BLACK);

        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                String source = sourceFileField.getText();
                String encrypt = encryptFileField.getText();
                String decrypt = decryptFileField.getText();
                String sourceText;
                String encryptText;
                String decryptText;
                try {
                    FileReader fileReader = new FileReader(source);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    sourceText = bufferedReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    FileReader fileReader = new FileReader(encrypt);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    encryptText = bufferedReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    FileReader fileReader = new FileReader(decrypt);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    decryptText = bufferedReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sourceFileField.setText(sourceText);
                encryptFileField.setText(encryptText);
                decryptFileField.setText(decryptText);
            }
        });


        leftVigenerePanel.add(keywordLabel);
        leftVigenerePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        leftVigenerePanel.add(keywordField);
        leftVigenerePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftVigenerePanel.add(encryptButton);
        leftVigenerePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftVigenerePanel.add(decryptButton);
        leftVigenerePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftVigenerePanel.add(vigenereSquareButton);
        leftVigenerePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftVigenerePanel.add(resultButton);
        vigenere.add(leftVigenerePanel, BorderLayout.WEST);

        rightVigenerePanel.add(sourceFileButton);
        rightVigenerePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        rightVigenerePanel.add(sourceFileField);
        rightVigenerePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightVigenerePanel.add(encryptFileButton);
        rightVigenerePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        rightVigenerePanel.add(encryptFileField);
        rightVigenerePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightVigenerePanel.add(decryptFileButton);
        rightVigenerePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        rightVigenerePanel.add(decryptFileField);
        vigenere.add(rightVigenerePanel, BorderLayout.EAST);

        vigenere.setVisible(true);
    }
}
