package theskidster.java.main;

import java.awt.Graphics2D;
import theskidster.java.input.Input;
import theskidster.java.resource.Resource;

/**
 * @author J Hoffman
 * Created: Dec 29, 2017
 */

public interface MainLogicLoop {
    
    /**
     * Called only once prior to update and render, typically used to load assets, instantiate
     * objects, and initialize variables.
     * 
     * @param mc - State machine that acts as a "central hub" through which data is handled.
     * @param re - Manages various external resources such as sounds, sprites, etc.
     */
    void init(MainContainer mc, Resource re);
    
    /**
     * Updates game logic, this includes things like; entity position, score, etc.
     * 
     * @param mc - State machine that acts as a "central hub" through which data is handled.
     * @param in - Object that handles input received from the keyboard.
     */
    void update(MainContainer mc, Input in);
    
    /**
     * Renders state to the screen. Since this project was programmed to use only java native 
     * libraries, all rendering is done on the CPU. As such, I'd recommend keeping complexity 
     * to a minimum here.
     * 
     * @param g - Graphics object required to draw to the screen.
     */
    void render(Graphics2D g);
    
}