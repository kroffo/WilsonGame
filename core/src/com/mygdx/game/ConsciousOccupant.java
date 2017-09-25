package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kennethroffo on 9/17/17.
 */
public abstract class ConsciousOccupant extends Occupant implements Steppable {

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

    public ConsciousOccupant(CharacterType characterType, Orientation orientation, float xpos, float ypos) {

        // This is kind of a hack since super has to be the first line,
        // but I need to get the sprites based on the character type first
        super(new Sprite(Game.WILSON_FACING_LEFT_SPRITE), xpos, ypos);
        Sprite[] sprites = TypeMappings.getCharacterSprites(characterType);
        if (sprites == null) {
            System.err.println("Invalid character type passed to ConsciousOccupant. Cannot get sprite names.");
            System.exit(1);
        }
        this.leftSprite = sprites[0];
        this.rightSprite = sprites[1];
        this.downSprite = sprites[2];
        this.upSprite = sprites[3];

        for (Sprite s : sprites) {
            s.setPosition(xpos, ypos);
        }

        // Sets the orientation, as well as setting the sprite to the appropriate sprite
        this.setOrientation(orientation);

        this.moveStart = -1;
        this.target = null;
        this.movementSpeed = TypeMappings.getCharacterTypeSpeed(characterType);

        Game.STEPPABLES.add(this);
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

    public void step(double delta) {
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

    // Together these methods will enable movement to occupied tiles
    public abstract boolean isTileEnterable(Tile target);
    public abstract void arriveAtTile(Tile target);

    private void startMoveIfPossible(Tile target) {
        // If the target is a regular tile and is not occupied, move there!
        if ( target != null && this.isTileEnterable(target)) {
            this.moveStart = System.currentTimeMillis();
            this.target = target;

            // Set the target as occupied so nothing else can occupy it
            // This means both the target and current tile are occupied by this
            // occupant.
            target.setBusy(true);
        } else {
            // Play a sound to indicate the move is not possible at the moment
        }
    }

    public void attack() {

    }

    // Step forward in time for the animation of moving
    private void stepMovement() {
        long elapsed = System.currentTimeMillis() - this.moveStart;
        if (elapsed < this.movementSpeed) {
            float x = this.getLocation().getX() + (( target.getX() - this.getLocation().getX() ) * (elapsed / (float)this.movementSpeed));
            float y = this.getLocation().getY() + (( target.getY() - this.getLocation().getY() ) * (elapsed / (float)this.movementSpeed));
            this.sprite.setPosition(x, y);
        } else {
            // Movement time completed. Finish the move
            this.moveStart = -1;

            // Use a conditional in case something else moved to this location somehow
            if (this.getLocation().getOccupant() == this) this.getLocation().setOccupant(null);
            this.setLocation(this.target);
            this.sprite.setPosition(target.getX(), target.getY());
            this.arriveAtTile(target);
            this.target.setOccupant(this);
            this.target.setBusy(false);
            this.target = null;
        }
    }
}
