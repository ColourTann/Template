package com.tann.jamgame.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.tann.jamgame.Main;
import com.tann.jamgame.util.*;

public class MissionInstructionScreen extends Screen{

    Runnable r;
    public MissionInstructionScreen(TextureRegion alcoholRegion, TextureRegion planetRegion, String text, Runnable r) {
        this.r=r;
        float imageSize = Main.height/2.5f;
        text= text.toUpperCase();
        // left column
        Group left = new Group();
        left.setSize(getWidth()/2, getHeight());
        Layoo column = new Layoo(left);
        ImageActor planet = new ImageActor(planetRegion);
        planet.setSize(imageSize, imageSize);
        planet.setBorder(3, Colours.red);
        column.row(1);
        column.actor(planet);
        column.row(1);
        ImageActor drink = new ImageActor(alcoholRegion);
        drink.setBorder(3, Colours.red);
        drink.setSize(imageSize, imageSize);
        column.actor(drink);
        column.row(1);
        column.layoo();

        // right column
        Group right = new Group();
        right.setSize(getWidth()/2, getHeight());
        column = new Layoo(right);
        TextBox tb = new TextBox(text, Fonts.fontBig, right.getWidth()*.8f, Align.left);
        tb.setTextColour(Colours.red);
        column.actor(tb);
        column.layoo();

        //main layout
        Layoo l= new Layoo(this);
        ImageActor line = new ImageActor(Main.atlas.findRegion("line"));
        line.setSize(3, getHeight()*.95f);
        l.add(1, left, 1, line, 1, right, 1);
        l.layoo();
    }

    @Override
    public void preDraw(Batch batch) {
        batch.setColor(Colours.black);
        Draw.fillActor(batch,this);
    }

    @Override
    public void postDraw(Batch batch) {

    }

    @Override
    public void preTick(float delta) {

    }

    @Override
    public void postTick(float delta) {

    }

    @Override
    public void keyPress(int keycode) {
        if(active && (System.currentTimeMillis()-startRead)>minRead) {
            r.run();
            Sounds.playSound(Sounds.beep, null);
        }
    }

    long startRead;
    public static final long minRead = 300;

    @Override
    public void setActive(boolean active) {
        startRead = System.currentTimeMillis();
        super.setActive(active);
    }
}
