package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.formation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Tanker;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.EnemyShip;
import com.tann.jamgame.util.Colours;
import com.tann.jamgame.util.Draw;
import com.tann.jamgame.util.Maths;

public abstract class Formation {
    public Array<EnemyShip> ships = new Array<>();
    public float x;
    public float y;
    int aggroRadius;
    protected abstract void setup();
    boolean triggered;
    float alpha = .2f;
    public Formation(float x, float y, int aggroRadius) {
        this.x = x;
        this.y = y;
        this.aggroRadius = aggroRadius;
        setup();
    }

    public boolean dead;
    public void checkAggro(Tanker tanker){
        if(triggered) {
            alpha-=.03f;
            if(alpha<=0){
                dead=true;
            }
            return;
        }
        if(Maths.distance(tanker.getX(), tanker.getY(), x, y)<aggroRadius){
            trigger();
        }
    }

    Vector2 temp = new Vector2();
    protected Vector2 getRandomPosition(){
        float angle = (float) (Math.random()*Math.PI*2);
        float dist = (float) (Math.random()*aggroRadius*.9f);
        temp.set(x+(float)Math.cos(angle)*dist, (float) (y+Math.sin(angle)*dist));
        return temp;
    }

    private void trigger(){
        triggered=true;
        for(EnemyShip s:ships){
            s.aggro();
        }
        alpha=1;
    }

    public void draw(Batch batch){
        batch.setColor(Colours.withAlpha(Colours.red, alpha));
        Draw.fillEllipse(batch, x-aggroRadius, y-aggroRadius, aggroRadius*2, aggroRadius*2);
    }
    
    public void drawMinimap(Batch batch, float xScale, float yScale) {
        batch.setColor(Colours.withAlpha(Colours.red, alpha));
        Draw.fillEllipse(batch, (x-aggroRadius)*xScale, (y-aggroRadius)*yScale, aggroRadius*2*xScale, aggroRadius*2*yScale);
    }


}
