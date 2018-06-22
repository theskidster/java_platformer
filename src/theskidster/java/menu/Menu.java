package theskidster.java.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import theskidster.java.input.Input;
import theskidster.java.main.MainContainer;

/**
 * @author J Hoffman
 * Created: Dec 29, 2017
 */

public enum Menu {

    //works as a little ity-bitty state machine inside our state machine!
    
    MENU_MAIN,
    MENU_FPS,
    MENU_STATE,
    MENU_SCALE,
    MENU_COLOR;
    
    public static int choice = 0;
    
    private String sStartOptions[] = {
        "resume",
        "exit program",
        "set fps",
        "set state",
        "set scale",
        "set bg color"
    };
    
    private String sFPSOptions[] = {
        "15",
        "30",
        "60 (recommended)",
        "80",
        "120",
        "back"
    };
    
    private String sStateOptions[] = {
        "level 1",
        "level 2",
        "back"
    };
        
    private String sScaleOptions[] = {
        "X1",
        "X2",
        "X3",
        "X4",
        "X5",
        "X6",
        "back"
    };
            
    private String sColorOptions[] = {
        "white",
        "black",
        "red",
        "orange",
        "yellow",
        "green",
        "blue",
        "purple",
        "back"
    };
    
    /**
     * Controls the debug menus flow. Kind of wrote myself into a corner here since keyEvents need to be 
     * handled via if statement, but I used a switch where I could thats gotta count for something right?
     * 
     * @param mc - State machine that acts as a "central hub" through which data is handled.
     * @param g  - Graphics object required to draw to the screen.
     * @param in - Object that handles input received from the keyboard.
     */
    public void navigation(MainContainer mc, Graphics2D g, Input in) {
        switch(this) {
            case MENU_MAIN:
                createMenu(sStartOptions, 4, 64, 12, mc, g, in);
                
                if(choice == 0 && in.getInput(KeyEvent.VK_ENTER, true)) MainContainer.isPaused = false;
                if(choice == 1 && in.getInput(KeyEvent.VK_ENTER, true)) System.exit(0);
                if(choice == 2 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setMenu(MENU_FPS);
                if(choice == 3 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setMenu(MENU_STATE);
                if(choice == 4 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setMenu(MENU_SCALE);
                if(choice == 5 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setMenu(MENU_COLOR);
                break;
            
            case MENU_FPS:
                createMenu(sFPSOptions, 4, 64, 12, mc, g, in);
                
                if(choice == 0 && in.getInput(KeyEvent.VK_ENTER, true)) mc.fps = 15;
                if(choice == 1 && in.getInput(KeyEvent.VK_ENTER, true)) mc.fps = 30;
                if(choice == 2 && in.getInput(KeyEvent.VK_ENTER, true)) mc.fps = 60;
                if(choice == 3 && in.getInput(KeyEvent.VK_ENTER, true)) mc.fps = 80;
                if(choice == 4 && in.getInput(KeyEvent.VK_ENTER, true)) mc.fps = 120;
                if(choice == 5 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setMenu(MENU_MAIN);
                break;
                
            case MENU_STATE:
                createMenu(sStateOptions, 4, 64, 12, mc, g, in);
                
                if(choice == 0 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setState(0);
                if(choice == 1 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setState(1);
                if(choice == 2 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setMenu(MENU_MAIN);
                break;
                
            case MENU_SCALE:
                createMenu(sScaleOptions, 4, 64, 12, mc, g, in);
                
                if(choice == 0 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setScale(1);
                if(choice == 1 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setScale(2);
                if(choice == 2 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setScale(3);
                if(choice == 3 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setScale(4);
                if(choice == 4 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setScale(5);
                if(choice == 5 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setScale(6);
                if(choice == 6 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setMenu(MENU_MAIN);
                break;
                
            case MENU_COLOR:
                createMenu(sColorOptions, 4, 64, 12, mc, g, in);
                
                if(choice == 0 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setBGColor(Color.decode("#EEEEEE"));
                if(choice == 1 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setBGColor(Color.BLACK);
                if(choice == 2 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setBGColor(Color.decode("#AC161D"));
                if(choice == 3 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setBGColor(Color.decode("#FF9900"));
                if(choice == 4 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setBGColor(Color.YELLOW);
                if(choice == 5 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setBGColor(Color.decode("#00CC33"));
                if(choice == 6 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setBGColor(Color.BLUE);
                if(choice == 7 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setBGColor(Color.decode("#990099"));
                if(choice == 8 && in.getInput(KeyEvent.VK_ENTER, true)) mc.setMenu(MENU_MAIN);
                break;
        }
    }
    
    /**
     * Creates new menu or sub-menu, establishes general look and feel of the GUI.
     * 
     * @param sOptions  - Options available
     * @param xPos      - X position of the menu
     * @param yPos      - Y position of the menu
     * @param spacing   - The line space between options 
     * @param mc        - State machine that acts as a "central hub" through which data is handled.
     * @param g         - Graphics object required to draw to the screen.
     * @param in        - Object that handles input received from the keyboard.
     */
    public void createMenu(String[] sOptions, int xPos, int yPos, int spacing, MainContainer mc, Graphics2D g, Input in) {
        if(!MainContainer.enableDebug) yPos = 4; 
        
        g.setColor(Color.decode("#13161D"));
            g.fillRect(xPos, yPos, xPos + 120, spacing * sOptions.length + 4);
        g.setColor(Color.decode("#ABB2BF"));
        
        if(in.getInput(KeyEvent.VK_UP, true)) choice--;
        if(choice == -1) choice = sOptions.length - 1;
        
        if(in.getInput(KeyEvent.VK_DOWN, true)) choice++;
        if(choice == sOptions.length) choice = 0;
        
        for(int i = 0; i < sOptions.length; i++) {
            if(i == choice) g.drawString(">", xPos + 4, yPos + i * spacing + 12);
            g.drawString(sOptions[i], xPos + spacing + 2, yPos + i * spacing + 12);
        }
    }
    
}
