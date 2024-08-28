package ru.aston3.hibernate.repository.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ru.aston3.hibernate.db.SessionManager;
import ru.aston3.hibernate.db.SessionManagerImpl;
import ru.aston3.hibernate.exeption.RepositoryException;
import ru.aston3.hibernate.model.BankAccount;
import ru.aston3.hibernate.repository.BankAccountRepository;

import java.sql.SQLException;
import java.util.List;

public class BankAccountRepositoryImpl implements BankAccountRepository {


    private static BankAccountRepository instance;
    private final SessionManager sessionManager = SessionManagerImpl.getInstance();

    private BankAccountRepositoryImpl() {
    }

    public static synchronized BankAccountRepository getInstance() {
        if (instance == null) {
            instance = new BankAccountRepositoryImpl();
        }
        return instance;
    }

    @Override
    public BankAccount save(BankAccount bankAccount) {
        Transaction transaction = null;
        try (Session session = sessionManager.getSession().openSession();) {
            transaction = session.beginTransaction();
            session.persist(bankAccount);
            transaction.commit();
            return bankAccount;
        } catch (SQLException e) {
            transaction.rollback();
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<BankAccount> findAll() {
            Transaction transaction = null;
            try (Session session = sessionManager.getSession().openSession();) {
                transaction = session.beginTransaction();
                List<BankAccount> list = session.createQuery("select ba from BankAccount ba").list();
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
            BankAccount bankAccount = session.get(BankAccount.class, id);
            if (bankAccount != null) {
                session.delete(bankAccount);
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
