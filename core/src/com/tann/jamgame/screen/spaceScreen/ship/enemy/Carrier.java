package com.tann.jamgame.screen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.util.Maths;
import com.tann.jamgame.util.Noise;
import com.tann.jamgame.util.Particle;

public class Carrier extends EnemyShip{
    private static TextureRegion tr = Main.atlas.findRegion("ship/carrier");
    float dist = Ship.AGGRO_RANGE*.7f;
    float rot;
    float targetDist = 820;
    int ticks;
    int seed;
    static final float FREQ = .005f;
    static final float MAG = 250.1f;
    static final float ROT_SPD = .004f;
    public Carrier() {
        super(tr, .3f, 20, .02f);
        float sizeMult = 1.9f;
        setSize(tr.getRegionWidth()*sizeMult, tr.getRegionHeight()*sizeMult);
        seed = (int) (Math.random()*100);
        setHp(60);
    }

    @Override
    protected void internalAct(float delta) {
        if(!aggroed) {
            checkAggro(getTanker(),1.5f);
            if(aggroed){
                rot = (float) Math.atan2(getY()-getTanker().getY(), getX()-getTanker().getX());
            }
            return;
        }
        ticks++;
        if(ticks%60==0){
            spawnSpeeder();
        }
        float targetX = getTanker().getX()+(float)(Math.cos(rot))*dist;
        float targetY = getTanker().getY()+(float)(Math.sin(rot))*dist;


        float lerp = .02f;
        setPosition(
                getX()+(float)(targetX+Noise.noise(ticks*FREQ+seed, 0)*MAG-getX())*lerp,
                getY()+(float)(targetY+Noise.noise(ticks*FREQ+100+seed, 0)*MAG-getY())*lerp);
    }

    private void spawnSpeeder() {
        Speeder s = new Speeder();
        SpaceScreen.get().map.addShip(s);
        s.setPosition(getX(), getY());
    }

    @Override
    public Shape2D getShape() {
        return getDefaultShape();
    }

}
