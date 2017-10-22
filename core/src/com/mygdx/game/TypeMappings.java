package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 * Created by kennethroffo on 9/17/17.
 */
public class TypeMappings {

    public static Sprite getTileSpriteFromType(String type) {
        switch(type) {
            case "water":
                return new Sprite(Game.WATER_TILE_SPRITE);
            case "wood":
                return new Sprite(Game.WOOD_TILE_SPRITE);
            default:
                return null;
        }
    }

    public static Sprite getKeySpriteFromColor(String color) {
        switch(color) {
            case "blue":
                return new Sprite(Game.BLUE_KEY_SPRITE);
            case "green":
                return new Sprite(Game.GREEN_KEY_SPRITE);
            case "pink":
                return new Sprite(Game.PINK_KEY_SPRITE);
            case "red":
                return new Sprite(Game.RED_KEY_SPRITE);
            case "yellow":
                return new Sprite(Game.YELLOW_KEY_SPRITE);
            default:
                return null;
        }
    }

    public static String[] getKeyColors() {
        String[] colors = {"blue", "green", "pink", "red", "yellow"};
        return colors;
    }

    public static Occupant createAppropriateOccupant(String type, float xpos, float ypos) {
        switch(type) {
            case "sword":
                return new Sword(xpos, ypos);
            case "bow":
                return new Bow(xpos, ypos);
            case "arrows":
                return new Arrows(xpos, ypos);
            case "potion":
                return new Potion(xpos, ypos);
            case "bluekey":
                return new Key("blue", xpos, ypos);
            case "greenkey":
                return new Key("green", xpos, ypos);
            case "pinkkey":
                return new Key("pink", xpos, ypos);
            case "redkey":
                return new Key("red", xpos, ypos);
            case "yellowkey":
                return new Key("yellow", xpos, ypos);
            default:
                return null;
        }
    }

    public static Tile.TileType getTileType(String type) {
        switch(type) {
            case "water":
                return Tile.TileType.BARRIER;
            case "wood":
                return Tile.TileType.REGULAR;
            default:
                return null;
        }
    }

    // Returns an array sprites for orientations left, right, down, up of the supplied character type
    // or null if no such character exists
    public static Sprite[] getCharacterSprites(ConsciousOccupant.CharacterType ctype) {
        if (ctype == ConsciousOccupant.CharacterType.PLAYER) {
            Sprite[] sprites = {
                    new Sprite(Game.WILSON_FACING_LEFT_SPRITE),
                    new Sprite(Game.WILSON_FACING_RIGHT_SPRITE),
                    new Sprite(Game.WILSON_FACING_DOWN_SPRITE),
                    new Sprite(Game.WILSON_FACING_UP_SPRITE),
            };
            return sprites;
        }
        else return null;
    }

    // Returns an array of sprites for striking orientations left, right, down, up of the supplied character type
    // or null if no such character exists
    public static Sprite[] getCharacterStrikeSprites(ConsciousOccupant.CharacterType ctype) {
        if (ctype == ConsciousOccupant.CharacterType.PLAYER) {
            Sprite[] sprites = {
                    new Sprite(Game.WILSON_STRIKING_LEFT_SPRITE),
                    new Sprite(Game.WILSON_STRIKING_RIGHT_SPRITE),
                    new Sprite(Game.WILSON_STRIKING_DOWN_SPRITE),
                    new Sprite(Game.WILSON_STRIKING_UP_SPRITE),
            };
            return sprites;
        }
        else return null;
    }

    public static Array<Animation<Texture>> getCharacterWalkAnimations(ConsciousOccupant.CharacterType ctype) {
        float speed = (float)getCharacterTypeWalkSpeed(ctype) / 8;
        if (ctype == ConsciousOccupant.CharacterType.PLAYER) {
            Array<Animation<Texture>> animations = new Array<Animation<Texture>>();
            animations.add(new Animation<Texture>(speed, Game.WILSON_WALKING_LEFT_ANIMATION.getKeyFrames()));
            animations.add(new Animation<Texture>(speed, Game.WILSON_WALKING_RIGHT_ANIMATION.getKeyFrames()));
            animations.add(new Animation<Texture>(speed, Game.WILSON_WALKING_DOWN_ANIMATION.getKeyFrames()));
            animations.add(new Animation<Texture>(speed, Game.WILSON_WALKING_UP_ANIMATION.getKeyFrames()));
            return animations;
        }
        else return null;
    }

    public static double getCharacterTypeWalkSpeed(ConsciousOccupant.CharacterType ctype) {
        if (ctype == ConsciousOccupant.CharacterType.PLAYER) {
            return 500.0;
        }
        else return 500.0;
    }
}

