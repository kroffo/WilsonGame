package com.mygdx.game;

/**
 * Created by kennethroffo on 9/9/17.
 */
public class Player extends ConsciousOccupant {

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
        super(CharacterType.PLAYER, Orientation.LEFT, xpos, ypos);
    }
}
