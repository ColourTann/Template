package com.tann.jamgame.screen.spaceScreen.particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.util.Animation;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Particle;

public class ExplosionAnimationParticle extends Particle {

    static Animation animation = new Animation("effects/explosion",  10);
    float rotation;
    public float size = 60;
    @Override
    public void specificSetup() {
        setupLife(.3f);
        size=60;
        this.rotation = (float)(Math.random()*Math.PI*2);
    }

    @Override
    public void tick(float delta) {
            this.x+=dx;
    }

    @Override
    public void draw(Batch batch) {
        batch.setColor(Colours.white);
        animation.draw(batch, x, y, (int)((1-ratio)*animation.frames.size), size, size, rotation);
    }
}
