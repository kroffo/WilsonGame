package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

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

    public enum AttackType {
        STRIKE,
        FIRE
    }

    private final static double STRIKE_SPEED = 250.0;
    private final static double FIRE_SPEED = 250.0;

    // A sprite for facing each direction
    private Sprite leftSprite;
    private Sprite rightSprite;
    private Sprite downSprite;
    private Sprite upSprite;

    // An animation for walking each direction
    private Animation<Texture> leftAnimation;
    private Animation<Texture> rightAnimation;
    private Animation<Texture> downAnimation;
    private Animation<Texture> upAnimation;

    // An animation reference for the animation currently being executed when one exists
    private Animation<Texture> currentAnimation;

    // A sprite for striking in each direction. The appropriate weapon will be overlayed on this sprite.
    private Sprite leftStrikeSprite;
    private Sprite rightStrikeSprite;
    private Sprite downStrikeSprite;
    private Sprite upStrikeSprite;

    // A sprite to hold the appropriate strike sprite during a strike
    private Sprite strikeSprite;

    // A sprite to hold the appropriate sword sprite to be used during a strike if necessary
    private Sprite swordSprite;

    private AttackType attackType;

    // Some tracker variables
    private Orientation orientation;
    private long moveStart;
    private long attackStart;
    private Tile target;
    private double movementSpeed;
    private double strikeSpeed;
    private double fireSpeed;
    private Sword sword;
    private Bow bow;
    private int arrows;
    private int potions;
    private int max_health;
    private int health;
    private HashMap<String, Integer> keys;


    public ConsciousOccupant(CharacterType characterType, Orientation orientation, float xpos, float ypos, int max_health) {

        // This is kind of a hack since super has to be the first line,
        // but I need to get the sprites based on the character type first
        super(new Sprite(Game.WILSON_FACING_LEFT_SPRITE), xpos, ypos);

        // Get all the sprites and animations from TypeMappings.
        // Perhaps I should think about not doing this, and instead
        // just getting each individual sprite when needed
        Sprite[] sprites = TypeMappings.getCharacterSprites(characterType);
        Array<Animation<Texture>> animations = TypeMappings.getCharacterWalkAnimations(characterType);
        Sprite[] strikeSprites = TypeMappings.getCharacterStrikeSprites(characterType);
        this.leftSprite = sprites[0];
        this.rightSprite = sprites[1];
        this.downSprite = sprites[2];
        this.upSprite = sprites[3];
        this.leftAnimation = animations.get(0);
        this.rightAnimation = animations.get(1);
        this.downAnimation = animations.get(2);
        this.upAnimation = animations.get(3);
        this.leftStrikeSprite = strikeSprites[0];
        this.rightStrikeSprite = strikeSprites[1];
        this.downStrikeSprite = strikeSprites[2];
        this.upStrikeSprite = strikeSprites[3];

        // Sets the orientation, as well as setting the sprite to the appropriate sprite
        this.setOrientation(orientation);

        this.attackType = null;
        this.moveStart = -1;
        this.attackStart = -1;
        this.target = null;
        this.movementSpeed = TypeMappings.getCharacterTypeWalkSpeed(characterType);
        this.strikeSpeed = STRIKE_SPEED;
        this.fireSpeed = FIRE_SPEED;


        this.sword = null;
        this.bow = null;
        this.potions = 0;
        this.arrows = 0;
        this.keys = new HashMap<>();
        this.max_health = max_health;
        this.health = max_health;
        String[] keyColors = TypeMappings.getKeyColors();
        for (String color : keyColors)
            this.keys.put(color, 0);

        // Add to steppables, so the step function is called every render cycle.
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
        if (this.moveStart > -1) return true;
        if (this.attackType != null) return true;

        return false;
    }

    public void step(double delta) {
        if (this.moveStart > -1) {
            this.stepMovement();
        } else if (this.attackStart > -1) {
            if (this.attackType == AttackType.STRIKE) {
                this.stepStrike();
            } else if (this.attackType == AttackType.FIRE) {
                this.stepFire();
            } else {
                System.err.println("attackStart is >-1 but attack type is neither STRIKE nor FIRE");
            }
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

    public abstract boolean ableToStrike();

    // Method to be used to start a melee attack
    public void strike() {
        if (this.ableToStrike()) {
            this.attackType = AttackType.STRIKE;
            this.attackStart = System.currentTimeMillis();
            switch(this.orientation) {
                case LEFT:
                    this.strikeSprite = this.leftStrikeSprite;
                    break;
                case RIGHT:
                    this.strikeSprite = this.rightStrikeSprite;
                    break;
                case DOWN:
                    this.strikeSprite = this.downStrikeSprite;
                    break;
                case UP:
                    this.strikeSprite = this.upStrikeSprite;
                    break;
                default:
                    this.strikeSprite = this.rightStrikeSprite;
            }
            this.strikeSprite.setPosition(this.sprite.getX(), this.sprite.getY());
        }
    }

    public void stepStrike() {
        long elapsed = System.currentTimeMillis() - this.attackStart;
        if (elapsed < this.strikeSpeed) {

        } else {
            this.attackStart = -1;
            this.attackType = null;
            this.strikeSprite = null;
        }
    }

    // Method to be used to start a ranged attack
    public void fire() {

    }

    public void stepFire() {

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
            batch.draw(this.currentAnimation.getKeyFrame(delta), this.getX(), this.getY(), Game.TILE_WIDTH, Game.TILE_HEIGHT);
        } else if (this.attackType == AttackType.STRIKE) {
            this.strikeSprite.draw(batch);
        } else {
            super.draw(batch);
        }
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }

    public Sword getSword() {
        return this.sword;
    }

    public void setBow(Bow bow) {
        this.bow = bow;
    }

    public Bow getBow() {
        return this.bow;
    }

    public void addPotion() {
        ++this.potions;
    }

    public void usePotion() {
        if (this.potions > 0) {
            --this.potions;
            this.health += (this.health < this.max_health - Potion.EFFECTIVENESS) ? Potion.EFFECTIVENESS : this.max_health - this.health;
        }
    }

    public int getNumberOfPotions() {
        return this.potions;
    }

    public void addArrows(int numberOfArrows) {
        this.arrows += numberOfArrows;
    }

    public void removeArrows(int numberOfArrows) {
        this.arrows -= numberOfArrows;
    }

    public int getNumberOfArrows() {
        return this.arrows;
    }

    public void addKey(Key key) {
        String color = key.getColor();
        if (this.keys.containsKey(color)) {
            this.keys.put(color, this.keys.get(color)+1);
        } else {
            System.err.println("Keys with color \"" + color + "\" should not exist.");
            System.exit(1);
        }
    }

    public boolean removeKey(String color) {
        if (this.keys.containsKey(color)) {
            if (this.keys.get(color) > 0) {
                this.keys.put(color, this.keys.get(color)-1);
                return true;
            }
        } else {
            System.err.println("Keys with color \"" + color + "\" do not exist.");
        }
        return false;
    }

    public int getNumberOfColoredKeys(String color) {
        return this.keys.get(color);
    }

    public int getHealth() {
        return this.health;
    }

    public abstract boolean collectsItems();

    public boolean isCollectible() {
        return false;
    }
}
