package theskidster.java.entity;

import java.awt.Graphics2D;
import theskidster.java.input.Input;
import theskidster.java.main.MainContainer;
import theskidster.java.main.MainLogicLoop;
import theskidster.java.resource.Resource;
import theskidster.java.world.World;

/**
 * @author J Hoffman
 * Created: Jan 5, 2018
 */

public abstract class Entity implements MainLogicLoop {

    protected int width;
    protected int height;
    
    protected double xPos;
    protected double yPos;
    
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean botLeft;
    protected boolean botRight;
    
    protected World wo;
    
    /**
     * Inclusion forced by implementing MainLogicLoop. Left unused in favor of initializing entity objects through their 
     * constructors instead.
     * 
     * @see theskidster.java.main.MainLogicLoop#init(theskidster.java.main.MainContainer, theskidster.java.resource.Resource) 
     */
    @Override
    public abstract void init(MainContainer mc, Resource re);

    /**
     * @see theskidster.java.main.MainLogicLoop#update(theskidster.java.main.MainContainer, theskidster.java.input.Input) 
     */
    @Override
    public abstract void update(MainContainer mc, Input in);

    /**
     * @see theskidster.java.main.MainLogicLoop#render(java.awt.Graphics2D) 
     */
    @Override
    public abstract void render(Graphics2D g);
    
    /**
     * Sets the position of the entity.
     * 
     * @param xPos - Desired x position of entity.
     * @param yPos - Desired y position of entity.
     */
    public abstract void setPos(int xPos, int yPos);
    
    /**
     * Used to calculate the "axis aligned bounding box" or collision box of the entity. A more in-depth explanation of 
     * this theory can be found at http://www.metanetsoftware.com/technique/tutorialA.html
     * 
     * @param xPos - Next x position relative to the current one.
     * @param yPos - Next y position relative to the current one.
     */
    public abstract void aabb(double xPos, double yPos);
    
}