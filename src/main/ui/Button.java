package main.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Handler;


public class Button {
    
    private Handler handler;
    private BufferedImage texture;
    private BufferedImage highlightTexture;
    private int width, height;
    private String text;
    
    private boolean highlighted=false;
    
    public Button(Handler handler, BufferedImage texture, BufferedImage highlightTexture, int width, int height, String text){
        this.handler = handler;
        this.texture = texture;
        this.highlightTexture = highlightTexture;
        this.width = width;
        this.height = height;
        this.text = text;
    }
    
    public void update(int x, int y){
        if(handler.getMouseManager().getMouseX() > x && handler.getMouseManager().getMouseX() < x + width){
            if(handler.getMouseManager().getMouseY() > y && handler.getMouseManager().getMouseY() < y + height){
                highlighted = true;
            } else {
                highlighted = false;
            }
        } else {
            highlighted = false;
        }
        
    }
    
    public void render(Graphics g, int x, int y){
        if(highlighted){
            g.drawImage(highlightTexture, x, y, width, height, null);
        } else {
            g.drawImage(texture, x, y, width, height,null);
        }
        g.setColor(Color.white);
        g.drawString(text, (x + (width / 2) ) - (g.getFontMetrics().stringWidth(text) / 2), (y + (height / 2)));
    }
    
    public boolean click(){
        if(highlighted){
            if(handler.getMouseManager().getLeftPressed()){
                return true;
            }
        }
        return false;
    }
    
    //getters & setters
    public String getText(){return text;}
    
    public void setTexture(BufferedImage texture){this.texture = texture;}
    public void setHighlightTexture(BufferedImage highlightTexture){this.highlightTexture = highlightTexture;}
}
