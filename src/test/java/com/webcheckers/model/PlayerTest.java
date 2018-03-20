package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class PlayerTest {

    private void testPlayer(String name){
        Player newPlayer = new Player(name);

        Player mockPlayer = new Player("Mock");

        //Tests if it recognizes different players as unique
        assertNotNull(newPlayer, "Player was created");
        assertEquals(name, newPlayer.getName());
        assertNotEquals(mockPlayer.getName(), newPlayer.getName());
    }

    private void testPlayerEquals(Player p1, Player p2){
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
    }

    private void testPlayerHashCode(Player p1, Player p2){
        assertTrue(p1.hashCode() == p2.hashCode());
        assertTrue(p2.hashCode() == p1.hashCode());
    }

    /**
     * Tests if the Player can be created using a single space
     */
    @Test
    public void testPlayer_Single_Space_Name() { testPlayer(" "); }

    /**
     * Tests if the Player can be created with a NULL
     */
    @Test
    public void testPlayer_Null_Given() { testPlayer(null);}

    /**
     * Tests if the Player can be created with a simple, valid name
     */
    @Test
    public void testPlayer_Simple_Name() { testPlayer("John Wilder");}

    /**
     * Tests if the Player Equals methods works with the same Player
     */
    @Test
    public void testPlayerEquals_With_Same_Players(){
        Player player = new Player("John");
        Player player2 = player;
        testPlayerEquals(player, player2);
    }

    /**
     * Tests if the Player Equals method works with different Player's, which it should'nt
     */
    @Test
    public void testPlayerEquals_With_Diff_Players(){
        Player player = new Player("John");
        Player player2 = new Player("Jack");
        testPlayerEquals(player, player2);
    }

    /**
     *  Tests if the Player Hashcode method works with the same player
     */
    @Test
    public void testPlayerHashCode_With_Same_Player(){
        Player player = new Player("John");
        Player player2 = player;
        testPlayerHashCode(player, player2);
    }

    /**
     *  Tests if the Player Hashcode method works with different Players.
     */
    @Test
    public void testPlayerHashCode_With_Diff_Players(){
        Player player = new Player("John");
        Player player2 = new Player("Jack");
        testPlayerHashCode(player, player2);
    }

}