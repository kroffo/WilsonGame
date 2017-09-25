package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kennethroffo on 9/24/17.
 */
public class HealthBar {

    private Sprite backdrop;
    private Sprite valueBar;
    private int maxValue;
    private float maxWidth;

    public HealthBar(float xpos, float ypos, float width, float height, int maxValue) {
        this.backdrop = new Sprite(new Texture("WhiteSquare.png"));
        this.backdrop.setSize(width, height);
        this.backdrop.setColor((float)0.5, (float)0.5, (float)0.5, 1);
        this.backdrop.setOriginCenter();
        this.backdrop.setPosition(xpos, ypos);

        this.valueBar = new Sprite(new Texture("WhiteSquare.png"));
        this.maxWidth = (float)(width*0.99);
        this.valueBar.setSize(this.maxWidth, (float)(height*0.9));
        this.valueBar.setColor(1, 0, 0, 1);
        this.valueBar.setOriginCenter();
        this.valueBar.setPosition(xpos + (this.backdrop.getWidth() - this.valueBar.getWidth())/2, ypos + (this.backdrop.getHeight() - this.valueBar.getHeight())/2);

        this.maxValue = maxValue;
    }

    public void updateWidth(int value) {
        this.valueBar.setSize((value/this.maxValue)*maxWidth, this.valueBar.getHeight());
    }

    public void draw(SpriteBatch batch) {
        this.backdrop.draw(batch);
        this.valueBar.draw(batch);
    }

    public void translate(float x, float y) {
        this.backdrop.translate(x, y);
        this.valueBar.translate(x, y);
    }
}
