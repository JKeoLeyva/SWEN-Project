package com.webcheckers.model;

import java.util.Objects;

/**
 * Represents a Player (or user) of WebCheckers
 */
public class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
