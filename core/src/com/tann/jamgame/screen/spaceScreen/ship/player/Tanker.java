package com.tann.jamgame.screen.spaceScreen.ship.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.Blaster;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon.BroadSide;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Maths;
import com.tann.jamgame.util.Sounds;

public class Tanker extends PlayerShip {

    static TextureRegion tanker = Main.atlas.findRegion("ship/ss_brewballs1");;
    static final float AUTO_ACCEL =  .2f;
    public Tanker() {
        super(tanker, 0, 400, .004f);
        setPosition(500,500);
        setSize(370, 70);
        setHp(500);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.shiftedTowards(Colours.white, Colours.red, flash));
        Draw.drawCenteredRotatedScaled(batch, tr, getX(), getY(), getWidth()/tr.getRegionWidth(), getHeight()/tr.getRegionHeight(), getRotation());
        float dist = 18;
        if(!SpaceScreen.get().finished) {
            for (int i = -1; i < 2; i++) {
                drawThrustAt(batch,
                        (float) (getButtX() + Math.cos(getRotation() + Math.PI / 2) * dist * i),
                        (float) (getButtY() + Math.sin(getRotation() + Math.PI / 2) * dist * i), 2);
            }
        }
        super.draw(batch, parentAlpha);
    }

    float ticker;

    @Override
    protected void internalAct(float delta) {
        ticker += delta;
        if(ticker>=.15f){
            hp=Math.min(maxHp, hp+1);
            ticker=0;
        }
        if(SpaceScreen.get().finished){
            return;
        }
        float dxChange = (float)Math.cos(getRotation());
        float dyChange = (float)Math.sin(getRotation());
        if(SpaceScreen.get().map.ships.size<4){
            dxChange*=2;
        }
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

    @Override
    protected void onDeath() {
        super.onDeath();
        SpaceScreen.get().defeat();
        Sounds.playSound(Sounds.sad_long_pow, Maths.v.set(getX(), getY()));
    }

}
