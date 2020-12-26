package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDaoHibernateImpl user1 = new UserDaoHibernateImpl();
        UserServiceImpl user2 = new UserServiceImpl();
        user1.createUsersTable();

        List<User> list = new ArrayList<>();
        list.add(new User("Igor", "Cherenov", (byte) 2));
        list.add(new User("Alex", "White", (byte) 3));
        list.add(new User("Jhon", "Lenom", (byte) 8));
        list.add(new User("Max", "Nova", (byte) 2));

        for (User user : list) {
            String name = user.getName();
            String lastName = user.getLastName();
            user2.saveUser(name, lastName, user.getAge());
            System.out.printf("User с именем – %s, добавлен в базу данных\n", name);
        }

        List<User> users = user1.getAllUsers();
        users.forEach(System.out::println);

        user2.cleanUsersTable();
        user2.dropUsersTable();
    }
}
