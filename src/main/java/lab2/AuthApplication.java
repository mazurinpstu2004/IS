package lab2;

import java.sql.SQLException;

public class AuthApplication {
    public static void main(String[] args) throws SQLException {
        AuthGUI application = new AuthGUI();
        application.startWindow();
    }
}
