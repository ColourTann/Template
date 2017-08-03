package com.tann.jamgame.screen.spaceScreen.ship.weapons.weapon;

import com.badlogic.gdx.utils.Pools;
import com.tann.jamgame.screen.spaceScreen.SpaceScreen;
import com.tann.jamgame.screen.spaceScreen.ship.Ship;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.BoringBullet;
import com.tann.jamgame.screen.spaceScreen.ship.weapons.bullet.Bullet;
import com.tann.jamgame.util.Maths;
import com.tann.jamgame.util.Particle;
import com.tann.jamgame.util.Sounds;

public class DoubleShot extends Weapon {
    public DoubleShot() {
        super(30);
    }

    @Override
    protected void internalFire() {
        Ship ship = getShip();
        Sounds.playSound(Sounds.pow_little, .07f, Particle.rand(1, 1.2f), Maths.v.set(ship.getX(), ship.getY()));
        float dist = ship.getHeight()/2;
        float bXDiff = (float) (Math.cos(ship.getRotation()+Math.PI/2)*dist);
        float bYDiff = (float) (Math.sin(ship.getRotation()+Math.PI/2)*dist);
        for(int i=0;i<2;i++){
            Bullet b = BoringBullet.getInstance();
            b.init();
            b.type = friend? Bullet.BulletType.Friendly: Bullet.BulletType.Enemy;
            int mult = i*2-1;
            b.setup(ship.getNoseX()+bXDiff*mult, ship.getNoseY()+bYDiff*mult, ship.dx, ship.dy, ship.getRotation(), 22);
            SpaceScreen.get().addBullet(b);
        }
    }

    @Override
    public String getName() {
        return "DoubleShot";
    }

}
