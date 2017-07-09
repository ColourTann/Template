package com.tann.jamgame.screen.gameScreen.spaceScreen.ship;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Weapon;


public abstract class Ship extends Group{

    public float dx, dy;
    Weapon weapon1;
    Weapon weapon2;

    public float getNoseX(){
        return getX() + (float)Math.cos(getRotation())*getWidth()/2;
    }

    public float getNoseY(){
        return getY() + (float)Math.sin(getRotation())*getWidth()/2;
    }

    public float getButtX(){
        return getX() - (float)Math.cos(getRotation())*getWidth()/2;
    }

    public float getButtY(){
        return getY() - (float)Math.sin(getRotation())*getWidth()/2;
    }

    public float getSpeed() {
        return (float)Math.sqrt(dx*dx+dy*dy);
    }

    public abstract Shape2D getShape();
}
