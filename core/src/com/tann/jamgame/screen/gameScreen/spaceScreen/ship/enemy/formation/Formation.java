package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.formation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.map.Map;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.EnemyShip;
import com.tann.jamgame.util.*;

public abstract class Formation {
    public Array<EnemyShip> ships = new Array<>();
    public float x;
    protected abstract void setup();
    boolean triggered;
    float alpha = .2f;
    float width = 3500;
    public Formation(float x) {
        this.x = x;
        setup();
    }

    public boolean dead;
    public void checkAggro(Ship ship){
        if(triggered) {
//            alpha-=.03f;
//            if(alpha<=0){
//                dead=true;
//            }
//            return;
        }
        if(ship.getX()>x-width/2){
            trigger();
        }
    }

    Vector2 temp = new Vector2();
    protected Vector2 getRandomPosition(){
        float randX = this.x+ Particle.rand(-width/2,width/2);
        float randY = Particle.rand(Map.HEIGHT*.2f, Map.HEIGHT*.3f);
        if(Math.random()>.5f){
            randY = Map.HEIGHT-randY;
        }
        temp.set(randX, randY);
        return temp;
    }

    private void trigger(){
        if(triggered) return;
        triggered=true;
//        for(EnemyShip s:ships) {
//            s.aggro();
//        }
        TextWisp tw = new TextWisp("!!WARNING!! Entering Temperence League Space !!WARNING!!", Fonts.fontBig, Main.width/2, Main.height/2);
        SpaceScreen.get().addActor(tw);
    }

    public void draw(Batch batch){
        batch.setColor(Colours.withAlpha(Colours.red, alpha));
        Draw.fillRectangle(batch, x-width/2, 0, width, Map.HEIGHT);
    }
    
    public void drawMinimap(Batch batch, float xScale, float yScale) {
        batch.setColor(Colours.withAlpha(Colours.red, alpha));
        Draw.fillRectangle(batch, (x-width/2)*xScale, 0*yScale, width*xScale, Map.HEIGHT*yScale);
    }


}
