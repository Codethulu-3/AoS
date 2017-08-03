package main.level;

import main.tiles.WaterTile;
import main.tiles.TileID;
import main.tiles.GrassTile;
import main.tiles.Tile;
import java.awt.Graphics;
import java.util.ArrayList;
import main.Handler;
import main.combat.Pistol;
import main.entities.Entity;
import main.entities.EntityManager;
import main.entities.Unit;
import main.entities.Unit1;
import main.entities.pathfinding.AStarPathfinding;
import main.gfx.Camera;

/**
 *
 * CodeNMore
 */
public class Level {

    private Handler handler;
    
    private int width, height;
    private int spawnX, spawnY;
    
    private Tile[][] tiles;
    
    private Camera camera;
    
    private EntityManager entityManager;
    private AStarPathfinding aStarPathfinding;
    
    public Level(Handler handler, String path) {
        this.handler = handler;
        camera = new Camera(handler);
        entityManager = new EntityManager(handler);
        loadWorld(path);
        aStarPathfinding = new AStarPathfinding(this);
    }
    
    public void initEntities(){
        entityManager.addEntity(new Unit1(handler, Unit.PLAYER_SIDE, 3, 4));
        //entityManager.addEntity(new Unit1(handler, Unit.PLAYER_SIDE, 4, 4));
        entityManager.addEntity(new Unit1(handler, Unit.ENEMY_SIDE, 5, 6));
        entityManager.getEntities().get(0).giveWeapon(new Pistol());
    }

    public void update() {
        entityManager.update();
        //temp
        if(handler.getKeyManager().getSpaceTapped()){
            newTurn();
        }
    }

    public void render(Graphics g) {
        renderMap(g);
        entityManager.render(g);
        camera.updateCamera(g);
    }
    
    private void renderMap(Graphics g){
        int xStart = (int) Math.max(0, camera.getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (camera.getxOffset() + handler.getDisplayWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, camera.getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (camera.getyOffset() + handler.getDisplayHeight()) / Tile.TILEHEIGHT + 1);
        
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                int screenX = (int) (x * Tile.TILEWIDTH - camera.getxOffset());
                int screenY = (int) (y * Tile.TILEHEIGHT - camera.getyOffset());
                tiles[x][y].render(g, screenX, screenY);
            }
        }
    }
    
    private void newTurn(){
        entityManager.reActivateAll();
    }
    
    private void loadWorld(String path) {
        String file = LevelUtils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = LevelUtils.parseInt(tokens[0]);
        height = LevelUtils.parseInt(tokens[1]);
        spawnX = LevelUtils.parseInt(tokens[2]);
        spawnY = LevelUtils.parseInt(tokens[3]);

        tiles = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int tt = LevelUtils.parseInt(tokens[(x + y * width) + 4]);
                switch (tt) {
                    case 0:
                        tiles[x][y] = new GrassTile(handler, x, y);
                        break;
                    case 1:
                        tiles[x][y] = new WaterTile(handler, x, y);
                        break;
                    default:
                        System.err.println("Unregonized tile id " + tt + "!");
                        break;
                }
            }
        }
    }
    
    public boolean unitAt(int worldX, int worldY){
        for(Entity e: entityManager.getEntities()){
            if(e.getWorldX() == worldX && e.getWorldY() == worldY){
                return true;
            }
        }
        return false;
    }
    
    public Unit getUnitAt(int worldX, int worldY){
        for(Entity e: entityManager.getEntities()){
            if(e.getWorldX() == worldX && e.getWorldY() == worldY){
                return (Unit)e;
            }
        }
        return null;
    }
    
    public Tile getTileAt(int worldX, int worldY) {
        return tiles[worldX][worldY];
    }
    
    public TileID getTileID(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {//if no tile is found
            return TileID.Null; //defaults to null
        }
        return tiles[x][y].getID();
    }
    
    public boolean outOfBounds(int worldX, int worldY) {
        if (worldX < 0 || worldY < 0 || worldX >= width || worldY >= height)
            return true;
        return false;
    }
    
    public ArrayList<Tile> getWalkableNeighbors(Tile tile) {
        ArrayList<Tile> walkableNeighbors = new ArrayList<>();
        for (int x = -1; x <= 1; x += 2) {
            Tile neighborToCheck = tiles[tile.getWorldX() + x][tile.getWorldY()];
            if (!neighborToCheck.isBlocked()) {
                walkableNeighbors.add(neighborToCheck);
            }
        }
        for (int y = -1; y <= 1; y += 2) {
            Tile neighborToCheck = tiles[tile.getWorldX()][tile.getWorldY() + y];
            if (!neighborToCheck.isBlocked()) {
                walkableNeighbors.add(neighborToCheck);
            }
        }
        return walkableNeighbors;
    }
    
    //getters
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    
    public Tile[][] getTiles(){return tiles;}
    
    public Camera getCamera(){return camera;}
    
    public AStarPathfinding getPathing() {return aStarPathfinding;}
}
