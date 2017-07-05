package main.states;

import java.awt.Graphics;
import main.Handler;
import main.level.Level;

public class GameState extends State {

    private Level level;

    public GameState(Handler handler) {
        super(handler);
        level = new Level(handler, "res/levels/level1.txt");//loads current level
        handler.setLevel(level);//sets getter for level in handler
        level.initEntities();
    }

    @Override
    public void update() {
        level.update();//calls the levels tick
    }

    @Override
    public void render(Graphics g) {
        level.render(g);//calls render
    }
}
