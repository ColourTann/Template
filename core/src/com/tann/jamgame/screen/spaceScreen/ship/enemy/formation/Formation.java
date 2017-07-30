package com.tann.jamgame.screen.spaceScreen.ship.enemy.formation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tann.jamgame.Main;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.map.Map;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.*;
import com.tann.jamgame.util.*;

public class Formation {
    public Array<EnemyShip> ships = new Array<>();
    public float x;
    float alpha = .1f;
    float width = 3500;
    String message;
    boolean triggered;

    public Formation(String message, float middle, float width, int speeders, int hulks, int bombers, int carriers){
        this.message=message;
        this.x = middle;
        this.width=width;
        for(int i=0;i<speeders;i++){
            setupShip(new Speeder());
        }
        for(int i=0;i<hulks;i++){
            setupShip(new Hulk());
        }
        for(int i=0;i<bombers;i++){
            setupShip(new Bomber());
        }
        for(int i=0;i<carriers;i++){
            setupShip(new Carrier());
        }
    }

    public void checkTrigger(){
        if(triggered) return;
        if(SpaceScreen.get().map.tanker.getX()>x-width/2){
            trigger();
        }
    }

    private void trigger() {
        triggered=true;
        TextWisp tw = new TextWisp(message.toUpperCase(), Fonts.fontBig, Main.width/2, Main.height/4*3);
        SpaceScreen.get().addActor(tw);
    }

    private void setupShip(EnemyShip s){
        Vector2 random = getRandomPosition();
        s.setPosition(random.x, random.y);
        ships.add(s);
    }

    Vector2 temp = new Vector2();
    protected Vector2 getRandomPosition(){
        float randX = this.x+ Particle.rand(-width/3,width/3);
        float randY = Particle.rand(Map.HEIGHT*.2f, Map.HEIGHT*.3f);
        if(Math.random()>.5f){
            randY = Map.HEIGHT-randY;
        }
        temp.set(randX, randY);
        return temp;
    }

    public void draw(Batch batch){
        batch.setColor(Colours.withAlpha(Colours.red, alpha));
        Draw.fillRectangle(batch, x-width/2, 0, width, Map.HEIGHT);
    }
    
}
