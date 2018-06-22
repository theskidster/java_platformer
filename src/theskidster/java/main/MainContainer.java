package theskidster.java.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import theskidster.java.input.Input;
import theskidster.java.menu.Menu;
import theskidster.java.resource.Resource;
import theskidster.java.state.State;
import theskidster.java.state.StateStart;
import theskidster.java.state.StateTest;

/**
 * @author J Hoffman
 * Created: Dec 29, 2017
 */

public class MainContainer implements Runnable {

    public static final int WIDTH = 320;
    public static final int HEIGHT = 224;
    private int scale = 2;
    public int fps = 60;
    private int recordedFPS;
    private int frameCount;
    
    private long currTime;
    private long frameTime;
    private long varYieldTime;
    private long lastTime;
    
    public static boolean isPaused;
    public static boolean enableDebug = true;
    
    private JFrame jf = new JFrame("Simple 2D Platformer");
    private Canvas ca = new Canvas();
    private JPanel jp = (JPanel) jf.getContentPane();
    private Dimension di;
    private BufferStrategy bs;
    private Graphics2D g;
    private Input in = new Input();
    private HashMap hm = new HashMap();
    private State s;
    private Menu m = Menu.MENU_MAIN;
    private Thread th = Thread.currentThread();
    private Resource re = new Resource();
    
    /**
     * Controls the main loop.
     */
    @Override
    public void run() {
        while(th.isAlive()) {
            if(!isPaused) s.update(this, in);
            draw();
            sync(fps);
        }
        System.out.println("ERROR: main thread has stopped unexpectedly.");
        System.exit(0);
    }
    
    /**
     * Creates window, initializes jPanel, and establishes other utilities required for the 
     * game to function.
     */
    public MainContainer() {
        setScale(scale);
        jp.add(ca);
        
        ca.setIgnoreRepaint(true);
        ca.addKeyListener(in);
        ca.createBufferStrategy(3);
        bs = ca.getBufferStrategy();
        
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        
        ca.requestFocus();
        
        initStates();
    }
    
    /**
     * Initializes states, ALL existing states must be included here.
     */
    private void initStates() {
        addState(new StateStart());
        addState(new StateTest());
        
        setState(State.STATE_START);
    }
    
    /**
     * Adds states to hashmap
     * 
     * @param s - State to be added
     */
    private void addState(State s) {
        hm.put(s.getID(), s);
    }
    
    /**
     * Handles our delicate graphics object. Not the most ideal solution, but the best 
     * I could come up with using the standard library.
     */
    private void draw() {
        g = (Graphics2D) bs.getDrawGraphics();
        g.clearRect(0, 0, WIDTH * scale, HEIGHT * scale);
        g.scale(scale, scale);
        
        s.render(g);
        
        if(enableDebug) debug(g);
        
        if(isPaused) {
            drawMenu(g);
        } else {
            setMenu(Menu.MENU_MAIN);
        }
        
        g.dispose();
        bs.show();
    }
    
    /**
     * Draws the debug menu.
     * 
     * @param g - Graphics object required to draw to the screen.
     */
    private void drawMenu(Graphics2D g) {
        m.navigation(this, g, in);
    }
    
    /**
     * Attempts to increase performance by limiting the max amount of times the update and 
     * render methods can be called per second. This fixed-timestep implementation will 
     * dynamically change depending on system requirements for maximum consistency. A more 
     * in-depth explanation can be found at https://gafferongames.com/post/fix_your_timestep/
     * 
     * @param fps - Desired fps value
     */
    private void sync(int fps) {
        if(fps <= 0) return;
        
        long sleepTime = 1000000000 / fps; //calculate nanoseconds
        long yieldTime = Math.min(sleepTime, varYieldTime - sleepTime % (1000 * 1000));
        long overSleep = 0;
        
        try {
            while(th.isAlive()) {
                long time = System.nanoTime() - lastTime;
                
                if(time < sleepTime - yieldTime) {
                    Thread.sleep(1);
                } else if(time < sleepTime) {
                    Thread.yield(); //known to be unreliable in regards to cross-platform use.
                } else {
                    overSleep = time - sleepTime;
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);
            
            //dynamically change speed, increase if behind or decrease if ahead.
            if(overSleep > varYieldTime) {
                varYieldTime = Math.min(varYieldTime + 200 * 1000, sleepTime);
            } else if(overSleep < varYieldTime - 200 * 1000) {
                varYieldTime = Math.min(varYieldTime - 2 * 1000, 0);
            }
        }
    }
    
    /**
     * Obtains ID of the state object currently using the state instance.
     * 
     * @return - The ID associated with the state.
     */
    public int getState() {
        return s.getID();
    }
    
    /**
     * You'll never guess what this does!
     * 
     * @param stateID - Desired state we want to change to via ID
     */
    public void setState(int stateID) {
        s = (State) hm.get(stateID);
        
        s.init(this, re);
        run();
    }
    
    /**
     * Obtains the scale that the graphics object and window will be multiplied by.
     * 
     * @return - Current scale multiplier.
     */
    public int getScale() { 
        return scale;
    }
    
    /**
     * Sets the scale that the graphics object and window will be multiplied by.
     * 
     * @param scale - Desired scale multiplier.
     */
    public void setScale(int scale) {
        this.scale = scale;
        di = new Dimension(WIDTH * scale, HEIGHT * scale);
        
        jp.setPreferredSize(di);
        
        ca.setBounds(0, 0, WIDTH * scale, HEIGHT * scale);
        
        jf.setMinimumSize(di);
        jf.setResizable(false);
        jf.pack();
        jf.setLocationRelativeTo(null);
    }
    
    /**
     * Sets the canvas background color.
     * 
     * @param co - Desired background color
     */
    public void setBGColor(Color co) {
        ca.setBackground(co);
    }
    
    /**
     * Sets the menu to be displayed via GUI
     * 
     * @param m - Desired menu to display
     */
    public void setMenu(Menu m) {
        Menu.choice = 0;
        this.m = m;
    }
    
    /**
     * Unique debug menu that displays a bunch of useless <i>shit</i>.
     * 
     * @param g - Graphics object required to draw to the screen.
     */
    public void debug(Graphics2D g) {
        currTime = System.currentTimeMillis();
        frameCount++;
        
        if(currTime - frameTime > 1000) {
            frameTime = currTime;
            recordedFPS = frameCount;
            frameCount = 0;
        }
        
        g.setColor(Color.decode("#13161D"));
            g.fillRect(4, 4, 124, 56);
            g.fillRect(WIDTH - 128, 4, 124, 32);
        g.setColor(Color.decode("#ABB2BF"));
            g.drawString("FPS: " + recordedFPS, 8, 18);
            g.drawString("TRG FPS: " + fps, 8, 30);
            g.drawString("SCALE: " + scale, 8, 42);
            g.drawString("STATE: " + s.getID(), 8, 54);
            g.drawString("[ESC] open menu", WIDTH - 124, 18);
            g.drawString("[F1] debug info", WIDTH - 124, 30);
    }
    
}