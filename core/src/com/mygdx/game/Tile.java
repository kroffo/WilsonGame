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

    // To be used when an occupant is moving to this tile
    // Don't allow things to move here when busy, but keep the
    // current occupant (an item perhaps) until the moving one
    // is here so it will be rendered
    private boolean busy;

    public Tile(Sprite s, Room r, TileType t) {
        this.type = t;
        this.sprite = s;
        this.room = r;
    }

    public boolean isOccupied() {
        return this.occupant != null;
    }

    public Occupant getOccupant() {
        return this.occupant;
    }

    public void setOccupant(Occupant occupant) {
        this.occupant = occupant;
    }

    public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    public void drawOccupants(SpriteBatch batch) {
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

    public void setBusy(boolean value) {
        this.busy = value;
    }

    public boolean isBusy() {
        return busy;
    }
}
