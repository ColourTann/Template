package com.tann.jamgame.screen.gameScreen.map.entities.weapons.Bullet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.screen.gameScreen.map.entities.Drone;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class LaserBullet extends Bullet {


    public LaserBullet(float x, float y, Drone target) {
        super(x, y, target, .3f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.red);
        Draw.fillEllipse(batch, getX(), getY(), 4, 4);
        super.draw(batch, parentAlpha);
    }

    @Override
    protected int getDamage() {
        return 1;
    }
}
