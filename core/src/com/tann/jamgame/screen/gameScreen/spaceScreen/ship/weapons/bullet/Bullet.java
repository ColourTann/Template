package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;

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

    public abstract Shape2D getShape();

    static Circle c = new Circle();
    protected Shape2D getDefaultBulletShape() {
        c.set(x+size/2,y+size/2,size/2);
        return c;
    }

    public abstract void internalUpdate();
    public abstract void draw(Batch batch);
    public abstract int getDamage();
}
