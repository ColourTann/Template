package com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pools;

public abstract class Bullet{



    public enum BulletType{
        Friendly, Enemy
    }
    public BulletType type;
    public boolean dead;
    float x;
    float y;
    float dx;
    float dy;
    public float size;
    float drag=1, life=100, startingLife=100;
    boolean firstUpdate;
    public void setDrag(float drag){
        this.drag=drag;
    }

    public void setLife(float life){
        this.startingLife=life;
        this.life=life;
    }

    public void init(){
        dead=false;x=0;y=0;dx=0;dy=0;size=0;drag=1;life=100;startingLife=100;type=null;firstUpdate=true;
        specificInit();
    }

    public abstract void specificInit();
    public abstract void setup(float x, float y, float shipDX, float shipDY, double angle, float speed);

    float stepX;
    float stepY;
    int steps;
    public boolean update(){
        if(dead){
            return true;
        }
        if(firstUpdate){
            firstUpdate=false;
            this.dx *= drag;
            this.dy *= drag;
            steps=1;
            if(Math.abs(this.dx)>size||Math.abs(this.dy)>size){
                steps = (int) (Math.max(Math.abs(this.dx), Math.abs(this.dy))/size);
            }
            if(steps<=0) steps=1;
            stepX = dx/(float)steps;
            stepY = dy/(float)steps;
        }

        this.x+=stepX;
        this.y+=stepY;

        steps--;
        if(steps==0){
            this.life--;
            if(life<=0){
                dead=true;
                Pools.free(this);
            }
            internalUpdate();
            firstUpdate=true;
            return true;
        }
        return false;
    }

    public void impact(){
        dead=true;
        impactEffect();
    }

    public abstract void impactEffect();

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
