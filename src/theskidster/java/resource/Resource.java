package theskidster.java.resource;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import theskidster.java.world.World;

/**
 * @author J Hoffman
 * Created: Jan 5, 2018
 */

public class Resource {
    
    private BufferedImage bi;
    public static World wo;
    
    public static Map <String, BufferedImage> images;
    public static Map <String, World> levels;
    //needs a Map for sounds but eh- I'm too lazy.
    
    /**
     * Initializes collections that resources will be stored in. Resources defined in the try
     * block below are fallback assets, as demonstrated in the Test State.
     */
    public Resource() {
        images = new HashMap<>();
        levels = new HashMap<>();
        
        try {
            
            images.put("nullChicken", loadImage("img_null.png"));
            levels.put("testLevel", loadLevel("map_java_test.txt"));
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads an image from the filename specified.
     * 
     * @param sFileName - Desired file to be loaded.
     * @return          - Image associated with that file.
     */
    public BufferedImage loadImage(String sFileName) {
        try {
            bi = ImageIO.read(getClass().getResource("/theskidster/java/assets/" + sFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi;
    }
    
    /**
     * Gets the image from the hashmap, displays the infamous null chicken if it cant find it.
     * 
     * @param sName - Key in the form of a name associated with the filename when it was loaded.
     * @return      - Image of the specified key.
     */
    public static BufferedImage getImage(String sName) {
        if(!images.containsKey(sName)) {
            return images.get("nullChicken");
        } else {
            return images.get(sName);
        }
    }
    
    /**
     * Loads a level from the filename specified.
     * 
     * @param sFileName - Desired file to be loaded.
     * @return          - Level data associated with that file.
     */
    public World loadLevel(String sFileName) {
        wo = new World(sFileName);
        return wo;
    }
    
    /**
     * Gets the level from the hashmap. Needs to be written a little oddly to accommodate for 
     * how the World class loads in level data.
     * 
     * @param sName - Key in the form of a name associated with the filename when it was loaded.
     * @param g     - Graphics object required to draw to the screen.
     * @return      - Level of the specified key.
     */
    public static World getLevel(String sName, Graphics2D g) {
        wo.render(g);
        return levels.get(sName);
    }
    
}