package com.mygdx.game;

/**
 * Created by kennethroffo on 9/9/17.
 */
public class Player extends ConsciousOccupant {

    private static Player player;

    public static Player getPlayer() {
        return player;
    }

    public static Player createPlayer(float tileWidth, float tileHeight, float scaleW, float scaleH, float xpos, float ypos) {
        if (player == null) {
            player = new Player(tileWidth, tileHeight, scaleW, scaleH, xpos, ypos);
        } else {
            System.err.println("Player already exists. Player creation failed.");
            System.exit(1);
        }
        return player;
    }

    private Player(float tileWidth, float tileHeight, float scaleW, float scaleH, float xpos, float ypos) {
        super(CharacterType.PLAYER, Orientation.LEFT, tileWidth, tileHeight, scaleW, scaleH, xpos, ypos);
    }
}
