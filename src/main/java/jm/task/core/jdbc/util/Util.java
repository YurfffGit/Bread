package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Util instance;
    private Connection connection;
    private final static String URL = "jdbc:mysql://localhost:3306/pp_1_1_3";
    private final static String USER = "SSidash";
    private final static String PASSWORD = "KataPP113";

    private Util() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Соединение не удалось");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Util getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Util();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }


    
}
