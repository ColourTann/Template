package com.tann.jamgame.screen.spaceScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.ship.player.Tanker;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Fonts;

public class TankerHealth extends Actor{
    Tanker tank;
    public TankerHealth(Tanker tank) {
        this.tank = tank;
        setSize(Main.width/2, 25);
        setPosition(Main.width/2-getWidth()/2, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.green);
        Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
        batch.setColor(Colours.red);
        Draw.fillRectangle(batch, getX(), getY(), getWidth()*(1-tank.getHealthRatio()), getHeight());
        Fonts.draw(batch, "lorry hp", Fonts.font, Colours.light, getX(), getY(), getWidth(), getHeight(), Align.center);
        super.draw(batch, parentAlpha);
    }
}
