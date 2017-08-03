package com.tann.jamgame.screen.spaceScreen.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Particle;

import java.util.ArrayList;
import java.util.List;

public class EngineParticle extends Particle {

    private static List<EngineParticle> engineParticlesPool = new ArrayList<>();
    public static EngineParticle getInstance(){
        if(engineParticlesPool.size()>0){
            return engineParticlesPool.remove(0);
        }
        return new EngineParticle();
    }

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
    protected void endLife() {
        engineParticlesPool.add(this);
    }

    @Override
    public void draw(Batch batch) {
        Color c = Colours.shiftedTowards(col, Colours.white, ratio);
        c.a=ratio;
        batch.setColor(c);

        Draw.fillEllipse(batch, x-size/2, y-size/2, size, size);

    }
}
