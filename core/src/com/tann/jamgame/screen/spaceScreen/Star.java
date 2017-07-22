package com.tann.jamgame.screen.spaceScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.Main;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Star extends Actor{
    public Star(float x, float y) {
        setPosition((int)x,(int)y);
        setSize(2,2);
    }

    public void draw(Batch batch, float x, float y) {
        if(Math.abs(x-getX())> Main.width || Math.abs(y-getY())>Main.height){
            return;
        }
        batch.setColor(Colours.light);
        Draw.fillActor(batch,this);
    }
}
