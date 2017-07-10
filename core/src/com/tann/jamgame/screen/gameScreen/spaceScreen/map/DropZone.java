package com.tann.jamgame.screen.gameScreen.spaceScreen.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class DropZone {
    public float x;
    public float y;
    private float radius;
    private float r2;
    public DropZone(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        r2 = radius * radius;
    }

    public boolean inside(Ship s){
        float xDiff = s.getX()-x;
        float yDiff = s.getY()-y;
        return xDiff*xDiff+yDiff*yDiff<r2;
    }

    public void draw(Batch batch){
        batch.setColor(Colours.withAlpha(Colours.green, .3f));
        Draw.fillEllipse(batch, x-radius, y-radius, radius*2, radius*2);
    }

    public void drawMinimap(Batch batch, float xScale, float yScale) {
        batch.setColor(Colours.withAlpha(Colours.green, .3f));
        Draw.fillEllipse(batch, (x-radius)*xScale, (y-radius)*yScale, radius*2*xScale, radius*2*yScale);
    }
}
