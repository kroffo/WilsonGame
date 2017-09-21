package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kennethroffo on 9/17/17.
 */
public class TypeMappings {

    public static String getSpriteNameFromType(String type) {
        switch(type) {
            case "water":
                return "WaterTile.png";
            case "wood":
                return "WoodTile.png";
            case "sword":
                return "Sword.png";
            case "bow":
                return "Bow.png";
            case "arrows":
                return "Arrows.png";
            case "greenkey":
                return "GreenKey.png";
            case "bluekey":
                return "BlueKey.png";
            case "redkey":
                return "RedKey.png";
            case "yellowkey":
                return "YellowKey.png";
            case "pinkkey":
                return "PinkKey.png";
            default:
                return null;
        }
    }

    public static Occupant createAppropriateOccupant(String type, Sprite sprite) {
        switch(type) {
            case "sword":
                return new Sword(sprite);
            case "bow":
                return new Bow(sprite);
            case "arrows":
                return new Arrows(sprite);
            case "greenkey":
                return new Key(sprite,"green");
            case "bluekey":
                return new Key(sprite,"blue");
            case "redkey":
                return new Key(sprite,"red");
            case "yellowkey":
                return new Key(sprite,"yellow");
            case "pinkkey":
                return new Key(sprite,"pink");
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

    public static String[] getSpriteNames(ConsciousOccupant.CharacterType ctype) {
        if (ctype == ConsciousOccupant.CharacterType.PLAYER) {
            String[] sprites = {"WilsonFacingLeft.png", "WilsonFacingRight.png", "WilsonFacingLeft.png", "WilsonFacingRight.png"};
            return sprites;
        }
        else return null;
    }

    public static double getCharacterTypeSpeed(ConsciousOccupant.CharacterType ctype) {
        if (ctype == ConsciousOccupant.CharacterType.PLAYER) {
            return 500.0;
        }
        else return 500.0;
    }
}

