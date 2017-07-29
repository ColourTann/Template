package com.tann.jamgame.screen.spaceScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.player.Tanker;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Fonts;

public class ShipHealth extends Actor{
    Ship ship;
    String name;
    public ShipHealth(Ship ship, String name, float width) {
        this.ship = ship;
        this.name=name;
        setSize(width, 25);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Draw.fillActor(batch, this, Colours.green, Colours.light, 2);
        batch.setColor(Colours.red);
        Draw.fillRectangle(batch, getX(), getY(), getWidth()*(1-ship.getHealthRatio()), getHeight());
        Fonts.draw(batch, name, Fonts.font, Colours.dark, getX(), getY(), getWidth(), getHeight(), Align.center);
        super.draw(batch, parentAlpha);
    }
}
