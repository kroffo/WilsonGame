package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kennethroffo on 9/9/17.
 */
public class Room {

    public static final int NUMBER_OF_COLUMNS = 10;
    public static final int NUMBER_OF_ROWS = 8;
    private float tileWidth;
    private float tileHeight;
    private Tile[][] tiles;
    private Player player;

    public Room(String[][] layout, float width, float height) {
        boolean startSpecified = false;

        tileWidth = width / NUMBER_OF_COLUMNS;
        tileHeight = height / NUMBER_OF_ROWS;
        this.tiles = new Tile[layout.length][layout[0].length];

        // Traverse the layout, creating the Tiles and their contents
        for (int i = 0; i < layout.length; ++i) {
            String[] row = layout[i];

            // For each tile in the row
            for (int j = 0; j < row.length; ++j) {

                // Get the details, which should be the tile type, optionally followed by a colong and occupant type
                String[] details = row[j].trim().split(":");
                String type = details[0];
                Occupant occupant = null;

                // Get the name of the sprite file from the type of tile and if it doesn't exist, error
                String spriteName = TypeMappings.getSpriteNameFromType(type);
                Tile.TileType tileType = TypeMappings.getTileType(type);
                if (spriteName == null) {
                    System.err.println("Tile of type: " + type + " does not exist.");
                    System.exit(1);
                }

                // This positioning issue should really be fixed. For some reason the positions don't line up to
                // fill the screen how you would expect.
                float xpos = ((float)(j) - ((float)NUMBER_OF_COLUMNS)/2) * tileWidth + 3*tileWidth/2;
                float ypos = -(((float)(i) - ((float)NUMBER_OF_ROWS)/2) * tileHeight + tileHeight/4);

                Sprite s = new Sprite(new Texture(spriteName));
                s.setOriginCenter();
                float scaleW = tileWidth / s.getWidth();
                float scaleH = tileHeight / s.getHeight();
                s.setSize(s.getWidth() * scaleW, s.getHeight() * scaleH);
                s.setPosition(xpos, ypos);
                this.tiles[i][j] = new Tile(s, this, tileType);

                // If an occupant type is specified, handle it.
                if (details.length > 1) {
                    String occupantType = details[1].trim();
                    if (occupantType.equals("start")) {
                        if (startSpecified) {
                            System.err.println("Start specified already -- Multiple starts is prohibited.");
                            System.exit(1);
                        }
                        // Start has been found. Mark the coordinates, and set up the player
                        occupant = Player.createPlayer(tileWidth, tileHeight, scaleW, scaleH, xpos, ypos);
                    } else {
                        String occupantSpriteName = TypeMappings.getSpriteNameFromType(occupantType);
                        if (occupantSpriteName == null) {
                            System.err.println("Occupant of type: " + occupantType + " does not exist.");
                            System.exit(1);
                        }
                        Sprite o = new Sprite(new Texture(occupantSpriteName));
                        scaleW = tileWidth / o.getWidth();
                        scaleH = tileHeight / o.getHeight();
                        o.setOriginCenter();
                        o.setSize(o.getWidth() * scaleW, o.getHeight() * scaleH);
                        o.setPosition(xpos, ypos);
                        occupant = TypeMappings.createAppropriateOccupant(occupantType, o);
                    }
                    this.tiles[i][j].setOccupant(occupant);
                    occupant.setLocation(this.tiles[i][j]);
                }
            }
        }

        player = Player.getPlayer();
        if (player == null) {
            System.err.println("No player found in room. Exiting.");
            System.exit(1);
        }
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < this.tiles.length; ++i) {
            for (int j = 0; j < this.tiles[i].length; ++j) {
                this.tiles[i][j].draw(batch);
            }
        }
    }

    public Tile getLeftNeighbor(Tile t) {
        Tile neighbor = null;
        for (int i = 0; i < this.tiles.length; ++i) {
            for (int j = 0; j < this.tiles[i].length; ++j) {
                // Found the tile, now return the neighbor
                if (this.tiles[i][j] == t) {
                    try {
                        neighbor = this.tiles[i][j-1];
                    } catch (IndexOutOfBoundsException e) {
                        // t must have been an edge, and thus has no neighbor this way
                        neighbor = null;
                    }
                }
            }
        }
        return neighbor;
    }

    public Tile getRightNeighbor(Tile t) {
        Tile neighbor = null;
        for (int i = 0; i < this.tiles.length; ++i) {
            for (int j = 0; j < this.tiles[i].length; ++j) {
                // Found the tile, now return the neighbor
                if (this.tiles[i][j] == t) {
                    try {
                        neighbor = this.tiles[i][j+1];
                    } catch (IndexOutOfBoundsException e) {
                        // t must have been an edge, and thus has no neighbor this way
                        neighbor = null;
                    }
                }
            }
        }
        return neighbor;
    }

    public Tile getDownNeighbor(Tile t) {
        Tile neighbor = null;
        for (int i = 0; i < this.tiles.length; ++i) {
            for (int j = 0; j < this.tiles[i].length; ++j) {
                // Found the tile, now return the neighbor
                if (this.tiles[i][j] == t) {
                    try {
                        neighbor = this.tiles[i+1][j];
                    } catch (IndexOutOfBoundsException e) {
                        // t must have been an edge, and thus has no neighbor this way
                        neighbor = null;
                    }
                }
            }
        }
        return neighbor;
    }

    public Tile getUpNeighbor(Tile t) {
        Tile neighbor = null;
        for (int i = 0; i < this.tiles.length; ++i) {
            for (int j = 0; j < this.tiles[i].length; ++j) {
                // Found the tile, now return the neighbor
                if (this.tiles[i][j] == t) {
                    try {
                        neighbor = this.tiles[i-1][j];
                    } catch (IndexOutOfBoundsException e) {
                        // t must have been an edge, and thus has no neighbor this way
                        neighbor = null;
                    }
                }
            }
        }
        return neighbor;
    }
}
