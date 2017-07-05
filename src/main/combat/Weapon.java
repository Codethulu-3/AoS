package main.combat;

import main.entities.Unit;

/**
 *
 * @author Alex
 */
public abstract class Weapon {
    
    protected int range,damage,ammo;
    
    public Weapon(int range, int damage, int ammo){
        this.range = range;
        this.damage = damage;
        this.ammo = ammo;
    }
    
    public void shoot(Unit u){
        u.subtractHealth(damage);
        ammo--;
    }
}
