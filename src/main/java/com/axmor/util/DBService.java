package com.axmor.util;

import com.axmor.entity.Comment;
import com.axmor.entity.Issue;
import com.axmor.entity.User;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class DBService {

    private static SessionFactory sessionFactory;

    private final static Logger logger = LoggerFactory.getLogger(DBService.class);

    public static SessionFactory getSessionFactory() throws Exception {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Issue.class);
                configuration.addAnnotatedClass(Comment.class);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                StandardServiceRegistry registry = builder.build();
                sessionFactory = configuration.buildSessionFactory(registry);
            } catch (Exception e) {
                logger.error("Failed creation session factory. \n", e);
                throw new SessionException("Session factory creation failed.\n" + e.getMessage());
            }
        }
        return sessionFactory;
    }

    public static Transaction getTransaction() throws Exception{

        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = getSessionFactory().getCurrentSession().getTransaction();
        if (transaction == null){
            throw new TransactionException("Transaction creation failed.");
        }
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction = session.beginTransaction();
        }
        return transaction;
    }

    public static void transactionRollback(Transaction transaction){
        if (transaction == null)
            return;
        if (transaction.getStatus() == TransactionStatus.ACTIVE
                || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
            transaction.rollback();
        }
    }

}
