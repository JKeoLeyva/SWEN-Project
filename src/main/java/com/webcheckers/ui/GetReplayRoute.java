package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.ReplayManager;
import com.webcheckers.model.*;
import spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

public class GetReplayRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetReplayRoute.class.getName());
    private final TemplateEngine templateEngine;
    private final ReplayManager replayManager;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request
     *
     * @param templateEngine the HTML template rendering engine
     * @param replayManager  the replay manager
     */
    public GetReplayRoute(TemplateEngine templateEngine, ReplayManager replayManager) {
        this.templateEngine = templateEngine;
        this.replayManager = replayManager;
    }

    /**
     * Render the WebCheckers Replay page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Replay page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetGameRoute is invoked.");

        Session session = request.session();
        Player currentPlayer = session.attribute(Strings.Session.PLAYER);

        if(currentPlayer == null) {
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        List<Replay> replays = replayManager.getReplays(currentPlayer);
        int replayID;
        int moveID;

        try {
            replayID = Integer.parseInt(request.queryParams(Strings.Request.REPLAY_ID));
            moveID = Integer.parseInt(request.queryParams(Strings.Request.MOVE_ID));
        } catch(NumberFormatException e) {
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        Replay replay = replays.get(replayID);

        if(replay == null) {
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        Game game = new Game(replay.getRedPlayer(), replay.getWhitePlayer());
        Queue<Move> moves = replay.getMoves();

        if(moveID > moves.size()) {
            response.redirect(WebServer.HOME_URL);
            return null;
        }

        for(int i = 0; i < moveID; i++) {
            game.tryMove(moves.remove());
            game.submitTurn();
        }

        Map<String, Object> vm = new HashMap<>();
        vm.put(Strings.Template.Game.CURRENT_PLAYER, currentPlayer);
        vm.put(Strings.Template.Game.VIEW_MODE, ViewMode.REPLAY);
        vm.put(Strings.Template.Game.RED_PLAYER, game.getRedPlayer());
        vm.put(Strings.Template.Game.WHITE_PLAYER, game.getWhitePlayer());
        vm.put(
                Strings.Template.Game.ACTIVE_COLOR,
                game.getRedPlayer().equals(currentPlayer) ? Piece.Color.RED : Piece.Color.WHITE
        );
        vm.put(Strings.Template.Game.BOARD, game.makeBoardView(currentPlayer));

        return templateEngine.render(new ModelAndView(vm, Strings.Template.Game.FILE_NAME));
    }
}
