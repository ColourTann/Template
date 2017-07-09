package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Bullet{

    public boolean dead;

    float x,y,dx,dy, size;

    float drag=1, life=100, startingLife=100;

    public void setDrag(float drag){
        this.drag=drag;
    }

    public void setLife(float life){
        this.startingLife=life;
        this.life=life;
    }

    public abstract void setup(float x, float y, float shipDX, float shipDY, double angle, float speed);

    public void update(){
        this.dx *= drag;
        this.dy *= drag;
        this.x+=dx;
        this.y+=dy;
        this.life--;
        if(life<=0){
            dead=true;
        }
        internalUpdate();
    }

    public abstract void internalUpdate();
    public abstract void draw(Batch batch);

}
