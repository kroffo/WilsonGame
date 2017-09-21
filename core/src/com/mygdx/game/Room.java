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
    private Tile[][] tiles;
    private Player player;

    public Room(String[][] layout, float width, float height) {

        this.tiles = new Tile[layout.length][layout[0].length];

        // Traverse the layout, creating the Tiles and their contents
        for (int i = 0; i < layout.length; ++i) {
            String[] row = layout[i];

            // For each tile in the row
            for (int j = 0; j < row.length; ++j) {

                // Get the details, which should be the tile type, optionally followed by a colong and occupant type
                String[] details = row[j].trim().split(":");
                String type = details[0];
                Occupant occupant;

                // Get the name of the sprite file from the type of tile and if it doesn't exist, error
                Sprite s = TypeMappings.getTileSpriteFromType(type);
                Tile.TileType tileType = TypeMappings.getTileType(type);
                if (s == null) {
                    System.err.println("Tile of type: " + type + " does not exist.");
                    System.exit(1);
                }

                // This positioning issue should really be fixed. For some reason the positions don't line up to
                // fill the screen how you would expect.
                float xpos = ((float)(j) - ((float)NUMBER_OF_COLUMNS)/2) * Game.TILE_WIDTH + 3*Game.TILE_WIDTH/2;
                float ypos = -(((float)(i) - ((float)NUMBER_OF_ROWS)/2) * Game.TILE_HEIGHT + Game.TILE_HEIGHT/4);

                s.setPosition(xpos, ypos);
                this.tiles[i][j] = new Tile(s, this, tileType);

                // If an occupant type is specified, handle it.
                if (details.length > 1) {
                    String occupantType = details[1].trim();
                    if (occupantType.equals("start")) {
                        if (Player.getPlayer() != null) {
                            System.err.println("Start specified already -- Multiple starts is prohibited.");
                            System.exit(1);
                        }
                        // Start has been found. Mark the coordinates, and set up the player
                        occupant = Player.createPlayer(xpos, ypos);
                    } else {
                        occupant = TypeMappings.createAppropriateOccupant(occupantType, xpos, ypos);
                        if (occupant == null) {
                            System.err.println("Occupant of type: " + occupantType + " does not exist.");
                            System.exit(1);
                        }
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
        for (Tile[] row : this.tiles)
            for (Tile tile : row)
                tile.draw(batch);
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
