package com.tann.jamgame.screen.gameScreen.map.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.map.Path;
import com.tann.jamgame.screen.gameScreen.map.entities.weapons.Laser;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Hover extends Drone {
    public static final TextureRegion hoverWhite = Main.atlas.findRegion("drone/hoverWhite");
    public static final TextureRegion hoverBlack = Main.atlas.findRegion("drone/hoverBlack");
    int size = 20;

    Vector2 laserTarget = new Vector2();

    public Hover(Path path) {
        super(path);
        setWeapon(new Laser(this));
    }

    @Override
    public void die() {

    }

    @Override
    protected void tick(float delta) {
    }

    @Override
    public int getRange() {
        return 100;
    }

    @Override
    public Vector2 getBulletOffset() {
        if(target == null) {
            tmp.set(nextPoint.x - getX(), nextPoint.y - getY());
        }
        else{
            tmp.set(target.posX - getX(), target.posY - getY());
        }
        tmp.nor().scl(7);
        return tmp;
    }

    Vector2 tmp = new Vector2();
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.white);
        batch.draw(white?hoverWhite:hoverBlack, (int)getX() - (int)hoverBlack.getRegionWidth()/2, (int)getY() - (int)hoverBlack.getRegionHeight()/2);
        batch.setColor(Colours.red);
        getBulletOffset();
        Draw.fillEllipse(batch, (int)(getX()+tmp.x), (int)(getY()+tmp.y), 4, 4);

        super.draw(batch, parentAlpha);
    }
}
