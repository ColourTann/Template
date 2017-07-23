package com.tann.jamgame.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.jamgame.Main;

public class InputBlocker extends Actor{

    private static InputBlocker self;
    private Runnable r;
    public static InputBlocker get(Runnable action){
        if(self==null)self = new InputBlocker();
        self.r= action;
        return self;
    }

    public static InputBlocker get(){
        return get(null);
    }


    private InputBlocker() {
        setSize(Main.width*50, Main.height*50);
        setPosition(-getWidth()/2, -getHeight()/2);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(r!=null) r.run();
                event.cancel();
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(0,0,0,.5f);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }
}
