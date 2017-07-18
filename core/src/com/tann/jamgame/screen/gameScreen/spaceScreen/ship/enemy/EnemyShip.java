package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public abstract class EnemyShip extends Ship {

    boolean aggroed;

    public EnemyShip(float accel, float maxSpeed, float turnSpeed) {
        super(accel, maxSpeed, turnSpeed);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public boolean affectedBy(Bullet.BulletType type) {
        switch (type){
            case Friendly:
                return true;
            case Enemy:
                return false;
        }
        System.err.println("unknown bullet type "+type+" for ship "+getClass());
        return false;
    }

    @Override
    public void drawMinimap(Batch batch, float x, float y) {
//        batch.setColor(Colours.red);
//        float size = 1;
//        Draw.fillEllipse(batch, x-size/2, y-size/2, size, size);
    }

    public void checkAggro(Ship ship) {
        float xDiff = ship.getX() - getX();
        float yDiff = ship.getY() - getY();
        float distance = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        if (!aggroed && distance < 1500) {
            aggro();
        }
    }

    public float getTankerDist(){
        Ship tanker = SpaceScreen.get().map.tanker;
        float xDiff = tanker.getX()-getX();
        float yDiff = tanker.getY()-getY();
        return (float) Math.sqrt(xDiff*xDiff+yDiff*yDiff);
    }

    public float getTankerTargetRotation(){
        Ship tanker = SpaceScreen.get().map.tanker;
        float xDiff = tanker.getX()-getX();
        float yDiff = tanker.getY()-getY();
        float targetRotation = (float) Math.atan2(yDiff, xDiff);
        while(targetRotation>getRotation()+Math.PI){
            targetRotation-=Math.PI*2;
        }
        while(targetRotation<getRotation()-Math.PI){
            targetRotation+=Math.PI*2;
        }
        return targetRotation;
    }


    public void pursueTanker(){
        checkAggros();
        if(!aggroed)return;
        float rotationDelta = getTankerTargetRotation()-getRotation();
        setRotation(getRotation()+(Math.signum(rotationDelta))*turnSpeed);
        float move  = accel/2*(1-Math.abs(rotationDelta));
        move = Math.max(0,move);
        if(move > 0){
            makeParticle=true;
        }
        accelerate(move+accel/2);
    }

    public void tailTanker(float dist){
        checkAggros();
        if(!aggroed)return;
        float rotationDelta = getTankerTargetRotation()-getRotation();
        setRotation(getRotation()+(Math.signum(rotationDelta))*turnSpeed);
        float move  = accel/2*(1-Math.abs(rotationDelta))*(getTankerDist()<dist?.1f:1);
        move = Math.max(0,move);
        if(move > 0){
            makeParticle=true;
        }
        accelerate(move+accel/2);
    }

    protected void checkAggros(){
        if(!aggroed) {
            checkAggro(SpaceScreen.get().map.fighter);
            checkAggro(SpaceScreen.get().map.tanker);
        }
    };

    public void aggro(){
        aggroed=true;
    }
}
