package ru.aston3.hibernate.repository.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.aston3.hibernate.db.SessionManager;
import ru.aston3.hibernate.db.SessionManagerImpl;
import ru.aston3.hibernate.exeption.RepositoryException;
import ru.aston3.hibernate.model.CreditCard;
import ru.aston3.hibernate.repository.CreditCardRepository;

import java.sql.SQLException;
import java.util.List;

public class CreditCardRepositoryImpl implements CreditCardRepository {

    private static CreditCardRepository instance;
    private final SessionManager sessionManager = SessionManagerImpl.getInstance();

    private CreditCardRepositoryImpl() {
    }

    public static synchronized CreditCardRepository getInstance() {
        if (instance == null) {
            instance = new CreditCardRepositoryImpl();
        }
        return instance;
    }


    @Override
    public CreditCard save(CreditCard creditCard) {
        Transaction transaction = null;
        try (Session session = sessionManager.getSession().openSession();) {
            transaction = session.beginTransaction();
            session.persist(creditCard);
            transaction.commit();
            return creditCard;
        } catch (SQLException e) {
            transaction.rollback();
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<CreditCard> findAll() {
        Transaction transaction = null;
        try (Session session = sessionManager.getSession().openSession();) {
            transaction = session.beginTransaction();
            List<CreditCard> list = session.createQuery("select bd from CreditCard bd").list();
            transaction.commit();
            return list;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        Transaction transaction = null;
        try (Session session = sessionManager.getSession().openSession();) {
            transaction = session.beginTransaction();
            CreditCard creditCard = session.get(CreditCard.class, id);
            if (creditCard != null) {
                session.delete(creditCard);
                transaction.commit();
                return true;
            }
            transaction.commit();
            return false;
        } catch (SQLException e) {
            transaction.rollback();
            throw new RepositoryException(e);
        }
    }
}
