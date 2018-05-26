package com.tann.jamgame.screen.gameScreen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.Main;
import com.tann.jamgame.util.Colours;

public class Orb extends Actor{
    public static final ShapeRenderer sr = new ShapeRenderer();
    public boolean white;
    public Orb(boolean white) {
        setSize(1000, 1000);
        setColor(white? Colours.grey[4]:Colours.grey[0]);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        sr.setProjectionMatrix(Main.self.orthoCam.combined);
        sr.setTransformMatrix(batch.getTransformMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(getX(), getY(), getWidth(), 100);
        sr.end();
        batch.begin();
        super.draw(batch, parentAlpha);
    }
}
