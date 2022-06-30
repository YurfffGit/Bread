package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE Users(" +
                    "id INTEGER NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(64) NOT NULL," +
                    "lastName VARCHAR(64) NOT NULL," +
                    "age SMALLINT NOT NULL," +
                    "PRIMARY KEY(ID))").executeUpdate();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            throw new RuntimeException("Ошибка 1");
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createNativeQuery("DROP TABLE if exists Users");
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            throw new RuntimeException("Ошибка 2");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User saveThis = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(saveThis);
            transaction.commit();
        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
