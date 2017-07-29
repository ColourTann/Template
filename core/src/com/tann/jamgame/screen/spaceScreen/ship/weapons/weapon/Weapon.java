package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.RocketBullet;
import com.tann.jamgame.screen.spaceScreen.shipUpgrade.Upgrade;

public abstract class Weapon {
    
    public final int cooldown;
    private final int base_max_charges;
    public int charges;
    int reload;
    Ship ship;
    public boolean friend;
    Array<Upgrade> upgrades = new Array<>();
    public Weapon(int cooldown, int maxCharges){
        this.cooldown=cooldown; this.base_max_charges = maxCharges;
        reset();
    }
    public Weapon(int cooldown){
       this(cooldown, 1);
    }

    public Ship getShip(){
        return ship;
    }

    public void update(){
        if(reload>0 && charges< getMaxCharges()){
            reload--;
        }
        if(reload==0){
            if(charges< getMaxCharges()){
                charges++;
                reload=cooldown;
            }
        }
    }
    
    public boolean fire(){
        if(charges==0)return false;
        charges--;
        internalFire();
        Ship s = getShip();
        if(getBonus(Upgrade.UpgradeType.Fire)!=0){
            s.ignite(50);
        }
        if(getBonus(Upgrade.UpgradeType.Rockets)!=0){
            for(int i=0;i<2;i++){
                for(int j=0;j<6;j++) {
                    RocketBullet rb = Pools.obtain(RocketBullet.class);
                    rb.init();
                    rb.setup(s.getX(), s.getY(), 0, 0, s.getRotation() + Math.PI/2 * (i * 2 - 1), 3);
                    SpaceScreen.get().addBullet(rb);
                }
            }
        }
        return true;
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
        return (float)charges/ getMaxCharges()+(1-((float)reload/cooldown))/(float) getMaxCharges();
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

    public void reloadCharge() {
        charges= Math.min(charges+1, getMaxCharges());
    }

    public int getMaxCharges(){
        return (int) (base_max_charges + getBonus(Upgrade.UpgradeType.Max_Shots));
    }

    public void reset() {
        this.charges=getMaxCharges();
        this.reload=cooldown;
    }
}
