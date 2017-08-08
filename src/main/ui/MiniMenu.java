package main.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import main.Handler;
import main.entities.Unit;
import main.gfx.Assets;

/**
 *
 * @author Alex
 */
public class MiniMenu {
    
    private Handler handler;
    private Unit unit;
    
    private ArrayList<Button> buttons = new ArrayList<>();
    private Button waitButton;
    private Button attackButton;
    
    private int options;
    
    public MiniMenu(Handler handler, Unit unit){
        this.handler = handler;
        this.unit = unit;
        
        initButtons();
    }
    
    private void initButtons(){
        waitButton = new Button(handler, Assets.button, Assets.buttonHighlight, 
                Assets.buttonWidth, Assets.buttonHeight, "Wait");
        attackButton = new Button(handler, Assets.button, Assets.buttonHighlight,
                    Assets.buttonWidth, Assets.buttonHeight, "Attack");
        
        if(unit.canAttack()){
            buttons.add(attackButton);
        } 
        buttons.add(waitButton);
        
    }
    
    public void update(int x, int y){
        for(int i = 0; i < buttons.size(); i++){
            buttons.get(i).update(x + Assets.tileTextureWidth + 20, (y - 20) + (i * Assets.buttonHeight));
        }
        checkClicks();
    }
    
    private void checkClicks(){
        if(waitButton.click()){
            unit.active = false;
        }
        if(attackButton.click()){
        }           
    }
    
    public void render(Graphics g, int x, int y){
        for(int i = 0; i < buttons.size(); i++){
            buttons.get(i).render(g, x + Assets.tileTextureWidth + 20, (y - 20) + (i * Assets.buttonHeight));
        }
    }
    
    /**
     * @return right evaluates to true
     */
    public boolean determineRight(int x){
        if(x > handler.getDisplayWidth() / 2){
            return false;
        }
        return true;
    }
}
