package com.tann.jamgame.screen.spaceScreen.ship.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Blaster;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Reversinator;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Defender extends PlayerShip {


    public static TextureRegion defender = Main.atlas.findRegion("ship/playerFighter");

    public Defender() {
        super(defender, .5f,200, .1f);
        setPosition(500,500);
        float sizeMult = 1.2f;
        setSize(tr.getRegionWidth()*sizeMult, tr.getRegionHeight()*sizeMult);
        addWeapon(new Blaster());
        addWeapon(new Reversinator());
        setHp(150);
    }

    @Override
    protected void internalAct(float delta) {
        super.internalAct(delta);
    }

    public float getBaseZoom() {
        return 1.6f;
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.white);
        Draw.drawCenteredRotatedScaled(batch, tr, getX(), getY(), getWidth()/tr.getRegionWidth(), getHeight()/tr.getRegionHeight(), getRotation());
//        drawThrustAt(batch, getButtX(), getButtY());
//        batch.draw(thrust, getX()-getWidth()/2, getY()-getHeight()/2, tr.getRegionWidth(), tr.getRegionHeight()/2, thrust.getRegionWidth(), thrust.getRegionHeight(), 10, 10, Draw.rad2deg(getRotation()));
//        Draw.drawCenteredRotatedScaled(batch, thruster[(int)(Main.ticks*10)%thruster.length], getButtX(), getY(), getWidth()/tr.getRegionWidth(), getHeight()/tr.getRegionHeight(), getRotation());
        super.draw(batch, parentAlpha);
    }
    @Override
    public void drawMinimap(Batch batch, float x, float y) {
        batch.setColor(Colours.withAlpha(Colours.light, batch.getColor().a));
        float size = 4;
        Draw.fillEllipse(batch, x-size/2, y-size/2, size, size);
    }
}
