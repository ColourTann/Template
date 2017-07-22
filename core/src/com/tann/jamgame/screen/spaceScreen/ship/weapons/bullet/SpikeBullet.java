package com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.particle.ExplosionAnimationParticle;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class SpikeBullet extends Bullet {

    TextureRegion tr = Main.atlas.findRegion("spikeball");

    @Override
    public void specificInit() {

    }

    @Override
    public void setup(float x, float y, float shipDX, float shipDY, double angle, float speed) {
        this.x=x;
        this.y=y;
        dx=(float)(Math.cos(angle)*speed)+shipDX;
        dy=(float)(Math.sin(angle)*speed)+shipDY;
        float speedRatio = (float) (Math.sqrt(dx*dx+dy*dy) / speed);
        if(speedRatio<1){
            this.dx/=speedRatio;
            this.dy/=speedRatio;
        }
        this.size= 50;
    }

    @Override
    public void impactEffect() {
        ExplosionAnimationParticle eap = Pools.obtain(ExplosionAnimationParticle.class);
        eap.setup();
        eap.x=x+dx;
        eap.y=y+dy;
        eap.dx = SpaceScreen.get().map.tanker.dx;
        SpaceScreen.get().addParticle(eap);
    }

    @Override
    public Shape2D getShape() {
        return getDefaultBulletShape();
    }

    @Override
    public void internalUpdate() {

    }

    @Override
    public void draw(Batch batch) {
        batch.setColor(Colours.white);

        Draw.drawCenteredRotatedScaled(batch, tr, x, y, size/tr.getRegionWidth(), size/tr.getRegionHeight(), 0);
    }

    @Override
    public int getDamage() {
        return 40;
    }
}
