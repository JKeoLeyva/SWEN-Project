package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Application-tier")
public class PlayerLobbyTest {

    /*
    * Test instantiation of playerLobby
    * */
    @Test
    void testPlayerLobbyInstantiation() {
        PlayerLobby playerLobby = new PlayerLobby();

        // Test whether the PlayerLobby constructor returns something.
        assertNotNull(playerLobby);

        // Test whether a new PlayerLobby has 0 players
        // & getPlayerCount returns the correct size of player array
        assertEquals(playerLobby.getPlayerCount(), 0);

        // Test whether getPlayers() returns the list of Players
        assertNotNull(playerLobby.getPlayers());
    }

    /*
    * Test adding Players to playerLobby
    *
    * */
    @Test
    void testAddingPlayersToPlayerLobby() {
        String testUsername = "Testuser";
        PlayerLobby playerLobby = new PlayerLobby();

        // Add 1 player to PlayerLobby
        playerLobby.signInPlayer(testUsername);

        // Test whether the getPlayerCount returns the player count
        assertEquals(playerLobby.getPlayerCount(), 1);
    }

    @Test
    void testAddingInvalidPlayerToPlayerLobby() {
        String testUsername = "Testuser";
        PlayerLobby playerLobby = new PlayerLobby();

        // Add 1 player to PlayerLobby
        playerLobby.signInPlayer(testUsername);

        // Test whether isNameAvailable returns false on occupied player name
        assertFalse(playerLobby.isNameAvailable(testUsername));

        // Test whether signInPlayer returns null on attempting to add occupied player name
        assertNull(playerLobby.signInPlayer(testUsername));
    }

    @Test
    void signOutPlayer() {
        PlayerLobby playerLobby = new PlayerLobby();
        String playerName = "player";

        // Sign the player in
        assertEquals(new Player(playerName), playerLobby.signInPlayer(playerName));

        // Verify it was signed in
        List<Player> players = playerLobby.getPlayers();
        List<Player> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(new Player(playerName));
        assertEquals(expectedPlayers, players);

        // Sign the player out
        assertTrue(playerLobby.signOutPlayer(playerName));

        // Verify it was signed out
        assertEquals(new ArrayList<>(), playerLobby.getPlayers());
    }

    @Test
    void onlinePlayer() {
        PlayerLobby playerLobby = new PlayerLobby();
        String playerName = "player";

        assertEquals(new Player(playerName), playerLobby.signInPlayer(playerName));
        assertFalse(playerLobby.isNameAvailable(playerName));
        assertTrue(playerLobby.signOutPlayer(playerName));
        assertTrue(playerLobby.isNameAvailable(playerName));
    }
}
