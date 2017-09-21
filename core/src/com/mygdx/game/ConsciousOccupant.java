package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kennethroffo on 9/17/17.
 */
public class ConsciousOccupant extends Occupant {

    public enum CharacterType {
        PLAYER
    }

    public enum Orientation {
        LEFT,
        RIGHT,
        DOWN,
        UP
    }

    // A sprite for facing each direction
    private Sprite leftSprite;
    private Sprite rightSprite;
    private Sprite downSprite;
    private Sprite upSprite;
    private Orientation orientation;
    private long moveStart;
    private Tile target;
    private double movementSpeed;

    public ConsciousOccupant(CharacterType characterType, Orientation orientation, float tileWidth, float tileHeight, float scaleW, float scaleH, float xpos, float ypos) {
        super(null);
        Sprite[] sprites = new Sprite[4];
        String[] spriteNames = TypeMappings.getSpriteNames(characterType);
        if (spriteNames == null) {
            System.err.println("Invalid character type passed to ConsciousOccupant. Cannot get sprite names.");
            System.exit(1);
        }
        this.leftSprite = sprites[0] = new Sprite(new Texture(spriteNames[0]));
        this.rightSprite = sprites[1] = new Sprite(new Texture(spriteNames[1]));
        this.downSprite = sprites[2] = new Sprite(new Texture(spriteNames[2]));
        this.upSprite = sprites[3] = new Sprite(new Texture(spriteNames[3]));

        for (Sprite s : sprites) {
            scaleW = tileWidth / s.getWidth();
            scaleH = tileHeight / s.getHeight();
            s.setOriginCenter();
            s.setSize(s.getWidth() * scaleW, s.getHeight() * scaleH);
            s.setPosition(xpos, ypos);
        }
        this.setSprite(leftSprite);

        this.setOrientation(orientation);

        this.moveStart = -1;
        this.target = null;
        this.movementSpeed = TypeMappings.getCharacterTypeSpeed(characterType);

        Game.CONSCIOUS_OCCUPANTS.add(this);
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        switch(orientation) {
            case LEFT:
                this.leftSprite.setPosition(this.sprite.getX(), this.sprite.getY());
                this.setSprite(leftSprite);
                break;
            case RIGHT:
                this.rightSprite.setPosition(this.sprite.getX(), this.sprite.getY());
                this.setSprite(rightSprite);
                break;
            case DOWN:
                this.downSprite.setPosition(this.sprite.getX(), this.sprite.getY());
                this.setSprite(downSprite);
                break;
            case UP:
                this.upSprite.setPosition(this.sprite.getX(), this.sprite.getY());
                this.setSprite(upSprite);
                break;
            default:
                rightSprite.setPosition(this.sprite.getX(), this.sprite.getY());
                this.setSprite(rightSprite);
        }
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public boolean isBusy() {
        return this.moveStart > -1;
    }

    public void step() {
        if (this.moveStart > -1) {
            this.stepMovement();
        }
    }

    // Start a leftward move if possible
    public void moveLeft() {
        this.setOrientation(Orientation.LEFT);
        Tile target = this.getLocation().getLeftNeighbor();
        startMoveIfPossible(target);
    }

    public void moveRight() {
        this.setOrientation(Orientation.RIGHT);
        Tile target = this.getLocation().getRightNeighbor();
        startMoveIfPossible(target);
    }

    public void moveDown() {
        this.setOrientation(Orientation.DOWN);
        Tile target = this.getLocation().getDownNeighbor();
        startMoveIfPossible(target);
    }

    public void moveUp() {
        this.setOrientation(Orientation.UP);
        Tile target = this.getLocation().getUpNeighbor();
        startMoveIfPossible(target);
    }

    private void startMoveIfPossible(Tile target) {
        // If the target is a regular tile and is not occupied, move there!
        if ( target != null && target.getType() == Tile.TileType.REGULAR && !target.isOccupied()) {
            this.moveStart = System.currentTimeMillis();
            this.target = target;

            // Set the target as occupied so nothing else can occupy it
            // This means both the target and current tile are occupied by this
            // occupant.
            target.setOccupant(this);
        } else {
            // Play a sound to indicate the move is not possible at the moment
        }
    }

    public void attack() {

    }

    // Step forward in time for the animation of moving
    public void stepMovement() {
        long elapsed = System.currentTimeMillis() - this.moveStart;
        if (elapsed < this.movementSpeed) {
            float x = this.getLocation().getX() + (( target.getX() - this.getLocation().getX() ) * (elapsed / (float)this.movementSpeed));
            float y = this.getLocation().getY() + (( target.getY() - this.getLocation().getY() ) * (elapsed / (float)this.movementSpeed));
            this.sprite.setPosition(x, y);
        } else {
            // Movement time completed. Finish the move
            this.moveStart = -1;
            this.getLocation().setOccupant(null);
            this.setLocation(this.target);
            this.sprite.setPosition(target.getX(), target.getY());
            this.target = null;
        }
    }
}
