package com.axmor.service;



import com.axmor.dao.DaoFactory;
import com.axmor.dao.IssueDao;
import com.axmor.entity.Issue;
import com.axmor.entity.Status;
import com.google.common.base.Enums;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public class IssueServiceImpl implements IssueService<Issue> {

    private IssueDao issueDao = DaoFactory.getIssueDAOInstance();

    @Override
    public Integer createIssue(Issue issue) {

        Transaction transaction = DBService.getTransaction();
        try {
            Integer id = issueDao.createIssue(issue);
            transaction.commit();
            return id;
        } catch (HibernateException | NoResultException e) {
            DBService.transactionRollback(transaction);
            throw e;
        }
    }

    @Override
    public void deleteIssueByID(Integer issueId) {

        Transaction transaction = DBService.getTransaction();
        try {
            issueDao.deleteIssueByID(issueId);
            transaction.commit();
        } catch (HibernateException | NoResultException e) {
            DBService.transactionRollback(transaction);
            throw e;
        }
    }

    @Override
    public void updateIssue(Issue issue) {

        Transaction transaction = DBService.getTransaction();
        try {
            issueDao.updateIssue(issue);
            transaction.commit();
        } catch (HibernateException | NoResultException e) {
            DBService.transactionRollback(transaction);
            throw e;
        }
    }

    @Override
    public List<Issue> getAllIssues() {
        Transaction transaction = DBService.getTransaction();
        try {
            List<Issue> result = issueDao.getAllIssues();
            transaction.commit();
            return result;
        } catch (HibernateException | NoResultException e) {
            DBService.transactionRollback(transaction);
            throw e;
        }
    }

    @Override
    public Issue getIssueByID(Integer id) {

        Transaction transaction = DBService.getTransaction();
        try {
            Issue result = issueDao.getIssueByID(id);
            transaction.commit();
            return result;
        } catch (HibernateException | NoResultException e) {
            DBService.transactionRollback(transaction);
            throw e;
        }
    }

    @Override
    public boolean setStatusByID(Integer issueID, String status) {

        if (StringUtils.isBlank(status)) {
            return Boolean.FALSE;
        }
        Status newStatus = Enums.getIfPresent(Status.class, status.toUpperCase()).orNull();
        if (newStatus == null){
            throw new IllegalArgumentException("Unsupported status [" + status + "] has been given.");
        }
        Transaction transaction = DBService.getTransaction();
        try {
            Issue issue = issueDao.getIssueByID(issueID);
            if (status.equalsIgnoreCase(issue.getState())){
                return Boolean.FALSE;
            }
            issue.setState(newStatus.name());
            issueDao.updateIssue(issue);
            transaction.commit();
            return Boolean.TRUE;
        } catch (HibernateException | NoResultException e) {
            DBService.transactionRollback(transaction);
            throw e;
        }
    }
}
