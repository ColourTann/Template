package com.tann.jamgame.screen.spaceScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Fonts;

public class MapProgress extends Actor{

    public MapProgress(float width) {
        setSize(width, 25);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Draw.fillActor(batch, this, Colours.dark, Colours.light, 2);
        batch.setColor(Colours.blue);
        Draw.fillRectangle(batch, getX(), getY(), getWidth()* SpaceScreen.get().map.getProgress(), getHeight());
        Fonts.draw(batch, "Progress", Fonts.font, Colours.light, getX(), getY(), getWidth(), getHeight(), Align.center);
        super.draw(batch, parentAlpha);
    }
}
