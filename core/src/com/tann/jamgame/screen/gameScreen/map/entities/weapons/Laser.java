package com.tann.jamgame.screen.gameScreen.map.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.jamgame.screen.gameScreen.map.Map;
import com.tann.jamgame.screen.gameScreen.map.entities.Drone;
import com.tann.jamgame.screen.gameScreen.map.entities.weapons.Bullet.LaserBullet;

public class Laser extends Weapon {

    public Laser(Drone source) {
        super(source, 1);
    }

    @Override
    void attack(Drone target) {
        Vector2 offset = source.getBulletOffset();
        LaserBullet lb = new LaserBullet(source.getX()+offset.x, source.getY()+offset.y, target);
        Map.get().addActor(lb);
    }
}
