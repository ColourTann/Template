package com.tann.jamgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.jamgame.Main;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Screen;

public class EndScreen extends Screen{

    Texture[] dance = new Texture[]{
            new Texture(Gdx.files.internal("singleImages/proud_celebrate1.png")),
            new Texture(Gdx.files.internal("singleImages/proud_celebrate2.png"))
    };

    @Override
    public void preDraw(Batch batch) {

    }

    @Override
    public void postDraw(Batch batch) {
        batch.setColor(Colours.black);
        Draw.fillActor(batch,this);
        batch.setColor(Colours.white);
        Draw.drawCenteredScaled(batch, dance[(int)((Main.ticks*2)%2)], getWidth()/2, getHeight()/2, 2, 2);
    }

    @Override
    public void preTick(float delta) {

    }

    @Override
    public void postTick(float delta) {

    }

    @Override
    public void keyPress(int keycode) {

    }
}
