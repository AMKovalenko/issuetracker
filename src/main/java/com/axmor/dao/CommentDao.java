package com.axmor.dao;

import com.axmor.entity.Comment;

public interface CommentDao {

    Integer addCommentByIssueID(Integer issueID, Comment comment);


}
