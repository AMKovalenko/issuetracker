package com.axmor.dao;


import com.axmor.entity.Issue;

import java.util.List;

public interface IssueDao {

    Integer createIssue(Issue o);

    Issue deleteIssueByID(Integer id);

    void updateIssue(Issue issue);

    List<Issue> getAllIssues();

    Issue getIssueByID(Integer id);

}
