package main.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import main.Handler;

public class EntityManager {

    private Handler handler;
    private ArrayList<Entity> entities;

    public EntityManager(Handler handler) {
        this.handler = handler;
        entities = new ArrayList<>();
    }

    public void update() {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            e.update();
            if (!e.isAlive()) {
                handler.getLevel().getTileAt(e.worldX, e.getWorldY()).setBlocked(false);
                it.remove();
            }
        }
    }

    public void render(Graphics g) {
        for (Entity e : entities) {
            e.render(g);
            
        }
        
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }
    
    public void reActivateAll(){
        for(Entity e: entities){
            if(e instanceof Unit){
                e.reactivate((Unit)e);
            }
        }
    }
    
    public ArrayList<Entity> getEntities() {return entities;}
}
