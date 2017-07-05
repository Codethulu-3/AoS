package main;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import main.gfx.Assets;
import main.input.KeyManager;
import main.input.MouseManager;
import main.states.GameState;
import main.states.State;

/**
 *
 * @author Alex
 */
public class Game extends GameLoop{
    
    private BufferStrategy bs;
    private Graphics g;
    
    private Display display;
    private int width,height;
    
    private Handler handler;
    
    private State gameState;
    
    private MouseManager mouseManager;
    private KeyManager keyManager;
    
    @Override
    public void startup() {
        //sets width and height to max
        Toolkit tk = Toolkit.getDefaultToolkit();  
        width = ((int) tk.getScreenSize().getWidth());  
        height = ((int) tk.getScreenSize().getHeight());  
        
        //test width & height
        width=1280;
        height=720;
        
        display = new Display("AoS", width, height);
        
        //set up closing operation
        display.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
            }
        });
        
        //initiate variables
        handler = new Handler(this);
        
        Assets.init();
        
        gameState = new GameState(handler);
        State.setState(gameState);
        
        mouseManager = new MouseManager();
        keyManager = new KeyManager();
        
        display.getFrame().addMouseListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        display.getFrame().addKeyListener(keyManager);
        display.getCanvas().addKeyListener(keyManager);
    }
    
    @Override
    public void shutdown() {
        System.exit(0);
    }
    
    @Override
    public void update() {
        State.getState().update();
        mouseManager.update();
        keyManager.update();
    }

    @Override
    public void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //Clear Screen
        g.clearRect(0, 0, width, height);
        
        draw(g);
        
        bs.show();
        g.dispose();
        
    }
    
    public void draw(Graphics g){//draws
        g.clipRect(0, 0, width, height);
        State.getState().render(g);
    }
    
    //getters
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    
    public MouseManager getMouseManager(){return mouseManager;}
    public KeyManager getKeyManager(){return keyManager;}
}
