package com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.weapon;

import com.tann.jamgame.screen.gameScreen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.BlasterBullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.BoringBullet;
import com.tann.jamgame.screen.gameScreen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.util.Particle;

import java.util.regex.Pattern;

public class Blaster extends Weapon {
    public Blaster() {
        super(40);
    }

    static final int NUM_SHOTS=20;
    static final float ANGLE_RAND = .3f;


    @Override
    protected void internalFire() {
        Ship ship = getShip();
        for(int i=0;i<NUM_SHOTS;i++){
            Bullet b = new BlasterBullet();
            b.setup(ship.getNoseX(), ship.getNoseY(), ship.dx, ship.dy, ship.getRotation()+ Particle.rand(-ANGLE_RAND, ANGLE_RAND), Particle.rand(40,60));
            b.setDrag(.90f);
            b.setLife(35);
            SpaceScreen.get().addBullet(b);
        }
    }
}
