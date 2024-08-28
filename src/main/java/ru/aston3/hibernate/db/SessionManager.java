package ru.aston3.hibernate.db;

import org.hibernate.SessionFactory;
import java.sql.SQLException;

public interface SessionManager {
     SessionFactory getSession() throws SQLException;
}
