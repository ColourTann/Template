package com.tann.jamgame.screen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.DoubleShot;
import com.tann.jamgame.util.Sounds;

public class Speeder extends EnemyShip{

    static TextureRegion speeder = Main.atlas.findRegion("ship/enemyFighter");

    public Speeder() {
        super(speeder, 0.65f, 60, .04f);
        float sizeMult = 1.2f;
        setSize(tr.getRegionWidth()*sizeMult, tr.getRegionHeight()*sizeMult);
        addWeapon(new DoubleShot(),0);
    }

    @Override
    protected void internalAct(float delta) {
        pursueTanker();
        if(getTankerDist()<700 && Math.abs(getTankerTargetRotation()-getRotation())<1){
            weapons[0].fire();
        }
    }

    @Override
    protected void onDeath() {
        Sounds.playSound(Sounds.explod_smol, .4f, 1);
    }

    @Override
    public Shape2D getShape() {
        return getDefaultShape();
    }

}
