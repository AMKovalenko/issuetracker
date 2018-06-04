package com.axmor.controllers;


import com.axmor.entity.Comment;
import com.axmor.entity.Issue;
import com.axmor.entity.Status;
import com.axmor.util.Path;
import com.axmor.util.RequestUtil;
import com.axmor.util.ViewUtil;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Date;
import java.util.HashMap;

import static com.axmor.Main.commentService;
import static com.axmor.Main.issueService;

public class IssueController {

//    private s IssueService issueService = new IssueServiceImpl();

    public static Route fetchAllIssues= (Request request, Response response) -> {
//        LoginController.ensureUserIsLoggedIn(request, response);

        HashMap<String, Object> model = new HashMap<>();
        model.put("issues", issueService.getAllIssues());
        return ViewUtil.render(request, model, Path.Template.ISSUES_ALL);


    };

    public static Route fetchOneBook = (Request request, Response response) -> {
//        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        Integer issueID = Integer.valueOf(RequestUtil.getParamIssueId(request));
        model.put("issue", issueService.getIssueByID(issueID));
        return ViewUtil.render(request, model, Path.Template.ISSUES_ONE);
//        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route addCommentToIssue = (Request request, Response response) -> {
//        LoginController.ensureUserIsLoggedIn(request, response);

        Comment comment = new Comment();
        comment.setAuthor(RequestUtil.getParamAuthor(request));
        comment.setText(RequestUtil.getParamCommentText(request));
        comment.setCreationdate(new Date());

        Integer issueID = Integer.valueOf(RequestUtil.getParamIssueId(request));
        String transition = RequestUtil.getParamTransition(request);
        if ( issueService.setStatusByID(issueID, transition) ){
            comment.setTransition(transition);
        }

        commentService.addCommentByIssueID(issueID, comment);
        HashMap<String, Object> model = new HashMap<>();
        model.put("issue", issueService.getIssueByID(issueID));
        return ViewUtil.render(request, model, Path.Template.ISSUES_ONE);
//        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route createIssue = (Request request, Response response) -> {
//        LoginController.ensureUserIsLoggedIn(request, response);
        Issue newIssue = new Issue();
        newIssue.setState(Status.CREATED.name());
        newIssue.setCreationdate(new Date());
        newIssue.setAuthor(RequestUtil.getParamAuthor(request));
        newIssue.setTitle(RequestUtil.getParamTitle(request));
        newIssue.setDescription(RequestUtil.getParamDescription(request));
        Integer issueID = issueService.createIssue(newIssue);

        HashMap<String, Object> model = new HashMap<>();
        model.put("issue", issueService.getIssueByID(issueID));
        return ViewUtil.render(request, model, Path.Template.ISSUES_ONE);
//        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route createIssuePage = (Request request, Response response) -> {
//        LoginController.ensureUserIsLoggedIn(request, response);
        return ViewUtil.render(request, new HashMap<>(), Path.Template.ISSUE);
//        return ViewUtil.notAcceptable.handle(request, response);
    };


}
