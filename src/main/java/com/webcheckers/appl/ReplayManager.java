package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for holding all of the Replay objects that are associated with
 * a Player, storing them in a Map, with a Player object as a key, and the list of Replays
 * as the value.  Is also able to add a Replay to a Player's list of Replays, retrive all of
 * the Replays associated with a Player, and to delete a Player's Replays
 */
public class ReplayManager {
    private Map<Player, List<Replay>> replays;

    /**
     * Constructor for ReplayManager, uses a Map to hold Player, Replay List pairs
     */
    public ReplayManager(Map<Player, List<Replay>> replays) {
        this.replays = replays;
    }

    /**
     * Adds a new Replay object to a given Player's list of replays if the Player exists in the PlayerManager,
     * if not, then a new entry is created in ReplayManager.
     * @param game Used to make the new Replay Object to be added
     * @param player Used to either make a new entry in the ReplayManager, or to find the Replay List associated with
     *        that player, in order to add the new Replay
     */
    public void addReplay(Game game, Player player) {
        Replay replay = new Replay(game);

        if(this.replays.get(player) != null) {
            this.replays.get(player).add(replay);
        } else {
            List<Replay> playerReplays = new ArrayList<>();
            playerReplays.add(replay);
            this.replays.put(player, playerReplays);
        }
    }

    /**
     * Retrieves the List of Replays associated with the given Player
     * @param player Used to find the corresponding Replay List
     * @return a List of Replays
     */
    public List<Replay> getReplays(Player player) {
        return replays.get(player);
    }

    /**
     * Deletes the Player from the ReplayManager, which also deletes the List of Replays that
     * is associated with that Player
     * @param player used to delete that Player from the ReplayManager
     */
    public void deleteReplays(Player player) {
        replays.remove(player);
    }
}
