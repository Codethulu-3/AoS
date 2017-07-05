package main.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import main.Handler;
import main.combat.Weapon;
import main.gfx.Camera;
import main.level.Level;

/**
 *
 * CodeNMore
 */
public abstract class Entity {

    public static final int DEFAULT_WIDTH=32, DEFAULT_HEIGHT=32;
    
    protected Handler handler;
    protected Level level;
    protected Camera camera;
    
    protected float screenX, screenY;
    protected int worldX, worldY, width, height;
    protected Rectangle bounds;
    
    protected Weapon weapon;
    
    protected boolean alive = true;
    protected boolean active = true;

    public Entity(Handler handler, int worldX, int worldY, int width, int height) {
        this.handler = handler;
        level = handler.getLevel();
        
        this.width = width;
        this.height = height;
        
        this.worldX = worldX;
        this.worldY = worldY;
        
        bounds = new Rectangle(0, 0, width, height);
        
        level.getTileAt(worldX, worldY).setBlocked(true);
    }

    public abstract void update();
    public abstract void render(Graphics g);
    
    public void giveWeapon(Weapon weapon){
        this.weapon = weapon;
    }
    
    public void attack(Unit u){
        System.out.println("sdkjf");
        weapon.shoot(u);
    }
    
    public void reactivate(Unit u){
        u.moveable = true;
        u.active = true;
    }
    
    //getters and setters
    public float getScreenX() {return screenX;}
    public void setScreenX(float screenX) {this.screenX = screenX;}

    public float getScreenY() {return screenY;}
    public void setScreenY(float screenY) {this.screenY = screenY;}
    
    public int getWorldX() {return worldX;}
    public void setWorldX(int x) {worldX = x;}
    
    public void setWorldY(int y) {worldY = y;}
    public int getWorldY() {return worldY;}
    
    public int getWidth() {return width;}
    public void setWidth(int width) {this.width = width;}
    
    public int getHeight() {return height;}
    public void setHeight(int height) {this.height = height;}
    
    public boolean isAlive() {return alive;}
    public void setAlive(boolean active) {this.alive = active;}
}
