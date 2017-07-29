package com.tann.jamgame.screen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.BoringBullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.util.*;

public class Bomber extends EnemyShip {
	static TextureRegion bomber = Main.atlas.findRegion("ship/bomber");
	Animation<TextureRegion> idleAnimation = null;
	float animationTimeAccumulator = 0f;

	// Tune-ables
	public int numBulletsOnExplosion = 220;
    private static final float BASE_ACCEL = .01f;
    private static final float ACCEL_MULT = .6f;
    private static final int MAX_CHARGE = 310;
    int charge = 0;

	// Private logic
	protected float destructTimeRemaining = Float.POSITIVE_INFINITY; // Infinite while not self-destructing.


	public Bomber() {
		super(bomber, BASE_ACCEL, 100.5f, .11f);
		// Assume we're tiled four frames on X.  TODO: Automate the loading of animations.
		idleAnimation = new Animation<>(0.2f, new Array<>(new TextureRegion[]{
				new TextureRegion(bomber, 64 * 0, 0, 64, 64),
				new TextureRegion(bomber, 64 * 1, 0, 64, 64),
				new TextureRegion(bomber, 64 * 2, 0, 64, 64),
				new TextureRegion(bomber, 64 * 3, 0, 64, 64)
		}));
		this.tr = bomber;
		setSize(64, 64);
		// No Weapon.
		setHp(26); // Takes a lot of hits. (35 is a lot!)
	}

	@Override
	protected void internalAct(float delta) {
        this.tr = idleAnimation.getKeyFrame(animationTimeAccumulator, true);
        animationTimeAccumulator += delta;
        if(Shape.overlaps(getShape(), getTanker().getShape())){
            explode();
        }
        checkAggros();
        if(aggroed){
            charge = Math.min(charge+1, MAX_CHARGE);
            rotateTowardsTanker();
            this.accel = (float) (BASE_ACCEL + ACCEL_MULT * Math.pow((float)charge/MAX_CHARGE, 2f));
            moveTowardsTanker();
        }
    }

	protected void explode() {
		float radiansPerBullet = (float)((Math.PI*2.0f)/(float)numBulletsOnExplosion);
		for(int i=0; i < numBulletsOnExplosion; i++) {
			Bullet b = Pools.obtain(BoringBullet.class);
			b.init();
			b.type = Bullet.BulletType.Enemy;
			b.setup(this.getX(), this.getY(), 0,0, i*radiansPerBullet+Particle.rand(0,.2f), Particle.rand(30,55));
			b.setDrag(.90f);
			b.setLife(32);
			SpaceScreen.get().addBullet(b);
		}
		Sounds.playSound(Sounds.explod_bomber, .7f, 1, Maths.v.set(getX(), getY()));
		// Destroy this.
		this.destroy(true);
	}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(aggroed && charge>0){
            float ratio = 1;
            batch.setColor(Colours.shiftedTowards(Colours.light, Colours.red, ratio));
            Ship tanker = SpaceScreen.get().map.tanker;
            Draw.drawLine(batch, getX(), getY(), tanker.getX(), tanker.getY(), 5-ratio*4);
        }
        super.draw(batch, parentAlpha);
    }

    @Override
    protected void onDeath() {
        super.onDeath();
        Sounds.playSound(Sounds.pew, Maths.v.set(getX(), getY()));
    }

    @Override
	public Shape2D getShape() {
		return getDefaultShape();
	}
}
