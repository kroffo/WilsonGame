package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kennethroffo on 9/9/17.
 */
public class Arrows extends Item {

    private int numberOfArrows;

    public Arrows(float xpos, float ypos) {
        super(new Sprite(Game.ARROWS_SPRITE), xpos, ypos);
        this.numberOfArrows = 2;
    }

    public Arrows(float xpos, float ypos, int numberOfArrows) {
        super(new Sprite(Game.ARROWS_SPRITE), xpos, ypos);
        this.numberOfArrows = numberOfArrows;
    }

    public int getNumberOfArrows() {
        return this.numberOfArrows;
    }

    public void processArrivalOfOccupant(ConsciousOccupant c) {
        c.addArrows(this.numberOfArrows);
    }
}
