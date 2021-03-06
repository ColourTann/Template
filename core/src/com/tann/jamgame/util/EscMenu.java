package com.tann.jamgame.util;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tann.jamgame.Main;

public class EscMenu extends Group {
    private static int WIDTH = 300, HEIGHT = 200;

    private static EscMenu self;
    public static EscMenu get(){
        if(self==null) self = new EscMenu();
        return self;
    }

    private EscMenu() {
        setSize(WIDTH,HEIGHT);
        Slider.music.setSize(getWidth()*.8f, 30);
        Slider.SFX.setSize(getWidth()*.8f, 30);
        setPosition(Main.width/2-getWidth()/2, Main.height/2-getHeight()/2);
        TextButton quit = new TextButton(110, 40, "Quit");
        TextButton restart = new TextButton(110, 40, "Restart");
        quit.setRunnable(new Runnable() {
            @Override
            public void run() {
            }
        });
        restart.setRunnable(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.dark);
        Draw.fillActor(batch,this);
        TannFont.font.drawString(batch, "Version "+Main.version, (int)getX(), (int) getY());
        super.draw(batch, parentAlpha);
    }
}
