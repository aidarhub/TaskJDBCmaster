package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;

    public void createUsersTable() {
        Transaction tx = null;
        try {
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(100) NOT NULL, lastName CHAR(100) NOT NULL, age INT NOT NULL, PRIMARY KEY (id))").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    public void dropUsersTable() {
        Transaction tx = null;
        try {
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Transaction tx1 = null;
        session = Util.getSessionFactory().openSession();
        try {
            tx1 = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx1.commit();
        } catch (Exception e) {
            if (tx1 != null) {
                tx1.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void removeUserById(long id) {
        Transaction tx = null;
        try {
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.createQuery("delete User where id = :id").executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }

    public List<User> getAllUsers() {
        Transaction tx = null;
        List userList = null;
        try {
            session = Util.getSessionFactory().openSession();
            tx = session.beginTransaction();
            userList = Util.getSessionFactory().openSession().createQuery("FROM User").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        session = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
                e.printStackTrace();
            }
        } finally {
            session.close();
        }
    }
}
