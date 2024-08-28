package ru.aston3.hibernate.db;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import ru.aston3.hibernate.exeption.DataBaseDriverLoadException;
import ru.aston3.hibernate.exeption.RepositoryException;
import ru.aston3.hibernate.model.BankAccount;
import ru.aston3.hibernate.model.BillingDetails;
import ru.aston3.hibernate.model.CreditCard;
import ru.aston3.hibernate.model.Users;
import ru.aston3.hibernate.util.PropertiesUtil;

import java.util.Properties;
import java.sql.SQLException;

public class SessionManagerImpl implements SessionManager {

    private static final String DRIVER_CLASS_KEY = "db.driver-class-name";
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static SessionManager instance;
    private static SessionFactory sessionFactory;

    private SessionManagerImpl() {
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManagerImpl();
            loadDriver(PropertiesUtil.getProperties(DRIVER_CLASS_KEY));
        }
        return instance;
    }

    private static void loadDriver(String driverClass) {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new DataBaseDriverLoadException("Database driver not loaded.");
        }
    }

    @Override
    public SessionFactory getSession() throws SQLException {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, PropertiesUtil.getProperties(DRIVER_CLASS_KEY));
                settings.put(Environment.URL, PropertiesUtil.getProperties(URL_KEY));
                settings.put(Environment.USER, PropertiesUtil.getProperties(USERNAME_KEY));
                settings.put(Environment.PASS, PropertiesUtil.getProperties(PASSWORD_KEY));
                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Users.class);
                configuration.addAnnotatedClass(BillingDetails.class);
                configuration.addAnnotatedClass(BankAccount.class);
                configuration.addAnnotatedClass(CreditCard.class);



                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                return sessionFactory;

            } catch (Exception e) {
                throw new RepositoryException("Database not loaded.");
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
