package theskidster.java.state;

import java.awt.Graphics2D;
import theskidster.java.input.Input;
import theskidster.java.main.MainContainer;
import theskidster.java.main.MainLogicLoop;
import theskidster.java.resource.Resource;

/**
 * @author J Hoffman
 * Created: Dec 29, 2017
 */

public abstract class State implements MainLogicLoop {

    public static int STATE_START = 0;
    public static int STATE_TEST = 1;
    
    /** 
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
     * Used to store states in a collection located in theskidster.java.main.MainContainer
     * 
     * @return - ID associated with the state.
     */
    public abstract int getID();
    
}