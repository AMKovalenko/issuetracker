package com.axmor.util;

import org.apache.velocity.app.*;
import org.eclipse.jetty.http.HttpStatus;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.velocity.*;

import java.util.HashMap;
import java.util.Map;

import static com.axmor.util.RequestUtil.getSessionCurrentUser;


public class ViewUtil {

    /**
     * Renders a template given a model and a request.
     * The request is needed to see if the user is logged in.
     *
     * @param request - instance of Request class. The storage for session with current user information
     * @param model - storage of variables for rendering page
     * @param templatePath - path to velocity template for rendering page
     * @return - String value for building HTML page
     */
    public static String render(Request request, Map<String, Object> model, String templatePath) {
        model.put("msg", new MessageBundle());
        model.put("currentUser", getSessionCurrentUser(request));
        model.put("WebPath", Path.Web.class); // Access application URLs from templates
        return strictVelocityEngine().render(new ModelAndView(model, templatePath));
    }

    public static Route notFound = (Request request, Response response) -> {
        response.status(HttpStatus.NOT_FOUND_404);
        return render(request, new HashMap<>(), Path.Template.NOT_FOUND);
    };

    private static VelocityTemplateEngine strictVelocityEngine() {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loader", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityTemplateEngine(configuredEngine);
    }
}
