package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.DoubleShot;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class EnemyFighter extends  EnemyShip{

    static TextureRegion tr = Main.atlas.findRegion("ship/enemyFighter");

    public EnemyFighter() {
        super(0.2f, 30, .04f);
        float sizeMult = 1.2f;
        setSize(tr.getRegionWidth()*sizeMult, tr.getRegionHeight()*sizeMult);
        addWeapon(new DoubleShot());
    }

    public void checkAggro(Ship ship){
        float xDiff = ship.getX()-getX();
        float yDiff = ship.getY()-getY();
        float distance = (float) Math.sqrt(xDiff*xDiff+yDiff*yDiff);
        if(!aggroed && distance<1500){
            aggro();
        }
    }

    @Override
    protected void internalAct(float delta) {
        if(!aggroed) {
            checkAggro(SpaceScreen.get().map.fighter);
            checkAggro(SpaceScreen.get().map.tanker);
        }
        if(!aggroed)return;
        Ship tanker = SpaceScreen.get().map.tanker;
        float xDiff = tanker.getX()-getX();
        float yDiff = tanker.getY()-getY();
        float distance = (float) Math.sqrt(xDiff*xDiff+yDiff*yDiff);
        float targetRotation = (float) Math.atan2(yDiff, xDiff);
        while(targetRotation>getRotation()+Math.PI){
            targetRotation-=Math.PI*2;
        }
        while(targetRotation<getRotation()-Math.PI){
            targetRotation+=Math.PI*2;
        }
        float rotationDelta = targetRotation-getRotation();
        setRotation(getRotation()+(Math.signum(rotationDelta))*turnSpeed);
        float move  = accel/2*(1-Math.abs(rotationDelta));
        move = Math.max(0,move);
        if(move > 0){
            makeParticle=true;
        }
        accelerate(move+accel/2);

        if(distance<700 && Math.abs(rotationDelta)<1){
            weapons.get(0).fire();
        }
    }

    @Override
    public Shape2D getShape() {
        return getDefaultShape();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.white);
        Draw.drawCenteredRotatedScaled(batch, tr, getX(), getY(), getWidth()/tr.getRegionWidth(), getHeight()/tr.getRegionHeight(), getRotation());
        super.draw(batch, parentAlpha);
    }
}
