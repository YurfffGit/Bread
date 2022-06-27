package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pp_1_1_3", "SSidash", "KataPP113");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
