package com.axmor.dao;

import com.axmor.entity.Comment;
import com.axmor.entity.Issue;
import com.axmor.util.DBService;
import com.axmor.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommentDaoImpl implements CommentDao {

    private final static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public Integer addCommentByIssueID(Integer issueID, Comment comment) throws Exception {

        LoggingUtils.startMethodDebug(logger, "addCommentByIssueID", String.format("IssueID - %d, comment - %s", issueID, comment.toString()));

        Issue issue = new Issue();
        issue.setId(issueID);
        comment.setIssuesByIssueid(issue);

        Integer commentId = (Integer) DBService.getSessionFactory().getCurrentSession().save(comment);

        LoggingUtils.finishMethodDebug(logger, "addCommentByIssueID", "New comment identifier = " + commentId);

        return commentId;
    }

}
