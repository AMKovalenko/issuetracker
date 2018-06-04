package com.axmor.dao;

public abstract class DaoFactory {

    private static volatile IssueDao issueDAOInstance;

    private static volatile CommentDao commentDAOInstance;


    public static IssueDao getIssueDAOInstance() {
        if(issueDAOInstance == null) {
            synchronized(DaoFactory.class) {
                if(issueDAOInstance == null) {
                    issueDAOInstance = new IssueDaoImpl();
                }
            }
        }
        return issueDAOInstance;
    }

    public static CommentDao getCommentDAOInstance() {
        if(commentDAOInstance == null) {
            synchronized(DaoFactory.class) {
                if(commentDAOInstance == null) {
                    commentDAOInstance = new CommentDaoImpl();
                }
            }
        }
        return commentDAOInstance;
    }
}