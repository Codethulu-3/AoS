package main.input;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class MouseManager implements MouseListener, MouseMotionListener {

    private boolean leftPressed, rightPressed, leftClicked, rightClicked;
    private int mouseX,mouseY;
    private Point origin = new Point(0,0);
    private Point mousePt;
    private Robot r;
    
    public MouseManager(){
        try {
            r = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(MouseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
            leftClicked = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
            rightClicked = true;
        }
        if(rightPressed){
            mousePt = e.getPoint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
            r.mouseMove(e.getXOnScreen() ,e.getYOnScreen()+1);//cheap hack
            r.mouseMove(e.getXOnScreen() ,e.getYOnScreen());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(rightPressed){
            int dx = e.getX() - mousePt.x;
            int dy = e.getY() - mousePt.y;
            origin.setLocation(origin.x + dx, origin.y + dy);
            mousePt = e.getPoint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    
    public void update() {
        leftClicked = false;
        rightClicked = false;
    }
    
    //getters
    public boolean getLeftPressed(){return leftPressed;}
    public boolean getRightPressed(){return rightPressed;}
    
    public boolean getLeftClicked() {return leftClicked;}
    public boolean getRightClicked() {return rightClicked;}
    
    public int getMouseX(){return mouseX;}
    public int getMouseY(){return mouseY;}
    
    public Point getOrigin(){return origin;}
}
