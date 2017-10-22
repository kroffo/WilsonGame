package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kennethroffo on 10/21/17.
 */
public abstract class Weapon extends Item {

    public Weapon(Sprite sprite, float xpos, float ypos) {
        super(sprite, xpos, ypos);
    }
}
