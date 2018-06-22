package theskidster.java.state;

import java.awt.Graphics2D;
import theskidster.java.input.Input;
import theskidster.java.main.MainContainer;
import theskidster.java.resource.Resource;

/**
 * @author J Hoffman
 * Created: Dec 29, 2017
 */

public class StateTest extends State {

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
        g.drawImage(Resource.getImage(""), MainContainer.WIDTH / 2, MainContainer.HEIGHT / 2, null);
    }

    /**
     * @see theskidster.java.state.State#getID()
     */
    @Override
    public int getID() {
        return STATE_TEST;
    }
    
}