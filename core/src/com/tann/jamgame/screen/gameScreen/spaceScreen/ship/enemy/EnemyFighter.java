package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.DoubleShot;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class EnemyFighter extends  EnemyShip{

    public EnemyFighter() {
        super(0.2f, 30);
        setSize(40,20);
        weapon1 = new DoubleShot(false);
        weapon1.setShip(this);
    }

    @Override
    protected void internalAct(float delta) {
        Ship tanker = SpaceScreen.get().map.tanker;
        float xDiff = tanker.getX()-getX();
        float yDiff = tanker.getY()-getY();
        float distance = (float) Math.sqrt(xDiff*xDiff+yDiff*yDiff);
        if(!aggroed && distance<1500){
            aggro();
        }
        if(!aggroed)return;
        float targetRotation = (float) Math.atan2(yDiff, xDiff);
        while(targetRotation>getRotation()+Math.PI){
            targetRotation-=Math.PI*2;
        }
        while(targetRotation<getRotation()-Math.PI){
            targetRotation+=Math.PI*2;
        }
        float rotationDelta = targetRotation-getRotation();
        setRotation(getRotation()+(Math.signum(rotationDelta))*.02f);
        float move  = accel/2*(1-Math.abs(rotationDelta));
        move = Math.max(0,move);
        accelerate(move+accel/2);

        if(distance<700 && Math.abs(rotationDelta)<1){
            weapon1.fire();
        }
    }

    @Override
    public Shape2D getShape() {
        return getDefaultShape();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float alpha = getColor().a;
        batch.setColor(Colours.withAlpha(Colours.red, alpha));
        Draw.drawCenteredRotatedScaled(batch, Draw.getSq(), getX(), getY(), getWidth(), getHeight(), getRotation());
    }
}
