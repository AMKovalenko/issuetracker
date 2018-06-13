package com.axmor.service;

import com.axmor.util.DBService;
import com.axmor.util.DaoFactory;
import com.axmor.dao.IssueDao;
import com.axmor.entity.Issue;
import com.axmor.util.LoggingUtils;
import com.axmor.util.Status;
import com.google.common.base.Enums;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import java.util.List;

public class IssueServiceImpl implements IssueService {

    private IssueDao issueDao = DaoFactory.getIssueDAOInstance();

    private final static Logger logger = LoggerFactory.getLogger(IssueServiceImpl.class);

    @Override
    public Integer createIssue(Issue issue) throws Exception {

        LoggingUtils.startMethodDebug(logger, "createIssue", issue.toString());

        Transaction transaction = null;
        try {
            transaction = DBService.getTransaction();
            Integer id = issueDao.createIssue(issue);
            if (id == null){
                throw new NoResultException();
            }
            transaction.commit();
            LoggingUtils.finishMethodDebug(logger, "createIssue", id);
            return id;
        } catch (HibernateException e) {
            String message = String.format("Failed attempt to create new issue with params [ %s ] due to database problem.\n", issue.toString());
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new HibernateException(message);
        } catch (Exception e) {
            String message = String.format("Failed attempt to create new issue [ %s ].\n", issue.toString());
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new Exception(message);
        }
    }

    @Override
    public void deleteIssueByID(Integer issueId) throws Exception {

        LoggingUtils.startMethodDebug(logger, "deleteIssueByID", issueId);
        Transaction transaction = null;
        try {
            transaction = DBService.getTransaction();
            int result = issueDao.deleteIssueByID(issueId);
            if (result == 0){
                throw new NoResultException();
            }
            transaction.commit();
            LoggingUtils.finishMethodDebug(logger, "deleteIssueByID", null);
        } catch (HibernateException e) {
            String message = String.format("Failed attempt to delete issue by Id [ %s ] due to database problem.\n", issueId);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new HibernateException(message);
        } catch (NoResultException e) {
            String message = String.format("Failed attempt to delete issue by Id [ %s ]. There is no one issue with given ID in the database.", issueId);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new NoResultException(message);
        } catch (Exception e) {
            String message = String.format("Failed attempt to delete issue by Id [ %s ].", issueId);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new Exception(message);
        }
    }

    @Override
    public void updateIssue(Issue issue) throws Exception {

        LoggingUtils.startMethodDebug(logger, "updateIssue", issue.toString());

        Transaction transaction = null;
        try {
            transaction = DBService.getTransaction();
            issueDao.updateIssue(issue);
            transaction.commit();
            LoggingUtils.finishMethodDebug(logger, "updateIssue", null);
        } catch (HibernateException e) {
            String message = String.format("Failed attempt to update issue with params [ %s ] due to database problem.\n", issue.toString());
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new HibernateException(message);
        } catch (NoResultException e) {
            String message = String.format("Failed attempt to update issue with params  [ %s ]. There is no one issue with given ID in the database.", issue.toString());
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new NoResultException(message);
        } catch (Exception e) {
            String message = String.format("Failed attempt to update issue [ %s ].\n", issue.toString());
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new Exception(message);
        }
    }

    @Override
    public List<Issue> getAllIssues() throws Exception {

        LoggingUtils.startMethodDebug(logger, "getAllIssues", null);

        Transaction transaction = null;
        try {
            transaction = DBService.getTransaction();
            List<Issue> result = issueDao.getAllIssues();
            transaction.commit();
            LoggingUtils.finishMethodDebug(logger, "getAllIssues", result);
            return result;
        } catch (HibernateException e) {
            String message = "Failed attempt to get all issues due to database problem.\n";
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new HibernateException(message);
        } catch (Exception e) {
            String message = "Failed attempt to get all issues.";
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new Exception(message);
        }
    }

    @Override
    public Issue getIssueByID(Integer issueId) throws Exception {

        LoggingUtils.startMethodDebug(logger, "getIssueByID", issueId);

        Transaction transaction = null;
        try {
            transaction = DBService.getTransaction();
            Issue result = issueDao.getIssueByID(issueId);
            if (result == null){
                throw new NoResultException();
            }
            transaction.commit();
            LoggingUtils.finishMethodDebug(logger, "getIssueByID", result);
            return result;
        } catch (HibernateException e) {
            String message = String.format("Failed attempt to get issue by Id [ %s ] due to database problem.\n", issueId);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new HibernateException(message);
        } catch (NoResultException e) {
            String message = String.format("Failed attempt to get issue by Id [ %s ]. There is no one issue with given ID in the database.", issueId);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new NoResultException(message);
        } catch (Exception e) {
            String message = String.format("Failed attempt to get issue by Id [ %s ].", issueId);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new Exception(message);
        }
    }

    @Override
    public boolean setStatusByID(Integer issueId, String status) throws Exception {

        LoggingUtils.startMethodDebug(logger, "setStatusByID", String.format("IssueID = %d, Status = %s", issueId, status));

        if (StringUtils.isBlank(status)) {
            return Boolean.FALSE;
        }
        Status newStatus = Enums.getIfPresent(Status.class, status.toUpperCase()).orNull();

        if (newStatus == null){
            throw new IllegalArgumentException(String.format("Unsupported status [%s] has been given.", status));
        }

        Transaction transaction = null;
        try {
            transaction = DBService.getTransaction();
            Issue issue = issueDao.getIssueByID(issueId);
            if (issue == null){
                throw new NoResultException();
            }
            if (Status.isFinalCondition(issue.getState()) || status.equalsIgnoreCase(issue.getState())){
                return Boolean.FALSE;
            }
            issue.setState(newStatus.name());
            issueDao.updateIssue(issue);
            transaction.commit();
            LoggingUtils.finishMethodDebug(logger, "setStatusByID", Boolean.TRUE);
            return Boolean.TRUE;
        } catch (HibernateException e) {
            String message = String.format("Failed attempt to set new status [%s] for Issue by Id [ %s ] due to database problem.\n", status, issueId);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new HibernateException(message);
        } catch (NoResultException e) {
            String message = String.format("Failed attempt to set new status [%s] for Issue by Id [ %s ]. There is no one issue with given ID in the database.", status, issueId);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new NoResultException(message);
        } catch (Exception e) {
            String message = String.format("Failed attempt to set new status [%s] for Issue by Id [ %s ].\n", status, issueId);
            logger.error(message, e);
            DBService.transactionRollback(transaction);
            throw new Exception(message);
        }
    }
}
