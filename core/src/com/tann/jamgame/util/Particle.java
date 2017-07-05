package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Particle {
	public boolean dead;
	public float x,y,dx,dy,angle,ratio;
	private float life, startLife;
	protected Color colour;
	public abstract void tick(float delta);
	public abstract void draw(Batch batch);
	public void act(float delta){
		tickLife(delta);
		tick(delta);
	}
	protected void setupLife(float life){
		startLife=life;
		this.life=life;
	}
	protected void tickLife(float delta){
		life-=delta;
		if(life<=0){
			dead=true;
			life=0;
		}
		ratio=life/startLife;
	}
	public static float rand(float min, float max){
		return (float)(Math.random()*(max-min)+min);
	}
}
