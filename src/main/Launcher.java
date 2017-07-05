package main;



/**
 *
 * @author Alex
 */
public class Launcher {
    
    private static double frameRate = 1.0/200.0;
    
    public static void main(String[] args){
        GameLoop game = new Game();
        game.run(frameRate);
    }
    
}
