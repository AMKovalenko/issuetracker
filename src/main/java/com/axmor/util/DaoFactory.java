package com.axmor.util;

import com.axmor.dao.*;

public abstract class DaoFactory {

    private static volatile IssueDao issueDAOInstance;

    private static volatile CommentDao commentDAOInstance;

    private static volatile UserDao userDAOInstance;

    /**
     *
     * @return single instance of IssueDao.
     */
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

    /**
     *
     * @return single instance of CommentDao.
     */
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

    /**
     *
     * @return single instance of UserDao.
     */
    public static UserDao getUserDaoInstance() {
        if(userDAOInstance == null) {
            synchronized(DaoFactory.class) {
                if(userDAOInstance == null) {
                    userDAOInstance = new UserDaoImpl();
                }
            }
        }
        return userDAOInstance;
    }

}