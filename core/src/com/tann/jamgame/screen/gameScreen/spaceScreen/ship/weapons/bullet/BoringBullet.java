package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class BoringBullet extends Bullet {

    @Override
    public void specificInit() {

    }

    @Override
    public void setup(float x, float y, float shipDX, float shipDY, double angle, float speed) {
        this.x=x;
        this.y=y;
        dx=(float)(Math.cos(angle)*speed)+shipDX;
        dy=(float)(Math.sin(angle)*speed)+shipDY;
        float speedRatio = (float) (Math.sqrt(dx*dx+dy*dy) / speed);
        if(speedRatio<1){
            this.dx/=speedRatio;
            this.dy/=speedRatio;
        }
        this.size= 8;
    }

    @Override
    public Shape2D getShape() {
        return getDefaultBulletShape();
    }

    @Override
    public void internalUpdate() {

    }


    @Override
    public void draw(Batch batch) {
        batch.setColor(Colours.yellow);
        Draw.fillEllipse(batch, x-size/2, y-size/2, size, size);
    }

    @Override
    public int getDamage() {
        return 4;
    }
}
