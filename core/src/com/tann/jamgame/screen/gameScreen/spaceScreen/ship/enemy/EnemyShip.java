package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public abstract class EnemyShip extends Ship {

    boolean aggroed;

    public EnemyShip(float accel, float maxSpeed) {
        super(accel, maxSpeed);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.red);
        Draw.drawCenteredRotatedScaled(batch, Draw.getSq(), getX(), getY(), getWidth(), getHeight(), getRotation());
        super.draw(batch, parentAlpha);
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
        batch.setColor(Colours.red);
        float size = 1;
        Draw.fillEllipse(batch, x-size/2, y-size/2, size, size);
    }

    public void aggro(){
        aggroed=true;
    }
}
