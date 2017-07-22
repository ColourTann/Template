package com.tann.jamgame.screen.spaceScreen.ship.enemy.formation;

import com.badlogic.gdx.math.Vector2;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.Speeder;
import com.tann.jamgame.screen.spaceScreen.ship.enemy.EnemyShip;

public class BasicFormation extends Formation{

    public BasicFormation(float x) {
        super(x);
    }

    @Override
    protected void setup() {
        for(int i=0;i<20;i++){
            EnemyShip es = new Speeder();
            Vector2 pos = getRandomPosition();
            es.setPosition(pos.x, pos.y);
            ships.add(es);
        }
    }
}
