package com.webcheckers.ui;

import com.webcheckers.Strings;
import spark.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * A Spark Route to display a sign-in page.
 */
public class GetSigninRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /signin} HTTP request
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetSigninRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSigninRoute is invoked.");
        if(request.session().attribute(Strings.Session.PLAYER) != null){
            response.redirect(WebServer.HOME_URL);
            return null;
        }


        return templateEngine.render(new ModelAndView(new HashMap<>(), Strings.Template.SignIn.FILE_NAME));
    }
}
