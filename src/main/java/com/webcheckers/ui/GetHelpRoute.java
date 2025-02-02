package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Player Help Page
 */
public class GetHelpRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHelpRoute.class.getName());

    private final TemplateEngine templateEngine;

    public GetHelpRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.templateEngine = templateEngine;
    }

    @Override
    public Object handle(Request request, Response response){
        LOG.finer("GetHelpRoute is invoked.");

        Session session = request.session();
        Player currPlayer = session.attribute(Strings.Session.PLAYER);

        if( currPlayer == null ){
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        Map<String, Object> vm = new HashMap<>();
        vm.put(Strings.Template.Help.CURRENT_PLAYER, currPlayer);

        return templateEngine.render(new ModelAndView(vm, Strings.Template.Help.FILE_NAME));

    }
}
