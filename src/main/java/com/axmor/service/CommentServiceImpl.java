package com.axmor.service;

import com.axmor.dao.CommentDao;
import com.axmor.dao.CommentDaoImpl;
import com.axmor.util.DBService;
import com.axmor.entity.Comment;
import com.axmor.util.LoggingUtils;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;

public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao = new CommentDaoImpl();

    private final static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public void addCommentByIssueID(Integer issueID, Comment comment) throws Exception {

        LoggingUtils.startMethodDebug(logger, "addCommentByIssueID", String.format("IssueID = %s, %s.", issueID, comment.toString()));

        if (logger.isDebugEnabled()){
            logger.debug(String.format("Started method addCommentByIssueID. Input params: %s, %s.", issueID, comment.toString()));
        }
        Transaction transaction = null;
        try {
            transaction = DBService.getTransaction();
            Integer id = commentDao.addCommentByIssueID(issueID, comment);
            if (id == null){
                throw new NoResultException();
            }
            transaction.commit();
            LoggingUtils.finishMethodDebug(logger, "addCommentByIssueID", id);
        } catch (HibernateException e) {
            String message = String.format("Failed attempt to add new comment for issue [ %s ] due to database problem. \n", issueID);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new HibernateException(message);
        } catch (NoResultException e) {
            String message = String.format("There is no one issue with given ID [ %s ] in the database. \n", issueID);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new NoResultException(message);
        } catch (Exception e) {
            String message = String.format("Failed attempt to add new comment for issue [ %s ]. \n", issueID);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new Exception(message);
        }
    }
}
