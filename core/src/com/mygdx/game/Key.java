package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kennethroffo on 9/9/17.
 */
public class Key extends Occupant {

    private String color;

    public Key(String color, float xpos, float ypos) {
        super(TypeMappings.getKeySpriteFromColor(color), xpos, ypos);
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
