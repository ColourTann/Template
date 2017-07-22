package com.tann.jamgame.screen.spaceScreen.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Particle;

public class EngineParticle extends Particle {

    float size;
    public Color col = Colours.blue;

    @Override
    public void specificSetup() {
        setupLife(.4f);
        size =3;
    }

    @Override
    public void tick(float delta) {
        this.dx *=.95f;
        this.dy *=.95f;
        this.x+=dx;
        this.y+=dy;
        size += 0.2f;
    }

    @Override
    public void draw(Batch batch) {
        Color c = Colours.shiftedTowards(col, Colours.white, ratio);
        c.a=ratio;
        batch.setColor(c);

        Draw.fillEllipse(batch, x-size/2, y-size/2, size, size);

    }
}
