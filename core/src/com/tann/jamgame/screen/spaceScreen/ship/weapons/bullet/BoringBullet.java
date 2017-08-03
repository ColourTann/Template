package com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.particle.ExplosionAnimationParticle;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

import java.util.ArrayList;
import java.util.List;

public class BoringBullet extends Bullet {


    private static List<BoringBullet> boringBullets = new ArrayList<>();
    public static BoringBullet getInstance(){
        if(boringBullets.size()>0){
            return boringBullets.remove(0);
        }
        return new BoringBullet();
    }

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
        this.size= 8;
    }

    @Override
    public void impactEffect() {
        ExplosionAnimationParticle eap = ExplosionAnimationParticle.getInstance();
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
        batch.setColor(Colours.withAlpha(Colours.yellow, 1-(float) Math.pow(1-(life/startingLife), 7)));

        Draw.fillEllipse(batch, x-size/2, y-size/2, size, size);
    }

    @Override
    public int getDamage() {
        return 1;
    }

    @Override
    protected void onDispose() {
        boringBullets.add(this);
    }
}
