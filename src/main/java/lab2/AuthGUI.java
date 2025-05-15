package lab2;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class AuthGUI {

    private int attempts = 0;

    public void startWindow() {
        Function f = new Function();

        try {
            f.initAdmin();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        JFrame start = new JFrame("Authorization");
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setSize(400, 400);
        start.setLocationRelativeTo(null);
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.setBorder(BorderFactory.createEmptyBorder(75, 0, 0, 0));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField loginField = new JTextField();
        loginField.setMaximumSize(new Dimension(200, 30));
        loginField.setFont(new Font("Arial", Font.PLAIN, 16));
        loginField.setHorizontalAlignment(JTextField.CENTER);

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

        authenticateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    String message = f.authorization(login, password);
                    JOptionPane.showMessageDialog(null, message);
                    if (message.equals("Вы авторизованы")) {
                        start.dispose();
                        if (login.equals("ADMIN")) {
                            adminWindow();
                        } else {
                            userWindow(login);
                        }
                    } else {
                        loginField.setText("");
                        passwordField.setText("");
                        attempts++;
                    }
                    if (attempts >= 3) {
                        JOptionPane.showMessageDialog(null, "Попытки входа закончились");
                        start.dispose();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        startPanel.add(loginLabel);
        startPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        startPanel.add(loginField);
        startPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        startPanel.add(passwordLabel);
        startPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        startPanel.add(passwordField);
        startPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        startPanel.add(authenticateButton);
        start.add(startPanel);
        start.setVisible(true);
    }

    public void adminWindow() {
        Function f = new Function();
        JFrame admin = new JFrame("ADMIN");
        admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        admin.setSize(400, 400);
        admin.setLocationRelativeTo(null);

        JPanel leftAdminPanel = new JPanel();
        leftAdminPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
        leftAdminPanel.setLayout(new BoxLayout(leftAdminPanel, BoxLayout.Y_AXIS));

        JPanel rightAdminPanel = new JPanel();
        rightAdminPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 10));

        JButton changePasswordButton = new JButton("Change password");
        changePasswordButton.setMaximumSize(new Dimension(250, 25));
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 14));
        changePasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                admin.remove(leftAdminPanel);
                admin.remove(rightAdminPanel);
                admin.validate();
                admin.repaint();
                JPanel changePasswordPanel = new JPanel();
                changePasswordPanel.setLayout(new BoxLayout(changePasswordPanel, BoxLayout.Y_AXIS));
                changePasswordPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

                JLabel oldPasswordLabel = new JLabel("Enter the old password");
                oldPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                oldPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JPasswordField oldPasswordField = new JPasswordField();
                oldPasswordField.setMaximumSize(new Dimension(200, 30));
                oldPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
                oldPasswordField.setHorizontalAlignment(JPasswordField.CENTER);
                oldPasswordField.setEchoChar('*');

                JLabel newPasswordLabel = new JLabel("Enter the new password");
                newPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                newPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JPasswordField newPasswordField = new JPasswordField();
                newPasswordField.setMaximumSize(new Dimension(200, 30));
                newPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
                newPasswordField.setHorizontalAlignment(JPasswordField.CENTER);
                newPasswordField.setEchoChar('*');

                JButton confirmButton = new JButton("Change");
                confirmButton.setMaximumSize(new Dimension(100, 40));
                confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                confirmButton.setFont(new Font("Arial", Font.BOLD, 14));

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        String oldPassword = new String(oldPasswordField.getPassword());
                        String newPassword = new String(newPasswordField.getPassword());
                        String login = "ADMIN";
                        try {
                            String message = f.changePassword(login, oldPassword, newPassword);
                            JOptionPane.showMessageDialog(null, message);
                            if (message.equals("Пароль успешно изменен")) {
                                admin.remove(changePasswordPanel);
                                admin.add(leftAdminPanel, BorderLayout.WEST);
                                admin.add(rightAdminPanel, BorderLayout.EAST);
                                admin.revalidate();
                                admin.repaint();
                                admin.setVisible(true);
                            } else {
                                oldPasswordField.setText("");
                                newPasswordField.setText("");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                JButton returnButton = new JButton("Return");
                returnButton.setFont(new Font("Arial", Font.BOLD, 14));
                returnButton.setMaximumSize(new Dimension(100, 25));
                returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                returnButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        admin.remove(changePasswordPanel);
                        admin.add(leftAdminPanel, BorderLayout.WEST);
                        admin.add(rightAdminPanel, BorderLayout.EAST);
                        admin.revalidate();
                        admin.repaint();
                        admin.setVisible(true);
                    }
                });

                changePasswordPanel.add(returnButton);
                changePasswordPanel.add(Box.createRigidArea(new Dimension(0, 50)));
                changePasswordPanel.add(oldPasswordLabel);
                changePasswordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                changePasswordPanel.add(oldPasswordField);
                changePasswordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                changePasswordPanel.add(newPasswordLabel);
                changePasswordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                changePasswordPanel.add(newPasswordField);
                changePasswordPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                changePasswordPanel.add(confirmButton);
                admin.add(changePasswordPanel);
                admin.setVisible(true);
            }
        });

        JButton checkUserListButton = new JButton("Check user list");
        checkUserListButton.setMaximumSize(new Dimension(250, 25));
        checkUserListButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkUserListButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        checkUserListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                admin.remove(leftAdminPanel);
                admin.remove(rightAdminPanel);
                admin.validate();
                admin.repaint();
                admin.setSize(750, 500);

                JPanel buttonPanel = new JPanel();
                JPanel checkUserListPanel = new JPanel(new BorderLayout());
                JButton returnButton = new JButton("Return");
                returnButton.setFont(new Font("Arial", Font.BOLD, 14));
                returnButton.setMaximumSize(new Dimension(100, 25));
                returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                returnButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        admin.remove(checkUserListPanel);
                        admin.add(leftAdminPanel, BorderLayout.WEST);
                        admin.add(rightAdminPanel, BorderLayout.EAST);
                        admin.revalidate();
                        admin.repaint();
                        admin.setSize(400, 400);
                        admin.setVisible(true);
                    }
                });

                String[] columnNames = {"Login", "Password", "Blocked", "Number", "Lower Letter", "Upper Letter", "Special Symbol"};
                Object[][] data = {};

                try {
                    List<String[]> userList = f.checkUserList();
                    data = new Object[userList.size()][columnNames.length];
                    for (int i = 0; i < userList.size(); i++) {
                        String[] user = userList.get(i);
                        data[i] = user;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                JTable userTable = new JTable(data, columnNames);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < userTable.getColumnCount(); i++) {
                    userTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                JScrollPane userTableScrollPane = new JScrollPane(userTable);

                buttonPanel.add(returnButton);
                checkUserListPanel.add(buttonPanel, BorderLayout.NORTH);
                checkUserListPanel.add(userTableScrollPane, BorderLayout.CENTER);
                admin.add(checkUserListPanel);
                admin.setVisible(true);
            }
        });

        JButton addUserButton = new JButton("Add user");
        addUserButton.setMaximumSize(new Dimension(250, 25));
        addUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        addUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                admin.remove(leftAdminPanel);
                admin.remove(rightAdminPanel);
                admin.validate();
                admin.repaint();

                JPanel addUserPanel = new JPanel();
                addUserPanel.setLayout(new BoxLayout(addUserPanel, BoxLayout.Y_AXIS));
                addUserPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

                JLabel addUserLabel = new JLabel("Enter new user's login");
                addUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                addUserLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JTextField addUserField = new JTextField();
                addUserField.setMaximumSize(new Dimension(150, 30));
                addUserField.setFont(new Font("Arial", Font.PLAIN, 14));
                addUserField.setHorizontalAlignment(JTextField.CENTER);

                JButton confirmButton = new JButton("Add user");
                confirmButton.setMaximumSize(new Dimension(100, 25));
                confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                confirmButton.setFont(new Font("Arial", Font.BOLD, 14));

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        String login = addUserField.getText();
                        try {
                            String message = f.addUser(login);
                            JOptionPane.showMessageDialog(null, message);
                            if (message.equals("Пользователь добавлен")) {
                                admin.remove(addUserPanel);
                                admin.add(leftAdminPanel, BorderLayout.WEST);
                                admin.add(rightAdminPanel, BorderLayout.EAST);
                                admin.revalidate();
                                admin.repaint();
                                admin.setVisible(true);
                            } else {
                                addUserField.setText("");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                JButton returnButton = new JButton("Return");
                returnButton.setFont(new Font("Arial", Font.BOLD, 14));
                returnButton.setMaximumSize(new Dimension(100, 25));
                returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                returnButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        admin.remove(addUserPanel);
                        admin.add(leftAdminPanel, BorderLayout.WEST);
                        admin.add(rightAdminPanel, BorderLayout.EAST);
                        admin.revalidate();
                        admin.repaint();
                        admin.setVisible(true);
                    }
                });

                addUserPanel.add(returnButton);
                addUserPanel.add(Box.createRigidArea(new Dimension(0, 75)));
                addUserPanel.add(addUserLabel);
                addUserPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                addUserPanel.add(addUserField);
                addUserPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                addUserPanel.add(confirmButton);

                admin.add(addUserPanel);
                admin.setVisible(true);
            }
        });

        JButton blockUserButton = new JButton("Block user");
        blockUserButton.setMaximumSize(new Dimension(250, 25));
        blockUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        blockUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        blockUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                admin.remove(leftAdminPanel);
                admin.remove(rightAdminPanel);
                admin.validate();
                admin.repaint();

                JPanel blockUserPanel = new JPanel();
                blockUserPanel.setLayout(new BoxLayout(blockUserPanel, BoxLayout.Y_AXIS));
                blockUserPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

                JLabel blockUserLabel = new JLabel("Enter user's login");
                blockUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                blockUserLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JTextField blockUserField = new JTextField();
                blockUserField.setMaximumSize(new Dimension(150, 30));
                blockUserField.setFont(new Font("Arial", Font.PLAIN, 14));
                blockUserField.setHorizontalAlignment(JTextField.CENTER);

                JButton confirmButton = new JButton("Confirm");
                confirmButton.setMaximumSize(new Dimension(100, 25));
                confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                confirmButton.setFont(new Font("Arial", Font.BOLD, 14));

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        String login = blockUserField.getText();
                        try {
                            String message = f.blockUser(login);
                            JOptionPane.showMessageDialog(null, message);
                            if (message.equals("Пользователь заблокирован") || message.equals("Пользователь разблокирован")) {
                                admin.remove(blockUserPanel);
                                admin.add(leftAdminPanel, BorderLayout.WEST);
                                admin.add(rightAdminPanel, BorderLayout.EAST);
                                admin.revalidate();
                                admin.repaint();
                                admin.setVisible(true);
                            } else {
                                blockUserField.setText("");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                JButton returnButton = new JButton("Return");
                returnButton.setFont(new Font("Arial", Font.BOLD, 14));
                returnButton.setMaximumSize(new Dimension(100, 25));
                returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                returnButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        admin.remove(blockUserPanel);
                        admin.add(leftAdminPanel, BorderLayout.WEST);
                        admin.add(rightAdminPanel, BorderLayout.EAST);
                        admin.revalidate();
                        admin.repaint();
                        admin.setVisible(true);
                    }
                });

                blockUserPanel.add(returnButton);
                blockUserPanel.add(Box.createRigidArea(new Dimension(0, 75)));
                blockUserPanel.add(blockUserLabel);
                blockUserPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                blockUserPanel.add(blockUserField);
                blockUserPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                blockUserPanel.add(confirmButton);

                admin.add(blockUserPanel);
                admin.setVisible(true);
            }
        });

        JButton setNumberRestrictionButton = new JButton("Set number restriction");
        setNumberRestrictionButton.setMaximumSize(new Dimension(250, 25));
        setNumberRestrictionButton.setFont(new Font("Arial", Font.BOLD, 14));
        setNumberRestrictionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setNumberRestrictionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                admin.remove(leftAdminPanel);
                admin.remove(rightAdminPanel);
                admin.validate();
                admin.repaint();

                JPanel setNumberRestrictionPanel = new JPanel();
                setNumberRestrictionPanel.setLayout(new BoxLayout(setNumberRestrictionPanel, BoxLayout.Y_AXIS));
                setNumberRestrictionPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

                JLabel loginLabel = new JLabel("Enter user's login");
                loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                loginLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JTextField loginField = new JTextField();
                loginField.setMaximumSize(new Dimension(150, 30));
                loginField.setFont(new Font("Arial", Font.PLAIN, 14));
                loginField.setHorizontalAlignment(JTextField.CENTER);

                JButton enterButton = new JButton("Set");
                enterButton.setMaximumSize(new Dimension(100, 25));
                enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                enterButton.setFont(new Font("Arial", Font.BOLD, 14));

                enterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        String login = loginField.getText();
                        try {
                            String message = f.setNumberRestriction(login);
                            JOptionPane.showMessageDialog(null, message);
                            if (message.equals("Ограничение на цифры поставлено") || message.equals("Ограничение на цифры убрано")) {
                                admin.remove(setNumberRestrictionPanel);
                                admin.add(leftAdminPanel, BorderLayout.WEST);
                                admin.add(rightAdminPanel, BorderLayout.EAST);
                                admin.revalidate();
                                admin.repaint();
                                admin.setVisible(true);
                            } else {
                                loginField.setText("");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                setNumberRestrictionPanel.add(loginLabel);
                setNumberRestrictionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                setNumberRestrictionPanel.add(loginField);
                setNumberRestrictionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                setNumberRestrictionPanel.add(enterButton);
                admin.add(setNumberRestrictionPanel);
                admin.setVisible(true);
            }
        });

        JButton setLowerLetterRestrictionButton = new JButton("Set lower letter restriction");
        setLowerLetterRestrictionButton.setMaximumSize(new Dimension(250, 25));
        setLowerLetterRestrictionButton.setFont(new Font("Arial", Font.BOLD, 14));
        setLowerLetterRestrictionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLowerLetterRestrictionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                admin.remove(leftAdminPanel);
                admin.remove(rightAdminPanel);
                admin.validate();
                admin.repaint();

                JPanel setLowerLetterRestrictionPanel = new JPanel();
                setLowerLetterRestrictionPanel.setLayout(new BoxLayout(setLowerLetterRestrictionPanel, BoxLayout.Y_AXIS));
                setLowerLetterRestrictionPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

                JLabel loginLabel = new JLabel("Enter user's login");
                loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                loginLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JTextField loginField = new JTextField();
                loginField.setMaximumSize(new Dimension(150, 30));
                loginField.setFont(new Font("Arial", Font.PLAIN, 14));
                loginField.setHorizontalAlignment(JTextField.CENTER);

                JButton enterButton = new JButton("Set");
                enterButton.setMaximumSize(new Dimension(100, 25));
                enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                enterButton.setFont(new Font("Arial", Font.BOLD, 14));

                enterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        String login = loginField.getText();
                        try {
                            String message = f.setLowerLetterRestriction(login);
                            JOptionPane.showMessageDialog(null, message);
                            if (message.equals("Ограничение на буквы нижнего регистра поставлено") || message.equals("Ограничение на буквы нижнего регистра убрано")) {
                                admin.remove(setLowerLetterRestrictionPanel);
                                admin.add(leftAdminPanel, BorderLayout.WEST);
                                admin.add(rightAdminPanel, BorderLayout.EAST);
                                admin.revalidate();
                                admin.repaint();
                                admin.setVisible(true);
                            } else {
                                loginField.setText("");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                setLowerLetterRestrictionPanel.add(loginLabel);
                setLowerLetterRestrictionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                setLowerLetterRestrictionPanel.add(loginField);
                setLowerLetterRestrictionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                setLowerLetterRestrictionPanel.add(enterButton);
                admin.add(setLowerLetterRestrictionPanel);
                admin.setVisible(true);
            }
        });

        JButton setUpperLetterRestrictionButton = new JButton("Set upper letter restriction");
        setUpperLetterRestrictionButton.setMaximumSize(new Dimension(250, 25));
        setUpperLetterRestrictionButton.setFont(new Font("Arial", Font.BOLD, 14));
        setUpperLetterRestrictionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setUpperLetterRestrictionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                admin.remove(leftAdminPanel);
                admin.remove(rightAdminPanel);
                admin.validate();
                admin.repaint();

                JPanel setUpperLetterRestrictionPanel = new JPanel();
                setUpperLetterRestrictionPanel.setLayout(new BoxLayout(setUpperLetterRestrictionPanel, BoxLayout.Y_AXIS));
                setUpperLetterRestrictionPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

                JLabel loginLabel = new JLabel("Enter user's login");
                loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                loginLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JTextField loginField = new JTextField();
                loginField.setMaximumSize(new Dimension(150, 30));
                loginField.setFont(new Font("Arial", Font.PLAIN, 14));
                loginField.setHorizontalAlignment(JTextField.CENTER);

                JButton enterButton = new JButton("Set");
                enterButton.setMaximumSize(new Dimension(100, 25));
                enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                enterButton.setFont(new Font("Arial", Font.BOLD, 14));

                enterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        String login = loginField.getText();
                        try {
                            String message = f.setUpperLetterRestriction(login);
                            JOptionPane.showMessageDialog(null, message);
                            if (message.equals("Ограничение на буквы верхнего регистра поставлено") || message.equals("Ограничение на буквы верхнего регистра убрано")) {
                                admin.remove(setUpperLetterRestrictionPanel);
                                admin.add(leftAdminPanel, BorderLayout.WEST);
                                admin.add(rightAdminPanel, BorderLayout.EAST);
                                admin.revalidate();
                                admin.repaint();
                                admin.setVisible(true);
                            } else {
                                loginField.setText("");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                setUpperLetterRestrictionPanel.add(loginLabel);
                setUpperLetterRestrictionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                setUpperLetterRestrictionPanel.add(loginField);
                setUpperLetterRestrictionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                setUpperLetterRestrictionPanel.add(enterButton);
                admin.add(setUpperLetterRestrictionPanel);
                admin.setVisible(true);
            }
        });

        JButton setSpecialSymbolRestrictionButton = new JButton("Set special symbol restriction");
        setSpecialSymbolRestrictionButton.setMaximumSize(new Dimension(250, 25));
        setSpecialSymbolRestrictionButton.setFont(new Font("Arial", Font.BOLD, 14));
        setSpecialSymbolRestrictionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        setSpecialSymbolRestrictionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                admin.remove(leftAdminPanel);
                admin.remove(rightAdminPanel);
                admin.validate();
                admin.repaint();

                JPanel setUpperLetterRestrictionPanel = new JPanel();
                setUpperLetterRestrictionPanel.setLayout(new BoxLayout(setUpperLetterRestrictionPanel, BoxLayout.Y_AXIS));
                setUpperLetterRestrictionPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

                JLabel loginLabel = new JLabel("Enter user's login");
                loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                loginLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JTextField loginField = new JTextField();
                loginField.setMaximumSize(new Dimension(150, 30));
                loginField.setFont(new Font("Arial", Font.PLAIN, 14));
                loginField.setHorizontalAlignment(JTextField.CENTER);

                JButton enterButton = new JButton("Set");
                enterButton.setMaximumSize(new Dimension(100, 25));
                enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                enterButton.setFont(new Font("Arial", Font.BOLD, 14));

                enterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        String login = loginField.getText();
                        try {
                            String message = f.setSpecialSymbolRestriction(login);
                            JOptionPane.showMessageDialog(null, message);
                            if (message.equals("Ограничение на знаки препинания и специальные символы поставлено") || message.equals("Ограничение на знаки препинания и специальные символы убрано")) {
                                admin.remove(setUpperLetterRestrictionPanel);
                                admin.add(leftAdminPanel, BorderLayout.WEST);
                                admin.add(rightAdminPanel, BorderLayout.EAST);
                                admin.revalidate();
                                admin.repaint();
                                admin.setVisible(true);
                            } else {
                                loginField.setText("");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                setUpperLetterRestrictionPanel.add(loginLabel);
                setUpperLetterRestrictionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                setUpperLetterRestrictionPanel.add(loginField);
                setUpperLetterRestrictionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                setUpperLetterRestrictionPanel.add(enterButton);
                admin.add(setUpperLetterRestrictionPanel);
                admin.setVisible(true);
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                admin.dispose();
            }
        });

        leftAdminPanel.add(changePasswordButton);
        leftAdminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftAdminPanel.add(checkUserListButton);
        leftAdminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftAdminPanel.add(addUserButton);
        leftAdminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftAdminPanel.add(blockUserButton);
        leftAdminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftAdminPanel.add(setNumberRestrictionButton);
        leftAdminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftAdminPanel.add(setLowerLetterRestrictionButton);
        leftAdminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftAdminPanel.add(setUpperLetterRestrictionButton);
        leftAdminPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftAdminPanel.add(setSpecialSymbolRestrictionButton);
        rightAdminPanel.add(exitButton);

        admin.add(leftAdminPanel, BorderLayout.WEST);
        admin.add(rightAdminPanel, BorderLayout.EAST);
        admin.setVisible(true);
    }

    public void userWindow(String userName) {
        Function f = new Function();

        JFrame user = new JFrame(userName);
        user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        user.setSize(400, 400);
        user.setLocationRelativeTo(null);

        JPanel userPanel = new JPanel();
        userPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));

        JButton changePasswordButton = new JButton("Change password");
        changePasswordButton.setMaximumSize(new Dimension(100, 40));
        changePasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 14));

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                user.remove(userPanel);
                user.validate();
                user.repaint();
                JPanel changePasswordPanel = new JPanel();
                changePasswordPanel.setLayout(new BoxLayout(changePasswordPanel, BoxLayout.Y_AXIS));
                changePasswordPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

                JLabel oldPasswordLabel = new JLabel("Enter the old password");
                oldPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                oldPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JPasswordField oldPasswordField = new JPasswordField();
                oldPasswordField.setMaximumSize(new Dimension(200, 30));
                oldPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
                oldPasswordField.setHorizontalAlignment(JPasswordField.CENTER);
                oldPasswordField.setEchoChar('*');

                JLabel newPasswordLabel = new JLabel("Enter the new password");
                newPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                newPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));

                JPasswordField newPasswordField = new JPasswordField();
                newPasswordField.setMaximumSize(new Dimension(200, 30));
                newPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
                newPasswordField.setHorizontalAlignment(JPasswordField.CENTER);
                newPasswordField.setEchoChar('*');

                JButton confirmButton = new JButton("Change");
                confirmButton.setMaximumSize(new Dimension(100, 40));
                confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                confirmButton.setFont(new Font("Arial", Font.BOLD, 14));

                JButton returnButton = new JButton("Return");
                returnButton.setFont(new Font("Arial", Font.BOLD, 14));
                returnButton.setMaximumSize(new Dimension(100, 25));
                returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                returnButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        user.remove(changePasswordPanel);
                        user.add(userPanel, BorderLayout.WEST);
                        user.revalidate();
                        user.repaint();
                        user.setVisible(true);
                    }
                });

                changePasswordPanel.add(returnButton);
                changePasswordPanel.add(Box.createRigidArea(new Dimension(0, 50)));

                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent action) {
                        String oldPassword = new String(oldPasswordField.getPassword());
                        String newPassword = new String(newPasswordField.getPassword());
                        String login = user.getTitle();
                        try {
                            String message = f.changePassword(login, oldPassword, newPassword);
                            JOptionPane.showMessageDialog(null, message);
                            if (message.equals("Пароль успешно изменен")) {
                                user.remove(changePasswordPanel);
                                user.add(userPanel);
                                user.revalidate();
                                user.repaint();
                                user.setVisible(true);
                            } else {
                                oldPasswordField.setText("");
                                newPasswordField.setText("");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                changePasswordPanel.add(oldPasswordLabel);
                changePasswordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                changePasswordPanel.add(oldPasswordField);
                changePasswordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                changePasswordPanel.add(newPasswordLabel);
                changePasswordPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                changePasswordPanel.add(newPasswordField);
                changePasswordPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                changePasswordPanel.add(confirmButton);
                user.add(changePasswordPanel);
                user.setVisible(true);
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                user.dispose();
            }
        });

        userPanel.add(changePasswordButton, BorderLayout.WEST);
        userPanel.add(Box.createRigidArea(new Dimension(120, 0)));
        userPanel.add(exitButton, BorderLayout.EAST);
        user.add(userPanel);
        user.setVisible(true);
    }
}
