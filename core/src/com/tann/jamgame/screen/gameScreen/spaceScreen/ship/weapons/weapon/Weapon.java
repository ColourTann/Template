package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon;

import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.PlayerShip;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;

public abstract class Weapon {
    
    final int cooldown;
    int reload;
    Ship ship;
    boolean friend;
    public Weapon(int cooldown, boolean friend){
       this.cooldown=cooldown;this.friend = friend;
    }

    public Ship getShip(){
        return ship;
    }

    public void update(){
        reload--;
    }
    
    public void fire(){
        if(reload>0)return;
        reload=cooldown;
        internalFire();
    }

    protected abstract void internalFire();

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
