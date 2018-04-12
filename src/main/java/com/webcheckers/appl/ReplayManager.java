package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplayManager {
    private Map<Player, List<Replay>> replays;

    public ReplayManager() {
        this.replays = new HashMap<Player, List<Replay>>();
    }

    public void addReplay(Game game, Player player) {
        Replay replay = new Replay(game);

        if(this.getReplays(player) != null) {
            this.getReplays(player).add(replay);
        } else {
            List<Replay> playerReplays = new ArrayList<Replay>();
            playerReplays.add(replay);
            this.replays.put(player, playerReplays);
        }
    }

    public List<Replay> getReplays(Player player) {
        return replays.get(player);
    }

    public void deleteReplays(Player player) {
        replays.remove(player);
    }
}
