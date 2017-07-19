package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Blaster;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.BroadSide;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.DoubleShot;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Tanker extends PlayerShip {

    static TextureRegion tr = Main.atlas.findRegion("ship/ss_brewballs1");;
    static final float AUTO_ACCEL = .2f;
    public Tanker() {
        super(0, 4, .004f);
        setPosition(500,500);
        setSize(370, 70);
        addWeapon(new BroadSide());
        addWeapon(new Blaster());
        setHp(700);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.shiftedTowards(Colours.white, Colours.red, flash));
        Draw.drawCenteredRotatedScaled(batch, tr, getX(), getY(), getWidth()/tr.getRegionWidth(), getHeight()/tr.getRegionHeight(), getRotation());
        float dist = 18;
        for(int i=-1;i<2;i++){
            drawThrustAt(batch,
                    (float) (getButtX() +Math.cos(getRotation()+Math.PI/2)*dist*i),
                    (float) (getButtY() +Math.sin(getRotation()+Math.PI/2)*dist*i), 2);
        }
        super.draw(batch, parentAlpha);
    }

    @Override
    protected void internalAct(float delta) {
        float dxChange = (float)Math.cos(getRotation());
        float dyChange = (float)Math.sin(getRotation());
        dx += AUTO_ACCEL * dxChange;
        dy += AUTO_ACCEL * dyChange;
    }

    @Override
    public Shape2D getShape() {
        return getDefaultShape();
    }

    @Override
    public void drawMinimap(Batch batch, float x, float y) {
        float scaleFactor = .05f;
        batch.setColor(1,1,1, batch.getColor().a);
        Draw.drawCenteredRotatedScaled(batch, tr, x, y, getWidth()/tr.getRegionWidth()*scaleFactor, getHeight()/tr.getRegionHeight()*scaleFactor, getRotation());
    }

    @Override
    public boolean affectedBy(Bullet.BulletType type) {
        return type== Bullet.BulletType.Enemy;
    }

    public float getBaseZoom() {
        return 1.2f;
    }
}
