package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kennethroffo on 9/9/17.
 */
public class Tile {

    public enum TileType {
        REGULAR,
        BARRIER
    }

    private TileType type;
    private Sprite sprite;
    private Room room;
    private Occupant occupant;

    public Tile(Sprite s, Room r, TileType t) {
        this.type = t;
        this.sprite = s;
        this.room = r;
    }

    public boolean isOccupied() {
        return this.occupant != null;
    }

    public void setOccupant(Occupant occupant) {
        this.occupant = occupant;
    }

    public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
        if(this.occupant != null) {
            this.occupant.draw(batch);
        }
    }

    public float getX() {
        return this.sprite.getX();
    }

    public float getY() {
        return this.sprite.getY();
    }

    // Returns the neighboring tile to the left
    public Tile getLeftNeighbor() {
        return this.room.getLeftNeighbor(this);
    }

    // Returns the neighboring tile to the right
    public Tile getRightNeighbor() {
        return this.room.getRightNeighbor(this);
    }

    // Returns the neighboring tile below
    public Tile getDownNeighbor() {
        return this.room.getDownNeighbor(this);
    }

    // Returns the neighboring tile above
    public Tile getUpNeighbor() {
        return this.room.getUpNeighbor(this);
    }

    public TileType getType() {
        return this.type;
    }
}
