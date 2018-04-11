package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Replay;

import java.util.HashMap;
import java.util.Map;

public class ReplayManager {
    private Map<Player, Replay> replays;

    public ReplayManager() {
        this.replays = new HashMap<Player, Replay>();
    }

    public void addReplay(Game game, Player player) {
        Replay replay = new Replay(game);
        this.replays.put(player, replay);
    }

    public Replay getReplay(Player player) {
        return replays.get(player);
    }

    public void deleteReplay(Player player) {
        replays.remove(player);
    }
}
