package main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Alex
 */
public class KeyManager implements KeyListener{
    
    private boolean[] oldKeys, curKeys;
    
    public KeyManager(){
        oldKeys = new boolean[256];
        curKeys = new boolean[256];
    }
    
    public void update(){
        oldKeys = curKeys.clone();
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        curKeys[ke.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        curKeys[ke.getKeyCode()] = false;
    }
    
    public boolean getSpacePressed(){return curKeys[KeyEvent.VK_SPACE];}
    public boolean getSpaceTapped() {
        return curKeys[KeyEvent.VK_SPACE] && curKeys[KeyEvent.VK_SPACE] != oldKeys[KeyEvent.VK_SPACE];
    }
    
}
