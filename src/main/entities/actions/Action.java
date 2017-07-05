package main.entities.actions;

import java.awt.Graphics;
import java.util.ArrayList;
import main.Handler;
import main.entities.Unit;
import main.tiles.Tile;

/**
 *
 * @author Matthew Galan
 */
public abstract class Action {
    
    protected Handler handler;
    protected Unit unit;
    
    protected Tile hoveredTile;
    
    public Action(Handler handler, Unit unit) {
        this.handler = handler;
        this.unit = unit;
    }
    
    public abstract void update();
    
    protected void updateTileHover() {
        int x = handler.getCamera().getTileX();
        int y = handler.getCamera().getTileY();
        if (!handler.getLevel().outOfBounds(x, y)) {
            Tile newHoveredTile = handler.getLevel().getTileAt(x,y);
            if (newHoveredTile != hoveredTile) {
                hoveredTile = newHoveredTile;
                newTileHovered();
            }
        }
    }
    
    protected abstract void newTileHovered(); //Called when the mouse moves to another tile
    public abstract void draw(Graphics g);
    public abstract void recentlyClicked();
}
