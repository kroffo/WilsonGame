package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends ApplicationAdapter {

    // Global list of conscious occupants to step through each turn
    public static ArrayList<ConsciousOccupant> CONSCIOUS_OCCUPANTS = new ArrayList<>();

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
}
