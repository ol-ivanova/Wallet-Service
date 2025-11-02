package org.example.wallet_service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    /** ключ для получения значения пароля в объекте application.properties
     */
    private static final String PASSWORD_KEY = "db.password";
    /** ключ для получения значения логина в объекте application.properties
     */
    private static final String USERNAME_KEY = "db.username";
    /**
     * ключ для получения значения url в объекте application.properties
     */
    private static final String URL_KEY = "db.url";
    private ConnectionManager() {
    }

    static {
        loadDriver();
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
