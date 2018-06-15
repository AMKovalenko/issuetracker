package com.axmor.util;

public class Path {
    /**
     * Storage for paths to templates for rendering pages.
     */
    public interface Template {
        String ISSUES_ALL = "velocity/issue/all.vm";
        String ISSUE = "velocity/issue/create.vm";
        String ISSUES_ONE = "velocity/issue/one.vm";
        String NOT_FOUND = "/velocity/notFound.vm";
        String LOGIN = "/velocity/login/login.vm";
        String REGISTER = "/velocity/login/register.vm";
    }

    /**
     * Storage for paths for creation links.
     */
    public interface Web {
        String INDEX = "/";
        String LOGIN = "/login/";
        String REGISTER = "/register/";
        String LOGOUT = "/logout/";
        String ISSUES = "/issues/";
        String ISSUE = "/issue/";
        String ONE_ISSUE = "/issue/:id";
        String DEL_ISSUE = "/issue/:id";
        String ADD_COMMENT = "/issues/comment/:id";
    }
}
