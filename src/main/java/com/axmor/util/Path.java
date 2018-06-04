package com.axmor.util;

public class Path {

    public interface Template {
        String ISSUES_ALL = "velocity/issue/all.vm";
        String ISSUE = "velocity/issue/create.vm";
        String ISSUES_ONE = "velocity/issue/one.vm";
        String NOT_FOUND = "/velocity/notFound.vm";
    }

    public static class Web {

//        public static final String INDEX = "/index/";
//        public static final String LOGIN = "/login/";
//        public static final String LOGOUT = "/logout/";
        public static final String ISSUES = "/issues/";
        public static final String ISSUE = "/issue/";
        public static final String ONE_ISSUE = "/issues/:id";
        public static final String ADD_COMMENT = "/issues/comment/:id";

//        public String getINDEX(){
//            return INDEX;
//        }
//
//        public String getLOGIN(){
//            return LOGIN;
//        }
//        public String getLOGOUT(){
//            return LOGOUT;
//        }
        public String getIssues(){
            return ISSUES;
        }
//        public String getONE_BOOK(){
//
//            return ONE_BOOK;
//        }
    }
}
