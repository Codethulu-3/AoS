package main.entities;

import java.awt.Graphics;
import java.awt.Point;
import javafx.scene.shape.Line;
import main.Handler;
import main.combat.Weapon;
import static main.entities.Entity.DEFAULT_HEIGHT;
import static main.entities.Entity.DEFAULT_WIDTH;
import main.entities.actions.Action;
import main.entities.actions.Move;
import main.entities.actions.Shoot;
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
    
    protected Action shoot;
    public boolean shooting = false;
    
    protected int side;
    public static int PLAYER_SIDE = 1;
    public static int ENEMY_SIDE = 2;
    
    private MiniMenu miniMenu;
    
    private Line[][][] lines;
    
    public Unit(Handler handler, int side, int worldX, int worldY, int width, int height, int health, int moveRange) {
        super(handler, worldX, worldY, width, height);
        this.health = health;
        this.moveRange = moveRange;
        move = new Move(handler, this);
        shoot = new Shoot(handler, this);
        this.side = side;
        miniMenu = new MiniMenu(handler, this);
        lines = new Line[handler.getLevel().getTiles().length][handler.getLevel().getTiles()[0].length][4];
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
        if(shooting){
            //System.out.println("shooting");
           // shoot.update();
        }
        if(side == PLAYER_SIDE){
            for(int r = 0; r < handler.getLevel().getTiles().length; r++){
                for(int c = 0; c < handler.getLevel().getTiles()[0].length; c++){
                    if(handler.getLevel().getTileAt(r, c).isBlocked() && !handler.getLevel().getTileAt(r, c).surrounded()){
                        lines[r][c][0] = new Line(worldX * Tile.TILEWIDTH + (DEFAULT_WIDTH / 2), worldY * Tile.TILEHEIGHT + (DEFAULT_HEIGHT / 2), 
                            handler.getLevel().getTileAt(r, c).getWorldX() * Tile.TILEWIDTH, 
                            handler.getLevel().getTileAt(r, c).getWorldY() * Tile.TILEHEIGHT);
                    }
                }
            }
            /*
            int i=0;
            for(Tile[] t: handler.getLevel().getTiles()){
                for(Tile s: t){
                    lines[i][0] = new Line(screenX, screenY, s.getWorldX() * (i * Tile.TILEWIDTH), s.getWorldY() * ( Tile.TILEWIDTH));
                }
                i++;
            }*/
        }
    }
    
    @Override
    public void render(Graphics g) {
        if(!moveable && active){
            miniMenu.render(g, (int)screenX, (int)screenY);
        }
        if(side == PLAYER_SIDE){
            
            for(int r = 0; r < handler.getLevel().getTiles().length; r++){
                for(int c = 0; c < handler.getLevel().getTiles()[0].length; c++){
                    if(lines[r][c][0]!=null)
                    g.drawLine((int)lines[r][c][0].getStartX(), (int)lines[r][c][0].getStartY(),
                            (int) lines[r][c][0].getEndX(),(int) lines[r][c][0].getEndY());
                }
            }
            /*
            int i = 0;
            for(Tile[] t: handler.getLevel().getTiles()){
                g.drawLine((int)lines[i][][0].getStartX(), (int)lines[i][0].getStartY(),(int) lines[i][0].getEndX(),(int) lines[i][0].getEndY());
                i++;
            }*/
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
    

    public int getMoveRange() {return moveRange;}
    public int getSide(){return side;}
    
    public int getHealth(){return health;}
    public void subtractHealth(int damage){health -= damage; checkAlive();}
}
