package main.tiles;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javafx.geometry.Bounds;
import javafx.scene.shape.Line;
import main.Handler;
import static main.entities.Entity.DEFAULT_HEIGHT;
import static main.entities.Entity.DEFAULT_WIDTH;

/**
 *
 * 
 */

public abstract class Tile {

    //STATIC STUFF HERE
    public static Tile[] tiles = new Tile[256];

    //CLASS
    public static final int TILEWIDTH = 64, TILEHEIGHT = 64;
    protected final TileID id;
    protected Handler handler;
    protected int worldX, worldY;
    protected float screenX, screenY;
    
    protected boolean blocked;
    protected boolean lit;
    
    public Tile(Handler handler, int worldX, int worldY, TileID id) {
        this.id = id;
        this.handler = handler;
        this.worldX = worldX;
        this.worldY = worldY;
    }
    
    
    public void update() {
        screenX = handler.getCamera().worldToScreenX(worldX);
        screenY = handler.getCamera().worldToScreenY(worldY);
        checkIfHovered();
    }

    public void checkIfHovered() {
        if (handler.getMouseManager().getMouseX() > screenX && handler.getMouseManager().getMouseX() < screenX + DEFAULT_WIDTH
                && handler.getMouseManager().getMouseY() > screenY && handler.getMouseManager().getMouseY() < screenY + DEFAULT_HEIGHT) {
            onMouseEnter();
        } else {
            onMouseExit();
        }
    }
    
    
    public boolean surrounded(){
        if(handler.getLevel().outOfBounds(worldX + 1, worldY) || handler.getLevel().outOfBounds(worldX - 1, worldY)
                || handler.getLevel().outOfBounds(worldX, worldY + 1) || handler.getLevel().outOfBounds(worldX, worldY - 1)){
            return true;
        }
        if(handler.getLevel().getTiles()[worldX + 1][worldY].blocked && handler.getLevel().getTiles()[worldX - 1][worldY].blocked){
            if(handler.getLevel().getTiles()[worldX][worldY + 1].blocked && handler.getLevel().getTiles()[worldX][worldY - 1].blocked){
                return true;
            }
        }
        return false;
    }
    
    public boolean intersects(Line l){
        return false;
    }
    
    public void onMouseEnter() {
        //When the mouse hovers over the tile
    }
    
    public void onMouseExit() {
        //When the mouse leaves the tile
        
    }
    
    public abstract void render(Graphics g, int x, int y);
    
    //Getters and setters
    public boolean isBlocked() {return blocked;}
    public void setBlocked(boolean blocked) {this.blocked = blocked;}
    
    public boolean isLit(){return lit;}
    public void setLit(boolean lit){this.lit = lit;}
    
    public TileID getID() {return id;}
    
    public int getWorldX() {return worldX;}
    public int getWorldY() {return worldY;}
    
    public float getScreenX(){return screenX;}
    public float getScreenY(){return screenY;}
}
