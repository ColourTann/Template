package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class BlasterBullet extends Bullet {
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
        this.size= 5;
    }

    @Override
    public void internalUpdate() {

    }

    @Override
    public void draw(Batch batch) {
        float ratio = life/startingLife;
        Color c = Colours.shiftedTowards(Colours.red, Colours.yellow, 1-(ratio));
        c.a=1-(1-ratio)*(1-ratio);
        batch.setColor(c);
        Draw.fillEllipse(batch, x-size/2, y-size/2, size, size);
    }
}
