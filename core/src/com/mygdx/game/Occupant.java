package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kennethroffo on 9/9/17.
 */
public abstract class Occupant {

    protected Sprite sprite;
    private Tile location;

    public Occupant(Sprite s, float xpos, float ypos) {
        this.sprite = s;
        this.sprite.setPosition(xpos, ypos);
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    public void setLocation(Tile t) {
        this.location = t;
    }

    public Tile getLocation() {
        return this.location;
    }

    public float getX() {
        return this.sprite.getX();
    }

    public float getY() {
        return this.sprite.getY();
    }

    public float getCenterX() {
        return this.sprite.getX() + this.sprite.getWidth()/2;
    }

    public float getCenterY() {
        return this.sprite.getY() + this.sprite.getHeight()/2;
    }

    public abstract void processArrivalOfOccupant(ConsciousOccupant c);

    public abstract boolean isCollectible();

}
