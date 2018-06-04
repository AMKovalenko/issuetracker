package com.axmor.service;


import java.util.List;

public interface IssueService<Issue> {

    Integer createIssue(Issue issue);

    void deleteIssueByID(Integer id);

    void updateIssue(Issue issue);

    List<Issue> getAllIssues();

    Issue getIssueByID(Integer id);

    boolean setStatusByID(Integer issueID, String status);

}
