package com.tann.jamgame.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.tann.jamgame.Main;
import com.tann.jamgame.util.*;

public class IncomingMissionScreen extends Screen{

    public static final TextureRegion tr = Main.atlas.findRegion("suited_blyat4");

    public IncomingMissionScreen() {
        Layoo l = new Layoo(this);
        ImageActor ia = new ImageActor(tr);
        ia.setSize(tr.getRegionWidth()*1.5f, tr.getRegionHeight()*1.5f);
        ia.setBorder(5, Colours.red);
        l.row(1);
        l.actor(ia);
        l.row(.3f);
        TextBox tb = new TextBox("!!INCOMING MISSION FROM BOSS!!", Fonts.fontBig, -1, Align.center);
        tb.setTextColour(Colours.red);
        tb.flash();
        l.actor(tb);
        l.row(.2f);
        TextBox press = new TextBox("PRESS ANY KEY FOR MISSION DETAILS", Fonts.font, -1, Align.center);
        press.setTextColour(Colours.red);
        l.actor(press);
        l.row(.2f);
        TextBox suit = new TextBox("suited blyat", Fonts.fontSmall, -1, Align.center);
        suit.setTextColour(Colours.red);
        l.actor(suit);
        l.row(1);
        l.layoo();
    }

    Runnable r;
    public void setRunnable(Runnable r){
        this.r=r;
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
        if(active && (System.currentTimeMillis()-startRead)>MissionInstructionScreen.minRead) {
            r.run();
            Sounds.playSound(Sounds.beep, null);
        }
    }

    long startRead;

    @Override
    public void setActive(boolean active) {
        startRead = System.currentTimeMillis();
        super.setActive(active);
    }
}
