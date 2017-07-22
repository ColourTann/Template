package com.tann.jamgame.screen.spaceScreen.ship.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.player.Tanker;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Shape;

public class Rammer extends EnemyShip{

    private static TextureRegion rammer = Main.atlas.findRegion("ship/rammer");

    public Rammer() {
        super(rammer, 3f, 60, .5f);
        float sizeMult = .7f;
        this.tr = rammer;
        setSize(tr.getRegionWidth()*sizeMult, tr.getRegionHeight()*sizeMult);
        setHp(3);
    }

    private static final int START_CHARGE = 210;
    int charge = START_CHARGE;

    @Override
    protected void internalAct(float delta) {
        rotateTwowardsTanker();
        checkAggros();
        if(!aggroed)return;
        charge--;
        if(charge>0)return;
        moveTowardsTanker();
        Tanker t = getTanker();
        if(Shape.overlaps(getShape(), t.getShape())){
            destroy();
            t.damage(30);
        }
    }

    @Override
    public Shape2D getShape() {
        return getDefaultShape();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(aggroed && charge>0){
            float ratio = 1-((float)charge/START_CHARGE);
            batch.setColor(Colours.shiftedTowards(Colours.light, Colours.red, ratio));
            Ship tanker = SpaceScreen.get().map.tanker;
            Draw.drawLine(batch, getX(), getY(), tanker.getX(), tanker.getY(), 5-ratio*4);
        }
        super.draw(batch, parentAlpha);
    }
}
