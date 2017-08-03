package com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.particle.ExplosionAnimationParticle;
import com.tann.jamgame.util.*;

public class RocketBullet extends Bullet {

    static TextureRegion tr = Main.atlas.findRegion("weapon/rocket");
    public float rot;
    @Override
    public void specificInit() {

    }

    @Override
    public boolean armed() {
        return armIn<=0;
    }

    @Override
    public void setup(float x, float y, float shipDX, float shipDY, double angle, float speed) {
        this.x= x;
        this.y= y;
        this.dx=(float) (Math.cos(rot)*speed);

        this.dy=(float) (Math.sin(rot)*speed);
        this.rot= (float) angle;
        setLife(Particle.rand(20,70));
        this.size= 30;
        this.type=BulletType.Friendly;
        this.armIn=10;
    }

    int armIn;

    @Override
    public void impactEffect() {
        ExplosionAnimationParticle eap = ExplosionAnimationParticle.getInstance();
        eap.setup();
        eap.x=x;
        eap.y=y;
        SpaceScreen.get().addParticle(eap);
        explodeSounds();
    }

    private void explodeSounds(){
        Sounds.playSound(Sounds.rock_splode, .35f, 1, Maths.v.set(x,y));
    }

    @Override
    public Shape2D getShape() {
        return getDefaultBulletShape();
    }
    float angleD;
    static float speed = 7;
    @Override
    public void internalUpdate() {
        this.armIn--;
        this.x += Math.cos(rot)*speed;
        this.y += Math.sin(rot)*speed;
        angleD += Particle.rand(-.05f,.05f);
        rot+=angleD;
        this.angleD*=.9f;

    }

    @Override
    protected void decayEffect() {
        ExplosionAnimationParticle eap = ExplosionAnimationParticle.getInstance();
        eap.setup();
        eap.x=x;
        eap.y=y;
        SpaceScreen.get().addParticle(eap);
        explodeSounds();
    }

    @Override
    public void draw(Batch batch) {
        batch.setColor(Colours.white);
        Draw.drawCenteredRotatedScaled(batch, tr, x, y, 2f, 2f, rot);
    }

    @Override
    public int getDamage() {
        return 3;
    }
}
