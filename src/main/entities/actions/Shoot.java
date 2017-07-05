package main.entities.actions;

import java.awt.Graphics;
import main.Handler;
import main.entities.Unit;

/**
 *
 * @author Alex
 */
public class Shoot extends Action{
    
    private Handler handler;
    private Unit unit;
    
    public Shoot(Handler handler, Unit unit){
        super(handler, unit);
        this.handler = handler;
        this.unit = unit;
    }
    
    @Override
    public void update() {
        updateTileHover();
    }

    @Override
    protected void newTileHovered() {
        if(handler.getLevel().unitAt(hoveredTile.getWorldX(), hoveredTile.getWorldY())){
            if(handler.getMouseManager().getLeftClicked()){
                unit.attack(handler.getLevel().getUnitAt(hoveredTile.getWorldX(), hoveredTile.getWorldY()));
                unit.active = false;
                unit.shooting = false;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
    }

    @Override
    public void recentlyClicked() {
    }
    
    
}
