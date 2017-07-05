package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class TextButton extends Group {
    String text;
    public TextButton(float width, float height, String text) {
        this.text=text;
        setSize(width, height);
    }

    public void setRunnable(final Runnable runnable){
        addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    runnable.run();
                    return true;
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    background = Colours.black;
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    background = Colours.white;
                    super.exit(event, x, y, pointer, toActor);
                }
            }


        );
    }

    Color background = Colours.black;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Draw.fillActor(batch,this, Colours.white, Colours.black, 3);
        Fonts.draw(batch, text, Fonts.fontSmall, Colours.black, getX(), getY(), getWidth(), getHeight());
    }
}
