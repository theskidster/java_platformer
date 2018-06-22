package theskidster.java.state;

import java.awt.Graphics2D;
import theskidster.java.entity.EntityPlayer;
import theskidster.java.input.Input;
import theskidster.java.main.MainContainer;
import theskidster.java.resource.Resource;

/**
 * @author J Hoffman
 * Created: Dec 29, 2017
 */

public class StateStart extends State {
    
    EntityPlayer ep;
    
    /** 
     * @see theskidster.java.main.MainLogicLoop#init(theskidster.java.main.MainContainer, theskidster.java.resource.Resource) 
     */
    @Override
    public void init(MainContainer mc, Resource re) {
        ep = new EntityPlayer(Resource.wo);
        ep.setPos(200, 40);
    }

    /**
     * @see theskidster.java.main.MainLogicLoop#update(theskidster.java.main.MainContainer, theskidster.java.input.Input) 
     */
    @Override
    public void update(MainContainer mc, Input in) {
        ep.update(mc, in);
    }

    /**
     * @see theskidster.java.main.MainLogicLoop#render(java.awt.Graphics2D) 
     */
    @Override
    public void render(Graphics2D g) {
        Resource.getLevel("testLevel", g);
        
        ep.render(g);
    }

    /**
     * @see theskidster.java.state.State#getID()
     */
    @Override
    public int getID() {
        return STATE_START;
    }
    
}