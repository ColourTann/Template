package com.tann.jamgame.util;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;

public class EscMenu extends Group {
    private static int WIDTH = 250, HEIGHT = 300;

    private static EscMenu self;
    public static EscMenu get(){
        if(self==null) self = new EscMenu();
        return self;
    }

    private EscMenu() {
        setSize(WIDTH,HEIGHT);
        Slider.SFX.setSize(getWidth()*.8f, 30);
        setPosition(Main.width/2-getWidth()/2, Main.height/2-getHeight()/2);
        TextBox madeBy = new TextBox("Made by tann and jo", Fonts.font, getWidth()*.9f, Align.center);
        TextBox attr = new TextBox("icons from game-icons.net", Fonts.font, getWidth()*.9f, Align.center);
        final Layoo l = new Layoo(this);
        l.row(1);
        l.actor(madeBy);
        l.row(1);
        l.actor(attr);
        l.row(1);
        l.actor(Slider.SFX);
        l.row(1);
        TextButton restart = new TextButton(110, 40, "Restart");
        l.actor(restart);
        l.row(1);
        l.layoo();
        restart.setRunnable(new Runnable() {
            @Override
            public void run() {
                SpaceScreen.get().map.setup();
                SpaceScreen.get().removeEsc();
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.grey);
        Draw.fillActor(batch,this);
        super.draw(batch, parentAlpha);
    }
}
