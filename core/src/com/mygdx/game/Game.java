package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends ApplicationAdapter {

    // Global list of conscious occupants to step through each turn
    public static ArrayList<ConsciousOccupant> CONSCIOUS_OCCUPANTS = new ArrayList<>();
    public static float TILE_WIDTH;
    public static float TILE_HEIGHT;
    public static Sprite ARROW_SPRITE;
    public static Sprite ARROWS_SPRITE;
    public static Sprite BLUE_KEY_SPRITE;
    public static Sprite BOW_SPRITE;
    public static Sprite GREEN_KEY_SPRITE;
    public static Sprite PINK_KEY_SPRITE;
    public static Sprite RED_KEY_SPRITE;
    public static Sprite SWORD_SPRITE;
    public static Sprite WATER_TILE_SPRITE;
    public static Sprite WILSON_FACING_LEFT_SPRITE;
    public static Sprite WILSON_FACING_RIGHT_SPRITE;
    public static Sprite WILSON_FACING_DOWN_SPRITE;
    public static Sprite WILSON_FACING_UP_SPRITE;
    public static Sprite WOOD_TILE_SPRITE;
    public static Sprite YELLOW_KEY_SPRITE;

    SpriteBatch batch;
    private OrthographicCamera camera;
	private float screenWidth, screenHeight;
	private Room room;

	// Some variables to allow orientation switching without moving
    // If a button is held for more than movementHoldCount frames,
    // movement will begin
	private int holdCount = 0;
    private final int movementHoldCount = 8;

	@Override
	// Called at start
	public void create () {
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        init_base_sprites(screenWidth, screenHeight);

        camera = new OrthographicCamera(screenHeight, screenWidth);
        camera.setToOrtho(false);

        try {
            Scanner roomScanner = new Scanner(new File("room1.csv"));
            ArrayList<String[]> rows = new ArrayList<>();
            int num_cols = -1;
            while (roomScanner.hasNextLine()) {
                String[] cols = roomScanner.nextLine().split(",");
                if (num_cols == -1)
                    num_cols = cols.length;
                else if (num_cols != cols.length) {
                    System.err.println("Room file has differing numbers of columns per row.");
                    System.exit(1);
                }
                rows.add(cols);
            }
            String[][] layout = rows.toArray(new String[rows.size()][num_cols]);

            room = new Room(layout, camera.viewportWidth, camera.viewportHeight);

        } catch (FileNotFoundException e) {
            System.err.println("Room load file not found.");
            System.exit(1);
        }

        batch = new SpriteBatch();
	}

	@Override
	// Called once per frame
	public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float delta = Gdx.graphics.getDeltaTime(); // Acquire elapsed animation time

        batch.setProjectionMatrix(camera.combined);
        Player player = Player.getPlayer();

        if (!player.isBusy()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                holdCount = 0;
                player.attack();
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.setOrientation(ConsciousOccupant.Orientation.LEFT);
                if (++holdCount > movementHoldCount)
                    player.moveLeft();
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.setOrientation(ConsciousOccupant.Orientation.RIGHT);
                if (++holdCount > movementHoldCount)
                    player.moveRight();
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                player.setOrientation(ConsciousOccupant.Orientation.DOWN);
                if (++holdCount > movementHoldCount)
                    player.moveDown();
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                player.setOrientation(ConsciousOccupant.Orientation.UP);
                if (++holdCount > movementHoldCount)
                    player.moveUp();
            } else {
                holdCount = 0;
            }
        }

        for (ConsciousOccupant c : CONSCIOUS_OCCUPANTS) {
            c.step();
        }

        camera.position.set(player.getCenterX(), player.getCenterY(), 0);
        camera.update();

        batch.begin();
        room.draw(batch);
        batch.end();
	}
	
	@Override
	// Called at finish
	public void dispose () {
		batch.dispose();
	}

	private void init_base_sprites(float width, float height) {
        Game.TILE_WIDTH = width / Room.NUMBER_OF_COLUMNS;
        Game.TILE_HEIGHT = height / Room.NUMBER_OF_ROWS;

        Game.ARROW_SPRITE = new Sprite(new Texture("Arrow.png"));
        Game.ARROW_SPRITE.setOriginCenter();
        Game.ARROW_SPRITE.setSize(
                Game.ARROW_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.ARROW_SPRITE.getWidth()),
                Game.ARROW_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.ARROW_SPRITE.getHeight())
        );
        Game.ARROWS_SPRITE = new Sprite(new Texture("Arrows.png"));
        Game.ARROWS_SPRITE.setOriginCenter();
        Game.ARROWS_SPRITE.setSize(
                Game.ARROWS_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.ARROWS_SPRITE.getWidth()),
                Game.ARROWS_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.ARROWS_SPRITE.getHeight())
        );
        Game.BLUE_KEY_SPRITE = new Sprite(new Texture("BlueKey.png"));
        Game.BLUE_KEY_SPRITE.setOriginCenter();
        Game.BLUE_KEY_SPRITE.setSize(
                Game.BLUE_KEY_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.BLUE_KEY_SPRITE.getWidth()),
                Game.BLUE_KEY_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.BLUE_KEY_SPRITE.getHeight())
        );
        Game.BOW_SPRITE = new Sprite(new Texture("Bow.png"));
        Game.BOW_SPRITE.setOriginCenter();
        Game.BOW_SPRITE.setSize(
                Game.BOW_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.BOW_SPRITE.getWidth()),
                Game.BOW_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.BOW_SPRITE.getHeight())
        );
        Game.GREEN_KEY_SPRITE = new Sprite(new Texture("GreenKey.png"));
        Game.GREEN_KEY_SPRITE.setOriginCenter();
        Game.GREEN_KEY_SPRITE.setSize(
                Game.GREEN_KEY_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.GREEN_KEY_SPRITE.getWidth()),
                Game.GREEN_KEY_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.GREEN_KEY_SPRITE.getHeight())
        );
        Game.PINK_KEY_SPRITE = new Sprite(new Texture("PinkKey.png"));
        Game.PINK_KEY_SPRITE.setOriginCenter();
        Game.PINK_KEY_SPRITE.setSize(
                Game.PINK_KEY_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.PINK_KEY_SPRITE.getWidth()),
                Game.PINK_KEY_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.PINK_KEY_SPRITE.getHeight())
        );
        Game.RED_KEY_SPRITE = new Sprite(new Texture("RedKey.png"));
        Game.RED_KEY_SPRITE.setOriginCenter();
        Game.RED_KEY_SPRITE.setSize(
                Game.RED_KEY_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.RED_KEY_SPRITE.getWidth()),
                Game.RED_KEY_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.RED_KEY_SPRITE.getHeight())
        );
        Game.SWORD_SPRITE = new Sprite(new Texture("Sword.png"));
        Game.SWORD_SPRITE.setOriginCenter();
        Game.SWORD_SPRITE.setSize(
                Game.SWORD_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.SWORD_SPRITE.getWidth()),
                Game.SWORD_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.SWORD_SPRITE.getHeight())
        );
        Game.WATER_TILE_SPRITE = new Sprite(new Texture("WaterTile.png"));
        Game.WATER_TILE_SPRITE.setOriginCenter();
        Game.WATER_TILE_SPRITE.setSize(
                Game.WATER_TILE_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.WATER_TILE_SPRITE.getWidth()),
                Game.WATER_TILE_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.WATER_TILE_SPRITE.getHeight())
        );
        Game.WILSON_FACING_LEFT_SPRITE = new Sprite(new Texture("WilsonFacingLeft.png"));
        Game.WILSON_FACING_LEFT_SPRITE.setOriginCenter();
        Game.WILSON_FACING_LEFT_SPRITE.setSize(
                Game.WILSON_FACING_LEFT_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.WILSON_FACING_LEFT_SPRITE.getWidth()),
                Game.WILSON_FACING_LEFT_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.WILSON_FACING_LEFT_SPRITE.getHeight())
        );
        Game.WILSON_FACING_RIGHT_SPRITE = new Sprite(new Texture("WilsonFacingRight.png"));
        Game.WILSON_FACING_RIGHT_SPRITE.setOriginCenter();
        Game.WILSON_FACING_RIGHT_SPRITE.setSize(
                Game.WILSON_FACING_RIGHT_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.WILSON_FACING_RIGHT_SPRITE.getWidth()),
                Game.WILSON_FACING_RIGHT_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.WILSON_FACING_RIGHT_SPRITE.getHeight())
        );
        Game.WILSON_FACING_DOWN_SPRITE = new Sprite(new Texture("WilsonFacingLeft.png"));
        Game.WILSON_FACING_DOWN_SPRITE.setOriginCenter();
        Game.WILSON_FACING_DOWN_SPRITE.setSize(
                Game.WILSON_FACING_DOWN_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.WILSON_FACING_DOWN_SPRITE.getWidth()),
                Game.WILSON_FACING_DOWN_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.WILSON_FACING_DOWN_SPRITE.getHeight())
        );
        Game.WILSON_FACING_UP_SPRITE = new Sprite(new Texture("WilsonFacingRight.png"));
        Game.WILSON_FACING_UP_SPRITE.setOriginCenter();
        Game.WILSON_FACING_UP_SPRITE.setSize(
                Game.WILSON_FACING_UP_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.WILSON_FACING_UP_SPRITE.getWidth()),
                Game.WILSON_FACING_UP_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.WILSON_FACING_UP_SPRITE.getHeight())
        );
        Game.WOOD_TILE_SPRITE = new Sprite(new Texture("WoodTile.png"));
        Game.WOOD_TILE_SPRITE.setOriginCenter();
        Game.WOOD_TILE_SPRITE.setSize(
                Game.WOOD_TILE_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.WOOD_TILE_SPRITE.getWidth()),
                Game.WOOD_TILE_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.WOOD_TILE_SPRITE.getHeight())
        );
        Game.YELLOW_KEY_SPRITE = new Sprite(new Texture("YellowKey.png"));
        Game.YELLOW_KEY_SPRITE.setOriginCenter();
        Game.YELLOW_KEY_SPRITE.setSize(
                Game.YELLOW_KEY_SPRITE.getWidth() * (Game.TILE_WIDTH / Game.YELLOW_KEY_SPRITE.getWidth()),
                Game.YELLOW_KEY_SPRITE.getHeight() * (Game.TILE_HEIGHT / Game.YELLOW_KEY_SPRITE.getHeight())
        );
    }
}
