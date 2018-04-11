package com.webcheckers;

public interface Strings {
    interface Session {
        String PLAYER = "player";
        String MESSAGE = "message";
    }

    interface Template {
        interface Home {
            String FILE_NAME = "home.ftl";
            String CURRENT_PLAYER = "currentPlayer";
            String PLAYER_LOBBY = "playerLobby";
            String GAME_MANAGER = "gameManager";
            String MESSAGE = "message";
        }

        interface SignIn {
            String FILE_NAME = "signin.ftl";
            String PLAYER_NAME = "name";
        }

        interface Game {
            String FILE_NAME = "game.ftl";
            String CURRENT_PLAYER = "currentPlayer";
            String VIEW_MODE = "viewMode";
            String RED_PLAYER = "redPlayer";
            String WHITE_PLAYER = "whitePlayer";
            String ACTIVE_COLOR = "activeColor";
            String BOARD = "board";
        }

        interface Help {
            String FILE_NAME = "help.ftl";
            String CURRENT_PLAYER = "currentPlayer";
        }
    }
}
