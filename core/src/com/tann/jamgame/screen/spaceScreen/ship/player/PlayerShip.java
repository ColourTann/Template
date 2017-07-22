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
        if(control) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                rotateBy(-turnSpeed);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                rotateBy(+turnSpeed);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                accelerate(accel);
                makeParticle = true;
            }


        }
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
        if(weaponIcons == null){
            weaponIcons = new Array<>();
            for(int i=0;i<weapons.size;i++){
                Weapon  w = weapons.get(i);
                weaponIcons.add(new WeaponIcon(Ship.keyFromWeapon(i), w));
            }
        }
        return weaponIcons;
    }

    public void keyPress(int keycode){
        switch(keycode){
            case Input.Keys.Z:
                if(weapons.size>0 && weapons.get(0)!=null) {
                    weapons.get(0).fire();
                }
                break;
            case Input.Keys.X:
                if(weapons.size>1 && weapons.get(1)!=null) {
                    weapons.get(1).fire();
                }
                break;
        }
    }


    public float getBaseZoom() {
        return .6f;
    }

}
