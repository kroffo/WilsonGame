package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kennethroffo on 9/9/17.
 */
public class Sword extends Weapon {

    public Sword(float xpos, float ypos) {
        super(new Sprite(Game.SWORD_SPRITE), xpos, ypos);
    }

    public void processArrivalOfOccupant(ConsciousOccupant c) {
        if (c.collectsItems()) {
            c.setSword(this);
        }
    }
}
