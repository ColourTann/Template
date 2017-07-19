package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.SpikeLauncher;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Hulk extends EnemyShip{

    TextureRegion hulk = Main.atlas.findRegion("ship/shitship");
    float seed = (float) (Math.random()*500);
    static final float BASE_DIST = 450;
    static final float DIST_RAND = 350;
    float dist = BASE_DIST+(float)Math.random()*DIST_RAND;
    public Hulk() {
        super(.3f, 7, .03f);
        this.tr=hulk;
        setSize(tr.getRegionWidth(), tr.getRegionHeight());
        addWeapon(new SpikeLauncher());
        setHp(25);
    }

    @Override
    protected void internalAct(float delta) {
        tailTanker(dist);
        setY((float) (getY()+Math.sin(Main.ticks*1.2+seed)*2.5f));
        if(getTankerDist()<dist+50 && Math.abs(getTankerTargetRotation()-getRotation())<.4f){
            weapons.get(0).fire();
        }
    }

    @Override
    public Shape2D getShape() {
        return getDefaultShape();
    }

}
