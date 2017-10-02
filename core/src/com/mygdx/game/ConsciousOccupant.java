package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

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
    private Animation<Texture> leftAnimation;
    private Animation<Texture> rightAnimation;
    private Animation<Texture> downAnimation;
    private Animation<Texture> upAnimation;
    private Animation<Texture> currentAnimation;
    private Orientation orientation;
    private long moveStart;
    private Tile target;
    private double movementSpeed;

    public ConsciousOccupant(CharacterType characterType, Orientation orientation, float xpos, float ypos) {

        // This is kind of a hack since super has to be the first line,
        // but I need to get the sprites based on the character type first
        super(new Sprite(Game.WILSON_FACING_LEFT_SPRITE), xpos, ypos);
        Sprite[] sprites = TypeMappings.getCharacterSprites(characterType);
        Array<Animation<Texture>> animations = TypeMappings.getCharacterAnimations(characterType);
        if (sprites == null) {
            System.err.println("Invalid character type passed to ConsciousOccupant. Cannot get sprite names.");
            System.exit(1);
        }
        this.leftSprite = sprites[0];
        this.rightSprite = sprites[1];
        this.downSprite = sprites[2];
        this.upSprite = sprites[3];
        this.leftAnimation = animations.get(0);
        this.rightAnimation = animations.get(1);
        this.downAnimation = animations.get(2);
        this.upAnimation = animations.get(3);


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
        if (movePossible(target)) {
            this.currentAnimation = this.leftAnimation;
            startMove(target);
        } else {
            // Play bump sound
        }
    }

    public void moveRight() {
        this.setOrientation(Orientation.RIGHT);
        Tile target = this.getLocation().getRightNeighbor();
        if (movePossible(target)) {
            this.currentAnimation = this.rightAnimation;
            startMove(target);
        } else {
            // Play bump sound
        }
    }

    public void moveDown() {
        this.setOrientation(Orientation.DOWN);
        Tile target = this.getLocation().getDownNeighbor();
        if (movePossible(target)) {
            this.currentAnimation = this.downAnimation;
            startMove(target);
        } else {
            // Play bump sound
        }
    }

    public void moveUp() {
        this.setOrientation(Orientation.UP);
        Tile target = this.getLocation().getUpNeighbor();
        if (movePossible(target)) {
            this.currentAnimation = this.upAnimation;
            startMove(target);
        } else {
            // Play bump sound
        }
    }

    // Together these methods will enable movement to occupied tiles
    public abstract boolean isTileEnterable(Tile target);
    public abstract void arriveAtTile(Tile target);

    private boolean movePossible(Tile target) {
        return target != null && this.isTileEnterable(target);
    }

    private void startMove(Tile target) {
        if ( target != null ) {
            this.moveStart = System.currentTimeMillis();
            this.target = target;

            // Set the target as occupied so nothing else can occupy it
            // This means both the target and current tile are occupied by this
            // occupant.
            target.setBusy(true);
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
            this.currentAnimation = null;
        }
    }

    public void draw(SpriteBatch batch) {
        if (this.currentAnimation != null) {
            float delta = System.currentTimeMillis() - this.moveStart;
            System.out.println(delta + " " + this.currentAnimation.getFrameDuration() + " " + this.currentAnimation.getAnimationDuration());
            batch.draw(this.currentAnimation.getKeyFrame(delta), this.getX(), this.getY(), Game.TILE_WIDTH, Game.TILE_HEIGHT);
        } else {
            super.draw(batch);
        }
    }
}
