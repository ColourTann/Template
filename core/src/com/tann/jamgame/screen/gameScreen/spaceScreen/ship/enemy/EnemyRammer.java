package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.Main;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class EnemyRammer extends EnemyShip{

    static TextureRegion tr = Main.atlas.findRegion("ship/enemyFighter");

    public EnemyRammer() {
        super(.15f, 30, .5f);
        float sizeMult = 1.2f;
        setSize(tr.getRegionWidth()*sizeMult, tr.getRegionHeight()*sizeMult);
    }

    @Override
    protected void internalAct(float delta) {
        pursueTanker();
        if(getTankerDist()<500){
            System.out.println("ahhh");
        }
    }

    @Override
    public Shape2D getShape() {
        return getDefaultShape();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.green);
        Draw.drawCenteredRotatedScaled(batch, tr, getX(), getY(), getWidth()/tr.getRegionWidth(), getHeight()/tr.getRegionHeight(), getRotation());
        super.draw(batch, parentAlpha);
    }
}
