package com.axmor.dao;

import com.axmor.entity.Issue;
import com.axmor.service.DBService;
import org.hibernate.LockMode;

import java.util.List;

public class IssueDaoImpl implements IssueDao {

    @Override
    public Integer createIssue(Issue issue) {

        return (Integer) DBService.getSessionFactory().getCurrentSession().save(issue);
    }

    @Override
    public Issue deleteIssueByID(Integer issueID)  {
       Issue issue = new Issue();
       issue.setId(issueID);
       DBService.getSessionFactory().getCurrentSession().delete(issue);
       return issue;
    }

    @Override
    public void updateIssue(Issue issue) {
        DBService.getSessionFactory().getCurrentSession().update(issue);
    }

    @Override
    public List<Issue> getAllIssues() {

        return DBService.getSessionFactory().getCurrentSession().createQuery("from Issue ").list();
    }

    @Override
    public Issue getIssueByID(Integer id) {
        return DBService.getSessionFactory()
                .getCurrentSession()
                .get(Issue.class, id, LockMode.PESSIMISTIC_READ);
    }
}
