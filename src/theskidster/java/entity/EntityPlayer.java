package theskidster.java.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import theskidster.java.input.Input;
import theskidster.java.main.MainContainer;
import theskidster.java.resource.Resource;
import theskidster.java.world.World;

/**
 * @author J Hoffman
 * Created: Jan 5, 2018
 */

public class EntityPlayer extends Entity {

    private double xDir;
    private double yDir;
    private double speed;
    private double maxSpeed;
    private double stopSpeed;
    private double jumpSpeed;
    private double fallSpeed;
    private double gravity;
    
    private boolean isFalling;
    
    /**
     * @see theskidster.java.entity.Entity#init(theskidster.java.main.MainContainer, theskidster.java.resource.Resource) 
     */
    @Override
    public void init(MainContainer mc, Resource re) {
    }

    /**
     * Defines how the entity can interact with the world, Includes a simple camera.
     * 
     * @see theskidster.java.main.MainLogicLoop#update(theskidster.java.main.MainContainer, theskidster.java.input.Input)
     */
    @Override
    public void update(MainContainer mc, Input in) {
        if(in.getInput(KeyEvent.VK_LEFT, false)) {
            xDir -= speed;
            if(xDir < -maxSpeed) {
                xDir = -maxSpeed;
            }
        } else if(in.getInput(KeyEvent.VK_RIGHT, false)) {
            xDir += speed;
            if(xDir > maxSpeed) {
                xDir = maxSpeed;
            }
        } else {
            if(xDir > 0) {
                xDir -= stopSpeed;
                if(xDir < 0) {
                    xDir = 0;
                }
            } else if(xDir < 0) {
                xDir += stopSpeed;
                if(xDir > 0) {
                    xDir = 0;
                }
            }
        }
        
        if(in.getInput(KeyEvent.VK_UP, true) && !isFalling) {
            yDir = jumpSpeed;
            isFalling = true;
        }
        
        if(isFalling) {
            yDir += gravity;
            if(yDir > fallSpeed) {
                yDir = fallSpeed;
            }
        } else {
            yDir = 0;
        }
        
        int currCol = wo.getTileAtCol((int) xPos);
        int currRow = wo.getTileAtRow((int) yPos);
        
        double tox = xPos + xDir;
        double toy = yPos + yDir;
        double xTemp = xPos;
        double yTemp = yPos;
        
        aabb(xPos, toy);
        if(yDir < 0) {
            if(topLeft || topRight) {
                yDir = 0;
                yTemp = currRow * World.TILE_SIZE + height / 2;
            } else {
                yTemp += yDir;
            }
        }
        if(yDir > 0) {
            if(botLeft || botRight) {
                yDir = 0;
                isFalling = false;
                yTemp = (currRow + 1) * World.TILE_SIZE - height / 2;
            } else {
                yTemp += yDir;
            }
        }
        
        aabb(tox, yPos);
        if(xDir < 0) {
            if(topLeft || botLeft) {
                xDir = 0;
                xTemp = currCol * World.TILE_SIZE + width / 2;
            } else {
                xTemp += xDir;
            }
        }
        if(xDir > 0) {
            if(topRight || botRight) {
                xDir = 0;
                xTemp = (currCol + 1) * World.TILE_SIZE - width / 2;
            } else {
                xTemp += xDir;
            }
        }
        
        if(!isFalling) {
            aabb(xPos, yPos + 1);
            if(!botLeft && !botRight) {
                isFalling = true;
            }
        }
        
        xPos = xTemp;
        yPos = yTemp;
        
        //temp camera
        wo.setX((int) (MainContainer.WIDTH / 2 - xPos));
        wo.setY((int) (MainContainer.HEIGHT / 2 - yPos));
    }

    /**
     * @see theskidster.java.main.MainLogicLoop#render(java.awt.Graphics2D) 
     */
    @Override
    public void render(Graphics2D g) {
        int tx = wo.getX();
        int ty = wo.getY();
        
        g.setColor(Color.RED);
        g.fillRect((int) (tx + xPos - width / 2), (int) (ty + yPos - height / 2), width, height);
    }

    /**
     * @see theskidster.java.entity.Entity#setPos(int, int)
     */
    @Override
    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * @see theskidster.java.entity.Entity#aabb(double, double)
     */
    @Override
    public void aabb(double xPos, double yPos) {
        int leftTile = wo.getTileAtCol((int) (xPos - width / 2));
        int rightTile = wo.getTileAtCol((int) (xPos + width / 2) - 1);
        int topTile = wo.getTileAtRow((int) (yPos - height / 2));
        int botTile = wo.getTileAtRow((int) (yPos + height / 2) - 1);
        
        topLeft = wo.getTileAt(topTile, leftTile) == 1;
        topRight = wo.getTileAt(topTile, rightTile) == 1;
        botLeft = wo.getTileAt(botTile, leftTile) == 1;
        botRight = wo.getTileAt(botTile, rightTile) == 1;
    }
    
    /**
     * Sets the various properties of the entity upon the objects creation.
     */
    public EntityPlayer(World wo) {
        this.wo = wo;
        
        width = 16;
        height = 16;
        
        speed = 0.3;
        maxSpeed = 3.4;
        stopSpeed = 0.2;
        jumpSpeed = -8.7;
        fallSpeed = 7.2;
        gravity = 0.48;
    }
    
}