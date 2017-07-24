package com.tann.jamgame.screen.spaceScreen.shipUpgrade;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.InputBlocker;


public class UpgradePanel extends Group{
    static final float SIZE = 80;
    float targetX, targetY;
    Array<Upgrade> upgrades =  new Array();
    Upgrade chosen;
    static final Interpolation terp = Interpolation.pow2Out;
    static final float intSpeed = .3f;
    boolean locked = true;

    public void unlock(){
        locked =false;
    }

    private static final TextureRegion lock = Main.atlas.findRegion("upgrade/lock");
    private static final TextureRegion unlock = Main.atlas.findRegion("upgrade/unlocked");
    public UpgradePanel(float x, float y, float boxX, float boxY, Upgrade one, Upgrade two) {
        upgrades.add(one);
        upgrades.add(two);
        setPosition(boxX, boxY);
        setSize(SIZE,SIZE);
        targetX = x-boxX; targetY=y-boxY;
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(locked) return true;
                showUpgrades();
                event.cancel();
                return true;
            }
        });
    }

    private void showUpgrades() {
        toFront();
        Runnable r = () -> {
            InputBlocker.get().remove();
            choose(null);
        };
        addActor(InputBlocker.get(r));

        for(int i=0;i<upgrades.size;i++){
            Upgrade u=upgrades.get(i);
            UpgradeChoicePanel ucp = u.getPanel();
            addActor(ucp);
            ucp.toFront();
            ucp.setScale(0);
            ucp.addAction(Actions.scaleTo(1,1,.3f, Interpolation.pow2Out));
            ucp.setPosition(getWidth()/2, getHeight()/2);
            ucp.addAction(Actions.moveTo(getWidth()/2-ucp.getWidth()*(i),getHeight()/2-ucp.getHeight()/2, intSpeed, terp));

        }
    }

    public void choose(Upgrade upgrade){
        if(upgrade!=null){
            chosen=upgrade;
        }
        for(Upgrade u:upgrades){
            u.getPanel().addAction(Actions.moveTo(getWidth()/2, getHeight()/2, intSpeed, terp));
            u.getPanel().addAction(Actions.scaleTo(0,0,.3f, Interpolation.pow2Out));
        }
        SpaceScreen.get().map.defender.setUpgrades(SpaceScreen.get().sug);
        SpaceScreen.get().refreshWeaponIcons();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.green);
        Draw.drawLine(batch, getX()+getWidth()/2, getY()+getHeight()/2, getX()+targetX, getY()+targetY, 2);
        Draw.fillActor(batch, this, Colours.dark, Colours.light, 4);
        TextureRegion tr;
        float imageScale = .8f;
        if(locked){
            batch.setColor(Colours.grey);
            tr = lock;
        }
        else if(chosen==null){
            batch.setColor(Colours.green);
            tr = unlock;
        }
        else{
            batch.setColor(Colours.neonBlue);
            tr = chosen.tr;
        }
        Draw.drawSizeCentered(batch, tr, getX()+getWidth()/2, getY()+getHeight()/2, getWidth()*imageScale, getHeight()*imageScale);
        super.draw(batch, parentAlpha);
    }
}
