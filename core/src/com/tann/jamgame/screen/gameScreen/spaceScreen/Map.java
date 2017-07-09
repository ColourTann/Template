package com.tann.jamgame.screen.gameScreen.spaceScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.Obstacle.Obstacle;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Particle;

public class Map extends Group{

    Array<Star> stars = new Array<>();
    public Array<Obstacle> obstacles = new Array<>();
    public Map() {
        setSize(5000,4000);
        for(int i=0;i<2000;i++){
            Star s = new Star((float)Math.random()*getWidth(), (float)Math.random()*getHeight());
            stars.add(s);
        }
        for(int i=0;i<20;i++){
            Obstacle o = new Obstacle(Particle.rand(0, getWidth()), Particle.rand(0, getHeight()));
            obstacles.add(o);
            addActor(o);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.dark);
        Draw.fillActor(batch,this);
        for(Star s:stars){
            s.draw(batch, Main.orthoCam.position.x, Main.orthoCam.position.y);
        }
        super.draw(batch, parentAlpha);
    }
}
