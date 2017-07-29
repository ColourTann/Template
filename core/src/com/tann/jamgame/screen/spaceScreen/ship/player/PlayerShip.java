package com.tann.jamgame.screen.spaceScreen.ship.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Weapon;
import com.tann.jamgame.screen.spaceScreen.ui.WeaponIcon;

public abstract class PlayerShip extends Ship {


    public PlayerShip(TextureRegion tr, float accel, float maxSpeed, float turnSpeed) {
        super(tr, accel, maxSpeed, turnSpeed);
    }

    @Override
    public void act(float delta) {

        super.act(delta);
    }



    @Override
    protected void internalAct(float delta) {

    }

    @Override
    public boolean affectedBy(Bullet.BulletType type) {
        switch (type){
            case Friendly:
                return false;
            case Enemy:
                return true;
        }
        System.err.println("unknown bullet type "+type+" for ship "+getClass());
        return false;
    }

    @Override
    public Shape2D getShape() {
       return getDefaultShape();
    }

    Array<WeaponIcon> weaponIcons;

    public Array<WeaponIcon> getWeaponIcons(){
            weaponIcons = new Array<>();
            for(int i=0;i<weapons.length;i++){
                Weapon  w = weapons[i];
                if(w!=null){
                    weaponIcons.add(new WeaponIcon(Ship.keyFromWeapon(i), w));
                }
            }
        return weaponIcons;
    }

    public void keyPress(int keycode){
        switch(keycode){
            case Input.Keys.Z:
                fireWeapon(0);
                break;
            case Input.Keys.X:
                fireWeapon(1);
                break;
            case Input.Keys.C:
                fireWeapon(2);
                break;
        }
    }




    public float getBaseZoom() {
        return .6f;
    }

}
