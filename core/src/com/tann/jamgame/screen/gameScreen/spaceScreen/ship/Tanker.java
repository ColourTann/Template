package com.tann.jamgame.screen.gameScreen.spaceScreen.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Blaster;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.DoubleShot;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Tanker extends Ship {

    TextureRegion tr;
    public Tanker() {
        super(.07f, 4);
        this.tr= Main.atlas.findRegion("bustanker");
        setPosition(500,500);
        setSize(370, 70);
        weapon1 = new DoubleShot(true);
        weapon1.setShip(this);
        weapon2 = new Blaster(true);
        weapon2.setShip(this);
        setHp(1500);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.shiftedTowards(Colours.white, Colours.red, flash));
        Draw.drawCenteredRotatedScaled(batch, tr, getX(), getY(), getWidth()/tr.getRegionWidth(), getHeight()/tr.getRegionHeight(), getRotation());
        super.draw(batch, parentAlpha);
    }

    @Override
    protected void internalAct(float delta) {
        float dxChange = (float)Math.cos(getRotation());
        float dyChange = (float)Math.sin(getRotation());
        dx += accel * dxChange;
        dy += accel * dyChange;
        if(control){
            float turnSpeed = .0035f;
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                rotateBy(-turnSpeed);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                rotateBy(+turnSpeed);
            }
        }
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
        return 1f;
    }
}
