package ru.aston3.hibernate;

import org.hibernate.Session;
import ru.aston3.hibernate.db.SessionManager;
import ru.aston3.hibernate.db.SessionManagerImpl;
import ru.aston3.hibernate.model.Users;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        //For testing N+1 and Lazy
        SessionManager sessionManager = SessionManagerImpl.getInstance();
        //Lazy
        try (Session session = sessionManager.getSession().openSession();) {
            Users users = session.get(Users.class, 1);

        }
        try (Session session = sessionManager.getSession().openSession();) {
            List<Users> list = session.createQuery("from Users p join fetch p.billingDetails", Users.class).list();

        }
        //N+1
        try (Session session = sessionManager.getSession().openSession();) {

            List<Users> users = session.createQuery("from Users", Users.class).list();
            for (Users user : users) {
                System.out.println(user.getName() + " have " + user.getBillingDetails().size());
            }

        }
        try (Session session = sessionManager.getSession().openSession();) {
            List<Users> users = session.createQuery("from Users p join fetch p.billingDetails", Users.class).list();

        }
    }
}

