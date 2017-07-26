package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.shipUpgrade.Upgrade;

public abstract class Weapon {
    
    public final int cooldown, maxCharges;
    public int charges;
    int reload;
    Ship ship;
    public boolean friend;
    Array<Upgrade> upgrades = new Array<>();
    public Weapon(int cooldown, int maxCharges){
        this.cooldown=cooldown; this.maxCharges=maxCharges; this.charges=maxCharges;
    }
    public Weapon(int cooldown){
       this(cooldown, 1);
    }

    public Ship getShip(){
        return ship;
    }

    public void update(){
        if(reload>0 && charges<maxCharges){
            reload--;
        }
        if(reload==0){
            if(charges<maxCharges){
                charges++;
                reload=cooldown;
            }
        }
    }
    
    public void fire(){
        if(charges==0)return;
        charges--;
        internalFire();
    }

    protected abstract void internalFire();

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public  String getName(){
        return getClass().getSimpleName().toLowerCase();
    }

    public TextureRegion getImage(){
        return Main.atlas.findRegion("weapon/"+getName().toLowerCase());
    }

    public float getCooldownRatio() {
        return (float)charges/maxCharges+(1-((float)reload/cooldown))/(float)maxCharges;
    }

    public float getBonus(Upgrade.UpgradeType type){
        float total = 0;
        for(Upgrade u:upgrades){
            if(u.type==type){
                total+=u.upgradeAmount;
            }
        }
        return total;
    }

    public void addUpgrade(Upgrade u) {
        upgrades.add(u);
    }

    public void clearUpgrades(){
        upgrades.clear();
    }
}
