package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;

public abstract class Weapon {
    
    public final int cooldown;
    int reload;
    Ship ship;
    public boolean friend;
    public Weapon(int cooldown){
       this.cooldown=cooldown;
    }

    public Ship getShip(){
        return ship;
    }

    public void update(){
        if(reload>0){
            reload--;
        }
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

    public abstract String getName();

    public TextureRegion getImage(){
        return Main.atlas.findRegion("weapon/"+getName().toLowerCase());
    }

    public float getCooldownRatio() {
        return 1-((float)reload/cooldown);
    }
}
