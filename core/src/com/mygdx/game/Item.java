package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kennethroffo on 10/22/17.
 */
public abstract class Item extends Occupant {
    public Item(Sprite s, float xpos, float ypos) {
        super(s, xpos, ypos);
    }

    public boolean isCollectible() {
        return true;
    }
}
