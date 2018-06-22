package theskidster.java.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import theskidster.java.input.Input;
import theskidster.java.main.MainContainer;
import theskidster.java.main.MainLogicLoop;
import theskidster.java.resource.Resource;

/**
 * @author J Hoffman
 * Created: Jan 5, 2018
 */

public class World implements MainLogicLoop {

    private int xPos;
    private int yPos;
    private int world[][];
    private int width;
    private int height;
    public static final int TILE_SIZE = 16;
    
    /** 
     * @see theskidster.java.main.MainLogicLoop#init(theskidster.java.main.MainContainer, theskidster.java.resource.Resource) 
     */
    @Override
    public void init(MainContainer mc, Resource re) {
    }

    /**
     * @see theskidster.java.main.MainLogicLoop#update(theskidster.java.main.MainContainer, theskidster.java.input.Input) 
     */
    @Override
    public void update(MainContainer mc, Input in) {
    }

    /**
     * @see theskidster.java.main.MainLogicLoop#render(java.awt.Graphics2D) 
     */
    @Override
    public void render(Graphics2D g) {
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                int rc = world[row][col];
                
                if(rc == 0) {
                    g.setColor(g.getBackground());
                } else {
                    g.setColor(Color.DARK_GRAY);
                }
                
                g.fillRect(xPos + col * TILE_SIZE, yPos + row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }
    
    /**
     * Creates a new level using the data provided from the Resource class.
     * 
     * @param sFileName - Desired file to be parsed and read.
     */
    public World(String sFileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/theskidster/java/assets/" + sFileName)));
            
            width = Integer.parseInt(br.readLine());
            height = Integer.parseInt(br.readLine());
            
            world = new int[height][width];
            
            for(int row = 0; row < height; row++) {
                String sLine = br.readLine();
                String[] sTokens = sLine.split(" ");
                
                for(int col = 0; col < width; col++) {                    
                    world[row][col] = Integer.parseInt(sTokens[col]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Obtains the x position of tiles that comprise the level.
     * 
     * @return - X position of the tile.
     */
    public int getX() { 
        return xPos; 
    }
    
    /**
     * Sets the x position of a tile
     * 
     * @param xPos - Desired x position of the tile.
     */
    public void setX(int xPos) { 
        this.xPos = xPos; 
    }
    
    /**
     * Obtains the y position of tiles that comprise the level.
     * 
     * @return - Y position of the tile.
     */
    public int getY() { 
        return yPos; 
    }
    
    /**
     * Sets the y position of a tile
     * 
     * @param yPos - Desired Y position of the tile.
     */
    public void setY(int yPos) { 
        this.yPos = yPos; 
    }
    
    /**
     * Obtains the tile at the specified row and column. Used in conjunction with the 
     * axis aligned bounding box found in entity objects.
     * 
     * @param row - Tile row
     * @param col - Tile column
     * @return    - Position independent of pixels.
     */
    public int getTileAt(int row, int col) { 
        return world[row][col]; 
    }
    
    /**
     * Obtains all tiles in a column based off their shared x position.
     * 
     * @param xPos - Desired tile x position
     * @return     - Entire Column associated with that position.
     */
    public int getTileAtCol(int xPos) {
        return xPos / TILE_SIZE;
    }
    
    /**
     * Obtains all tiles in a row based off their shared y position.
     * 
     * @param yPos - Desired tile y position
     * @return     - Entire Row associated with that position.
     */
    public int getTileAtRow(int yPos) {
        return yPos / TILE_SIZE;
    }
    
}