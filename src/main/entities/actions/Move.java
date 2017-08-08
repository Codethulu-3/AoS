package main.entities.actions;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import main.Handler;
import main.entities.Unit;
import main.gfx.Assets;
import main.tiles.Tile;

/**
 *
 * @author Matthew Galan
 */
public class Move extends Action {

    protected ArrayList<Tile> highlights, path;
    
    public Move(Handler handler, Unit unit) {
        super(handler, unit);
        highlights = new ArrayList<>();
        path = new ArrayList<>();
        calcHighlights();
    }
    
    @Override
    public void update() {
        updateTileHover();
        if(handler.getMouseManager().getLeftClicked()) {
            if (highlights.contains(hoveredTile)) {
                //Move
                handler.getLevel().getTileAt(unit.getWorldX(), unit.getWorldY()).setBlocked(false);
                unit.setWorldX(hoveredTile.getWorldX());
                unit.setWorldY(hoveredTile.getWorldY());
                handler.getLevel().getTileAt(unit.getWorldX(), unit.getWorldY()).setBlocked(true);
                unit.moveable = false;
                unit.calcLines();
            }
        }
    }
    
    @Override
    public void recentlyClicked() {
        path.clear();
        calcHighlights();
    }
    
    private void calcHighlights() {
        highlights.clear();
        highlights.add(handler.getLevel().getTileAt(unit.getWorldX(), unit.getWorldY()));
        for (int i = 1; i <= unit.getMoveRange(); i++) { //Generate selection
            int origSize = highlights.size();
            for (int j = 0; j < origSize; j++) {
                Tile cur = highlights.get(j);
                for (int xCheck = -1; xCheck <= 2; xCheck += 2) { //Check east/west neighbors
                    if (!handler.getLevel().outOfBounds(cur.getWorldX() + xCheck, cur.getWorldY())) { //If we're in the map
                        Tile neighbor = handler.getLevel().getTileAt(cur.getWorldX() + xCheck, cur.getWorldY());
                        if (!highlights.contains(neighbor) && !neighbor.isBlocked()) { //If we haven't already added it && it's walkable
                            highlights.add(neighbor);
                        }
                    }
                }
                for (int yCheck = -1; yCheck <= 2; yCheck += 2) { //Check north/south neighbors
                    if (!handler.getLevel().outOfBounds(cur.getWorldX(), cur.getWorldY() + yCheck)) { //If we're in the map
                        Tile neighbor = handler.getLevel().getTileAt(cur.getWorldX(), cur.getWorldY() + yCheck);
                        if (!highlights.contains(neighbor) && !neighbor.isBlocked()) { //If we haven't already added it && it's walkable
                            highlights.add(neighbor);
                        }
                    }
                }
            }
        }
        //highlights.remove(handler.getLevel().getTileAt(unit.getWorldX(), unit.getWorldY()));
    }
    
    @Override
    protected void newTileHovered() {
        if (highlights.contains(hoveredTile)) {
            path = handler.getLevel().getPathing().getPath(handler.getLevel().getTileAt(unit.getWorldX(), unit.getWorldY()), hoveredTile);
        }
        //Remove the tile the unit is on from the path
        path.remove(handler.getLevel().getTileAt(unit.getWorldX(), unit.getWorldY()));
    }
    
    @Override
    public void draw(Graphics g) {
        for (Tile t : highlights) { //Render selection highlights
            g.drawImage(Assets.highlight, (int)handler.getCamera().worldToScreenX(t.getWorldX()),
                    handler.getCamera().worldToScreenY(t.getWorldY()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
        }
        Color c = g.getColor();
        g.setColor(Color.PINK);
        for (Tile t : path) {
            g.fillRect((int)handler.getCamera().worldToScreenX(t.getWorldX()),
                    handler.getCamera().worldToScreenY(t.getWorldY()), Tile.TILEWIDTH, Tile.TILEHEIGHT);
        }
        g.setColor(c);
    }
}
