package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon;

import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.BoringBullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;

public class DoubleShot extends Weapon {
    public DoubleShot() {
        super(12);
    }

    @Override
    protected void internalFire() {
        Ship ship = getShip();
        float dist = ship.getHeight()/2;
        float bXDiff = (float) (Math.cos(ship.getRotation()+Math.PI/2)*dist);
        float bYDiff = (float) (Math.sin(ship.getRotation()+Math.PI/2)*dist);
        {
            Bullet b = new BoringBullet();
            b.setup(ship.getNoseX()+bXDiff, ship.getNoseY()+bYDiff, ship.dx, ship.dy, ship.getRotation(), 17);
            SpaceScreen.get().addBullet(b);
        }
        {
            Bullet b = new BoringBullet();
            b.setup(ship.getNoseX()-bXDiff, ship.getNoseY()-bYDiff, ship.dx, ship.dy, ship.getRotation(), 17);
            SpaceScreen.get().addBullet(b);
        }
    }
}
