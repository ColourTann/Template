package com.tann.jamgame.screen.spaceScreen.particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Particle;

public class FireParticle extends Particle {

    @Override
    public void specificSetup() {
        setupLife(.7f);
        this.colour = Colours.shiftedTowards(Colours.yellow, Colours.red, (float)(Math.random())).cpy();
    }

    @Override
    public void tick(float delta) {
        this.x+=dx;
        this.y+=dy;
    }

    float size = 15;

    @Override
    public void draw(Batch batch) {
        batch.setColor(Colours.withAlpha(colour, ratio));
        float s = size/2+size/2*(ratio);
        Draw.fillEllipse(batch, x-s/2, y-s/2, s, s);
    }
}
