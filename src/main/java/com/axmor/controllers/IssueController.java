package com.axmor.controllers;


import com.axmor.entity.Comment;
import com.axmor.entity.Issue;
import com.axmor.util.Status;
import com.axmor.util.Path;
import com.axmor.util.RequestUtil;
import com.axmor.util.ViewUtil;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.axmor.Application.commentService;
import static com.axmor.Application.issueService;


public class IssueController {

    public static Route fetchAllIssues= (request, response) -> {

        LoginController.ensureUserIsLoggedIn(request, response);

        Map<String, Object> model = new HashMap<>();

        List<Issue> issueList = null;
        try {
            issueList = issueService.getAllIssues();
        }catch (Exception e){
            model.put("failedMessage", e.getMessage());
        }

        model.put("issues", issueList);
        return ViewUtil.render(request, model, Path.Template.ISSUES_ALL);
    };


    public static Route deleteIssue= (Request request, Response response) -> {

        LoginController.ensureUserIsLoggedIn(request, response);
        Integer issueID = Integer.valueOf(RequestUtil.getParamIssueId(request));

        issueService.deleteIssueByID(issueID);

        response.redirect(Path.Web.ISSUES);
        // не рендерится новая страница после удаления, если вызвать редирект : response.redirect(Path.Web.ISSUES);
        // также не рендерится, если вернуть return fetchAllIssues.handle(request, response);
        // скорее всего это связано со спецификой метода delete. Пока реализовал костыльное решение для рендеринга новой страницы после
        // удаления issue (его можно увидеть в velocity/issue/all.vm).
        return null;
    };

    public static Route fetchOneIssue = (Request request, Response response) -> {


        LoginController.ensureUserIsLoggedIn(request, response);
        Integer issueID = Integer.valueOf(RequestUtil.getParamIssueId(request));

        Map<String, Object> model = getIssueModelById(issueID);
        return ViewUtil.render(request, model, Path.Template.ISSUES_ONE);
    };


    public static Route addCommentToIssue = (Request request, Response response) -> {

        LoginController.ensureUserIsLoggedIn(request, response);

        Comment comment = new Comment();
        comment.setAuthor(RequestUtil.getParamAuthor(request));
        comment.setText(RequestUtil.getParamCommentText(request));
        comment.setCreationdate(new Date());

        Integer issueID = Integer.valueOf(RequestUtil.getParamIssueId(request));
        String transition = RequestUtil.getParamTransition(request);

        try {
            if (issueService.setStatusByID(issueID, transition)){
                comment.setTransition(transition);
            }
            commentService.addCommentByIssueID(issueID, comment);
        }catch (Exception e){
            Map<String, Object> model = getIssueModelById(issueID);
            model.put("failedMessage", e.getMessage());
            return ViewUtil.render(request, model, Path.Template.ISSUES_ONE);
        }
        return fetchAllIssues.handle(request, response);
    };

    public static Route createIssue = (Request request, Response response) -> {

        LoginController.ensureUserIsLoggedIn(request, response);
        Issue newIssue = new Issue();
        newIssue.setState(Status.CREATED.name());
        newIssue.setCreationdate(new Date());
        newIssue.setAuthor(RequestUtil.getParamAuthor(request));
        newIssue.setTitle(RequestUtil.getParamTitle(request));
        newIssue.setDescription(RequestUtil.getParamDescription(request));

        try {
            issueService.createIssue(newIssue);
        }catch (Exception e){
            Map<String, Object> model = new HashMap<>();
            model.put("failedMessage", e.getMessage());
            return ViewUtil.render(request, model, Path.Template.ISSUE);
        }

        return fetchAllIssues.handle(request, response);
    };

    public static Route createIssuePage = (Request request, Response response) -> {

        LoginController.ensureUserIsLoggedIn(request, response);
        return ViewUtil.render(request, new HashMap<>(), Path.Template.ISSUE);
    };

    private static Map<String, Object> getIssueModelById(Integer issueID){
        HashMap<String, Object> model = new HashMap<>();
        Issue issue = null;
        try {
            issue = issueService.getIssueByID(issueID);
        }catch (Exception e){
            model.put("failedMessage", e.getMessage());
        }
        model.put("issue", issue);
        return model;
    }

}
