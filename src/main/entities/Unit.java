package main.entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javafx.geometry.Bounds;
import javafx.scene.shape.Line;
import main.Handler;
import static main.entities.Entity.DEFAULT_HEIGHT;
import static main.entities.Entity.DEFAULT_WIDTH;
import main.entities.actions.Action;
import main.entities.actions.Move;
import main.tiles.Tile;
import main.ui.MiniMenu;

/**
 *
 * @author Matthew Galan
 */
public class Unit extends Entity {
    
    protected int health, moveRange;
    protected boolean selected;
    
    protected Action move;
    public boolean moveable=true;
    public boolean active=true;
    
    
    protected int side;
    public static int PLAYER_SIDE = 1;
    public static int ENEMY_SIDE = 2;
    
    private MiniMenu miniMenu;
    
    private ArrayList<Line2D> lines;
    
    
    public Unit(Handler handler, int side, int worldX, int worldY, int width, int height, int health, int moveRange) {
        super(handler, worldX, worldY, width, height);
        
        this.health = health;
        this.moveRange = moveRange;
        this.side = side;
        
        move = new Move(handler, this);
        miniMenu = new MiniMenu(handler, this);
        lines = new ArrayList();
        if(side == PLAYER_SIDE){
            calcLines();
        }
    }

    @Override
    public void update() {
        screenX = handler.getCamera().worldToScreenX(worldX);
        screenY = handler.getCamera().worldToScreenY(worldY);
        if (selected && moveable && active) {
            move.update();
        }
        checkIfClicked();
        if(!moveable && active){
            miniMenu.update((int)screenX, (int)screenY);
        }
    }
    
    @Override
    public void render(Graphics g) {
        if(!moveable && active){
            miniMenu.render(g, (int)screenX, (int)screenY);
        }
        if(side == PLAYER_SIDE){
            for(Line2D l: lines){
                g.drawLine((int)l.getX1() - (int)handler.getLevel().getCamera().getxOffset(),
                        (int)l.getY1() - (int)handler.getLevel().getCamera().getyOffset(),
                        (int)l.getX2() - (int)handler.getLevel().getCamera().getxOffset(),
                        (int)l.getY2() - (int)handler.getLevel().getCamera().getyOffset());
            }
        }
    }
    
    public void checkIfClicked(){
        if(handler.getMouseManager().getLeftClicked()){
            if (handler.getMouseManager().getMouseX()>screenX && handler.getMouseManager().getMouseX()<screenX+DEFAULT_WIDTH
                    && handler.getMouseManager().getMouseY()>screenY && handler.getMouseManager().getMouseY()<screenY+DEFAULT_HEIGHT) {
                onClick();
            } else {
                onDeclick();
            }
        }
    }
    
    public boolean canAttack(){
        return true;
    }
    
    public void onClick() { //When the entity is clicked
        if(side == PLAYER_SIDE){
            if(!selected){
                selected = true;
                move.recentlyClicked();
            } else {
                moveable = false;
            }
        }
    }

    public void onDeclick() { //When there's a click elsewhere
        selected = false;
    }
    
    public void drawEffects(Graphics g) {
        if (move != null && selected && moveable)
            move.draw(g);
    }
    
    private void checkAlive(){
        if(health <= 0){
            alive = false;
        }
    }
    
    public void calcLines() {
        lines.clear();
        handler.getLevel().calcLines(this);
        for (int r = 0; r < handler.getLevel().getTiles().length; r++) {
            for (int c = 0; c < handler.getLevel().getTiles()[0].length; c++) {
                if (handler.getLevel().getTileAt(r, c).isBlocked() && !handler.getLevel().getTileAt(r, c).surrounded() ) {
                    if(r == worldX && c == worldY){
                        System.out.println("dslfkh");
                    } else {
                        int realX = handler.getLevel().getTileAt(r, c).getWorldX() * Tile.TILEWIDTH;
                        int realY = handler.getLevel().getTileAt(r, c).getWorldY() * Tile.TILEHEIGHT;
                        for (int i = 0; i < 4; i++) {
                            Line2D line;
                            switch (i) {
                                case 0:
                                    line = new Line2D.Float(worldX * Tile.TILEWIDTH + (DEFAULT_WIDTH / 2),
                                            worldY * Tile.TILEHEIGHT + (DEFAULT_HEIGHT / 2), realX, realY);
                                    break;
                                case 1:
                                    line = new Line2D.Float(worldX * Tile.TILEWIDTH + (DEFAULT_WIDTH / 2),
                                            worldY * Tile.TILEHEIGHT + (DEFAULT_HEIGHT / 2), realX + Tile.TILEWIDTH, realY);
                                    break;
                                case 2:
                                    line = new Line2D.Float(worldX * Tile.TILEWIDTH + (DEFAULT_WIDTH / 2),
                                            worldY * Tile.TILEHEIGHT + (DEFAULT_HEIGHT / 2), realX, realY + Tile.TILEHEIGHT);
                                    break;
                                default:
                                    line = new Line2D.Float(worldX * Tile.TILEWIDTH + (DEFAULT_WIDTH / 2),
                                            worldY * Tile.TILEHEIGHT + (DEFAULT_HEIGHT / 2), realX + Tile.TILEWIDTH, realY + Tile.TILEHEIGHT);
                                    break;
                            }
                            int crosses = 0;
                            ArrayList<Line2D> mapLines = handler.getLevel().getLines();
                            for (int j = 0; j < handler.getLevel().getLines().size(); j++) {
                                if (line.intersectsLine(mapLines.get(j))) {
                                    crosses++;
                                }
                            }
                            if (crosses < 4) {
                                lines.add(line);
                            }
                        }
                    }
                }
            }
        }
    }
    
    //getters & setters
    public int getMoveRange() {return moveRange;}
    public int getSide(){return side;}
    
    public int getHealth(){return health;}
    public void subtractHealth(int damage){health -= damage; checkAlive();}
}
