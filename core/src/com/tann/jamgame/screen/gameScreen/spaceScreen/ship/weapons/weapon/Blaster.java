package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.BlasterBullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.util.Particle;

public class Blaster extends Weapon {
    public Blaster() {
        super(40);
    }

    private static final int NUM_SHOTS=20;
    private static final float ANGLE_RAND = .3f;
    private static final float KNOCKBACK = 5;

    @Override
    protected void internalFire() {
        Ship ship = getShip();
        for(int i=0;i<NUM_SHOTS;i++){
            Bullet b = Pools.obtain(BlasterBullet.class);
            b.init();
            b.setup(ship.getNoseX(), ship.getNoseY(), ship.dx, ship.dy, ship.getRotation()+ Particle.rand(-ANGLE_RAND, ANGLE_RAND), Particle.rand(40,60));
            b.setDrag(.90f);
            b.setLife(35);
            SpaceScreen.get().addBullet(b);
        }
        ship.dx -= Math.cos(ship.getRotation())*KNOCKBACK;
        ship.dy -= Math.sin(ship.getRotation())*KNOCKBACK;
    }
}
