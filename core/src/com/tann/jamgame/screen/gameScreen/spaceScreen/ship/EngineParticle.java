package com.tann.jamgame.screen.gameScreen.spaceScreen.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Particle;

public class EngineParticle extends Particle {

    float size;


    @Override
    public void specificSetup() {
        setupLife(.5f);
        size =6;
    }

    @Override
    public void tick(float delta) {
        this.x+=dx;
        this.y+=dy;
        size -= 0.07f;
    }

    @Override
    public void draw(Batch batch) {
        batch.setColor(Colours.red);
        Draw.fillRectangle(batch, x-size/2, y-size/2, size, size);

    }
}
