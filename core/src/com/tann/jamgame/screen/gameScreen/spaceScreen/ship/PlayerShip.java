package com.tann.jamgame.screen.gameScreen.spaceScreen.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Blaster;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.DoubleShot;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Particle;

public class PlayerShip extends Ship{


    private static final float MAX_SPEED = 25;

    public PlayerShip() {
        setPosition(500,500);
        setSize(40, 20);
        weapon1 = new DoubleShot();
        weapon1.setShip(this);
        weapon2 = new Blaster();
        weapon2.setShip(this);
    }

    static final float ACCEL = .3f;
    static final float DRAG = .985f;


    @Override
    public void act(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            rotateBy(-0.1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            rotateBy(+0.1f);
        }
        boolean makeParticle=false;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            float dxChange = (float)Math.cos(getRotation());
            float dyChange = (float)Math.sin(getRotation());
            dx += ACCEL * dxChange;
            dy += ACCEL * dyChange;
            makeParticle= true;
        }
        weapon1.update();
        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            weapon1.fire();
        }

        weapon2.update();
        if(Gdx.input.isKeyPressed(Input.Keys.X)){
            weapon2.fire();
        }


        dx *= DRAG;
        dy *= DRAG;
        while(getSpeed()>MAX_SPEED) {
            dx *= DRAG;
            dy *= DRAG;
        }

        setPosition(
                getX()+dx,
                getY()+dy);
        if(makeParticle){
            EngineParticle ep = Pools.obtain(EngineParticle.class);
            ep.setup();
            ep.x=getButtX(); ep.y=getButtY();
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
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.green);
        Draw.drawCenteredRotatedScaled(batch, Draw.getSq(), getX(), getY(), getWidth(), getHeight(), getRotation());
        super.draw(batch, parentAlpha);
    }

    Polygon p = new Polygon();
    float[] points = new float[]{0,0,0,0,0,0,0,0};
    @Override
    public Shape2D getShape() {
        for(int i=0;i<points.length;i++){
            points[i]=(i%2==0)?-getWidth()/2:-getHeight()/2;
        }
        points[2]+= getWidth();
        points[4]+= getWidth();
        points[5]+=getHeight();
        points[7]+= getHeight();
        p.setVertices(points);
        p.setRotation((float) (getRotation()*360/(Math.PI*2)));
        p.setPosition(getX(), getY());
        return p;
    }
}
