package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class PlayerTest {
    private void testPlayer(String name){
        Player newPlayer = new Player(name);
        Player mockPlayer = new Player("Mock");

        //Tests if it recognizes different players as unique
        assertNotNull(newPlayer, "Player was created");
        assertEquals(name, newPlayer.getName());
        assertNotEquals(mockPlayer.getName(), newPlayer.getName());
    }

    /**
     * Tests if the Player can be created using a single space
     */
    @Test
    void testPlayer_Single_Space_Name() { testPlayer(" "); }

    /**
     * Tests if the Player can be created with a NULL
     */
    @Test
    void testPlayer_Null_Given() { testPlayer(null);}

    /**
     * Tests if the Player can be created with a simple, valid name
     */
    @Test
    void testPlayer_Simple_Name() { testPlayer("John Wilder");}

    /**
     * Tests if the Player Equals methods works with the same Player
     */
    @Test
    void testPlayerEquals_With_Same_Players(){
        Player player = new Player("John");
        Player player2 = new Player("John");
        assertEquals(player, player2);
    }

    /**
     * Tests if the Player Equals method works with different Player's, which it should'nt
     */
    @Test
    void testPlayerEquals_With_Diff_Players(){
        Player player = new Player("John");
        Player player2 = new Player("Jack");
        assertNotEquals(player, player2);
    }

    /**
     *  Tests if the Player Hashcode method works with the same player
     */
    @Test
    void testPlayerHashCode_With_Same_Player(){
        Player player = new Player("John");
        Player player2 = new Player("John");
        assertEquals(player.hashCode(), player2.hashCode());
    }

    /**
     *  Tests if the Player Hashcode method works with different Players.
     */
    @Test
    void testPlayerHashCode_With_Diff_Players(){
        Player player = new Player("John");
        Player player2 = new Player("Jack");
        assertNotEquals(player.hashCode(), player2.hashCode());
    }

    /**
     * Tests remaining equals branches.
     */
    @Test
    void testEquals(){
        Player player = new Player("");
        assertNotEquals(player, null);
        assertNotEquals(player, "");
        assertEquals(player, player);
    }
}