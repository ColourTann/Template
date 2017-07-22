package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.BlasterBullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.util.Particle;

public class BroadSide extends Weapon {
    public BroadSide() {
        super(200);
    }

    @Override
    protected void internalFire() {
        Ship ship = getShip();
        float width = ship.getWidth();
        float angle = ship.getRotation();
        int numBullets = 25;
        for(int j=0;j<2;j++) {
            for (int i = 0; i < numBullets; i++) {
                float x = -width / 2 + width / (numBullets - 1) * i;
                float y = x;
                x = (float) (Math.cos(angle) * x);
                x += ship.getX();
                y = (float) (Math.sin(angle) * y);
                y += ship.getY();
                Bullet b = Pools.obtain(BlasterBullet.class);
                b.init();
                b.type = friend ? Bullet.BulletType.Friendly : Bullet.BulletType.Enemy;
                float angleRand = .40f;
                b.setup(x, y, ship.dx, ship.dy,
                        ship.getRotation() + (j==0?(Math.PI/2):(-Math.PI/2)) + Particle.rand(-angleRand, angleRand),
                        Particle.rand(30, 40));
                b.setDrag(.93f);
                b.setLife(30);
                b.size=8;
                SpaceScreen.get().addBullet(b);
            }
        }
    }

    @Override
    public String getName() {
        return "BroadSide";
    }
}
