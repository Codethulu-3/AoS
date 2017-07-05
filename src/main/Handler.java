package main;

import main.gfx.Camera;
import main.input.KeyManager;
import main.input.MouseManager;
import main.level.Level;

public class Handler {

    private Game game;
    private Level level;

    public Handler(Game game) {
        this.game = game;
    }
    
    public int getDisplayWidth() {return game.getWidth();}
    public int getDisplayHeight() {return game.getHeight();}
    
    public Level getLevel(){return level;}
    public void setLevel(Level level){this.level = level;}
    
    public MouseManager getMouseManager(){return game.getMouseManager();}
    public KeyManager getKeyManager(){return game.getKeyManager();}
    
    public Camera getCamera(){return level.getCamera();}
}
