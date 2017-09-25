package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kennethroffo on 9/24/17.
 */
public class StatusIcon {
    private Sprite backdrop;
    private Sprite icon;
    private boolean showIcon;
    private int number;

    public StatusIcon(float xpos, float ypos, float width, float height, Sprite icon, boolean showIcon, int number) {
        Sprite backdrop = new Sprite(new Texture("WhiteSquare.png"));
        backdrop.setSize(width, height);
        backdrop.setColor((float)0.2, (float)0.2, (float)0.2, 1);
        backdrop.setOriginCenter();
        backdrop.setPosition(xpos, ypos);
        this.backdrop = backdrop;

        this.icon = new Sprite(icon);
        this.icon.setSize((float)(width*0.9), (float)(height*0.9));
        this.icon.setOriginCenter();
        this.icon.setPosition(xpos, ypos);

        this.showIcon = showIcon;
        this.number = number;
    }

    public void hideIcon() {
        this.showIcon = false;
    }

    public void showIcon() {
        this.showIcon = true;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void draw(SpriteBatch batch) {
        this.backdrop.draw(batch);
        if (this.showIcon) {
            this.icon.draw(batch);
            if (this.number > -1) {
                float xpos = this.icon.getX() + this.icon.getWidth()*9/10;
                float ypos = this.icon.getY() + this.icon.getHeight()/3;
                Game.FONT.draw(batch, "" + this.number, xpos, ypos);
            }
        }
    }

    public void translate(float x, float y) {
        this.backdrop.translate(x, y);
        this.icon.translate(x, y);
    }
}
