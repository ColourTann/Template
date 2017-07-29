package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pools;

public abstract class Particle {
	public boolean dead;
	public float x,y,dx,dy,angle,ratio;
	private float life, startLife;
	protected Color colour;
	public void setup(){
	    dead=false;
	    x=0;y=0;dx=0;dy=0;angle=0;ratio=0;life=0;startLife=0;colour=null;
	    specificSetup();
    }
	public abstract void specificSetup();
	public abstract void tick(float delta);
	public abstract void draw(Batch batch);
	public void act(float delta){
		tickLife(delta);
		tick(delta);
	}
	public void setupLife(float life){
		startLife=life;
		this.life=life;
	}
	protected void tickLife(float delta){
		life-=delta;
		if(life<=0){
			dead=true;
            life=0;
            Pools.free(this);
        }
		ratio=life/startLife;
	}
	public static float rand(float min, float max){
		return (float)(Math.random()*(max-min)+min);
	}
}
