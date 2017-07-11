package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Weapon;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ui.WeaponIcon;

public abstract class PlayerShip extends Ship {


    public PlayerShip(float accel, float maxSpeed, float turnSpeed) {
        super(accel, maxSpeed, turnSpeed);
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

            if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                weapons.get(0).fire();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.X)) {
                weapons.get(1).fire();
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



    public float getBaseZoom() {
        return .6f;
    }

}
