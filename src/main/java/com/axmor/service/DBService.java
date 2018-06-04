package com.axmor.service;

import com.axmor.entity.Comment;
import com.axmor.entity.Issue;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;


public abstract class DBService {


    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Issue.class);
                configuration.addAnnotatedClass(Comment.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                StandardServiceRegistry registry = builder.build();
                sessionFactory = configuration.buildSessionFactory(registry);
            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

    public static Transaction getTransaction(){
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = getSessionFactory().getCurrentSession().getTransaction();
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            transaction = session.beginTransaction();
        }
        return transaction;
    }

    public static void transactionRollback(Transaction transaction){
        if (transaction.getStatus() == TransactionStatus.ACTIVE
                || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
            transaction.rollback();
        }
    }

}
