package com.tann.jamgame.screen.gameScreen.spaceScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Weapon;
import com.tann.jamgame.util.*;

public class WeaponIcon extends Group{
    Weapon w;
    String key;
    TextBox letter;
    ImageActor image;
    public WeaponIcon(String key, Weapon w) {
        this.key=key;
        this.w=w;
        setSize(100,150);
        BitmapFont font = Fonts.font;
        TextBox keyBox = new TextBox(key, font, 9999, Align.center);
        keyBox.setTextColour(Colours.light);
        letter = new TextBox(w.getName(), font, 9999, Align.center);
        letter.setTextColour(Colours.light);
        float imageSize = getWidth()*.9f;
        image = new ImageActor(w.getImage(), imageSize, imageSize);
        image.setColor(Colours.neonBlue);
        Layoo l = new Layoo(this);
        l.row(1);
        l.actor(keyBox);
        l.row(1);
        l.actor(letter);
        l.row(1);
        l.actor(image);
        l.row(1);
        l.layoo();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.blue);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
        if(w.cooldown>20) {
            batch.setColor(Colours.withAlpha(Colours.dark, .5f));
            Draw.fillRectangle(batch, getX(), getY() + getHeight() * (w.getCooldownRatio()), getWidth(), getHeight() * (1 - w.getCooldownRatio()));
        }
    }
}
