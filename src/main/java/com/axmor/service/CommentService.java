package com.axmor.service;

import com.axmor.entity.Comment;

public interface CommentService {

    /**
     * Sets a new comment for given by identifier issue.
     * If comment has a new valid status for issue - issue status will be changed.
     *
     * @param issueID - issue identifier
     * @param comment - new comment for issue
     * @throws Exception
     */
    void addCommentByIssueID(Integer issueID, Comment comment) throws Exception;
}
