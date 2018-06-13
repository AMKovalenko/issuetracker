package com.axmor.util;

import spark.Request;

public class RequestUtil {

    public static String getParamIssueId(Request request) {
        return request.params("id");
    }

    public static String getParamCommentText(Request request) {
        return request.queryParams("Text");
    }

    public static String getParamTransition(Request request) {
        return request.queryParams("Transition");
    }

    public static String getParamDescription(Request request) {
        return request.queryParams("Description");
    }

    public static String getParamTitle(Request request) {
        return request.queryParams("Title");
    }

    public static String getParamAuthor(Request request) {
        return request.queryParams("Author");
    }

    public static String getQueryUsername(Request request) {
        return request.queryParams("username");
    }

    public static String getQueryPassword(Request request) {
        return request.queryParams("password");
    }

    public static String getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }

    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }
}
