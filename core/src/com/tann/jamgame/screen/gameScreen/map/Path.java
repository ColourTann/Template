package com.tann.jamgame.screen.gameScreen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Path extends Actor {
    private static final int width = Map.SIZE/40;
    public final Vector2[] points;
    public boolean white;
    public Path(Vector2... points) {
        this.points = points;
        white = true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.path);
        for(int i=0;i<points.length-1;i++){
            Vector2 a = points[i], b = points[i+1];
            Draw.drawLine(batch, a.x, a.y, b.x, b.y, width);
            Draw.fillEllipse(batch, b.x, b.y, width, width);
        }
        super.draw(batch, parentAlpha);
    }

    public Path invert(){
        Vector2[] inverted = new Vector2[points.length];
        for(int i=0;i<points.length;i++){
            inverted[points.length-i-1] = points[i];
        }
        Path invert = new Path(inverted);
        invert.white = false;
        return invert;
    }
}
