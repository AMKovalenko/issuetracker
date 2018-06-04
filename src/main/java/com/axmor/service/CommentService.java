package com.axmor.service;

import com.axmor.entity.Comment;

public interface CommentService {

    Integer addCommentByIssueID(Integer issueID, Comment comment);
}
