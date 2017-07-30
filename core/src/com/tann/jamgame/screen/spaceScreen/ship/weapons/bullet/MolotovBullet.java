package com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.particle.ExplosionParticle;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Maths;
import com.tann.jamgame.util.Sounds;

public class MolotovBullet extends  Bullet{

    static final int WIDTH = 25;
    static final int HEIGHT = 4;
    static final float ROTATION_SPEPED = 1.1f;

    @Override
    public void specificInit() {
        setLife(30);
    }


    @Override
    public void setup(float x, float y, float shipDX, float shipDY, double angle, float speed) {
        this.x=x;
        this.y=y;
        dx=(float)(Math.cos(angle)*speed);
        dy=(float)(Math.sin(angle)*speed);
        float speedRatio = (float) (Math.sqrt(dx*dx+dy*dy) / speed);
        if(speedRatio<1){
            this.dx/=speedRatio;
            this.dy/=speedRatio;
        }
        this.size= 1;
    }

    protected void decayEffect() {
        ExplosionParticle ep = Pools.obtain(ExplosionParticle.class);
        ep.setup();
        ep.resolution = 5;
        ep.perlinZoom = .02f;
        ep.radius=650;
        ep.x = x;
        ep.y = y;
        ep.setupLife(1.1f);
        SpaceScreen.get().addParticle(ep);
        Sounds.playSound(Sounds.molotov_explode, Maths.v.set(x,y));
        for(Ship s:SpaceScreen.get().map.ships){
            if(s.affectedBy(type)){
                if(Maths.distance(s.getX(), s.getY(), x, y)<ep.radius*.6f){
                    s.damage(40);
                }
            }
        }
    }

    @Override
    public void impactEffect() {

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
        batch.setColor(Colours.grey);
        Draw.drawCenteredRotatedScaled(batch, Draw.getSq(), x, y, WIDTH, HEIGHT, Main.ticks*ROTATION_SPEPED);

    }

    @Override
    public int getDamage() {
        return -1;
    }

}
