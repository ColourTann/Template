package com.tann.jamgame.util;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;

import java.util.ArrayList;

public abstract class Screen extends Group{
	//screenshake stuff//
    public Stage specialStage;
	private float shakeMagnitude=0;
	private static float shakeFrequency=100;
	private static float shakeDrag=.005f;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private ArrayList<Particle> newParticles = new ArrayList<Particle>();
	boolean active = false;
	public Screen() {
		setSize(Main.width, Main.height);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
	    if(specialStage!=null){
	        batch.end();
	        specialStage.draw();
            specialStage.getBatch().begin();
	        drawParticles(specialStage.getBatch());
            for(Bullet bill: SpaceScreen.get().bullets) {
                bill.draw(specialStage.getBatch());
            }
            specialStage.getBatch().end();
	        batch.begin();
        }
	    Main.logTime(null);
		preDraw(batch);
		Main.logTime("pre");
		batch.end();
		batch.begin();
		super.draw(batch, parentAlpha);
		Main.logTime("super");

		Main.logTime("partcles");
		postDraw(batch);
		Main.logTime("post");
	}
	public abstract void preDraw(Batch batch);
	public abstract void postDraw(Batch batch);

	@Override
	public void act(float delta) {
		if(Main.self.getState()== Main.MainState.Paused)return;
		if(active){
			if(shakeMagnitude>.1){
			setPosition((float)(Math.sin(Main.ticks*shakeFrequency)*shakeMagnitude), 
					(float) (Math.cos((Main.ticks+100)*shakeFrequency)*shakeMagnitude));
			}
			else{
				setPosition(0,0);
			}
		}
		shakeMagnitude*=Math.pow(shakeDrag, delta);
		tickParticles(delta);
		preTick(delta);
		super.act(delta);
		postTick(delta);
	}
	
	public void shake(float magnitude){
		this.shakeMagnitude+=magnitude;
	}

	public abstract void preTick(float delta);
	public abstract void postTick(float delta);

	public void addParticle(Particle p){
		newParticles.add(p);
	}
	private void tickParticles(float delta) {
		particles.addAll(newParticles);
		newParticles.clear();
		for(int i=particles.size()-1;i>=0;i--){
            Particle p = particles.get(i);
			p.act(delta);
            if(p.dead)particles.remove(p);
		}
	}

	public void drawParticles(Batch batch){
        for(Particle p : particles) p.draw(batch);
	}

	public abstract void keyPress(int keycode);

	public void setActive(boolean active) {
		this.active=active;
	}

	public void removeFromScreen(){}


}
