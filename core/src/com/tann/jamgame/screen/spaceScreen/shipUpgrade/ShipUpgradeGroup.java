package com.tann.jamgame.screen.spaceScreen.shipUpgrade;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.jamgame.screen.spaceScreen.ship.player.Defender;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class ShipUpgradeGroup extends Group{

    static TextureRegion tr = Defender.defender;
    float shipProporation=.45f;
    float shipScale;
    public ShipUpgradeGroup() {
        setSize(700,500);
        shipScale = getWidth()/tr.getRegionWidth()*shipProporation;
        addUpgradePanel(.4f, .1f, .2f, .15f);
        addUpgradePanel(.1f, .5f, .2f, .8f);
        addUpgradePanel(.4f, .9f, .5f, .85f);
        addUpgradePanel(.8f, .5f, .8f, .2f);
        addUpgradePanel(.5f, .5f, .8f, .7f);
    }

    private void addUpgradePanel(float shipX, float shipY, float boxX, float boxY){
        float shipStartX= (getWidth()-tr.getRegionWidth()*shipScale)/2f;
        float shipStartY= (getHeight()-tr.getRegionHeight()*shipScale)/2f;
        UpgradePanel  up = new UpgradePanel(
                shipStartX + shipX*tr.getRegionWidth()*shipScale,
                shipStartY + shipY*tr.getRegionHeight()*shipScale,
                boxX*getWidth()-UpgradePanel.SIZE/2, boxY*getHeight()-UpgradePanel.SIZE/2);
        addActor(up);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        Draw.fillActor(batch, this, Colours.dark, Colours.blue, 10);

        batch.setColor(Colours.white);

        Draw.drawCenteredScaled(batch, tr, getX()+getWidth()/2, getY()+getHeight()/2, shipScale, shipScale);
        super.draw(batch, parentAlpha);
    }
}
