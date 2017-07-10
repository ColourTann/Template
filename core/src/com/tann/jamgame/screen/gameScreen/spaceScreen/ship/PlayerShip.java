package com.tann.jamgame.screen.gameScreen.spaceScreen.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Blaster;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.DoubleShot;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Particle;

public class PlayerShip extends Ship{


    public PlayerShip() {
        super(.3f,20);
        setPosition(500,500);
        setSize(32, 15);
        weapon1 = new DoubleShot(true);
        weapon1.setShip(this);
        weapon2 = new Blaster(true);
        weapon2.setShip(this);
        setHp(150);
    }

    @Override
    public void act(float delta) {
        boolean makeParticle = false;
        if(control) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                rotateBy(-0.1f);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                rotateBy(+0.1f);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                accelerate(accel);
                makeParticle = true;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                weapon1.fire();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.X)) {
                weapon2.fire();
            }
        }

        if(makeParticle){
            EngineParticle ep = Pools.obtain(EngineParticle.class);
            ep.setup();
            ep.x=getButtX()+dx*1.5f; ep.y=getButtY()+dy*1.5f;
            float rand = .9f;
            float initialMult = -.1f;
            ep.dx = -dx*initialMult + Particle.rand(-rand, rand);
            ep.dy = -dy*initialMult + Particle.rand(-rand, rand);
            ep.x-=ep.dx;
            ep.y-=ep.dy;
            SpaceScreen.get().addParticle(ep);
        }
        super.act(delta);
    }

    @Override
    protected void internalAct(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.green);
        Draw.drawCenteredRotatedScaled(batch, Draw.getSq(), getX(), getY(), getWidth(), getHeight(), getRotation());
        super.draw(batch, parentAlpha);
    }

    @Override
    public Shape2D getShape() {
       return getDefaultShape();
    }

    @Override
    public void drawMinimap(Batch batch, float x, float y) {
        batch.setColor(Colours.withAlpha(Colours.light, batch.getColor().a));
        float size = 4;
        Draw.fillEllipse(batch, x-size/2, y-size/2, size, size);
    }

    @Override
    public boolean affectedBy(Bullet.BulletType type) {
        switch (type){
            case Friendly:
                return false;
            case Enemy:
                return true;
        }
        System.err.println("unknown bullet type "+type+" for ship "+getClass());
        return false;
    }

    public float getBaseZoom() {
        return 1.6f;
    }

}
