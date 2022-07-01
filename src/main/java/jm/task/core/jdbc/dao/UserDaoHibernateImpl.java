package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
//        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE Users(" +
                    "id INTEGER NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(64) NOT NULL," +
                    "lastName VARCHAR(64) NOT NULL," +
                    "age SMALLINT NOT NULL," +
                    "PRIMARY KEY(ID))").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
//        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE if exists Users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
//        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
//            String sql = "INSERT INTO Users (name, lastname, age) VALUES (\'" + name + "\', \'" + lastName + "\', \'" + age + "\')";
//            session.createNativeQuery(sql).executeUpdate();
            session.persist(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
//        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User deleteThis = session.get(User.class, id);
            session.delete(deleteThis);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            userList = session.createQuery("From " + User.class.getSimpleName()).list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
//        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
