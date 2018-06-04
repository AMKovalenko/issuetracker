package com.axmor;

import com.axmor.controllers.IssueController;
import com.axmor.service.CommentService;
import com.axmor.service.CommentServiceImpl;
import com.axmor.service.IssueService;
import com.axmor.service.IssueServiceImpl;
import com.axmor.util.Path;

import static spark.Spark.*;

/**
 * Application entry point
 */
public class Main {

    public static IssueService issueService = null;

    public static CommentService commentService = null;

    public static void main(String[] args) {

        issueService = new IssueServiceImpl();
        commentService = new CommentServiceImpl();

        port(8080);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);

        get(Path.Web.ISSUES,            IssueController.fetchAllIssues);
        post(Path.Web.ISSUE,            IssueController.createIssue);
        get(Path.Web.ISSUE,             IssueController.createIssuePage);
        get(Path.Web.ONE_ISSUE,         IssueController.fetchOneBook);
        post(Path.Web.ADD_COMMENT,      IssueController.addCommentToIssue);

    }
}
