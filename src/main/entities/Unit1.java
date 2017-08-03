package main.entities;

import java.awt.Color;
import java.awt.Graphics;
import main.Handler;

/**
 *
 * @author Alex
 */
public class Unit1 extends Unit {
    
    public Unit1(Handler handler, int side, int worldX, int worldY){
        super(handler, side, worldX, worldY, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, 20, 6);
    }
    
    @Override
    public void render(Graphics g) {
        super.render(g);
        drawEffects(g);
        if(side == PLAYER_SIDE){
            if(active){
                g.setColor(Color.blue);
            } else {
                g.setColor(Color.gray);
            }
        } else {
            g.setColor(Color.red);
        }
        g.fillRect((int)(screenX), (int)(screenY), width, height);
    }
}
