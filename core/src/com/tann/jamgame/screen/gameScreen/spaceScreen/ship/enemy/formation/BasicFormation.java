package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.formation;

import com.badlogic.gdx.math.Vector2;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.EnemyFighter;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.enemy.EnemyShip;

public class BasicFormation extends Formation{

    public BasicFormation(float x) {
        super(x);
    }

    @Override
    protected void setup() {
        for(int i=0;i<20;i++){
            EnemyShip es = new EnemyFighter();
            Vector2 pos = getRandomPosition();
            es.setPosition(pos.x, pos.y);
            ships.add(es);
        }
    }
}
