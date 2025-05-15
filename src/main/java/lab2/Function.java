package lab2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Function {

    private static final String URL = "jdbc:postgresql://localhost:5432/ISDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qwerty1";

    public void initAdmin() throws SQLException {
        String insertQuery = "INSERT INTO auth.users(login, password, \"isBlocked\", \"isNumber\", \"isLowerLetter\", \"isUpperLetter\", \"isSpecialSymbol\") " +
                "VALUES ('ADMIN', '', false, false, false, false, false)";
        String selectQuery = "SELECT * FROM auth.users";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

        ResultSet resultSet = selectStatement.executeQuery();
        if (!resultSet.next()) {
            insertStatement.execute();
        }
    }

    public String authorization(String login, String password) throws SQLException {
        String selectQuery = "SELECT password, \"isBlocked\" FROM auth.users WHERE login = ?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);

        selectStatement.setString(1, login);
        ResultSet resultSet = selectStatement.executeQuery();
        if (!resultSet.next()) {
            return "Такого пользователя не существует";
        }
        boolean isBlocked = resultSet.getBoolean("isBlocked");
        if (isBlocked) {
            return "Заблокирован";
        }
        String pass = resultSet.getString("password");
        if (pass.equals(password)) {
            return "Вы авторизованы";
        } else {
            return "Неверный пароль";
        }
    }

    public String changePassword(String login, String oldPassword, String newPassword) throws SQLException {
        String selectQuery = "SELECT password, \"isNumber\", \"isLowerLetter\", \"isUpperLetter\", \"isSpecialSymbol\" FROM auth.users WHERE login = ?";
        String updateQuery = "UPDATE auth.users SET password = ? WHERE login = ?";
        String currentPassword = "";
        boolean isNumber = false;
        boolean isLowerLetter = false;
        boolean isUpperLetter = false;
        boolean isSpecialSymbol = false;

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

        selectStatement.setString(1, login);
        ResultSet resultSet = selectStatement.executeQuery();
        if (resultSet.next()) {
            currentPassword = resultSet.getString("password");
            isNumber = resultSet.getBoolean("isNumber");
            isLowerLetter = resultSet.getBoolean("isLowerLetter");
            isUpperLetter = resultSet.getBoolean("isUpperLetter");
            isSpecialSymbol = resultSet.getBoolean("isSpecialSymbol");
        }
        if (!oldPassword.equals(currentPassword)) {
            return "Неправильный пароль";
        }
        if (isNumber && newPassword.matches(".*[0-9].*")) {
            return "Новый пароль не должен содержать цифр";
        }
        if (isLowerLetter && newPassword.matches(".*[a-z].*")) {
            return "Новый пароль не должен содержать строчных букв";
        }
        if (isUpperLetter && newPassword.matches(".*[A-Z].*")) {
            return "Новый пароль не должен содержать заглавных букв";
        }
        if (isSpecialSymbol && newPassword.matches(".*[\\W_].*")) {
            return "Новый пароль не должен содержать знаков препинания и специальных символов";
        }
        updateStatement.setString(1, newPassword);
        updateStatement.setString(2, login);
        updateStatement.executeUpdate();
        return "Пароль успешно изменен";
    }

    public List<String[]> checkUserList() throws SQLException {
        String selectQuery = "SELECT * FROM auth.users";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = selectStatement.executeQuery();

        List<String[]> userList = new ArrayList<>();

        while (resultSet.next()) {
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            boolean isBlocked = resultSet.getBoolean("isBlocked");
            boolean isNumber = resultSet.getBoolean("isNumber");
            boolean isLowerLetter = resultSet.getBoolean("isLowerLetter");
            boolean isUpperLetter = resultSet.getBoolean("isUpperLetter");
            boolean isSpecialSymbol = resultSet.getBoolean("isSpecialSymbol");
            userList.add(new String[]{
                    login,
                    password,
                    String.valueOf(isBlocked),
                    String.valueOf(isNumber),
                    String.valueOf(isLowerLetter),
                    String.valueOf(isUpperLetter),
                    String.valueOf(isSpecialSymbol)
            });
        }
        return userList;
    }
    public String addUser(String login) throws SQLException {
        String selectQuery = "SELECT login FROM auth.users";
        String insertQuery = "INSERT INTO auth.users(login, password, \"isBlocked\", \"isNumber\", \"isLowerLetter\", \"isUpperLetter\", \"isSpecialSymbol\") " +
                "VALUES (?, '', false, false, false, false, false)";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

        ResultSet resultSet = selectStatement.executeQuery();
        List<String> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(resultSet.getString("login"));
        }
        if (!users.contains(login)) {
            insertStatement.setString(1, login);
            insertStatement.executeUpdate();
            return "Пользователь добавлен";
        } else {
            return "Пользователь с таким именем уже существует";
        }
    }

    public String blockUser(String login) throws SQLException {
        String selectQuery = "SELECT \"isBlocked\" FROM auth.users WHERE login = ?";
        String updateQuery = "UPDATE auth.users SET \"isBlocked\" = ? WHERE login = ?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

        selectStatement.setString(1, login);
        ResultSet resultSet = selectStatement.executeQuery();
        boolean currentBlockStatus;
        boolean blockStatus;
        if (resultSet.next()) {
            currentBlockStatus = resultSet.getBoolean("isBlocked");
        } else {
            return "Такого пользователя не существует";
        }
        blockStatus = !currentBlockStatus;
        updateStatement.setBoolean(1, blockStatus);
        updateStatement.setString(2, login);
        updateStatement.executeUpdate();
        if (blockStatus) {
            return "Пользователь заблокирован";
        } else {
            return "Пользователь разблокирован";
        }
    }

    public String setNumberRestriction(String login) throws SQLException {
        String selectQuery = "SELECT \"isNumber\" FROM auth.users WHERE login = ?";
        String updateQuery = "UPDATE auth.users SET \"isNumber\" = ? WHERE login = ?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

        selectStatement.setString(1, login);
        ResultSet resultSet = selectStatement.executeQuery();
        boolean currentNumberStatus;
        boolean numberStatus;
        if (resultSet.next()) {
            currentNumberStatus = resultSet.getBoolean("isNumber");
        } else {
            return "Такого пользователя не существует";
        }
        numberStatus = !currentNumberStatus;
        updateStatement.setBoolean(1, numberStatus);
        updateStatement.setString(2, login);
        updateStatement.executeUpdate();
        if (numberStatus) {
            return "Ограничение на цифры поставлено";
        } else {
            return "Ограничение на цифры убрано";
        }
    }

    public String setLowerLetterRestriction(String login) throws SQLException {
        String selectQuery = "SELECT \"isLowerLetter\" FROM auth.users WHERE login = ?";
        String updateQuery = "UPDATE auth.users SET \"isLowerLetter\" = ? WHERE login = ?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

        selectStatement.setString(1, login);
        ResultSet resultSet = selectStatement.executeQuery();
        boolean currentLowerLetterStatus;
        boolean lowerLetterStatus;
        if (resultSet.next()) {
            currentLowerLetterStatus = resultSet.getBoolean("isLowerLetter");
        } else {
            return "Такого пользователя не существует";
        }
        lowerLetterStatus = !currentLowerLetterStatus;
        updateStatement.setBoolean(1, lowerLetterStatus);
        updateStatement.setString(2, login);
        updateStatement.executeUpdate();
        if (lowerLetterStatus) {
            return "Ограничение на буквы нижнего регистра поставлено";
        } else {
            return "Ограничение на буквы нижнего регистра убрано";
        }
    }

    public String setUpperLetterRestriction(String login) throws SQLException {
        String selectQuery = "SELECT \"isUpperLetter\" FROM auth.users WHERE login = ?";
        String updateQuery = "UPDATE auth.users SET \"isUpperLetter\" = ? WHERE login = ?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

        selectStatement.setString(1, login);
        ResultSet resultSet = selectStatement.executeQuery();
        boolean currentUpperLetterStatus;
        boolean upperLetterStatus;
        if (resultSet.next()) {
            currentUpperLetterStatus = resultSet.getBoolean("isUpperLetter");
        } else {
            return "Такого пользователя не существует";
        }
        upperLetterStatus = !currentUpperLetterStatus;
        updateStatement.setBoolean(1, upperLetterStatus);
        updateStatement.setString(2, login);
        updateStatement.executeUpdate();
        if (upperLetterStatus) {
            return "Ограничение на буквы верхнего регистра поставлено";
        } else {
            return "Ограничение на буквы верхнего регистра убрано";
        }
    }

    public String setSpecialSymbolRestriction(String login) throws SQLException {
        String selectQuery = "SELECT \"isSpecialSymbol\" FROM auth.users WHERE login = ?";
        String updateQuery = "UPDATE auth.users SET \"isSpecialSymbol\" = ? WHERE login = ?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

        selectStatement.setString(1, login);
        ResultSet resultSet = selectStatement.executeQuery();
        boolean currentSpecialSymbolStatus;
        boolean specialSymbolStatus;
        if (resultSet.next()) {
            currentSpecialSymbolStatus = resultSet.getBoolean("isSpecialSymbol");
        } else {
            return "Такого пользователя не существует";
        }
        specialSymbolStatus = !currentSpecialSymbolStatus;
        updateStatement.setBoolean(1, specialSymbolStatus);
        updateStatement.setString(2, login);
        updateStatement.executeUpdate();
        if (specialSymbolStatus) {
            return "Ограничение на знаки препинания и специальные символы поставлено";
        } else {
            return "Ограничение на знаки препинания и специальные символы убрано";
        }
    }
}
