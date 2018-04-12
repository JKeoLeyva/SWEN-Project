package com.webcheckers;

public interface Strings {
    interface Session {
        String PLAYER = "player";
        String MESSAGE = "message";
    }

    final class Template {
        public interface Home {
            String FILE_NAME = "home.ftl";
            String CURRENT_PLAYER = "currentPlayer";
            String PLAYER_LOBBY = "playerLobby";
            String GAME_MANAGER = "gameManager";
            String MESSAGE = "message";
            String REPLAY_MANAGER = "replayManager";

        }

        public interface SignIn {
            String FILE_NAME = "signin.ftl";
            String PLAYER_NAME = "name";
        }

        public interface Game {
            String FILE_NAME = "game.ftl";
            String CURRENT_PLAYER = "currentPlayer";
            String VIEW_MODE = "viewMode";
            String RED_PLAYER = "redPlayer";
            String WHITE_PLAYER = "whitePlayer";
            String ACTIVE_COLOR = "activeColor";
            String BOARD = "board";
        }

        public interface Help {
            String FILE_NAME = "help.ftl";
            String CURRENT_PLAYER = "currentPlayer";
        }

    }

    final class Request{
        public static final String OPPONENT = "opponent";
    }
}
