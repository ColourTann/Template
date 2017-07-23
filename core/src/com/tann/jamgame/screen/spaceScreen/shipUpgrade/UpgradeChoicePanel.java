package com.tann.jamgame.screen.spaceScreen.shipUpgrade;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.tann.jamgame.util.*;

public class UpgradeChoicePanel extends Group{
    Upgrade u;
    public UpgradeChoicePanel(Upgrade u) {
        setSize(200, 200);
        float imageSize = getWidth()*.6f;
        ImageActor ia = new ImageActor(u.tr, imageSize, imageSize);
        TextBox tb = new TextBox(u.text, Fonts.fontSmall, getWidth()*.9f, Align.left);
        Layoo l = new Layoo(this);
        l.row(1);
        l.actor(ia);
        l.row(1);
        l.actor(tb);
        l.row(1);
        l.layoo();
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                InputBlocker.get().remove();
                ((UpgradePanel)getParent()).choose(u);
                event.cancel();
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Draw.fillActor(batch, this, Colours.dark, Colours.light, 3);
        super.draw(batch, parentAlpha);
    }
}
