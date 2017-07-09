package com.tann.jamgame.screen.gameScreen.spaceScreen.Obstacle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Obstacle extends Actor{

    public Obstacle(float x, float y) {
        setPosition(x,y);
        setSize(60, 60);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.red);
        Draw.fillEllipse(batch, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
