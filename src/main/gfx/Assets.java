package main.gfx;

import java.awt.image.BufferedImage;

/**
 *
 * 
 */
public class Assets {

    public static final int tileTextureWidth = 32, tileTextureHeight = 32;
    public static BufferedImage water,grass;
    public static BufferedImage highlight;
    
    public static final int buttonWidth = 128, buttonHeight = 32;
    public static BufferedImage button,buttonHighlight;
    public static BufferedImage buttonTop, buttonTopHighlight;
    public static BufferedImage buttonBottom, buttonBottomHighlight;
    public static BufferedImage buttonMiddle, buttonMiddleHighlight;
    
    public static final int arrowWidth = 32, arrowHeight = 32;
    public static BufferedImage arrowUp, arrowLeft, arrowRight, arrowDown;
    public static BufferedImage arrowUp_Down, arrowLeft_Right;
    
    public static void init() {
        SpriteSheet tiles = new SpriteSheet(ImageLoader.loadImage("/textures/tiles.png"));
        SpriteSheet buttons = new SpriteSheet(ImageLoader.loadImage("/textures/menuButton.png"));
        SpriteSheet arrows = new SpriteSheet(ImageLoader.loadImage("/textures/arrows.png"));
        
        water = easyLoad(tiles, 1, 0);
        grass = easyLoad(tiles, 0, 0);
        highlight = easyLoad(tiles, 1, 1);
        
        button = buttons.crop(0, 0, buttonWidth, buttonHeight);
        buttonHighlight = buttons.crop(0, buttonHeight, buttonWidth, buttonHeight);
        
        buttonTop = buttons.crop(buttonWidth, 0, buttonWidth, buttonHeight);
        buttonTopHighlight = buttons.crop(buttonWidth * 2, buttonHeight, buttonWidth, buttonHeight);
        buttonBottom = buttons.crop(buttonWidth, 0, buttonWidth, buttonHeight);
        buttonBottomHighlight = buttons.crop(buttonWidth * 2, buttonHeight, buttonWidth, buttonHeight);
        buttonMiddle = buttons.crop(buttonWidth, 0, buttonWidth, buttonHeight);
        buttonMiddleHighlight = buttons.crop(buttonWidth * 2, buttonHeight, buttonWidth, buttonHeight);
        
        
    }
    
    private static BufferedImage easyLoad(SpriteSheet sheet, int x, int y){
        return sheet.crop(x * tileTextureWidth, y * tileTextureHeight, tileTextureWidth, tileTextureHeight);
    }
    
    //spritesheet crop reference
    /* 0,0 1,0 2,0 ...
     * 0,1 1,1 2,1 ...
     * 0,2
     * 0,3
     */
    
}
