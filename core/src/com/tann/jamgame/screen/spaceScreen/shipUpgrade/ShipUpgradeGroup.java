package com.tann.jamgame.screen.spaceScreen.shipUpgrade;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.screen.spaceScreen.ship.player.Defender;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;


public class ShipUpgradeGroup extends Group{

    static TextureRegion tr = Defender.defender;
    float shipProporation=.45f;
    float shipScale;
    Array<UpgradePanel> panels = new Array();
    public ShipUpgradeGroup() {
        setSize(600,400);
        shipScale = getWidth()/tr.getRegionWidth()*shipProporation;
        addUpgradePanel(.4f, .1f, .2f, .15f, Upgrade.p1Reversinator, Upgrade.p1Speedinator);
        addUpgradePanel(.1f, .5f, .2f, .8f, Upgrade.p2Shots, Upgrade.p2Range);
        addUpgradePanel(.4f, .9f, .5f, .85f, Upgrade.p3Turret, Upgrade.p3Bullets);
        addUpgradePanel(.8f, .5f, .8f, .2f, Upgrade.p5Rockets, Upgrade.p5Fire);
        addUpgradePanel(.5f, .5f, .8f, .7f, Upgrade.p4Molotov, Upgrade.p4TimeWarp);
        for(int i=0;i<5;i++)unlockNext(); //todo not this
    }

    private void addUpgradePanel(float shipX, float shipY, float boxX, float boxY, Upgrade one, Upgrade two){
        float shipStartX= (getWidth()-tr.getRegionWidth()*shipScale)/2f;
        float shipStartY= (getHeight()-tr.getRegionHeight()*shipScale)/2f;
        UpgradePanel  up = new UpgradePanel(
                shipStartX + shipX*tr.getRegionWidth()*shipScale,
                shipStartY + shipY*tr.getRegionHeight()*shipScale,
                boxX*getWidth()-UpgradePanel.SIZE/2, boxY*getHeight()-UpgradePanel.SIZE/2, one, two);
        panels.add(up);
        addActor(up);
    }

    public Array<Upgrade> getUpgrades(){
        Array<Upgrade> results = new Array<>();
        for(UpgradePanel up:panels){
            Upgrade u = up.chosen;
            if(u!=null) results.add(u);
        }
        return results;
    }

    public boolean isValid(){
        for(UpgradePanel up:panels){
            if(up.locked==false && up.chosen==null){
                return false;
            }
        }
        return true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        Draw.fillActor(batch, this, Colours.dark, Colours.blue, 10);

        batch.setColor(Colours.white);

        Draw.drawCenteredScaled(batch, tr, getX()+getWidth()/2, getY()+getHeight()/2, shipScale, shipScale);
        super.draw(batch, parentAlpha);
    }

    public void unlockNext() {
        for(UpgradePanel up:panels){
            if(up.locked){
                up.unlock();
                return;
            }
        }
    }
}
