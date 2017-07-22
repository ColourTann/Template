package com.tann.jamgame.screen.spaceScreen.shipUpgrade;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.jamgame.Main;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;


public class UpgradePanel extends Group{
    static final float SIZE = 80;
    float targetX, targetY;
    private static final TextureRegion lock = Main.atlas.findRegion("lock");
    public UpgradePanel(float x, float y, float boxX, float boxY) {
        setPosition(boxX, boxY);
        setSize(SIZE,SIZE);
        targetX = x-boxX; targetY=y-boxY;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.green);
        Draw.drawLine(batch, getX()+getWidth()/2, getY()+getHeight()/2, getX()+targetX, getY()+targetY, 2);
        Draw.fillActor(batch, this, Colours.dark, Colours.light, 4);
        batch.setColor(Colours.neonBlue);
        float lockScale = .8f;
        Draw.drawSizeCentered(batch, lock, getX()+getWidth()/2, getY()+getHeight()/2, getWidth()*lockScale, getHeight()*lockScale);
    }
}
