package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kennethroffo on 9/9/17.
 */
public class Player extends ConsciousOccupant {

    public static int MAX_HEALTH = 100;

    private static Player player;
    private Sword sword;
    private Bow bow;
    private int arrows;
    private int potions;
    private int health;
    private HashMap<String, Integer> keys;

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
        this.sword = null;
        this.bow = null;
        this.potions = 0;
        this.arrows = 0;
        this.keys = new HashMap<>();
        this.health = MAX_HEALTH;
        String[] keyColors = TypeMappings.getKeyColors();
        for (String color : keyColors)
            this.keys.put(color, 0);
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }

    public Sword getSword() {
        return this.sword;
    }

    public void setBow(Bow bow) {
        this.bow = bow;
    }

    public Bow getBow() {
        return this.bow;
    }

    public void addPotion() {
        ++this.potions;
    }

    public void usePotion() {
        if (this.potions > 0) {
            --this.potions;
            this.health += (this.health < MAX_HEALTH - Potion.EFFECTIVENESS) ? Potion.EFFECTIVENESS : MAX_HEALTH - this.health;
        }
    }

    public int getNumberOfPotions() {
        return this.potions;
    }

    public void addArrows(int numberOfArrows) {
        this.arrows += numberOfArrows;
    }

    public void removeArrows(int numberOfArrows) {
        this.arrows -= numberOfArrows;
    }

    public int getNumberOfArrows() {
        return this.arrows;
    }

    public void addKey(Key key) {
        String color = key.getColor();
        if (this.keys.containsKey(color)) {
            this.keys.put(color, this.keys.get(color)+1);
        } else {
            System.err.println("Keys with color \"" + color + "\" should not exist.");
            System.exit(1);
        }
    }

    public boolean removeKey(String color) {
        if (this.keys.containsKey(color)) {
            if (this.keys.get(color) > 0) {
                this.keys.put(color, this.keys.get(color)-1);
                return true;
            }
        } else {
            System.err.println("Keys with color \"" + color + "\" do not exist.");
        }
        return false;
    }

    public int getNumberOfColoredKeys(String color) {
        return this.keys.get(color);
    }

    public int getHealth() {
        return this.health;
    }

    public boolean isTileEnterable(Tile target) {
        Occupant occupant = target.getOccupant();
        return  (target.getType() == Tile.TileType.REGULAR) &&
                (
                        occupant == null ||
                        occupant instanceof Sword ||
                        occupant instanceof Bow ||
                        occupant instanceof Potion ||
                        occupant instanceof Arrows ||
                        occupant instanceof Key
                );
    }

    public void arriveAtTile(Tile target) {
        Occupant occupant = target.getOccupant();
        if (occupant instanceof Sword)
            this.setSword((Sword)occupant);
        else if (occupant instanceof Bow)
            this.setBow((Bow)occupant);
        else if (occupant instanceof Potion)
            this.addPotion();
        else if (occupant instanceof Arrows)
            this.addArrows(((Arrows)occupant).getNumberOfArrows());
        else if (occupant instanceof Key)
            this.addKey((Key)occupant);
    }
}
