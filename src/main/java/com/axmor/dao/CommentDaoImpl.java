package com.axmor.dao;

import com.axmor.entity.Comment;
import com.axmor.entity.Issue;
import com.axmor.service.DBService;

public class CommentDaoImpl implements CommentDao {

    @Override
    public Integer addCommentByIssueID(Integer issueID, Comment comment) {
        Issue issue = new Issue();
        issue.setId(issueID);
        comment.setIssuesByIssueid(issue);

        return (Integer) DBService.getSessionFactory().getCurrentSession().save(comment);
    }

}
