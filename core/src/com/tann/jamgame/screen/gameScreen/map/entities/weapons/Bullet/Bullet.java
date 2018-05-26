package com.tann.jamgame.screen.gameScreen.map.entities.weapons.Bullet;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.screen.gameScreen.map.entities.Drone;

public abstract class Bullet extends Actor{

    Drone target;
    float startTime, time;
    float startX, startY;
    float ratio = 0;
    public Bullet(float x, float y, Drone target, float time) {
        this.target = target;
        this.startTime = time; this.time = time;
        this.startX = x; this.startY = y;
        setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        this.time -= delta;
        this.ratio = (startTime-time)/startTime;
        setPosition(startX + (target.getX()-startX)*ratio, startY + (target.getY()-startY)*ratio);
        if(time<=0){
            impact();
            remove();
        }
        super.act(delta);
    }

    private void impact(){
        target.damage(getDamage());
    }

    protected abstract int getDamage();
}
