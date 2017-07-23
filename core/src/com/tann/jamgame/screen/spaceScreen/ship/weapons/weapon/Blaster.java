package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.BlasterBullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.screen.spaceScreen.shipUpgrade.Upgrade;
import com.tann.jamgame.util.Particle;

public class Blaster extends Weapon {
    public Blaster() {
        super(45, 5);
    }

    private static final int NUM_SHOTS=10;
    private static final float ANGLE_RAND = .48f, MIN_SPEED = 40, MAX_SPEED = 60;
    private static final float KNOCKBACK = 3;

    @Override
    protected void internalFire() {
        Ship ship = getShip();
        for(int i = 0; i<NUM_SHOTS+getBonus(Upgrade.UpgradeType.Shots); i++){
            Bullet b = Pools.obtain(BlasterBullet.class);
            b.init();
            b.type = friend? Bullet.BulletType.Friendly: Bullet.BulletType.Enemy;
            b.setup(ship.getNoseX(), ship.getNoseY(), ship.dx, ship.dy, ship.getRotation()+ Particle.rand(-ANGLE_RAND, ANGLE_RAND),
                    Particle.rand(MIN_SPEED,MAX_SPEED)*(1+getBonus(Upgrade.UpgradeType.BulletSpeed)));
            b.setDrag(.90f);
            b.setLife(35);
            SpaceScreen.get().addBullet(b);
        }
        ship.dx -= Math.cos(ship.getRotation())*KNOCKBACK;
        ship.dy -= Math.sin(ship.getRotation())*KNOCKBACK;
    }

    @Override
    public String getName() {
        return "Blaster";
    }

}
