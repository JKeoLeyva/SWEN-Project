package com.webcheckers;

public final class Strings {
    private Strings() {}

    public static final class Session {
        public static final String PLAYER = "player";
        public static final String MESSAGE = "message";
    }

    public static final class Template {
        public static final class Home {
            public static final String FILE_NAME = "home.ftl";
            public static final String CURRENT_PLAYER = "currentPlayer";
            public static final String PLAYER_LOBBY = "playerLobby";
            public static final String GAME_MANAGER = "gameManager";
            public static final String MESSAGE = "message";
        }

        public static final class SignIn {
            public static final String FILE_NAME = "signin.ftl";
            public static final String PLAYER_NAME = "name";
        }

        public static final class Game {
            public static final String FILE_NAME = "game.ftl";
            public static final String CURRENT_PLAYER = "currentPlayer";
            public static final String VIEW_MODE = "viewMode";
            public static final String RED_PLAYER = "redPlayer";
            public static final String WHITE_PLAYER = "whitePlayer";
            public static final String ACTIVE_COLOR = "activeColor";
            public static final String BOARD = "board";
        }

        public static final class Help {
            public static final String FILE_NAME = "help.ftl";
        }
    }
}
