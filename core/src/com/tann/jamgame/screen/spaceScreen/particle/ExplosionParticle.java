package com.tann.jamgame.screen.spaceScreen.particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.Main;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Noise;
import com.tann.jamgame.util.Particle;

public class ExplosionParticle extends Particle {


    float offset;
    @Override
    public void specificSetup() {
        offset = Particle.rand(0,999);
        setupLife(.65f);
        radius= 50;
        resolution = 3;
        perlinZoom = .03f;
    }

    @Override
    public void tick(float delta) {

    }

    public float radius = 50;
    public int resolution = 3;
    public float perlinZoom = .03f;
    @Override
    public void draw(Batch batch) {
        float radius = this.radius/2+ this.radius/2 * (1-ratio);
        batch.setColor(1,1,1,ratio*ratio*ratio*ratio*.16f);
        float whiteCircleRadius = radius *.7f;
        float zoom = perlinZoom - radius*.00001f;
        Draw.fillEllipse(batch, x-whiteCircleRadius, y-whiteCircleRadius, whiteCircleRadius*2, whiteCircleRadius*2);
        for(float dx = -radius; dx<radius; dx+=resolution){
            for(float dy = -radius; dy<radius; dy+=resolution){
                float perlin = (float) (Noise.noise((dx+offset)*zoom, (dy+offset)*zoom, Main.ticks*3.0f)+1)/2;
                Color col = Colours.shiftedTowards(Colours.neonBlue, Colours.red, perlin);
                col.r = Math.min(1,.6f+perlin);
                col.g = .1f+perlin*perlin*perlin*perlin;
                col.b = (1-perlin)/4;
                float dist = (float) Math.sqrt(dx*dx+dy*dy);
                float distFactor = dist/radius;
                distFactor *= distFactor *distFactor;
                float alpha = perlin;
                alpha -= distFactor;
                alpha *= ratio;
                alpha *= ratio;
                col.a=Math.max(0, alpha);
                batch.setColor(col);
                Draw.fillRectangle(batch, x+dx, y+dy, resolution, resolution);

            }
        }


    }
}
