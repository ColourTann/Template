package com.tann.jamgame.screen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
Friends
        DIRECT MESSAGES
        jo
        sunil
        fireblt
        Syfn
        Teg42
        Playing Starbound
        Pawntoe4
        Jack
        Rupert Buttermilk
        Forer
        tann
        #7549


        jo



        Search



        LOAD MORE MESSAGES
        tann - Last Saturday at 9:17 PM
        Oh yeah I've almost finished Sherlock and will listen to h2g2 on commutes next week!
        jo - Last Saturday at 9:22 PM
        More subtly lightning effects.  Should I add the thrusters to this?
        tann - Last Saturday at 9:25 PM
        I'm not sure, we could make the particle effects better and use them more
        Or we can use the same thrusters as the tanker, they're a separate thing you can add to stuff
        tann - Last Saturday at 10:53 PM
        Hi! I had to resolve some conflicts because I forgot to push code from a while ago I'm a dummy sorry
        Pull before working further so as to avoid more conflicts :smiley:
        jo - Last Saturday at 10:54 PM
        Will do.  No worries.
        July 23, 2017
        tann - Yesterday at 3:01 PM
        Oh yeah can you export as separate images so I don't have to rewrite the animation thing?
        I guess it would only take a minute, nevermind :stuck_out_tongue:
        jo - Yesterday at 5:00 PM
        D'oh.
        Yeah.
        Want me to do that still?
        tann - Yesterday at 5:00 PM
        Nah don't worry, would be good for the animation class to handle sheets or separate images anyway
        jo - Yesterday at 5:01 PM
        Well then.

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
        setHp(25);
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
