package ru.aston3.hibernate.repository.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.aston3.hibernate.db.SessionManager;
import ru.aston3.hibernate.db.SessionManagerImpl;
import ru.aston3.hibernate.exeption.RepositoryException;
import ru.aston3.hibernate.model.BillingDetails;
import ru.aston3.hibernate.repository.BillingDetailsRepository;

import java.sql.SQLException;
import java.util.List;

public class BillingDetailsRepositoryImpl implements BillingDetailsRepository {
    private static BillingDetailsRepository instance;
    private final SessionManager sessionManager = SessionManagerImpl.getInstance();

    private BillingDetailsRepositoryImpl() {
    }

    public static synchronized BillingDetailsRepository getInstance() {
        if (instance == null) {
            instance = new BillingDetailsRepositoryImpl();
        }
        return instance;
    }

    @Override
    public BillingDetails save(BillingDetails billingDetails) {
        Transaction transaction = null;
        try (Session session = sessionManager.getSession().openSession();) {
            transaction = session.beginTransaction();
            session.persist(billingDetails);
            transaction.commit();
            return billingDetails;
        } catch (SQLException e) {
            transaction.rollback();
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<BillingDetails> findAll() {
        Transaction transaction = null;
        try (Session session = sessionManager.getSession().openSession();) {
            transaction = session.beginTransaction();/*from Person p join fetch p.cars*/
           /* select bd from BillingDetails bd*/
            List<BillingDetails> list = session.createQuery("from BillingDetails b join fetch ").list();
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
            BillingDetails billingDetails = session.get(BillingDetails.class, id);
            if (billingDetails != null) {
                session.delete(billingDetails);
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
