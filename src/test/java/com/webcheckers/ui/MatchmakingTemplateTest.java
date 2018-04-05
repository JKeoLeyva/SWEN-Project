package com.webcheckers.ui;

import com.webcheckers.Strings;
import com.webcheckers.appl.GameManager;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class MatchmakingTemplateTest {

    private static final String PLAYER1 = "player1";
    private static final String PLAYER2 = "player2";
    private static final String PLAYER_INGAME = "<p>" + PLAYER1 + "</p>";
    private static final String PLAYER_NOGAME = "<input type=\"submit\" name=\"opponent\" value=\"" + PLAYER2 + "\">";

    private final TemplateEngine engine = new FreeMarkerEngine();

    private PlayerLobby playerLobby;
    private GameManager gameManager;
    private Player player1;
    private Player player2;

    private Map<String, Object> vm;

    @BeforeEach
    void setup() {
        playerLobby = mock(PlayerLobby.class);
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        gameManager = mock(GameManager.class);

        vm = new HashMap<>();
        vm.put(Strings.Template.Home.PLAYER_LOBBY, playerLobby);
        vm.put(Strings.Template.Home.GAME_MANAGER, gameManager);
    }

    @Test
    void playerInAndOutOfGame() {
        vm.put(Strings.Template.Home.CURRENT_PLAYER, new Player("test"));

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        when(gameManager.getGame(player2)).thenReturn(null);
        when(gameManager.getGame(player1)).thenReturn(new Game(player1, player2));
        when(playerLobby.getPlayers()).thenReturn(players);
        when(playerLobby.getPlayerCount()).thenReturn(2);
        when(player1.getName()).thenReturn(PLAYER1);
        when(player2.getName()).thenReturn(PLAYER2);

        final ModelAndView modelAndView = new ModelAndView(vm, Strings.Template.Home.FILE_NAME);
        final String viewHTML = engine.render(modelAndView);

        assertTrue(viewHTML.contains(PLAYER_INGAME));
        assertTrue(viewHTML.contains(PLAYER_NOGAME));
    }


}
