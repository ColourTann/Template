package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;

public class TextWisp extends Actor {
    String text;
    BitmapFont font;
    public TextWisp(String text, BitmapFont font, float x, float y) {
        this.font=font;
        this.text = text;
        setPosition(x,y);
        setColor(Colours.red);
        float duration = 1;
        float delay = 1.6f;
        addAction(Actions.delay(delay, Actions.fadeOut(duration)));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.setColor(getColor());
        Fonts.draw(batch, text, font, getColor(), getX(), getY(), 0, 0, Align.center);
        super.draw(batch, parentAlpha);
    }
}
