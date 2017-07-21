package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.SpikeBullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.SpikeLauncher;

public class Bomber extends EnemyShip {
	TextureRegion bomber = Main.atlas.findRegion("ship/bomber");

	// Stolen from the other file.
	float seed = (float) (Math.random()*500);
	static final float BASE_DIST = 450;
	static final float DIST_RAND = 350;
	float dist = BASE_DIST+(float)Math.random()*DIST_RAND;

	// Tune-ables
	public float selfDestructDelay = 2f; //
	public int numBulletsOnExplosion = 360;
	public float selfDestructDistanceThreshold = 200f;

	// Private logic
	protected float destructTimeRemaining = Float.POSITIVE_INFINITY; // Infinite while not self-destructing.

	public Bomber() {
		super(1.5f, 1.5f, .01f);
		this.tr = bomber;
		setSize(bomber.getRegionWidth(), bomber.getRegionHeight());
		// No Weapon.
		setHp(250); // Takes a lot of hits.
	}

	@Override
	protected void internalAct(float delta) {
		//setY((float) (getY()+Math.sin(Main.ticks*speed+seed)*2.5f));
		// Have we started self-destructing?
		if(Float.isInfinite(destructTimeRemaining)) {
			// Nope.  Are we near the ship?
			if(getTankerDist() < selfDestructDistanceThreshold){
				destructTimeRemaining = selfDestructDelay; // Now make it finite so we start self destructing.
				System.out.println("Started destructing.");
				// Also, vibrate a little?  JUICE!?
			} else {
				tailTanker(dist);
			}
		} else {
			destructTimeRemaining -= delta;
			if(destructTimeRemaining <= 0) {
				explode();
			}
		}
	}

	protected void explode() {
		float radiansPerBullet = (float)((Math.PI*2.0f)/(float)numBulletsOnExplosion);
		for(int i=0; i < numBulletsOnExplosion; i++) {
			Bullet b = Pools.obtain(SpikeBullet.class);
			b.init();
			b.type = Bullet.BulletType.Enemy;
			b.setup(this.getX(), this.getY(), this.dx, this.dy, i*radiansPerBullet, 20);
			b.setLife(350);
			SpaceScreen.get().addBullet(b);
		}
		// Destroy this.
		this.destroy();
	}

	@Override
	public Shape2D getShape() {
		return getDefaultShape();
	}
}
