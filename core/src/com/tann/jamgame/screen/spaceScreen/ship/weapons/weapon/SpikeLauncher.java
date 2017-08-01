package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.SpikeBullet;

public class SpikeLauncher extends Weapon {

    private static final float KNOCKBACK = 35;

    public SpikeLauncher() {
        super(200, 1);
    }

    @Override
    protected void internalFire() {
        Ship ship = getShip();
            Bullet b = Pools.obtain(SpikeBullet.class);
            b.init();
            b.type = friend? Bullet.BulletType.Friendly: Bullet.BulletType.Enemy;
            b.setup(ship.getNoseX(), ship.getNoseY(), ship.dx, ship.dy, ship.getRotation(), 20);
            b.setLife(350);
            SpaceScreen.get().addBullet(b);
        ship.dx -= Math.cos(ship.getRotation())*KNOCKBACK;
        ship.dy -= Math.sin(ship.getRotation())*KNOCKBACK;
    }

    @Override
    public String getName() {
        return "SpikeLauncher";
    }
}
