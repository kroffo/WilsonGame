package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kennethroffo on 9/9/17.
 */
public class Player extends ConsciousOccupant {

    public static int MAX_HEALTH = 100;

    private static Player player;
    public static Player getPlayer() {
        return player;
    }

    public static Player createPlayer(float xpos, float ypos) {
        if (player == null) {
            player = new Player(xpos, ypos);
        } else {
            System.err.println("Player already exists. Player creation failed.");
            System.exit(1);
        }
        return player;
    }

    private Player(float xpos, float ypos) {
        super(CharacterType.PLAYER, Orientation.LEFT, xpos, ypos, MAX_HEALTH);
    }

    public boolean isTileEnterable(Tile target) {
        Occupant occupant = target.getOccupant();
        return  (target.getType() == Tile.TileType.REGULAR) && (occupant == null || occupant.isCollectible());
    }

    public void arriveAtTile(Tile target) {
        Occupant occupant = target.getOccupant();
        if (occupant != null) {
            target.getOccupant().processArrivalOfOccupant(this);
        }
    }

    public boolean collectsItems() {
        return true;
    }

    public boolean ableToStrike() {
        return this.getSword() != null;
    }

    public void processArrivalOfOccupant(ConsciousOccupant c) {

    }

}
