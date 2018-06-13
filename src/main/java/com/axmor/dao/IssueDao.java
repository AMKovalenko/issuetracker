package com.axmor.dao;


import com.axmor.entity.Issue;

import java.util.List;

public interface IssueDao {

    Integer createIssue(Issue o) throws Exception;

    int deleteIssueByID(Integer id) throws Exception;

    void updateIssue(Issue issue) throws Exception;

    List<Issue> getAllIssues() throws Exception;

    Issue getIssueByID(Integer id) throws Exception;

}
