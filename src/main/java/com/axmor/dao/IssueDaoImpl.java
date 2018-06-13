package com.axmor.dao;

import com.axmor.entity.Issue;
import com.axmor.util.DBService;
import com.axmor.util.LoggingUtils;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class IssueDaoImpl implements IssueDao {

    private final static Logger logger = LoggerFactory.getLogger(IssueDaoImpl.class);

    @Override
    public Integer createIssue(Issue issue) throws Exception {

        LoggingUtils.startMethodDebug(logger, "createIssue", issue.toString());

        Integer issueId = (Integer) DBService.getSessionFactory().getCurrentSession().save(issue);

        LoggingUtils.finishMethodDebug(logger, "createIssue", issueId);
        return issueId;
    }

    @Override
    public int deleteIssueByID(Integer issueID) throws Exception {

        LoggingUtils.startMethodDebug(logger, "deleteIssueByID", issueID);

        int count = DBService.getSessionFactory().getCurrentSession().createQuery("delete from Issue where id =:issueId").
               setParameter("issueId", issueID).
               executeUpdate();

        LoggingUtils.finishMethodDebug(logger, "deleteIssueByID", String.format("%d issue(s) has been removed.", count));
        return count;
    }

    @Override
    public void updateIssue(Issue issue) throws Exception {

        LoggingUtils.startMethodDebug(logger, "updateIssue", issue.toString());

        DBService.getSessionFactory().getCurrentSession().update(issue);

        LoggingUtils.finishMethodDebug(logger, "updateIssue", null);
    }

    @Override
    public List<Issue> getAllIssues() throws Exception {

        LoggingUtils.startMethodDebug(logger, "getAllIssues", null);

        List<Issue> issueList = DBService.getSessionFactory().getCurrentSession().createQuery("from Issue ").list();

        LoggingUtils.finishMethodDebug(logger, "getAllIssues", "Issues count - " + issueList.size());
        return issueList;
    }

    @Override
    public Issue getIssueByID(Integer issueID) throws Exception {

        LoggingUtils.startMethodDebug(logger, "getIssueByID", issueID);

        Issue issue =  DBService.getSessionFactory()
                        .getCurrentSession()
                        .get(Issue.class, issueID, LockMode.PESSIMISTIC_READ);

        LoggingUtils.finishMethodDebug(logger, "getIssueByID", issue.toString());
        return issue;
    }
}
