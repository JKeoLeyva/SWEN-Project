package com.webcheckers.ui;

import com.webcheckers.model.Board;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author sam davis sjd8078
 */
public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final Map<String, Board> games;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public GetGameRoute(final TemplateEngine templateEngine,
                        final Map<String, Board> games) {

        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.templateEngine = templateEngine;
        this.games = games;

        LOG.config("GetGameRoute is initialized.");
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");
        Session session = request.session();
        Player currPlayer = session.attribute(PostSigninRoute.PLAYER_ATTR);
        Board board = games.get(currPlayer.getName());

        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game");
        vm.put("currentPlayer", currPlayer);
        vm.put("viewMode", "VIEWING");
        vm.put("redPlayer", board.getRedPlayer());
        vm.put("whitePlayer", board.getWhitePlayer());
        vm.put("activeColor", board.activeColor());
        vm.put("board", board);

        return templateEngine.render(new ModelAndView(vm, "game.ftl"));
    }
}
