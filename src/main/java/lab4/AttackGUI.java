package lab4;

import lab1.PassSecurity;
import lab2.AuthGUI;
import lab2.Function;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AttackGUI extends AuthGUI {
    private final Attack attack = new Attack();
    private boolean isBruteForce = false;

    public void startWindow() {
        JFrame start = new JFrame("Modes");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setSize(400, 400);
        start.setLocationRelativeTo(null);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.setBorder(BorderFactory.createEmptyBorder(120, 0, 0, 0));

        JButton attackButton = new JButton("Подбор пароля");
        attackButton.setMaximumSize(new Dimension(200, 40));
        attackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        attackButton.setFont(new Font("Arial", Font.BOLD, 14));

        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                start.dispose();
                authWindow();
            }
        });

        JButton passwordPowerButton = new JButton("Надежность пароля");
        passwordPowerButton.setMaximumSize(new Dimension(200, 40));
        passwordPowerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPowerButton.setFont(new Font("Arial", Font.BOLD, 14));

        passwordPowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                start.dispose();
                passwordPowerWindow();
            }
        });

        startPanel.add(attackButton);
        startPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        startPanel.add(passwordPowerButton);
        start.add(startPanel);
        start.setVisible(true);

    }

    public void authWindow() {
        Function f = new Function();

        JFrame auth = new JFrame("Подбор пароля");
        auth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        auth.setSize(400, 400);
        auth.setLocationRelativeTo(null);
        JPanel authPanel = new JPanel();
        authPanel.setLayout(new BoxLayout(authPanel, BoxLayout.Y_AXIS));
        authPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JButton dictionaryButton = new JButton("Подбор по словарю");
        dictionaryButton.setMaximumSize(new Dimension(200, 30));
        dictionaryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dictionaryButton.setFont(new Font("Arial", Font.BOLD, 14));

        JButton bruteForceButton = new JButton("Перебор");
        bruteForceButton.setMaximumSize(new Dimension(200, 30));
        bruteForceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bruteForceButton.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField loginField = new JTextField();
        loginField.setMaximumSize(new Dimension(200, 30));
        loginField.setFont(new Font("Arial", Font.PLAIN, 16));
        loginField.setHorizontalAlignment(JTextField.CENTER);
        loginField.setText("ADMIN");

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(200, 30));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setHorizontalAlignment(JPasswordField.CENTER);
        passwordField.setEchoChar('*');

        JButton authenticateButton = new JButton("Sign in");
        authenticateButton.setMaximumSize(new Dimension(100, 40));
        authenticateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        authenticateButton.setFont(new Font("Arial", Font.BOLD, 14));

        dictionaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    isBruteForce = false;
                    String attempt = attack.dictionaryAttack();
                    if (attempt != null) {
                        passwordField.setText(attempt);
                    } else {
                        JOptionPane.showMessageDialog(auth, "Словарная атака завершена");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        bruteForceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isBruteForce = true;
                String attempt = attack.bruteForceAttack(4);
                if (attempt != null) {
                    passwordField.setText(attempt);
                } else {
                    JOptionPane.showMessageDialog(auth, "Перебор завершен");
                }
            }
        });

        authenticateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    String message = f.authorization(login, password);
                    JOptionPane.showMessageDialog(null, message);
                    if (message.equals("Вы авторизованы")) {
                        auth.dispose();
                        if (login.equals("ADMIN")) {
                            adminWindow();
                        } else {
                            userWindow(login);
                        }
                    } else {
                        if (isBruteForce) {
                            String nextAttempt = attack.bruteForceAttack(5);
                            if (nextAttempt != null) {
                                passwordField.setText(nextAttempt);
                            } else {
                                JOptionPane.showMessageDialog(auth, "Перебор завершен");
                            }
                        } else {
                            try {
                                String nextAttempt = attack.dictionaryAttack();
                                if (nextAttempt != null) {
                                    passwordField.setText(nextAttempt);
                                } else {
                                    JOptionPane.showMessageDialog(auth, "Словарная атака завершена");
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        authPanel.add(dictionaryButton);
        authPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        authPanel.add(bruteForceButton);
        authPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        authPanel.add(loginLabel);
        authPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        authPanel.add(loginField);
        authPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        authPanel.add(passwordLabel);
        authPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        authPanel.add(passwordField);
        authPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        authPanel.add(authenticateButton);
        auth.add(authPanel);
        auth.setVisible(true);
    }

    public void passwordPowerWindow() {
        JFrame passFrame = new JFrame("Проверка надежности пароля");
        passFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        passFrame.setSize(400, 400);
        passFrame.setLocationRelativeTo(null);

        JPanel passPanel = new JPanel();
        passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.Y_AXIS));
        passPanel.setBorder(BorderFactory.createEmptyBorder(120, 0, 0, 0));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(200, 30));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setHorizontalAlignment(JPasswordField.CENTER);
        passwordField.setEchoChar('*');

        JButton checkButton = new JButton("Проверить");
        checkButton.setMaximumSize(new Dimension(150, 40));
        checkButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkButton.setFont(new Font("Arial", Font.BOLD, 14));

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PassSecurity sec = new PassSecurity();
                String password = new String(passwordField.getPassword());
                int length = password.length();
                int power = sec.checkAlphabetPower(password);
                long comb = sec.findCombinationCount(power, length);
                long time = sec.findBruteForceTime(comb, 1000000, 0, 0);
                System.out.print("Полный перебор для данного пароля займет: ");
                sec.formatBruteForceTime(time);
            }
        });

        passPanel.add(passwordField);
        passPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        passPanel.add(checkButton);
        passFrame.add(passPanel);
        passFrame.setVisible(true);
    }

    @Override
    public void adminWindow() {
        super.adminWindow();
    }
}
