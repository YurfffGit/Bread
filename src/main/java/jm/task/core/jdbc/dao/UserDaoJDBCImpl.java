package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.model.User;

import java.sql.Statement;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            Statement createUsersTable = Util.connection.createStatement();
            createUsersTable.executeUpdate("CREATE TABLE Users(" +
                    "id INTEGER NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(64) NOT NULL," +
                    "lastName VARCHAR(64) NOT NULL," +
                    "age SMALLINT NOT NULL," +
                    "PRIMARY KEY(ID))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            Statement dropUsersTable = Util.connection.createStatement();
            dropUsersTable.executeUpdate("DROP TABLE if exists Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement saveUser = Util.connection.prepareStatement("INSERT INTO Users VALUES(DEFAULT, ?, ?, ?)");
            saveUser.setString(1, name);
            saveUser.setString(2, lastName);
            saveUser.setByte(3, age);
            saveUser.execute();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement removeUser = Util.connection.prepareStatement("DELETE FROM Users WHERE ID = ?");
            removeUser.setLong(1, id);
            removeUser.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            Statement getAll = Util.connection.createStatement();
            ResultSet resultSet = getAll.executeQuery("SELECT * FROM Users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            Statement cleanUsers = Util.connection.createStatement();
            cleanUsers.executeUpdate("TRUNCATE TABLE Users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
