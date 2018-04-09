package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.ReplayManager;
import com.webcheckers.model.*;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * Spark Route for a page displaying the current game.
 */
public class GetGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final GameManager gameManager;
    private final ReplayManager replayManager;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request
     *
     * @param templateEngine the HTML template rendering engine
     * @param gameManager    hold all current games
     */
    public GetGameRoute(final TemplateEngine templateEngine,
                        final GameManager gameManager,
                        final ReplayManager replayManager) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.templateEngine = templateEngine;
        this.gameManager = gameManager;
        this.replayManager = replayManager;
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Game page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");
        Session session = request.session();
        Player currPlayer = session.attribute(Strings.Session.PLAYER);
        Game game = gameManager.getGame(currPlayer);

        if(game == null) {
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        if(game.isGameOver(currPlayer)) {
            Replay replay = replayManager.getReplay(currPlayer);
            for(Move move : game.getSubmittedMoves()) {
                replay.addMove(move);
            }

            gameManager.deleteGame(currPlayer);

            response.redirect(WebServer.HOME_URL);
            return null;
        }

        game.clearTurn();
        Map<String, Object> vm = new HashMap<>();
        vm.put(Strings.Template.Game.CURRENT_PLAYER, currPlayer);
        vm.put(Strings.Template.Game.VIEW_MODE, ViewMode.PLAY);
        vm.put(Strings.Template.Game.RED_PLAYER, game.getRedPlayer());
        vm.put(Strings.Template.Game.WHITE_PLAYER, game.getWhitePlayer());
        vm.put(Strings.Template.Game.ACTIVE_COLOR, game.getActiveColor());
        vm.put(Strings.Template.Game.BOARD, game.makeBoardView(currPlayer));

        return templateEngine.render(new ModelAndView(vm, Strings.Template.Game.FILE_NAME));
    }
}
