package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kennethroffo on 9/24/17.
 */
public class StatusBar {

    // height and width of the status bar
    private float height;
    private float width;
    private Player player;
    private StatusIcon swordIcon;
    private StatusIcon bowIcon;
    private StatusIcon potionIcon;
    private StatusIcon blueKeyIcon;
    private StatusIcon greenKeyIcon;
    private StatusIcon pinkKeyIcon;
    private StatusIcon redKeyIcon;
    private StatusIcon yellowKeyIcon;


    private Sprite bar;

    public StatusBar(float xpos, float ypos, float width, float height, Player player) {
        this.width = width;
        this.height = height;
        this.player = player;

        Sprite bar = new Sprite(new Texture("WhiteSquare.png"));
        bar.setSize(this.width, this.height);
        bar.setColor(0, 0, 0, 1);
        bar.setPosition(xpos, ypos);
        this.bar = bar;

        float iconHeight = (float)(height*0.8);
        float iconWidth = iconHeight;
        float iconSpacing = width/100;

        float iconX = xpos + iconSpacing;
        float iconY = ypos + (height - iconHeight)/2;

        this.swordIcon = new StatusIcon(iconX, iconY, iconWidth, iconHeight, Game.SWORD_SPRITE, false, -1);
        iconX += iconWidth + iconSpacing;
        this.bowIcon = new StatusIcon(iconX, iconY, iconWidth, iconHeight, Game.BOW_SPRITE, false, -1);
        iconX += iconWidth + iconSpacing;
        this.potionIcon = new StatusIcon(iconX, iconY, iconWidth, iconHeight, Game.POTION_SPRITE, false, -1);
        iconX += iconWidth + iconSpacing;
        this.blueKeyIcon = new StatusIcon(iconX, iconY, iconWidth, iconHeight, Game.BLUE_KEY_SPRITE, false, -1);
        iconX += iconWidth + iconSpacing;
        this.greenKeyIcon = new StatusIcon(iconX, iconY, iconWidth, iconHeight, Game.GREEN_KEY_SPRITE, false, -1);
        iconX += iconWidth + iconSpacing;
        this.pinkKeyIcon = new StatusIcon(iconX, iconY, iconWidth, iconHeight, Game.PINK_KEY_SPRITE, false, -1);
        iconX += iconWidth + iconSpacing;
        this.redKeyIcon = new StatusIcon(iconX, iconY, iconWidth, iconHeight, Game.RED_KEY_SPRITE, false, -1);
        iconX += iconWidth + iconSpacing;
        this.yellowKeyIcon = new StatusIcon(iconX, iconY, iconWidth, iconHeight, Game.YELLOW_KEY_SPRITE, false, -1);
    }

    private void updateIcons() {
        Sword sword = this.player.getSword();
        if (sword != null) {
            this.swordIcon.showIcon();
        } else {
            this.swordIcon.hideIcon();
        }

        Bow bow = this.player.getBow();
        if (bow != null) {
            this.bowIcon.showIcon();
        } else {
            this.bowIcon.hideIcon();
        }

        int numberOfPotions = this.player.getNumberOfPotions();
        if (numberOfPotions > -1) {
            this.potionIcon.setNumber(numberOfPotions);
            this.potionIcon.showIcon();
        }

        int numberOfBlueKeys = this.player.getNumberOfColoredKeys("blue");
        if (numberOfBlueKeys > -1) {
            this.blueKeyIcon.setNumber(numberOfBlueKeys);
            this.blueKeyIcon.showIcon();
        }

        int numberOfGreenKeys = this.player.getNumberOfColoredKeys("green");
        if (numberOfGreenKeys > -1) {
            this.greenKeyIcon.setNumber(numberOfGreenKeys);
            this.greenKeyIcon.showIcon();
        }

        int numberOfPinkKeys = this.player.getNumberOfColoredKeys("pink");
        if (numberOfPinkKeys > -1) {
            this.pinkKeyIcon.setNumber(numberOfPinkKeys);
            this.pinkKeyIcon.showIcon();
        }

        int numberOfRedKeys = this.player.getNumberOfColoredKeys("red");
        if (numberOfRedKeys > -1) {
            this.redKeyIcon.setNumber(numberOfRedKeys);
            this.redKeyIcon.showIcon();
        }

        int numberOfYellowKeys = this.player.getNumberOfColoredKeys("yellow");
        if (numberOfYellowKeys > -1) {
            this.yellowKeyIcon.setNumber(numberOfYellowKeys);
            this.yellowKeyIcon.showIcon();
        }

    }

    public void draw(SpriteBatch batch) {
        this.bar.draw(batch);
        this.updateIcons();
        this.swordIcon.draw(batch);
        this.bowIcon.draw(batch);
        this.potionIcon.draw(batch);
        this.blueKeyIcon.draw(batch);
        this.greenKeyIcon.draw(batch);
        this.pinkKeyIcon.draw(batch);
        this.redKeyIcon.draw(batch);
        this.yellowKeyIcon.draw(batch);
    }

    public void translate(float x, float y) {
        this.bar.translate(x, y);
        this.swordIcon.translate(x, y);
        this.bowIcon.translate(x, y);
        this.potionIcon.translate(x, y);
        this.blueKeyIcon.translate(x, y);
        this.greenKeyIcon.translate(x, y);
        this.pinkKeyIcon.translate(x, y);
        this.redKeyIcon.translate(x, y);
        this.yellowKeyIcon.translate(x, y);
    }
}
