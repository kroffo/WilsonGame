package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kennethroffo on 9/24/17.
 */
public class Potion extends Occupant {

    public static final int EFFECTIVENESS = 25;

    public Potion(float xpos, float ypos) {
        super(new Sprite(Game.POTION_SPRITE), xpos, ypos);
    }
}
