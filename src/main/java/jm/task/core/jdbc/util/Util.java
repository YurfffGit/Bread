package jm.task.core.jdbc.util;

import jakarta.persistence.EntityGraph;
import jm.task.core.jdbc.model.User;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.stat.Statistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class Util {
    private static Util instance;
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/pp_1_1_3";
    private static final String USER = "SSidash";
    private static final String PASSWORD = "KataPP113";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static SessionFactory sessionFactory;

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

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DRIVER); //Environment
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USER);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка в Утил");
            }
        }
        return sessionFactory;
    }
}
