package com.tann.jamgame.screen.gameScreen.spaceScreen.ship;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Weapon;


public abstract class Ship extends Group{

    public float dx, dy;
    Weapon weapon1;
    Weapon weapon2;

    static final float DRAG = .985f;
    final float accel;
    final float maxSpeed;

    public Ship(float accel, float maxSpeed) {
        this.accel = accel;
        this.maxSpeed =maxSpeed;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        dx *= DRAG;
        dy *= DRAG;
        while(getSpeed()> maxSpeed) {
            dx *= DRAG;
            dy *= DRAG;
        }
        setPosition(getX()+dx,getY()+dy);
        if(weapon1!=null)weapon1.update();
        if(weapon2!=null)weapon2.update();
        internalAct(delta);
    }

    protected abstract void internalAct(float delta);

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
