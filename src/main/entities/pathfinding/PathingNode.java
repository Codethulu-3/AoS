package main.entities.pathfinding;

import main.tiles.Tile;

/**
 *
 * @author Matthew Galan
 */
public class PathingNode {
    
    private Tile location, end;
    private PathingNode parent;
    private int g;
    private float h, f;
    
    public PathingNode(Tile location, Tile end, PathingNode parent) {
        this.location = location;
        this.end = end;
        this.parent = parent;
        if (parent == null) {
            g = 0;
        } else {
            g = parent.getG() + 1;
        }
        calcH();
        calcF();
    }
    
    private void calcH() { //h is the approximated distance to the end
        //h = Math.abs(location.getWorldX() - end.getWorldX()) + Math.abs(location.getWorldY() - end.getWorldY());
        h = (float) Math.sqrt(Math.pow(location.getWorldX() - end.getWorldX(), 2) + Math.pow(location.getWorldY() - end.getWorldY(), 2));
    }
    
    public void updateG(int newG, PathingNode parent) {
        if (newG < g) {
            g = newG;
            calcF();
            this.parent = parent;
        }
    }
    
    //getters
    private void calcF() {f = g + h;}
    
    public int getG() {return g;}
    public float getH() {return h;}
    public float getF() {return  f;}
    
    public Tile getTile() {return location;}
    
    public PathingNode getParent() {return parent;}
}
