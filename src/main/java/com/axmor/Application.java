package com.axmor;

import com.axmor.controllers.IssueController;
import com.axmor.controllers.LoginController;
import com.axmor.service.*;

import com.axmor.util.Path;
import com.axmor.util.ViewUtil;


import static spark.Spark.*;

/**
 * Application entry point
 */
public class Application {

    public static IssueService issueService = null;
    public static CommentService commentService = null;
    public static UserService userService = null;

    public static void main(String[] args) {

        issueService = new IssueServiceImpl();
        commentService = new CommentServiceImpl();
        userService = new UserServiceImpl();

        port(8080);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);

        redirect.get(Path.Web.INDEX,    Path.Web.LOGIN);

        get(Path.Web.ISSUES,            IssueController.fetchAllIssues);
        post(Path.Web.ISSUE,            IssueController.createIssue);
        get(Path.Web.ISSUE,             IssueController.createIssuePage);
        get(Path.Web.ONE_ISSUE,         IssueController.fetchOneIssue);
        post(Path.Web.ADD_COMMENT,      IssueController.addCommentToIssue);
        delete(Path.Web.DEL_ISSUE,      IssueController.deleteIssue);

        get(Path.Web.LOGIN,             LoginController.serveLoginPage);
        post(Path.Web.LOGIN,            LoginController.handleLoginPost);
        post(Path.Web.LOGOUT,           LoginController.handleLogoutPost);

        get(Path.Web.REGISTER,          LoginController.serveRegisterPage);
        post(Path.Web.REGISTER,         LoginController.handleRegisterPost);

        get("*",                     ViewUtil.notFound);

    }
}
