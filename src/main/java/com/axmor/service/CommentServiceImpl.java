package com.axmor.service;

import com.axmor.dao.CommentDao;
import com.axmor.dao.DaoFactory;
import com.axmor.entity.Comment;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;

public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao = DaoFactory.getCommentDAOInstance();

    @Override
    public Integer addCommentByIssueID(Integer issueID, Comment comment) {
        Transaction transaction = DBService.getTransaction();
        try {
            Integer id = commentDao.addCommentByIssueID(issueID, comment);
            transaction.commit();
            return id;
        } catch (HibernateException | NoResultException e) {
            DBService.transactionRollback(transaction);
            throw e;
        }
    }
}
