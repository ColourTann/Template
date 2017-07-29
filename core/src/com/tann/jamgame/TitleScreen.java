package com.tann.jamgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Screen;

public class TitleScreen extends Screen {

    Texture tr;
    Runnable r;
    public TitleScreen(Texture tr) {
        this.tr=tr;
    }

    public void setRunnable(Runnable r){
        this.r=r;
    }

    @Override
    public void preDraw(Batch batch) {

    }

    @Override
    public void postDraw(Batch batch) {
        float scale = Math.min(getWidth()/tr.getWidth(), getHeight()/tr.getHeight());
        Draw.drawCenteredScaled(batch, tr, getX()+getWidth()/2, getY()+getHeight()/2, scale, scale);
    }

    @Override
    public void preTick(float delta) {

    }

    @Override
    public void postTick(float delta) {

    }

    @Override
    public void keyPress(int keycode) {
        r.run();
    }
}
