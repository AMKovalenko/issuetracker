package com.axmor.controllers;


import com.axmor.util.LoginUtils;
import com.axmor.util.Path;
import com.axmor.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static com.axmor.Application.userService;
import static com.axmor.util.RequestUtil.*;

public class LoginController {

    public static Route serveLoginPage = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        model.put("registerSucceeded", request.session().attribute("registerSucceeded"));
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLoginPost = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        boolean isAuthenticated;
        try {
           isAuthenticated = userService.authenticateUser(getQueryUsername(request), getQueryPassword(request));
        }catch (Exception e){
            model.put("authenticationFailedMessage", e.getMessage());
            return ViewUtil.render(request, model, Path.Template.LOGIN);
        }

        if (!isAuthenticated) {
            model.put("authenticationFailed", true);
            return ViewUtil.render(request, model, Path.Template.LOGIN);
        }
        LoginUtils.putAuthentificatedUserToCache(getQueryUsername(request));

        request.session().attribute("currentUser", getQueryUsername(request));
        response.redirect(Path.Web.ISSUES);
        return null;
    };

    public static Route handleLogoutPost = (Request request, Response response) -> {

        LoginUtils.logoutUser(getSessionCurrentUser(request));
        request.session().removeAttribute("currentUser");
        request.session().removeAttribute("registerSucceeded");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.LOGIN);

        return null;
    };

    public static Route serveRegisterPage = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("registerRedirect", Path.Web.LOGIN );

        return ViewUtil.render(request, model, Path.Template.REGISTER);
    };

    public static Route handleRegisterPost = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        Integer userID;
        try{
            userID = userService.createUser(getQueryUsername(request), getQueryPassword(request));
        }catch (Exception e){
            model.put("registerFailedMessage", e.getMessage());
            return ViewUtil.render(request, model, Path.Template.REGISTER);
        }

        if ( userID == null) {
            model.put("registerFailed", true);
            return ViewUtil.render(request, model, Path.Template.REGISTER);
        }
        request.session().attribute("registerSucceeded", true);
        request.session().attribute("currentUser", getQueryUsername(request));
        response.redirect(Path.Web.LOGIN);

        return null;
    };


    public static void ensureUserIsLoggedIn(Request request, Response response) {

        if (!LoginUtils.isUserAuthentificated(getSessionCurrentUser(request))) {
            response.redirect(Path.Web.LOGIN);
        }
    };
}
