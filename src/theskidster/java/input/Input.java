package theskidster.java.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import theskidster.java.main.MainContainer;
import theskidster.java.menu.Menu;

/**
 * @author J Hoffman
 * Created: Dec 27, 2017
 */

public class Input implements KeyListener {

    private int keyCode;
    
    private boolean isReleased;
    
    private ArrayList<Input> al = new ArrayList<>(); //array list of pressed keys
    
    public Input() {
    }
    
    /**
     * Creates a new Input object that is used to find the keyCode of a keyEvent.
     * 
     * @param keyCode - Integer associated with the key pressed
     */
    public Input(int keyCode) {
        this.keyCode = keyCode;
    }
    
    /**
     * Unused, implementing KeyListener forced the inclusion of this method.
     * 
     * @param e - Event generated from a key being pressed.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    /**
     * Automatically called whenever a key is pressed. The keyCode in the form of a new Input object will
     * be temporarily added to the ArrayList. This approach allows us to efficiently make use of keyEvents 
     * without requiring each keyCode to be defined manually, or forcing each state to override KeyListener 
     * methods.
     * 
     * @param e - Event generated from a key being pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Input in = new Input(e.getKeyCode());
        if(!al.contains(in)) al.add(new Input(e.getKeyCode()));
    }

    /**
     * Similar to the above method, however it will remove Input objects once the key is released, some 
     * additional "global" controls are defined here to make debugging easier.
     * 
     * @param e - Event generated from a key being released.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        Input in = new Input(e.getKeyCode());
        if(al.contains(in)) al.remove(in);
        
        if(KeyEvent.VK_F1 == e.getKeyCode()) MainContainer.enableDebug = !MainContainer.enableDebug;
        
        if(KeyEvent.VK_ESCAPE == e.getKeyCode()) {
            MainContainer.isPaused = !MainContainer.isPaused;
            Menu.choice = 0;
        }
    }
    
    /**
     * Honestly, I made this like 6 months ago, your guess is as good as mine.
     * 
     * @param o - The object to be compared to?
     * @return  - keyCode associated with the input object.
     */
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Input)) return false;
        return ((Input) o).keyCode == keyCode;
    }
    
    /**
     * @return - keyCode
     */
    @Override
    public int hashCode() {
        return keyCode;
    }
    
    /**
     * Must be used in a if statement, obtains an integer associated with the key pressed and 
     * temporarily stores it in an ArrayList where it can then be properly processed. 
     * 
     * @param keyCode       - Integer associated with the key pressed
     * @param isReleased    - 
     * @return              - True if key specified by keyCode was pressed.
     */
    public boolean getInput(int keyCode, boolean isReleased) {
        if(isReleased) {
            Input in = new Input(keyCode);
            int keyIndex = al.indexOf(in);
        
            if(keyIndex == -1) return false;
            if(al.get(keyIndex).isReleased) return false;
            al.get(keyIndex).isReleased = true;
            
            return true;
        } else {
            return al.contains(new Input(keyCode));
        }
    }
    
}