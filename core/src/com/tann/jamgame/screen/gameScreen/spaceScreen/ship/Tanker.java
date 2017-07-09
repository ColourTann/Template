package com.tann.jamgame.screen.gameScreen.spaceScreen.ship;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Shape2D;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.Blaster;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon.DoubleShot;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;

public class Tanker extends Ship {

    public Tanker() {
        super(.07f, 4);
        setPosition(500,500);
        setSize(370, 70);
        weapon1 = new DoubleShot();
        weapon1.setShip(this);
        weapon2 = new Blaster();
        weapon2.setShip(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Colours.blue);
        Draw.drawCenteredRotatedScaled(batch, Draw.getSq(), getX(), getY(), getWidth(), getHeight(), getRotation());
        super.draw(batch, parentAlpha);
    }

    Polygon p = new Polygon();
    float[] points = new float[]{0,0,0,0,0,0,0,0};

    @Override
    protected void internalAct(float delta) {
        float dxChange = (float)Math.cos(getRotation());
        float dyChange = (float)Math.sin(getRotation());
        dx += accel * dxChange;
        dy += accel * dyChange;
        setRotation(getRotation()+.001f);
    }

    @Override
    public Shape2D getShape() {
        for(int i=0;i<points.length;i++){
            points[i]=(i%2==0)?-getWidth()/2:-getHeight()/2;
        }
        points[2]+= getWidth();
        points[4]+= getWidth();
        points[5]+=getHeight();
        points[7]+= getHeight();
        p.setVertices(points);
        p.setRotation((float) (getRotation()*360/(Math.PI*2)));
        p.setPosition(getX(), getY());
        return p;
    }
}
