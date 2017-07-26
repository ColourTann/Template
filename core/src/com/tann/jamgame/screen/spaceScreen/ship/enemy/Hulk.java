package com.tann.jamgame.screen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.SpikeLauncher;
import com.tann.jamgame.util.Animation;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Hulk extends EnemyShip{

    static Animation hulk = new Animation(Main.atlas.findRegion("ship/bigship"), 8,1, 10);
    float seed = (float) (Math.random()*500);
    static final float BASE_DIST = 450;
    static final float DIST_RAND = 350;
    float dist = BASE_DIST+(float)Math.random()*DIST_RAND;
    public Hulk() {
        super(hulk.getFrame(), .3f, 7, .03f);
        float sizeMult = 1.85f;
        setSize(tr.getRegionWidth()*sizeMult, tr.getRegionHeight()*sizeMult);
        addWeapon(new SpikeLauncher(), 0);
        setHp(45);
    }

    @Override
    protected void internalAct(float delta) {
        tailTanker(dist);
        setY((float) (getY()+Math.sin(Main.ticks*1.2+seed)*2.5f));
        if(getTankerDist()<dist+50 && Math.abs(getTankerTargetRotation()-getRotation())<.4f){
            fireWeapon(0);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.shiftedTowards(Colours.white, Colours.red, flash));
        Draw.drawCenteredRotatedScaled(batch, hulk.getFrame(), getX(), getY(), getWidth()/tr.getRegionWidth(), getHeight()/tr.getRegionHeight(), getRotation());
    }

    @Override
    public Shape2D getShape() {
        return getDefaultShape();
    }

}
