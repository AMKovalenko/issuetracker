package com.axmor.service;


import com.axmor.entity.Issue;

import java.util.List;

public interface IssueService {

    /**
     * Method for saving new issue into database.
     * @param issue - new instance of Issue for saving.
     * @return - new issue identifier.
     * @throws Exception
     */
    Integer createIssue(Issue issue) throws Exception;

    /**
     * Method for remove the issue by given identifier.
     * @param id - removable issue identifier.
     * @throws Exception
     */
    void deleteIssueByID(Integer id) throws Exception;

    /**
     * Method for modifying issue.
     * Issue must have a valid identifier.
     *
     * @param issue - instanse of issue with new params.
     * @throws Exception
     */
    void updateIssue(Issue issue) throws Exception;

    /**
     * Returns all presented in the database issues.
     *
     * @throws Exception
     */
    List<Issue> getAllIssues() throws Exception;

    /**
     * Returns the issue by given identifier.
     *
     * @param id - issue identifier.
     * @throws Exception
     */
    Issue getIssueByID(Integer id) throws Exception;

    /**
     * Sets a new status for issue by given identifier.
     * Validates the given status if it's correct (if no - throws IllegalArgumentException.)
     * Does nothing if the issue already has the same status or if issue already has final status.
     *
     * @param issueID - issue identifier.
     * @param status - new issue status.
     * @return -  TRUE if status has been changed during the method execution. If no - returns FALSE
     * @throws Exception
     */
    boolean setStatusByID(Integer issueID, String status) throws Exception;

}
