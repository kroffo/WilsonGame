package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kennethroffo on 9/9/17.
 */
public class Bow extends Occupant {

    public Bow(float xpos, float ypos) {
        super(new Sprite(Game.BOW_SPRITE), xpos, ypos);
    }
}
